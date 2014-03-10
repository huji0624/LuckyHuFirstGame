package com.luckyhu.game.bal.objectblocks;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.luckyhu.game.framework.game.engine.LHGameObject;
import com.luckyhu.game.framework.game.util.LHLogger;

public abstract class LHOBGBase implements LHObjectBlockGenerator{

	protected Array<LHGameObject> mArray;
	protected float width;
	protected float height;
	
	@Override
	public Array<LHGameObject> generate(World world, float width, float height) {
		// TODO Auto-generated method stub
		LHLogger.logD("genBlock "+this);
		mArray = new Array<LHGameObject>();
		this.width = width;
		this.height = height;
		gen(world);
		return mArray;
	}

	public abstract void gen(World world) ;
}
