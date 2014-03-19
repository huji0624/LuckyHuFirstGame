package com.luckyhu.game.bal.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.framework.game.util.PolyLinePath;

public class LHPolygonMovingObject extends LHPolygonObject{

	private PolyLinePath mPath;
	private float mSpeed;
	private boolean mReverse;
	
	private float mLoc = 0;
	
	public LHPolygonMovingObject(World world, float[] vertices) {
		super(world, vertices);
		// TODO Auto-generated constructor stub
	}
	
	public LHPolygonMovingObject(World world, float[] vertices,float speed,Vector2[] path,boolean reverse) {
		super(world, vertices);
		// TODO Auto-generated constructor stub
		mSpeed = speed;
		
//		Vector2 vector = new Vector2();
//		getPolygon().getBoundingRectangle().getCenter(vector);
//		for (int i = 0; i < path.length; i++) {
//			path[i].add(vector);
//		}
		
		mPath = new PolyLinePath(path);
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
			
			if(mLoc>mPath.getLength() || mLoc<0.0f){
				if(mReverse){
					mSpeed *= -1;
				}else{
					mLoc = 0.0f;
				}
			}
		}
		
//		mSRender.begin(ShapeType.Line);
//		mSRender.setColor(Color.GREEN);
//		for (int i = 0; i < mPath.points.length-1; i++) {
//			Vector2 s = mPath.points[i];
//			Vector2 e = mPath.points[i+1];
//			mSRender.line(s, e);
//		}
//		mSRender.end();
		
		super.render(batch, mSRender, delta);
	}
	
	@Override
	public void moveBy(float dx, float dy) {
		// TODO Auto-generated method stub
		super.moveBy(dx, dy);
		for (int i = 0; i < mPath.size(); i++) {
			Vector2 ve = mPath.get(i);
			ve.add(dx, dy);
		}
	}
}
