package com.luckyhu.game.bal.objectblocks;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.gameobject.LHBlackHoleObject;
import com.luckyhu.game.bal.gameobject.LHWhiteHoleObject;
import com.luckyhu.game.bal.ui.MainBall;


public class LHOBG6 extends LHOBGBase {

	@Override
	public void gen(World world) {
		// TODO Auto-generated method stub
		mArray.add(new LHBlackHoleObject(world, new Circle(50, 400, 30),MainBall.mainBall));
		mArray.add(new LHWhiteHoleObject(world, new Circle(200, 50, 30),MainBall.mainBall));
	}

}
