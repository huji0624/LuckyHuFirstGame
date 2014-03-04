package com.luckyhu.game.framework.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;

public class LHGame implements ApplicationListener{

	private Screen mCurrentScreen;
	
	public static void setCurrentSceen(Screen screen){
		LHGame game =(LHGame) Gdx.app.getApplicationListener();
		game.setScreen(screen);
	}
	
	private void setScreen(Screen screen){
		mCurrentScreen = screen;
		mCurrentScreen.show();
	}
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		mCurrentScreen.resize(width, height);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    float delta = Gdx.graphics.getDeltaTime();
	    if(mCurrentScreen!=null)
	    	mCurrentScreen.render(delta);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		mCurrentScreen.pause();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		mCurrentScreen.resume();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		mCurrentScreen.dispose();
	}

}
