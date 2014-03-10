package com.luckyhu.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.luckyhu.game.bal.LHBallGame;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "LuckyHuFirstGame";
		cfg.useGL20 = false;
		cfg.width = 320;
		cfg.height = 480;
		
		new LwjglApplication(new LHBallGame(), cfg);
	}
}
