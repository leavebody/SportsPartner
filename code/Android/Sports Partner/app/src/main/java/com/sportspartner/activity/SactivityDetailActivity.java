package com.sportspartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.models.SActivity;
import com.sportspartner.models.Sport;
import com.sportspartner.request.ActivityRequest;
import com.sportspartner.service.ActivityService;
import com.sportspartner.service.serviceresult.ModelResult;
import com.sportspartner.util.ActivityCallBack;

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

    //SActivity object
    private SActivity activityDetal = new SActivity();
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
        String activityId = myIntent.getStringExtra("activityId");

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

        //fill the content
        setTitle();
        setActivityDetail();
        setComment();
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
            activityDetal = sActivityResult.getModel();
        }
        else{
            //if failure, show a toast
            Toast toast = Toast.makeText(SactivityDetailActivity.this, "Load detail Error: " + message, Toast.LENGTH_LONG);
            toast.show();
        }

        //set data to Android Widget
        sport.setText(activityDetal.getSport());
        location.setText(activityDetal.getFacilityName());
        description.setText(activityDetal.getDetail());
        String size = activityDetal.getSize() + "/" + activityDetal.getCapacity();
        capacity.setText(size);

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
    }

}
