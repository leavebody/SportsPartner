package com.sportspartner.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.sportspartner.R;
import com.sportspartner.util.LoginDBHelper;
import com.sportspartner.util.gcm_notification.RegistrationIntentService;

public class WelcomeActivity extends Activity {
    private Intent intent;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        /**
         * checkPlayServices GCM
         */
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

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

    /**
     * Check whether this device support GCM API
     * @return Whether support or not
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}


