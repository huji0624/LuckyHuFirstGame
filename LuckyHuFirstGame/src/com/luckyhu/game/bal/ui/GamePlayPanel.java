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
	private Texture mPlay;
	private Texture mRank;

	/**
	 * center.
	 * @param x
	 * @param y
	 */
	public GamePlayPanel(float x,float y,boolean shownew) {
		super();
		float width = Gdx.graphics.getWidth()-40;
		float height = Gdx.graphics.getHeight()/3;
		mPanel = new Texture("data/panel.png");
		NinePatch nine = new NinePatch(mPanel,4,4,2,2);
		setBounds(x-width/2, y-height/2, width, height);
		
		Image background = new Image(nine);
		background.setPosition(0, 0);
		background.setSize(width, height);
		addActor(background);
		
		Preferences pre = Gdx.app.getPreferences("record");
		int best = pre.getInteger("best");
		
		Label label = new Label(shownew?("Best "+best+"(new)"):("Best "+best), new LabelStyle(new BitmapFont(), Color.BLACK));
		label.setPosition(getWidth()/2-label.getWidth()/2, getHeight()-50);
		addActor(label);
		
		mPlay = addButton("data/play.png", new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				LHGameCache.loadSound("data/click.wav").play();
				LHGame.setCurrentSceen(new LHMainScreen());
			}
		}, 50);
		
		mRank = addButton("data/rank.png", new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
			}
		}, 200);
	}
	
	private Texture addButton(String path,ClickListener listener,float x){
		Texture t = new Texture(path);
		TextureRegion trup = new TextureRegion(t, 0, 0, 132, 128);
		TextureRegion trdown = new TextureRegion(t, 132, 0, 128, 128);
		TextureRegionDrawable trdup = new TextureRegionDrawable(trup);
		TextureRegionDrawable trddown = new TextureRegionDrawable(trdown);
		Button button = new Button(new ButtonStyle(trdup, trddown, null));
		button.addListener(listener);
		float bw = 66;
		float bh = 64;
		button.setBounds(x, getHeight()/2 - bh/2,bw, bh);
		addActor(button);
		return t;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		mPanel.dispose();
		mPlay.dispose();
		mRank.dispose();
	}

}
