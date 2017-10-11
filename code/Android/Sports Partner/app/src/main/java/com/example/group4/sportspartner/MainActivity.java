package com.example.group4.sportspartner;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }
    public void login(View v){
        EditText userNameField = (EditText)findViewById(R.id.userName);
        EditText passwordField = (EditText)findViewById(R.id.password);

        String userName = userNameField.getText().toString();
        String password = passwordField.getText().toString();

        Context context = getApplicationContext();
        CharSequence text = "";
        int duration = Toast.LENGTH_SHORT;

        if(userName.equals("shirish") && password.equals("handsome"))
        {
            setContentView(R.layout.static_main_page);
        }else{
            text = "Login Fail";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }
    public void signup(View v){

        setContentView(R.layout.signup_activity);

    }
    public void signupIndividual(View v){
        EditText userNameField = (EditText)findViewById(R.id.userName);
        EditText passwordField = (EditText)findViewById(R.id.password);
        EditText confirmPasswordField = (EditText)findViewById(R.id.confirmPassword);
        EditText emailField = (EditText)findViewById(R.id.email);

        String userName = userNameField.getText().toString();
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();
        String email = emailField.getText().toString();

        Context context = getApplicationContext();
        CharSequence text = "";
        int duration = Toast.LENGTH_SHORT;


        if(userName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()){
            text = "missing info";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else if(!password.equals(confirmPassword)){
            text = "wrong password confirmation";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else{
            text = "success";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            setContentView(R.layout.static_main_page);
        }

    }
    public void signupProvider(View v){
        EditText userNameField = (EditText)findViewById(R.id.userName);
        EditText passwordField = (EditText)findViewById(R.id.password);
        EditText confirmPasswordField = (EditText)findViewById(R.id.confirmPassword);
        EditText emailField = (EditText)findViewById(R.id.email);

        String userName = userNameField.getText().toString();
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();
        String email = emailField.getText().toString();

        Context context = getApplicationContext();
        CharSequence text = "";
        int duration = Toast.LENGTH_SHORT;


        if(userName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()){
            text = "missing info";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else if(!password.equals(confirmPassword)){
            text = "wrong password confirmation";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else{
            text = "success";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            setContentView(R.layout.static_main_page);
        }

    }
}
