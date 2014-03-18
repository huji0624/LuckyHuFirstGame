package com.luckyhu.game.bal.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.framework.game.util.PolyLinePath;

public class LHCircleMoveObject extends LHCircleObject{

	private Sprite mSprite;
	private Texture mTexture;
	
	private PolyLinePath mPath;
	private float mSpeed;
	private boolean mReverse;
	
	private float mLoc = 0;
	
	public LHCircleMoveObject(World world, Circle circle,float speed,Vector2[] path,boolean reverse) {
		super(world, circle);
		// TODO Auto-generated constructor stub
		this.tag = -1;
		
		mTexture = new Texture("data/badguy.png");
		mSprite = new Sprite(mTexture);
		mSprite.setPosition(circle.x-circle.radius, circle.y-circle.radius);
		mSprite.setSize(circle.radius*2, circle.radius*2);
		mSprite.setOrigin(circle.radius, circle.radius);
		
		mPath = new PolyLinePath(path);
		mReverse = reverse;
		mSpeed = speed;
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer render, float delta) {
		// TODO Auto-generated method stub
		if(mPath!=null){			
			Vector2 po = new Vector2();
			mPath.valueAt(po, mLoc);
			
			Vector2 dire = new Vector2(po.x-circle.x, po.y-circle.y);
			mSprite.setRotation(dire.angle()-90);
			
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
		
		mSprite.draw(batch);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		mTexture.dispose();
	}
	
	@Override
	public void moveBy(float dx, float dy) {
		// TODO Auto-generated method stub
		super.moveBy(dx, dy);
		mSprite.setPosition(circle.x-circle.radius, circle.y-circle.radius);
		for (int i = 0; i < mPath.points.length; i++) {
			Vector2 ve = mPath.points[i];
			ve.add(dx, dy);
		}
	}
	
	@Override
	public void moveTo(float x, float y) {
		// TODO Auto-generated method stub
		super.moveTo(x, y);
		mSprite.setPosition(circle.x-circle.radius, circle.y-circle.radius);
	}

}
