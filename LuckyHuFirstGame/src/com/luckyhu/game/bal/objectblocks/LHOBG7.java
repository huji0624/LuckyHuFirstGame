package com.luckyhu.game.bal.objectblocks;

import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.gameobject.LHBezierMoveObject;
import com.luckyhu.game.bal.gameobject.LHPolygonMovingObject;

public class LHOBG7 extends LHOBGBase {

	@Override
	public void gen(World world) {
		// TODO Auto-generated method stub
		// DEBUG
		float ves[] = { 10, 10, 10, 20, 20, 20, 20, 10 };
		Bezier<Vector2> bez = new Bezier<Vector2>(new Vector2(20, 20),
				new Vector2(150, 100), new Vector2(300, 20));
		LHBezierMoveObject obj = new LHBezierMoveObject(world, ves, 0.5f, bez, true);
		mArray.add(obj);
		
	}

}
