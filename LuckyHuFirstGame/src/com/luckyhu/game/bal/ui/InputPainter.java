package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class InputPainter extends InputAdapter{
	
	private Vector2 mStartP;
	private Vector2 mEndP;
	private Body mBody;
	private World mWolrd;
	
	public InputPainter(World world){
		this.mWolrd = world;
		
		Gdx.input.setInputProcessor(this);
	}
	
	public void render(ShapeRenderer render, float delta,MainBall ball) {
		// TODO Auto-generated method stub
		if(Gdx.input.justTouched()){
			mStartP = new Vector2(Gdx.input.getX(), getTouchY());
			mEndP = new Vector2(mStartP);
		}
		
		if(Gdx.input.isTouched()){
			mEndP = new Vector2(Gdx.input.getX(), getTouchY());
		}
		
		if(mStartP!=null){			
			render.begin(ShapeType.Filled);
			render.triangle(x1, y1, x2, y2, x3, y3)
			render.end();
		}
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set((mEndP.x+mStartP.x)/2, (mEndP.y+mStartP.y)/2);
		
		if(mBody!=null)
			mWolrd.destroyBody(mBody);
		mBody = mWolrd.createBody(bd);
		
		float tan = (mEndP.y - mStartP.y)/(mEndP.x - mStartP.x);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(10, 5, new Vector2(0, 0), (float)Math.atan(tan));
		mBody.createFixture(shape, 0);
		shape.dispose();
		
		return true;
	}
	
	private float getTouchY(){
		return Gdx.graphics.getHeight() - Gdx.input.getY();
	}
	
}
