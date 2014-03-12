package com.luckyhu.game.framework.game.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public abstract class LHGameObject implements Disposable{
	
	public int tag = 0;
	protected World mWorld;
	
	public LHGameObject(World world){
		mWorld = world;
	}
	
	public  void render(SpriteBatch batch,float delta){}
	public  void render(ShapeRenderer render,float delta){}
	public abstract void moveBy(float dx,float dy);
	public void moveTo(float x,float y){}
	
	public void willRemove(){};
	public void didRemove(){};
	
	public abstract float getTop();
}
