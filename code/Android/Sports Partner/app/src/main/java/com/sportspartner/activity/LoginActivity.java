package com.sportspartner.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.sportspartner.R;

import com.sportspartner.service.UserService;
import com.sportspartner.util.ActivityCallBack;
import com.sportspartner.service.serviceresult.BooleanResult;
import com.sportspartner.util.gcm_notification.QuickstartPreferences;
import com.sportspartner.util.gcm_notification.RegistrationIntentService;

import static com.sportspartner.util.gcm_notification.RegistrationIntentService.*;


public class LoginActivity extends AppCompatActivity {
    private static final String FILE_CHARSET = "utf-8";
    EditText emailField;
    EditText passwordField;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    private TextView mInformationTextView;
    private boolean isReceiverRegistered;

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

        /*mRegistrationProgressBar = (ProgressBar) findViewById(R.id.progressBar2);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    mInformationTextView.setText("GCM");//getString(R.string.gcm_send_message));
                } else {
                    mInformationTextView.setText("ERROR");//getString(R.string.token_error_message));
                }
            }
        };
        mInformationTextView = (TextView) findViewById(R.id.textView123);

        // Registering BroadcastReceiver
        registerReceiver();*/

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
            Intent intent = new Intent(context, ProfileActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Onclick listener for sign up button
     * @param v
     */
    public void signup(View v){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);

    }
}
