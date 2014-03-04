package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.World;

public class InputPainter{
	
	private Vector2 mStartP;
	private Vector2 mEndP;
	private Body mBody;
	private World mWolrd;
	
	public InputPainter(World world){
		this.mWolrd = world;
	}
	
	public void render(ShapeRenderer render, float delta,MainBall ball) {
		// TODO Auto-generated method stub
		if(Gdx.input.justTouched()){
			mStartP = new Vector2(Gdx.input.getX(), getTouchY());
			mEndP = new Vector2(mStartP.x+100, mStartP.y+100);
			
			BodyDef bd = new BodyDef();
			bd.type = BodyType.StaticBody;
//			bd.position.set((mEndP.x-mStartP.x)/2, (mEndP.y-mEndP.y)/2);
			
			if(mBody!=null)
				mWolrd.destroyBody(mBody);
			mBody = mWolrd.createBody(bd);
			
			EdgeShape shape = new EdgeShape();
			shape.set(mStartP, mEndP);
			mBody.createFixture(shape, 0);
			shape.dispose();
		}
		
		if(Gdx.input.isTouched()){
//			mEndP.set(Gdx.input.getX(), getTouchY());
//			mBody.setTransform(bd.position.x, bd.position.y, 0);
		}
		
		if(mStartP!=null){			
			render.begin(ShapeType.Line);
//			render.line(mStartP.x, mStartP.y, mEndP.x, mEndP.y);
			render.end();
		}
	}
	
	private float getTouchY(){
		return Gdx.graphics.getHeight() - Gdx.input.getY();
	}
	
}
