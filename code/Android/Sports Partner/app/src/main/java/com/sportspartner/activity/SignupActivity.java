package com.sportspartner.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.service.UserService;
import com.sportspartner.util.ActivityCallBack;
import com.sportspartner.service.serviceresult.BooleanResult;
import com.sportspartner.util.gcm_notification.RegistrationIntentService;

public class SignupActivity extends AppCompatActivity {
    EditText emailField;
    EditText passwordField;
    EditText confirmPasswordField;
    String email;
    String password;
    String confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailField = (EditText)findViewById(R.id.signUpEmail);
        passwordField = (EditText)findViewById(R.id.password);
        confirmPasswordField = (EditText)findViewById(R.id.confirmPassword);
    }

    /**
     * Onclick listener for "sign up as indivadual" button
     * @param v
     */
    public void signupIndividual(View v){
        signupUser("person");
    }

    /**
     * Onclick listener for "sign up as facility provider" button
     * @param v
     */
    public void signupProvider(View v){
        signupUser("facilityprovider");
    }


    /**
     * Onclick listener for "Back to login" button
     * @param v
     */
    public void backToLogin(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Signup finciton:
     * Check whether the user fill all the information
     * Check whether the password match the confirmation password
     * If true, Send the  request to create an new account
     * @param type The userEmail which the user inputs
     */
    private void signupUser(String type) {
        email = emailField.getText().toString();
        password = passwordField.getText().toString();
        confirmPassword = confirmPasswordField.getText().toString();

        if(password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()){
            Context context = this.getApplicationContext();
            Toast toast = Toast.makeText(context, "missing field", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Context context = this.getApplicationContext();
            Toast toast = Toast.makeText(context, "confirm password doesn't match", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        UserService.signup(this, email, password, confirmPassword, type, new ActivityCallBack(){
            @Override
            public void onSuccess(BooleanResult signupResult) {
                signupHandler(signupResult);
            }
        });
    }

    /**
     * Handle the result from the Server
     * @param signupResult
     */
    private void signupHandler(BooleanResult signupResult) {
        // handle the result here
        String message = signupResult.getMessage();
        if (message != null) {
            Toast toast = Toast.makeText(SignupActivity.this, message, Toast.LENGTH_LONG);
            toast.show();
        }
        //get the token from GCM
        String token = RegistrationIntentService.getToken();
        if (signupResult.isStatus()) {
            Toast toast = Toast.makeText(SignupActivity.this, "sign up successfully!", Toast.LENGTH_LONG);
            toast.show();
            UserService.login(this, email, password, token, new ActivityCallBack(){
                @Override
                public void onSuccess(BooleanResult booleanResult) {
                    // TODO link to the main page activity of this user
                    Context context = getApplicationContext();
                    Intent intent = new Intent(context, ProfileActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

}
