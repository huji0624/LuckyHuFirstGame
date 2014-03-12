package com.luckyhu.game.bal.objectblocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.luckyhu.game.bal.gameobject.LHBallGameObject;

public interface LHObjectBlockGenerator {
	public Array<LHBallGameObject> generate(World world,float width,float height);
	public Vector2 blockSize();
}
