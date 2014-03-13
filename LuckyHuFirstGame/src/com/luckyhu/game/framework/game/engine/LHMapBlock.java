package com.luckyhu.game.framework.game.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class LHMapBlock implements Disposable{
	
	private Texture mTexture;
	
	private float x;
	private float y;
	
	public LHMapBlock(String path,int left,int right,int top,int bottom){
		mTexture = new Texture(path);
	}
	
	public void render(SpriteBatch batch,float delta){
		batch.draw(mTexture, x, y);
	}
	
	public void moveBy(float dx,float dy){
		
	}
	
	public void moveTo(float x,float y){
		this.x = x;
		this.y = y;
	}
	
	public float getTop(){
		return this.y+Gdx.graphics.getHeight();
	}
	
	public Vector2 blockSize(){
		return new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		mTexture.dispose();
	}
}
