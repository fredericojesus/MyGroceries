package com.example.mygroceries;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashActivity extends Activity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 5000;
	private int i = 1;
	private static int NUMBER_LOADING_IMAGES = 12;
	private static int TIME_CHANGE_IMAGE = 50; // miliseconds

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		final ImageView loading = (ImageView) findViewById(R.id.splash_loading1);

		Runnable r = new Runnable() {

			@Override
			public void run() {

				if (i == 0)
					loading.setImageResource(R.drawable.loading1);
				else if (i == 1)
					loading.setImageResource(R.drawable.loading2);
				else if (i == 2)
					loading.setImageResource(R.drawable.loading3);
				else if (i == 3)
					loading.setImageResource(R.drawable.loading4);
				else if (i == 4)
					loading.setImageResource(R.drawable.loading5);
				else if (i == 5)
					loading.setImageResource(R.drawable.loading6);
				else if (i == 6)
					loading.setImageResource(R.drawable.loading7);
				else if (i == 7)
					loading.setImageResource(R.drawable.loading8);
				else if (i == 8)
					loading.setImageResource(R.drawable.loading9);
				else if (i == 9)
					loading.setImageResource(R.drawable.loading10);
				else if (i == 10)
					loading.setImageResource(R.drawable.loading11);
				else if (i == 11)
					loading.setImageResource(R.drawable.loading12);
				loading.postDelayed(this, TIME_CHANGE_IMAGE);
				i++;
				i %= NUMBER_LOADING_IMAGES;

			}
		};
		loading.postDelayed(r, 500);
		
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start app main activity
				Intent i = new Intent(SplashActivity.this, MenuActivity.class);
				startActivity(i);
				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	}
}
