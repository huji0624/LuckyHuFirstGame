package com.luckyhu.game.bal.objectblocks;

import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.gameobject.LHPolygonObject;

public class LHOBG1 extends LHOBGBase {

	@Override
	public void gen(World world) {
		// TODO Auto-generated method stub
		float ves[] = { 100, 100, 110, 180, 200, 200, 200, 100, 150, 50 };
		mArray.add(new LHPolygonObject(world, ves));
	}

}
