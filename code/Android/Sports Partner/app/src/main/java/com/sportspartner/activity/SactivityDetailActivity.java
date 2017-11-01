package com.sportspartner.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.sportspartner.R;

public class SactivityDetailActivity extends AppCompatActivity {

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

    }

}
