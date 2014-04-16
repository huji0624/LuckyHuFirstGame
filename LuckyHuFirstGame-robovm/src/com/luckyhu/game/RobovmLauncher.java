package com.luckyhu.game;

import org.robovm.apple.coregraphics.CGPoint;
import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSString;
import org.robovm.cocoatouch.uikit.UIViewController;
import org.robovm.bindings.admob.GADAdSizeManager;
import org.robovm.bindings.admob.GADBannerView;
import org.robovm.bindings.admob.GADRequest;
import org.robovm.cocoatouch.foundation.NSAutoreleasePool;
import org.robovm.cocoatouch.uikit.UIApplication;
import org.robovm.objc.annotation.NotImplemented;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.luckyhu.game.bal.LHBallGame;
import com.luckyhu.game.framework.game.LHGame;
import com.luckyhu.game.framework.game.util.LHADable;

public class RobovmLauncher extends IOSApplication.Delegate implements LHADable{
	
	GADBannerView adView;
	
	@Override
	protected IOSApplication createApplication() {
		IOSApplicationConfiguration config = new IOSApplicationConfiguration();
		config.orientationLandscape = true;
		config.orientationPortrait = false;
		LHGame game = new LHBallGame();
		LHGame.adImp = this;
		return new IOSApplication(game, config);
	}

	public static void main(String[] argv) {
		NSAutoreleasePool pool = new NSAutoreleasePool();
		UIApplication.main(argv, null, RobovmLauncher.class);
		pool.drain();
	}
	
	@Override
	@NotImplemented("applicationDidFinishLaunching:")
	public void didFinishLaunching(UIApplication application) {
		// TODO Auto-generated method stub
		super.didFinishLaunching(application);
		
		UIViewController rootViewController = getWindow().getRootViewController();
		
		adView = new GADBannerView(GADAdSizeManager.banner(), new CGPoint(0, 0));
		adView.setHidden(false);
		adView.setRootViewController(rootViewController);
		rootViewController.getView().addSubview(adView);
		GADRequest req = GADRequest.request();
		NSArray<NSString> array = new NSArray<NSString>(new NSString("GAD_SIMULATOR_ID")); 
		req.setTestDevices(array);
		adView.loadRequest(req);
	}

	@Override
	public void showAd() {
		// TODO Auto-generated method stub
		adView.setHidden(false);
	}

	@Override
	public void hideAd() {
		// TODO Auto-generated method stub
		adView.setHidden(true);
	}
}
