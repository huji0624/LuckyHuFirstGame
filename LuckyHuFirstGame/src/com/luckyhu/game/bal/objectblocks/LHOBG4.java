package com.luckyhu.game.bal.objectblocks;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.gameobject.LHRectObject;


public class LHOBG4 extends LHOBGBase {

	@Override
	public void gen(World world) {
		// TODO Auto-generated method stub
		mArray.add(new LHRectObject(world, new Rectangle(lenReToWidth(0.1f), 100, lenReToWidth(0.8f), 10),
				1.9f, 3.4f));
	}
	
	@Override
	public Vector2 blockSize() {
		// TODO Auto-generated method stub
		return new Vector2(0, getWidth());
	}

}
