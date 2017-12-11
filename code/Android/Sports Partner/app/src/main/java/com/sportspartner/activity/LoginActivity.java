package com.sportspartner.activity;

/**
 * Created by yujiaxiao on 11/8/17.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.service.UserService;
import com.sportspartner.service.ModelResult;
import com.sportspartner.service.ActivityCallBack;
import com.sportspartner.util.DBHelper.LoginDBHelper;
import com.sportspartner.util.gcm_notification.MyNotificationService;
import com.sportspartner.util.gcm_notification.RegistrationIntentService;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    private static final String FILE_CHARSET = "utf-8";
    EditText emailField;
    EditText passwordField;

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
     * Onclick listener for login button
     * @param v
     */
    public void login(View v){
//        ResourceService.getGeocoding(this,new LatLng(39.328,-76.62),new ActivityCallBack<MapApiResult>(){
//            @Override
//            public void getModelOnSuccess(ModelResult<MapApiResult> modelResult){
//                Log.d("geocode", modelResult.getModel().toString());
//            }
//        });
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        //Get device Token from GCM
        String token = RegistrationIntentService.getToken();
        System.out.println(token);

        Intent i = new Intent(getApplicationContext(), MyNotificationService.class);
        i.putExtra("email", email);
        i.putExtra("upcomingDate", new Date());
        getApplicationContext().startService(i);

        UserService.login(this, email, password, token, new ActivityCallBack(){
            public void getModelOnSuccess(ModelResult booleanResult) {
                Log.d("notifitest", "login 90");
                loginHandler(booleanResult);
            }
        });
    }

    /**
     * Handel the login result from the server
     * Goto the profile page if success
     * @param booleanResult
     */
    private void loginHandler(ModelResult booleanResult) {
        // handle the result here
        String message = booleanResult.getMessage();
        if (message != null) {
            Toast toast = Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG);
            toast.show();
        }
        if (booleanResult.isStatus()) {
            Context context = getApplicationContext();

            //get userId from SQLite
            LoginDBHelper dbHelper = LoginDBHelper.getInstance(context);
            String email = dbHelper.getEmail();

            //go to profile activity
            Intent intent = new Intent(context, HomeActivity.class);
            startActivity(intent);
            finish();
            System.gc();
        }
    }

    /**
     * Onclick listener for sign up button
     * @param v
     */
    public void signup(View v){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}

