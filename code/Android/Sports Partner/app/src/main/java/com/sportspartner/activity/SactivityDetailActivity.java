package com.sportspartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.models.SActivity;
import com.sportspartner.models.UserOutline;
import com.sportspartner.service.ActivityService;
import com.sportspartner.service.ModelResult;
import com.sportspartner.service.ActivityCallBack;
import com.sportspartner.service.ProfileService;
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
    private EditText description;
    private TextView joinText;
    //recyclerView
    private RecyclerView recyclerView;
    private ArrayList<UserOutline> memberInfo = new ArrayList<>();
    private MemberPhotoAdapter memberAdapter;

    //SActivity object
    private SActivity activityDetail = new SActivity();
    private String activityId;
    private String userType = "";
    private Menu myMenu;


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

        //find widget by ID
        ViewGroup detail = (ViewGroup) findViewById(R.id.activity_detail);
        sport = (TextView) detail.findViewById(R.id.text_sport);
        startDate = (TextView) detail.findViewById(R.id.text_startDate);
        startTime = (TextView) detail.findViewById(R.id.text_startTime);
        endDate = (TextView) detail.findViewById(R.id.text_endDate);
        endTime = (TextView) detail.findViewById(R.id.text_endTime);
        location = (TextView) detail.findViewById(R.id.text_location);
        capacity = (TextView) detail.findViewById(R.id.text_capacity);
        description = (EditText) detail.findViewById(R.id.text_description);
        joinText = (TextView) findViewById(R.id.text_join);
        description.setFocusable(false);

        //recyclerView
        recyclerView = (RecyclerView) detail.findViewById(R.id.RecyclerView);

        memberAdapter = new MemberPhotoAdapter(memberInfo);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(memberAdapter);

        //fill the content
        setTitle();
        setActivityDetail();
        setComment();
    }

    /**
     * set the title of the Discussion
     */
    private void setTitle() {
        View viewComming = (View) findViewById(R.id.title_activityDissucss);
        TextView titleupComming = (TextView) viewComming.findViewById(R.id.title);
        titleupComming.setText("Discussion");
    }

    /**
     * get the SActivity detail from server
     */
    private void setActivityDetail(){
        ActivityService.getSActivity(SactivityDetailActivity.this, activityId, new ActivityCallBack<SActivity>(){
            @Override
            public void getModelOnSuccess(ModelResult<SActivity> sActivityResult){
                SActivityInfoHandler(sActivityResult);
            }
        });

    }

    /**
     * handle the result info from the server
     * set the content of the SA detail
     * @param sActivityResult
     */
    private void SActivityInfoHandler(ModelResult<SActivity> sActivityResult) {
        // handle the result of request here
        String message = sActivityResult.getMessage();
        Boolean status = sActivityResult.isStatus();

        if (status){
            //if successfully get the data, then get the data
            activityDetail = sActivityResult.getModel();
            this.userType = sActivityResult.getUserType();
            Log.d("Activity userType", String.valueOf(userType));
            switch (this.userType){
                case "CREATOR":
                    joinText.setText("Delete");
                    invalidateOptionsMenu();
                    break;
                case "MEMBER":
                    joinText.setText("Leave");
                    break;
                case "STRANGER":
                    joinText.setText("Join");
                    break;
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
        location.setText(activityDetail.getAddress());
        description.setText(String.valueOf(activityDetail.getDetail()));
        String size = activityDetail.getSize() + "/" + activityDetail.getCapacity();
        capacity.setText(size);

        //Time and date
        Date start = activityDetail.getStartTime();
        Log.d("Date", activityDetail.getStartTime().toString());
        Date end = activityDetail.getEndTime();
        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy.MM.dd", Locale.US);
        startDate.setText(sdf0.format(start.getTime()));
        endDate.setText(sdf0.format(end.getTime()));
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a", Locale.US);
        startTime.setText(sdf1.format(start.getTime()));
        endTime.setText(sdf1.format(end.getTime()));

        //member
        memberInfo = activityDetail.getMembers();

        //get creator Info
        ProfileService.getProfileOutline(this, activityDetail.getCreatorId(), new ActivityCallBack() {
            @Override
            public void getModelOnSuccess(ModelResult modelResult) {
                String message = modelResult.getMessage();
                Boolean status = modelResult.isStatus();

                if (status){
                    //if successfully get the data, then get the data
                    UserOutline creator = (UserOutline) modelResult.getModel();
                    memberInfo.add(creator);
                    memberAdapter.updateMember(memberInfo);
                    Log.d("memberInfo1", String.valueOf(memberInfo.size()));
                }
                else{
                    //if failure, show a toast
                    Toast toast = Toast.makeText(SactivityDetailActivity.this, "Load ProfileInfo Error: " + message, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        Log.d("getMembers", String.valueOf(activityDetail.getMembers().size()));
        Log.d("memberInfo", String.valueOf(memberInfo.size()));

    }


    private void setComment(){
        //TODO
    }

    /**
     * OnClick method for "Join" button
     * @param v
     */
    public void Join(View v){
        switch (this.userType){
            case "CREATOR":
                ActivityService.deleteActivity(this, activityDetail.getActivityId(), new ActivityCallBack() {
                    @Override
                    public void getModelOnSuccess(ModelResult modelResult) {
                        if (modelResult.isStatus()){
                            Toast.makeText(SactivityDetailActivity.this, "Delete Succeeded!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                        else {
                            Toast.makeText(SactivityDetailActivity.this, "Delete Failed: " + modelResult.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case "MEMBER":
                ActivityService.leaveActivity(this, activityDetail.getActivityId(), new ActivityCallBack() {
                    @Override
                    public void getModelOnSuccess(ModelResult modelResult) {
                        if(modelResult.isStatus()){
                            Toast.makeText(SactivityDetailActivity.this, "Leave Actviity Succeeded!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(SactivityDetailActivity.this, "Leave Activity Failed: " + modelResult.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case "STRANGER":
                ActivityService.joinActivity(this, activityDetail.getActivityId(), activityDetail.getCreatorId(), new ActivityCallBack() {
                    @Override
                    public void getModelOnSuccess(ModelResult modelResult) {
                        if (modelResult.isStatus()){
                            Toast.makeText(SactivityDetailActivity.this, "Send Application Succeeded!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(SactivityDetailActivity.this, "Send Application Failed: " + modelResult.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.myMenu = menu;
        return true;
    }


    /**
     * Set the visibility of the button on the toolbar to visible
     * set different icon according the userType
     */
    @Override
    public void invalidateOptionsMenu() {
        //change the visibility of toolbar edit button
        MenuItem editItem = myMenu.getItem(0);

        switch (this.userType) {
            case "CREATOR":
                editItem.setIcon(R.drawable.edit);
                editItem.setVisible(true);
                break;
        }

        onPrepareOptionsMenu(myMenu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_edit:
                switch (userType) {
                    case "CREATOR":
                        Intent intent = new Intent(this, EditSaActivity.class);
                        intent.putExtra("activity", activityDetail);
                        intent.putExtra("members", memberInfo);
                        this.startActivity(intent);
                        finish();
                        break;
                    default:
                        Toast.makeText(this, "UserType Error", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    @Override
    public void onPause(){
        super.onPause();
        finish();
    }

}
