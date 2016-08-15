package com.noel.imageprinter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends Activity {
	private static int SPLASH_TIME_OUT = 3000;
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent transIntent = new Intent(SplashActivity.this, ImageViewActivity.class);
				startActivity(transIntent);
				finish();
				SplashActivity.this.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
			}
		}, SPLASH_TIME_OUT);
	}
}
