package com.sportspartner.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.sportspartner.R;

import com.sportspartner.service.UserService;
import com.sportspartner.util.ActivityCallBack;
import com.sportspartner.service.serviceresult.BooleanResult;
import com.sportspartner.util.LoginDBHelper;
import com.sportspartner.util.gcm_notification.RegistrationIntentService;


public class LoginActivity extends AppCompatActivity {
    private static final String FILE_CHARSET = "utf-8";
    EditText emailField;
    EditText passwordField;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    /**
     * Load the LoginActivity
     * Find Widgets by Id
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = (EditText)findViewById(R.id.loginEmail);
        passwordField = (EditText)findViewById(R.id.password);

        /**
         * checkPlayServices GCM
         */
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }


    }

    /*@Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }*/

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

    /**
     * Onclick listener for login button
     * @param v
     */
    public void login(View v){
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        //Get device Token from GCM
        String token = RegistrationIntentService.getToken();
        System.out.println(token);

        UserService.login(this, email, password, token, new ActivityCallBack(){
            @Override
            public void onSuccess(BooleanResult booleanResult) {
                loginHandler(booleanResult);
            }
        });
    }

    /**
     * Handel the login result from the server
     * Goto the profile page if success
     * @param booleanResult
     */
    private void loginHandler(BooleanResult booleanResult) {
        // handle the result here
        String message = booleanResult.getMessage();
        if (message != null) {
            Toast toast = Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG);
            toast.show();
        }
        if (booleanResult.isStatus()) {
            // TODO link to the main page activity of this user
            Context context = getApplicationContext();

            //get userId from SQLite
            LoginDBHelper dbHelper = LoginDBHelper.getInstance(context);
            String email = dbHelper.getEmail();

            //go to profile activity
            Intent intent = new Intent(context, HomeActivity.class);
            //intent.putExtra("userId", email);
            startActivity(intent);
            onDestroy();
        }
    }

    /**
     * Onclick listener for sign up button
     * @param v
     */
    public void signup(View v){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
        onDestroy();
    }

    /**
     * OnDestroy Method of this Activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
