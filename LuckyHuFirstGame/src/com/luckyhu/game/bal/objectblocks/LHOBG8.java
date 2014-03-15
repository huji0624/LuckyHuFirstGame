package com.luckyhu.game.bal.objectblocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.gameobject.LHPolygonMovingObject;

public class LHOBG8 extends LHOBGBase {

	@Override
	public void gen(World world) {
		// TODO Auto-generated method stub
		float ves[] = { 10, 10, 10, 20, 20, 20, 20, 10 };
		Vector2[] path = {new Vector2(0, 0),new Vector2(60, 400),new Vector2(200, 200)};
		LHPolygonMovingObject pobj = new LHPolygonMovingObject(world, ves, 100, path, true);
		mArray.add(pobj);
		
	}

}
