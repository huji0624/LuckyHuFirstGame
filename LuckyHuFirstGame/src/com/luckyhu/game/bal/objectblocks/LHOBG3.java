package com.luckyhu.game.bal.objectblocks;

import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.ui.LHPolygonObject;

public class LHOBG3 extends LHOBGBase {

	@Override
	public void gen(World world) {
		// TODO Auto-generated method stub
		float ves[] = { 100, 100, 110, 180, 200, 200 };
		mArray.add(new LHPolygonObject(world, ves));
	}

}
