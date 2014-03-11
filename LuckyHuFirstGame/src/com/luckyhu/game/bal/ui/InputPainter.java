package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.framework.game.util.LHLogger;

public class InputPainter {

	private Vector2 mStartP;
	private Vector2 mEndP;
	private Body mBody;
	private World mWolrd;
	
	private float mOffset;
	
	public MainBall mainBall;

	static private float Box_Height = 10;
	
	private boolean touch = false;

	public InputPainter(World world) {
		this.mWolrd = world;
	}
	
	public void setOffset(float offset){
		mOffset = offset;
	}

	public void render(ShapeRenderer render, float delta) {
		// TODO Auto-generated method stub
		if (Gdx.input.justTouched()) {
			touch = true;
			
			mStartP = new Vector2(Gdx.input.getX(), getTouchY());
			mEndP = new Vector2(mStartP);
			
			if (mBody != null){
				mWolrd.destroyBody(mBody);
				mBody = null;
			}
		}

		if (Gdx.input.isTouched()) {
			touch = true;
			mEndP = new Vector2(Gdx.input.getX(), getTouchY());
		}else{
			if (touch){
				if(mBody != null){
					mWolrd.destroyBody(mBody);
					mBody = null;
				}
				touchUp();
				touch = false;
			}
		}

		if (mStartP != null) {
			render.begin(ShapeType.Filled);
			Vector2 an = new Vector2(mEndP.x-mStartP.x, mEndP.y-mStartP.y);
			render.rect(mStartP.x, mStartP.y,mStartP.dst(mEndP),
					Box_Height, 0, 0, an.angle());
			render.end();
		}
	}

	public boolean touchUp() {
		// TODO Auto-generated method stub
		
		if(mEndP.equals(mStartP)){
			return false;
		}
		
		if(checkOverLap()){
			LHLogger.logD("Painter and MainBall overlaps!");
			mStartP = null;
			return false;
		}
		
		Vector2 an = new Vector2(mEndP.x-mStartP.x, mEndP.y-mStartP.y);
		float anR = an.angle();
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		Vector2 center = new Vector2((mEndP.x + mStartP.x) / 2,	 (mEndP.y + mStartP.y) / 2);
		an.setAngle(anR+90);
		center.add(an.nor().scl(Box_Height/2));
		bd.position.set(center);

		mBody = mWolrd.createBody(bd);
		LHLogger.logD("Painter Create Body.");

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(mStartP.dst(mEndP)/2, Box_Height / 2,
				new Vector2(0, 0),(float)Math.toRadians(anR));
		FixtureDef fix = new FixtureDef();
		fix.shape = shape;
		fix.friction = 0.0f;
		mBody.createFixture(fix);
		shape.dispose();
		return true;
	}
	
	private boolean checkOverLap(){
		
		Vector2 an = new Vector2(mEndP.x-mStartP.x, mEndP.y-mStartP.y);
		float anR = an.angle();
		an.nor().setAngle(anR+90);
		
		Vector2 p3 = mEndP.cpy().add(an);
		
		anR = an.angle();
		an.setAngle(anR+90);
		
		Vector2 p4 = p3.cpy().add(an);
		
		Vector2 point = new Vector2(mainBall.circle.x, mainBall.circle.y);
		float dis = mainBall.circle.radius;
		
		if(Intersector.distanceLinePoint(mStartP, mEndP	, point)<=dis){
			return true;
		}else if(Intersector.distanceLinePoint(mEndP, p3	, point)<=dis){
			return true;
		}else if(Intersector.distanceLinePoint(p3, p4	, point)<=dis){
			return true;
		}else if(Intersector.distanceLinePoint(p4, mStartP	, point)<=dis){
			return true;
		}
		
		return false;
	}

	private float getTouchY() {
		return Gdx.graphics.getHeight() - Gdx.input.getY() + mOffset;
	}

}
