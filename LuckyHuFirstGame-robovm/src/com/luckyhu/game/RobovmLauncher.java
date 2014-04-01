package com.luckyhu.game;

import org.robovm.cocoatouch.foundation.NSAutoreleasePool;
import org.robovm.cocoatouch.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.luckyhu.game.bal.LHBallGame;
import com.luckyhu.game.framework.game.LHGame;
import com.luckyhu.game.framework.game.util.LHADable;

public class RobovmLauncher extends IOSApplication.Delegate implements LHADable{
	@Override
	protected IOSApplication createApplication() {
		IOSApplicationConfiguration config = new IOSApplicationConfiguration();
		config.orientationLandscape = true;
		config.orientationPortrait = false;
		LHGame game = new LHBallGame();
		game.adImp = this;
		return new IOSApplication(game, config);
	}

	public static void main(String[] argv) {
		NSAutoreleasePool pool = new NSAutoreleasePool();
		UIApplication.main(argv, null, RobovmLauncher.class);
		pool.drain();
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
