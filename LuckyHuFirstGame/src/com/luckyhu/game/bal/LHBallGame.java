package com.luckyhu.game.bal;

import com.luckyhu.game.bal.ui.LHStartScreen;
import com.luckyhu.game.framework.game.LHGame;
import com.luckyhu.game.framework.game.util.LHGameCache;

public class LHBallGame extends LHGame{
	@Override
	public void create() {
		// TODO Auto-generated method stub
		LHGameCache.initSound("data/hit.ogg");
		LHGameCache.initSound("data/click.wav");
		LHGameCache.initSound("data/walk1.wav");
		LHGameCache.initSound("data/walk2.wav");
		LHGameCache.initSound("data/walk3.wav");
		setCurrentSceen(new LHStartScreen());
	}
}
