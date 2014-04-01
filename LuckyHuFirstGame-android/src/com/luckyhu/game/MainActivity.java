package com.luckyhu.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.luckyhu.game.bal.LHBallGame;
import com.luckyhu.game.framework.game.LHGame;
import com.luckyhu.game.framework.game.util.LHADable;

public class MainActivity extends AndroidApplication implements LHADable{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        
        LHGame game = new LHBallGame();
        game.adImp =this;
        initialize(game, cfg);
    }
}