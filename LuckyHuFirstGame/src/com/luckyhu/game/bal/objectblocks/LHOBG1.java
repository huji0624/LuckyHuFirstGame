package com.luckyhu.game.bal.objectblocks;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.gameobject.LHWormHoleObject;

public class LHOBG1 extends LHOBGBase {

	@Override
	public void gen(World world) {
		// TODO Auto-generated method stub
		Circle A = new Circle(40, 40, 20);
		Circle B = new Circle(155,40,40);
		mArray.add(new LHWormHoleObject(world, A, B));
	}

	@Override
	public Vector2 blockSize() {
		// TODO Auto-generated method stub
		return new Vector2(0, 95);
	}
}
