package com.luckyhu.game.bal.objectblocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.luckyhu.game.bal.gameobject.LHBallGameObject;
import com.luckyhu.game.framework.game.util.LHLogger;

public abstract class LHOBGBase implements LHObjectBlockGenerator{

	protected Array<LHBallGameObject> mArray;
	private float width;
	private float height;
	
	@Override
	public Array<LHBallGameObject> generate(World world, float width, float height) {
		// TODO Auto-generated method stub
		LHLogger.logD("genBlock "+this);
		mArray = new Array<LHBallGameObject>();
		this.width = width;
		this.height = height;
		
		gen(world);
		return mArray;
	}
	
	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
	
	public float lenReToWidth(float rellen){
		return getWidth()*rellen;
	}
	
	public float lenReToHeight(float rellen){
		return getHeight()*rellen;
	}

	
	@Override
	public Vector2 blockSize() {
		// TODO Auto-generated method stub
		return new Vector2(this.width, this.height);
	}

	public abstract void gen(World world) ;
}
