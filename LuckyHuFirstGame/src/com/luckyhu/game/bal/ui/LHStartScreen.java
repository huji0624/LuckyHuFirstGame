package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class LHStartScreen implements Screen{

	private Stage mStage;
	private Texture mBackTexture;
	private GamePlayPanel mPanel;
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		mStage.draw();
		mStage.act(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Texture.setEnforcePotImages(false);
		
		mStage = new Stage();
		Gdx.input.setInputProcessor(mStage);
		
		mBackTexture = new Texture("data/background.png");
		Image background = new Image(mBackTexture);
		background.setBounds(0, 0, mStage.getWidth(), mStage.getHeight());
		mStage.addActor(background);
		
		mPanel = new GamePlayPanel(mStage.getWidth()/2,mStage.getHeight()/2,false);
		mStage.addActor(mPanel);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		mStage.dispose();
		mBackTexture.dispose();
		mPanel.dispose();
	}

}
