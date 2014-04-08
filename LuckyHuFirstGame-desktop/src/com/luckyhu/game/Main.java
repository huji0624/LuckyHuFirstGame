package com.luckyhu.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.luckyhu.game.bal.LHBallGame;
import com.luckyhu.game.framework.game.util.LHADable;

public class Main implements LHADable{
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "LuckyHuFirstGame";
		cfg.useGL20 = false;
		cfg.width = 320;
		cfg.height = 480;
		
		LHBallGame.adImp = new Main();
		
		new LwjglApplication(new LHBallGame(), cfg);
	}

	@Override
	public void showAd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideAd() {
		// TODO Auto-generated method stub
		
	}
}
