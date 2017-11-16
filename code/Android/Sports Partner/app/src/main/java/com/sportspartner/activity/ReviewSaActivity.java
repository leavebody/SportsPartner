package com.sportspartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.models.SActivity;
import com.sportspartner.models.UserOutline;
import com.sportspartner.service.ActivityCallBack;
import com.sportspartner.service.ActivityService;
import com.sportspartner.service.ModelResult;
import com.sportspartner.util.DBHelper.LoginDBHelper;
import com.sportspartner.util.adapter.Divider;
import com.sportspartner.util.adapter.ReviewMembersAdapter;

import java.util.ArrayList;

/**
 * @author  yujiaxiao
 * @author Xiaochen Li
 */

public class ReviewSaActivity extends BasicActivity {
    private String activityId;
    private String myEmail;

    private ReviewMembersAdapter reviewMembersAdapter;
    private ArrayList<UserOutline> listMembers;

    private RecyclerView reviewRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup content = (ViewGroup) findViewById(R.id.layout_home);
        getLayoutInflater().inflate(R.layout.activity_review, content, true);

        //set title of toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Review an Activity");

        reviewRecycler = findViewById(R.id.recycler_evaluate);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        reviewRecycler.setLayoutManager(mLayoutManager);
        reviewRecycler.setItemAnimator(new DefaultItemAnimator());
        reviewRecycler.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));


        // get the activity id from intent
        try {
            Intent myIntent = getIntent();
            activityId = myIntent.getStringExtra("activityId");
        } catch (Exception e) {
            Toast.makeText(this, "intent extra not sent to ReviewSaActivity", Toast.LENGTH_LONG).show();
            Log.d("ReviewSaActivity", "intent extra not sent to ReviewSaActivity");
            onBackPressed();
        }

        // get current user email from local database
        try {
            LoginDBHelper dbHelper = LoginDBHelper.getInstance(this);
            myEmail = dbHelper.getEmail();
        } catch (Exception e) {
            Toast.makeText(this, "login DB error", Toast.LENGTH_LONG).show();
            Log.d("ReviewSaActivity", "login DB error");
            onBackPressed();
        }

        getActivity();

    }

    private void getActivity() {
        ActivityService.getSActivity(this, activityId, new ActivityCallBack<SActivity>() {
            @Override
            public void getModelOnSuccess(ModelResult<SActivity> modelResult) {
                if (!modelResult.isStatus()) {
                    Log.e("ReviewAct", modelResult.getMessage());
                    return;
                }
                listMembers = modelResult.getModel().getMembers();
                setReviewList();

            }
        });
    }
    private void setReviewList(){
        Log.d("test","99");
        reviewMembersAdapter = new ReviewMembersAdapter(listMembers, ReviewSaActivity.this);
        reviewRecycler.setAdapter(reviewMembersAdapter);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
