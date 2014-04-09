package com.luckyhu.game.bal;

import com.luckyhu.game.bal.ui.LHStartScreen;
import com.luckyhu.game.framework.game.LHGame;
import com.luckyhu.game.framework.game.util.LHGameCache;

public class LHBallGame extends LHGame{
	@Override
	public void create() {
		// TODO Auto-generated method stub
		super.create();
		LHGameCache.initSound("data/hit.ogg");
		LHGameCache.initSound("data/click.wav");
		LHGameCache.initMusic("data/walk3.wav");
		setCurrentSceen(new LHStartScreen());
	}
}
