package com.sportspartner.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.sportspartner.R;
import com.sportspartner.util.LoginDBHelper;

public class WelcomeActivity extends Activity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //display the logo during 5 seconds,
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            /**
             * Check whether user has already logged in on this device
             * If yes jump to the Home Page
             * Else jump to the Login Page
             */
            @Override
            public void onFinish() {
                LoginDBHelper dbHelper = LoginDBHelper.getInstance(WelcomeActivity.this);
                // if is logged in
                if (dbHelper.isLoggedIn()) {
                    intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                } else {
                    intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                onDestroy();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}


