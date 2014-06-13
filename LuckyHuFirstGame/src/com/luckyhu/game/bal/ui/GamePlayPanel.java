package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.luckyhu.game.framework.game.LHGame;
import com.luckyhu.game.framework.game.util.LHGameCache;

public class GamePlayPanel extends Group implements Disposable {

	private Texture mPanel;

	/**
	 * center.
	 * @param x
	 * @param y
	 */
	public GamePlayPanel(float x,float y,boolean shownew) {
		super();
		float width = Gdx.graphics.getWidth()/5*4;
		float height = Gdx.graphics.getHeight()/3;
		mPanel = new Texture("data/panel.png");
		NinePatch nine = new NinePatch(mPanel,10,10,10,10);
		setBounds(x-width/2, y-height/2, width, height);
		
		Image background = new Image(nine);
		background.setPosition(0, height/2);
		background.setSize(width, height/2);
		addActor(background);
		
		Preferences pre = Gdx.app.getPreferences("record");
		int best = pre.getInteger("best");
		
		float dy = height/4*1.3f;
		
		BitmapFont font = new BitmapFont();
		font.setScale(Gdx.graphics.getDensity()*1.5f);
		Label label = new Label(best+"", new LabelStyle(font, Color.BLACK));
		label.setPosition(getWidth()/2-label.getWidth()/2, getHeight()-dy);
		addActor(label);
		
		float dx = width/10;
		Label labelbes = new Label("Best", new LabelStyle(font, Color.BLUE));
		labelbes.setPosition(label.getX()-labelbes.getWidth()-dx, label.getY());
		addActor(labelbes);
		
		if (shownew) {
			Label labelnew = new Label("New!", new LabelStyle(font, Color.RED));
			labelnew.setPosition(label.getX()+label.getWidth()+dx, label.getY());
			addActor(labelnew);
		}
		
		addButton("data/play", new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				LHGameCache.loadSound("click").play();
				LHGame.setCurrentSceen(new LHMainScreen());
			}
		}, getWidth()/2);
		
//		addButton("data/rank", new ClickListener(){
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				// TODO Auto-generated method stub
//				super.clicked(event, x, y);
//				LHGame.adImp.hideAd();
//			}
//		}, getWidth()/4*3);
	}
	
	private void addButton(String path,ClickListener listener,float x){
		TextureRegionDrawable trdup = new TextureRegionDrawable(new TextureRegion(LHGameCache.loadTexture(path+".png")));
		ButtonStyle style = new ButtonStyle(trdup, null, null);
		style.pressedOffsetY = 5;
		Button button = new Button(style);
		button.addListener(listener);
		float bw = Gdx.graphics.getWidth()/5;
		float bh = bw;
		button.setBounds(x-bw/2, 0,bw, bh);
		addActor(button);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		mPanel.dispose();
	}

}
