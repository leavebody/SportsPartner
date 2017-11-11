package com.sportspartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.models.SActivity;
import com.sportspartner.models.UserOutline;
import com.sportspartner.service.ActivityService;
import com.sportspartner.service.ModelResult;
import com.sportspartner.service.ActivityCallBack;
import com.sportspartner.util.adapter.Divider;
import com.sportspartner.util.adapter.MemberPhotoAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SactivityDetailActivity extends BasicActivity {
    private TextView sport;
    private TextView startDate;
    private TextView endDate;
    private TextView startTime;
    private TextView endTime;
    private TextView location;
    private TextView capacity;
    //private TextView member
    private TextView description;
    //recyclerView
    private RecyclerView recyclerView;
    private ArrayList<UserOutline> memberInfo = new ArrayList<>();
    private MemberPhotoAdapter memberAdapter;

    //SActivity object
    private SActivity activityDetail = new SActivity();
    private String activityId;


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
        activityId = myIntent.getStringExtra("activityId");

        Toast.makeText(this, activityId, Toast.LENGTH_SHORT).show();

        //find widget by ID
        sport = (TextView)findViewById(R.id.text_sport);
        startDate = (TextView)findViewById(R.id.text_startDate);
        startTime = (TextView) findViewById(R.id.text_startTime);
        endDate = (TextView)findViewById(R.id.text_endDate);
        endTime = (TextView) findViewById(R.id.text_endTime);
        location = (TextView) findViewById(R.id.text_location);
        capacity = (TextView) findViewById(R.id.text_capacity);
        description = (TextView) findViewById(R.id.text_description);

        //recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        memberAdapter = new MemberPhotoAdapter(memberInfo);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(memberAdapter);

        preparememberData();

        //fill the content
        setTitle();
        setActivityDetail();
        setComment();
    }

    private void loadSactivityHandler(ModelResult<SActivity> result) {
        // handle the result of request here
        String message = result.getMessage();
        Boolean status = result.isStatus();

        if (status){
            //if successfully get Activity, get the data
           activityDetail = result.getModel();
        }
        else {
            //if failure, show a toast
            Toast toast = Toast.makeText(SactivityDetailActivity.this, "Load activity Error: " + message, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void preparememberData() {

    }

    private void setTitle() {
        View viewComming = (View) findViewById(R.id.title_activityDissucss);
        TextView titleupComming = (TextView) viewComming.findViewById(R.id.title);
        titleupComming.setText("UpComing Activity");
    }

    private  void setActivityDetail(){
        ActivityService.getSActivity(SactivityDetailActivity.this, activityId, new ActivityCallBack<SActivity>(){
            @Override
            public void getModelOnSuccess(ModelResult<SActivity> sActivityResult){
                SActivityInfoHandler(sActivityResult);
            }
        });

    }

    private void SActivityInfoHandler(ModelResult<SActivity> sActivityResult) {
        // handle the result of request here
        String message = sActivityResult.getMessage();
        Boolean status = sActivityResult.isStatus();

        if (status){
            //if successfully get the data, then get the data
            activityDetail = sActivityResult.getModel();
            if (activityDetail == null){
                Log.d("Date", "null");
            }
            else  {
                Log.d("Date", "not full");
            }
        }
        else{
            //if failure, show a toast
            Toast toast = Toast.makeText(SactivityDetailActivity.this, "Load detail Error: " + message, Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        //set data to Android Widget
        sport.setText(activityDetail.getSportName());
        location.setText(activityDetail.getFacilitiName());
        description.setText(activityDetail.getDetail());
        String size = activityDetail.getSize() + "/" + activityDetail.getCapacity();
        capacity.setText(size);

        //Time and date
        //Date start = new Date();
        Date start = activityDetail.getStartTime();
        Log.d("Date", activityDetail.getStartTime().toString());
        Date end = new Date();
        end = activityDetail.getEndTime();
        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy.mm.dd", Locale.US);
        startDate.setText(sdf0.format(start.getTime()));
        endDate.setText(sdf0.format(end.getTime()));
        SimpleDateFormat sdf1 = new SimpleDateFormat("h:mm", Locale.US);
        startTime.setText(sdf1.format(start.getTime()));
        endTime.setText(sdf1.format(end.getTime()));

        //member

    }


    private void setComment(){
        //TODO
    }

    /**
     * OnClick method for "Join" button
     * @param v
     */
    public void Join(View v){
        //TODO
        // //ActivityService.
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

}
