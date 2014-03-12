package com.luckyhu.game.bal.objectblocks;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.gameobject.LHRectObject;


public class LHOBG5 extends LHOBGBase {

	@Override
	public void gen(World world) {
		// TODO Auto-generated method stub
		float width = 260;
		mArray.add(new LHRectObject(world, new Rectangle(0, 10, width, 20), 0));
		mArray.add(new LHRectObject(world, new Rectangle(getWidth()-width, 90, width, 20), 0));
		mArray.add(new LHRectObject(world, new Rectangle(0, 170, width, 20), 0));
		mArray.add(new LHRectObject(world, new Rectangle(getWidth()-width, 250, width, 20), 0));
	}

}
