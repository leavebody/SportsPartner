package com.sportspartner.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.sportspartner.R;

/**
 * Created by yujiaxiao on 11/14/17.
 */

public class ReviewSaActivity extends BasicActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup content = (ViewGroup) findViewById(R.id.layout_home);
        getLayoutInflater().inflate(R.layout.activity_moment, content, true);

        //set title of toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Review Activity");
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
