package com.luckyhu.game.bal.objectblocks;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.ui.LHRectObject;


public class LHOBG2 extends LHOBGBase {

	@Override
	public void gen(World world) {
		// TODO Auto-generated method stub
		mArray.add(new LHRectObject(world, new Rectangle(100, 200, 100, 20), 1.9f));
	}
	
}
