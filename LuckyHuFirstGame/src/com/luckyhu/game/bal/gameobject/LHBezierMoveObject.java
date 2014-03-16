package com.luckyhu.game.bal.gameobject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class LHBezierMoveObject extends LHPolygonObject{

	private float mSpeed;
	
	private Bezier<Vector2> mPath;
	private boolean mReverse = false;
	
	private float mLoc = 0;
	
	public LHBezierMoveObject(World world, float[] vertices) {
		super(world, vertices);
		// TODO Auto-generated constructor stub
		mColor = Color.GREEN;
	}
	
	public LHBezierMoveObject(World world, float[] vertices,float speed,Bezier<Vector2> bez,boolean reverse) {
		super(world, vertices);
		// TODO Auto-generated constructor stub
		mPath = bez;
		mSpeed = speed;
		mReverse = reverse;
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer mSRender, float delta) {
		// TODO Auto-generated method stub
		if(mPath!=null){			
			Vector2 po = new Vector2();
			mPath.valueAt(po, mLoc);
			moveTo(po.x, po.y);
			mLoc += mSpeed * delta;
			
			if(mLoc>1.0f || mLoc<0.0f){
				if(mReverse){
					mSpeed *= -1;
				}else{
					mLoc = 0.0f;
				}
			}
		}
		
		super.render(batch, mSRender, delta);
	}
	
	@Override
	public void moveBy(float dx, float dy) {
		// TODO Auto-generated method stub
		super.moveBy(dx, dy);
		for (int i = 0; i < mPath.points.size; i++) {
			Vector2 ve = mPath.points.get(i);
			ve.add(dx, dy);
		}
	}

}
