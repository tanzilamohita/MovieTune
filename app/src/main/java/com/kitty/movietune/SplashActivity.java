package com.kitty.movietune;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Created by Mohita on 4/29/2017.
 */

public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_TIME = 2500;
    private boolean isBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openLandingActivity();
            }
        }, SPLASH_DISPLAY_TIME);
    }
    protected void openLandingActivity() {
        Intent i = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (isBackPressed) {
            super.onBackPressed();
        }
    }
}
