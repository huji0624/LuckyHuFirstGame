package com.luckyhu.game.bal;

import com.luckyhu.game.bal.ui.LHMainScreen;
import com.luckyhu.game.framework.game.LHGame;

public class LHBallGame extends LHGame{
	@Override
	public void create() {
		// TODO Auto-generated method stub
		setCurrentSceen(new LHMainScreen());
	}
}
