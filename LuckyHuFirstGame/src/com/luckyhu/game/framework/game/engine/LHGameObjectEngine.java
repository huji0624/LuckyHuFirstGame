package com.luckyhu.game.framework.game.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class LHGameObjectEngine {
	
	private Array<LHGameObject> mObjects;
	
	public LHGameObjectEngine(){
		mObjects = new Array<LHGameObject>();
	}
	
	public void renderObject(SpriteBatch batch,float delta){
		for (int i = 0; i < mObjects.size; i++) {
			mObjects.get(i).render(batch, delta);
		}
	}
	
}
