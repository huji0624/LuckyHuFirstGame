package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.luckyhu.game.framework.game.LHGame;

public class LHStartScreen implements Screen,MainBallDelegate{

	private Stage mStage;
	private Texture mBackTexture;
	private GamePlayPanel mPanel;
	private MainBall mMainBall;
	private SpriteBatch mBatch;
	private ShapeRenderer mSRender;
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		mStage.draw();
		mStage.act(delta);
		
		mMainBall.render(mBatch, mSRender, delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	private Vector2 lastPoint = null;
	
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
		
		BitmapFont font = new BitmapFont();
		font.setScale(3);
		Label name = new Label("Move On", new LabelStyle(font, Color.WHITE));
		name.setPosition(mStage.getWidth()/2-name.getWidth()/2, mStage.getHeight()+name.getHeight());
		mStage.addActor(name);
		MoveToAction action = new MoveToAction();
		action.setDuration(1);
		action.setPosition(name.getX(),mPanel.getY()+mPanel.getHeight()+5);
		name.addAction(action);
		
		mBatch = new SpriteBatch();
		mSRender = new ShapeRenderer();
		mMainBall = new MainBall(null);
		mMainBall.setMainBallDelegate(this);
		mMainBall.drawPath = false;
		mainBallStopMoving();
		
		LHGame.adImp.showAd();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		LHGame.adImp.hideAd();
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
		mMainBall.initPath();
		MainBall.mainBall = null;
		
		mStage.dispose();
		mBackTexture.dispose();
		mPanel.dispose();
		
		mMainBall.dispose();
		mBatch.dispose();
		mSRender.dispose();
	}

	@Override
	public void mainBallStopMoving() {
		// TODO Auto-generated method stub
		if(lastPoint!=null){
			mMainBall.addPathPoint(lastPoint);
		}
		for (int i = 0; i < 5; i++) {
			float x = MathUtils.random(Gdx.graphics.getWidth());
			float y = MathUtils.random(Gdx.graphics.getHeight());
			Vector2 p = new Vector2(x, y);
			mMainBall.addPathPoint(p);
			lastPoint = p;
		}
	}

}
