package com.sportspartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sportspartner.R;

public class SactivityDetailActivity extends BasicActivity {

    /**
     * OnCreate of this Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup content = (ViewGroup) findViewById(R.id.layout_home);
        getLayoutInflater().inflate(R.layout.activity_sdetail, content, true);

        //set title of toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Activity Details");

        // gets the previously created intent
        Intent myIntent = getIntent();
        String activityId = myIntent.getStringExtra("activityId");

        Toast.makeText(this, activityId, Toast.LENGTH_SHORT).show();

    }

    /**
     * OnClick method for "Join" button
     * @param v
     */
    public void Join(View v){
        //TODO
    }

}
