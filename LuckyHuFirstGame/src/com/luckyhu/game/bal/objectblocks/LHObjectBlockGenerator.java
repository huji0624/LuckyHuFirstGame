package com.luckyhu.game.bal.objectblocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.luckyhu.game.framework.game.engine.LHGameObject;

public interface LHObjectBlockGenerator {
	public Array<LHGameObject> generate(World world,float width,float height);
	public Vector2 blockSize();
}
