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

        if(userName.equals("u1") && password.equals("p1"))
        {
            setContentView(R.layout.static_main_page);
        }else{
            text = "Login Fail";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }
}
