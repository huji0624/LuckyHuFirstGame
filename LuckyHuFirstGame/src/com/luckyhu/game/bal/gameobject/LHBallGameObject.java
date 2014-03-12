package com.luckyhu.game.bal.gameobject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.framework.game.engine.LHGameObject;

public abstract class LHBallGameObject extends LHGameObject{

	protected Color mColor = Color.WHITE;
	
	public LHBallGameObject(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}
	
	
}
