package com.luckyhu.game.framework.game.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class LHGameObjectEngine {
	
	private Array<LHGameObject> mObjects;
	
	public LHGameObjectEngine(){
		mObjects = new Array<LHGameObject>();
	}
	
	public void addObject(LHGameObject object){
		mObjects.add(object);
	}
	
	public void addObjects(Array<LHGameObject> objects){
		mObjects.addAll(objects);
	}
	
	public void renderObject(SpriteBatch batch,float delta){
		for (int i = 0; i < mObjects.size; i++) {
			mObjects.get(i).render(batch, delta);
		}
	}
	
	public void renderObject(ShapeRenderer batch,float delta){
		for (int i = 0; i < mObjects.size; i++) {
			mObjects.get(i).render(batch, delta);
		}
	}
	
}
