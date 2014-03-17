package com.luckyhu.game.bal.objectblocks;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.gameobject.LHCircleMoveObject;

public class LHOBG8 extends LHOBGBase {

	@Override
	public void gen(World world) {
		// TODO Auto-generated method stub
		
		Vector2[] path = {new Vector2(0, 0),new Vector2(60, 400),new Vector2(200, 200)};
		LHCircleMoveObject pobj = new LHCircleMoveObject(world, new Circle(100, 100, 30), 100, path, true);
		mArray.add(pobj);
		
	}

}
