package com.sportspartner.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sportspartner.R;

import com.sportspartner.models.SActivityOutline;
import com.sportspartner.request.ResourceRequest;
import com.sportspartner.service.ResourceService;
import com.sportspartner.service.UserService;
import com.sportspartner.util.ActivityCallBack;
import com.sportspartner.service.serviceresult.BooleanResult;

import java.util.ArrayList;


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

    /**
     * Onclick listener for login button
     * @param v
     */
    public void login(View v){

        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);


        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        UserService.login(this, email, password, new ActivityCallBack(){
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
