package com.luckyhu.game;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.luckyhu.game.bal.LHBallGame;
import com.luckyhu.game.framework.game.LHGame;
import com.luckyhu.game.framework.game.util.LHADable;

public class MainActivity extends AndroidApplication implements LHADable {

	private AdView adView;

	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;

	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (adView == null) {
				return;
			}

			switch (msg.what) {
			case SHOW_ADS: {
				adView.setVisibility(View.VISIBLE);
				break;
			}
			case HIDE_ADS: {
				adView.setVisibility(View.GONE);
				break;
			}
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = false;

		LHGame game = new LHBallGame();
		LHGame.adImp = this;
		// initialize(game, cfg);

		RelativeLayout layout = new RelativeLayout(this);

		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		View libgdxView = initializeForView(game, cfg);
		layout.addView(libgdxView);
		
		adView = new AdView(this, AdSize.BANNER, "test");
		AdRequest request = new AdRequest();
		request.addTestDevice("TEST_DEVICE_ID");
		adView.loadAd(request);
		adView.setVisibility(View.GONE);
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		layout.addView(adView, adParams);

		setContentView(layout);
	}

	@Override
	public void showAd() {
		// TODO Auto-generated method stub
		handler.sendEmptyMessage(SHOW_ADS);
	}

	@Override
	public void hideAd() {
		// TODO Auto-generated method stub
		handler.sendEmptyMessage(HIDE_ADS);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		adView.destroy();
		super.onDestroy();
	}
}