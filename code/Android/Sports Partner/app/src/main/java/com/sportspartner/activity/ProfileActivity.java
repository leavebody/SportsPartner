package com.sportspartner.activity;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.sportspartner.models.SActivityOutline;
import com.sportspartner.R;
import com.sportspartner.models.Profile;
import com.sportspartner.models.Sport;
import com.sportspartner.service.ActivityService;
import com.sportspartner.service.ProfileService;
import com.sportspartner.service.ResourceService;
import com.sportspartner.service.serviceresult.ModelResult;
import com.sportspartner.util.ActivityCallBack;
import com.sportspartner.util.adapter.Divider;
import com.sportspartner.util.adapter.FriendAdapter;
import com.sportspartner.util.adapter.InterestAdapter;
import com.sportspartner.util.adapter.MyActivityAdapter;

import java.util.ArrayList;

public class ProfileActivity extends BasicActivity {
    //userEmail
    private String usermail;

    //the main view of this activity
    RefreshLayout refreshLayout;

    private Profile profile = new Profile();
    private ArrayList<Sport> sports = new ArrayList<Sport>();

    // ListView adapters
    private InterestAdapter interestAdapter;
    private MyActivityAdapter historyListAdapter;
    private MyActivityAdapter upcommingListAdapter;

    //Android widgets in the profile xml
    private RecyclerView recyclerView;
    private View history;
    private ImageView profilePhoto;
    private TextView profileName;
    private TextView gender;
    private TextView age;
    private TextView location;
    private ListView historyActivityList;
    private ListView upcommingActivityList;
    private RatingBar puntuality;
    private RatingBar participation;
    private TextView titleBasicInfo;
    private TextView titleComment;
    private TextView titleUpcommingActivity;
    private TextView titleHistoryActivity;

    //reshresh helper data
    private int upcommingCount = 0; // the count of loaded upcomming activities
    private int historyCount = 0; // the count of loaded history activities
    private boolean upcommingFinished = false; // no more upcomming activity to load
    private boolean historyFinished = false; // no more history activity to load
    private final int REFRESH_LIMIT = 3;

    /**
     * OnCreate method for this Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup content1 = (ViewGroup) findViewById(R.id.layout_home);
        getLayoutInflater().inflate(R.layout.activity_profile, content1, true);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);

        //set title of toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");

        //get userId
        Intent myIntent = getIntent();
        usermail = myIntent.getStringExtra("userId");


        //find all the widgets by Id
        View basicInfo = findViewById(R.id.personal_info);
        profilePhoto = (ImageView) basicInfo.findViewById(R.id.profile_photo);
        profileName = (TextView) basicInfo.findViewById(R.id.profile_name);
        gender = (TextView) basicInfo.findViewById(R.id.gender);
        age = (TextView) basicInfo.findViewById(R.id.age);
        location = (TextView) basicInfo.findViewById(R.id.location);
        recyclerView = (RecyclerView) basicInfo.findViewById(R.id.RecyclerView);

        puntuality = (RatingBar) findViewById(R.id.rating_punctuality);
        participation = (RatingBar) findViewById(R.id.rating_participation);

        historyActivityList = (ListView) findViewById(R.id.list_history_activities);
        upcommingActivityList = (ListView) findViewById(R.id.list_upcomming_activties);

        final Intent intent = new Intent(this, SactivityDetailActivity.class);

        //set Adapter
        interestAdapter = new InterestAdapter(sports, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(interestAdapter);

        //Set List OnClick Listener
        upcommingActivityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                //activityItems
                SActivityOutline activityOutline = upcommingListAdapter.getActivityByindex(position);
                String activityId = activityOutline.getActivityId();

                Toast toast = Toast.makeText(ProfileActivity.this, "Id" + activityId, Toast.LENGTH_LONG);
                toast.show();

                intent.putExtra("activityId",activityId);
                startActivity(intent);
            }

        });

        historyActivityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                //activityItems
                SActivityOutline activityOutline = historyListAdapter.getActivityByindex(position);
                String activityId = activityOutline.getActivityId();

                Toast toast = Toast.makeText(ProfileActivity.this, "Id" + activityId, Toast.LENGTH_LONG);
                toast.show();

                intent.putExtra("activityId",activityId);
                startActivity(intent);
            }

        });

        //find all the titles by Id
        View basic = findViewById(R.id.title_basic_info);
        View comment = findViewById(R.id.title_others_say);
        View upcomming = findViewById(R.id.title_upcomming_activty);
        history = findViewById(R.id.title_history_activty);
        titleBasicInfo = (TextView) basic.findViewById(R.id.title);
        titleComment = (TextView) comment.findViewById(R.id.title);
        titleUpcommingActivity = (TextView) upcomming.findViewById(R.id.title);
        titleHistoryActivity = (TextView) history.findViewById(R.id.title);

        //set all the content
        setTitle();
        setProfileInfo();
        setUpcommingActivity();
        setHistoryActivity();
        setRefresh();
        refresh();
        refresh();
    }

    /**
     * Set the text of textView of some titles
     */
    private void setTitle(){
        titleBasicInfo.setText("Basic Info");
        titleComment.setText("Comments");
        titleUpcommingActivity.setText("Upcomming Activity");
        titleHistoryActivity.setText("History Activity");
        history.setVisibility(View.INVISIBLE);
    }

    /**
     * Set the profileInfo and interest
     * Sent the request
     * Call the ProfileInfoHandler if success
     */
    private void setProfileInfo(){
        //get basic info
        ProfileService.getProfileInfo(this, usermail, new ActivityCallBack<Profile>(){
            @Override
            public void getModelOnSuccess(ModelResult<Profile> userProfileResult){
                ProfileInfoHandler(userProfileResult);
            }
        });

        ProfileService.getInterests(this, usermail, new ActivityCallBack<ArrayList<Sport>>(){
            @Override
            public void getModelOnSuccess(ModelResult<ArrayList<Sport>> result){
                ProfileInterestHandler(result);
            }
        });

    }

    /**
     * Handle the result of response from the server
     * Fill the corresponding content of the interest
     * @param result
     */
    private void ProfileInterestHandler(ModelResult<ArrayList<Sport>> result) {
        // handle the result of request here
        String message = result.getMessage();
        Boolean status = result.isStatus();

        if (status){
            //if successfully get the data, then get the data
            sports= result.getModel();
            Log.d("count",""+interestAdapter.getItemCount());
            interestAdapter.updateInterests(sports);
            Log.d("count",""+interestAdapter.getItemCount());
        }
        else{
            //if failure, show a toast
            Toast toast = Toast.makeText(ProfileActivity.this, "Load interests Error: " + message, Toast.LENGTH_LONG);
            toast.show();
        }

        profileName.setText(profile.getUserName());
        age.setText(profile.getAge());
        gender.setText(profile.getGender());
        location.setText(profile.getAddress());
        puntuality.setRating((float)profile.getPunctuality());
        participation.setRating((float)profile.getParticipation());

    }

    /**
     * Handle the result of response from the server
     * Fill the corresponding content of the UI
     * @param userProfileResult The result from the server
     */
    private void ProfileInfoHandler(ModelResult<Profile> userProfileResult) {
        // handle the result of request here
        String message = userProfileResult.getMessage();
        Boolean status = userProfileResult.isStatus();

        if (status){
            //if successfully get the data, then get the data
            profile = userProfileResult.getModel();
        }
        else{
            //if failure, show a toast
            Toast toast = Toast.makeText(ProfileActivity.this, "Load ProfileInfo Error: " + message, Toast.LENGTH_LONG);
            toast.show();
        }

        //set profile photo
        String iconUUID = profile.getIconUUID();
        ResourceService.getImage(this, iconUUID, ResourceService.IMAGE_SMALL, new ActivityCallBack<Bitmap>(){
            @Override
            public void getModelOnSuccess(ModelResult<Bitmap> result){
                if (result.isStatus()) {
                    profilePhoto.setImageBitmap(result.getModel());
                } else{
                    //if failure, show a toast
                    Toast toast = Toast.makeText(ProfileActivity.this,
                            "Load user icon error: "+result.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        profileName.setText(profile.getUserName());
        age.setText(profile.getAge());
        gender.setText(profile.getGender());
        location.setText(profile.getAddress());
        puntuality.setRating((float)profile.getPunctuality());
        participation.setRating((float)profile.getParticipation());

    }

    /**
     * Set the list content of UpcomingActivity
     */
    private void setUpcommingActivity() {
        upcommingListAdapter = new MyActivityAdapter(this, new ArrayList<SActivityOutline>());
        upcommingActivityList.setAdapter(upcommingListAdapter);
    }

    /**
     * Set the list content of HistoryActivity
     */
    private void setHistoryActivity(){
        historyListAdapter = new MyActivityAdapter(this, new ArrayList<SActivityOutline>());
        historyActivityList.setAdapter(historyListAdapter);
    }

    /**
     * Handle the result from the ActivityService
     * Fill the content of list of UpcommingActivities
     * @param moreActivitiesResult The result from the ActivityService
     */
    private void loadUpcommingActivitiesHandler(ModelResult<ArrayList<SActivityOutline>> moreActivitiesResult) {
        // handle the result of request here
        String message = moreActivitiesResult.getMessage();
        Boolean status = moreActivitiesResult.isStatus();

        if (status){
            //if successfully get Activities, get the data
            ArrayList<SActivityOutline> moreSAs = moreActivitiesResult.getModel();
            int size = moreSAs.size();
            upcommingCount += size;
            if (size < REFRESH_LIMIT){
                upcommingFinished = true;
            }
            if (size > 0) {
                upcommingListAdapter.appendList(moreSAs);
                upcommingListAdapter.notifyDataSetChanged();
                //set the height of the listview
                MyActivityAdapter.setListViewHeightBasedOnChildren(upcommingActivityList);
            }
        } else {
            //if failure, show a toast
            Toast toast = Toast.makeText(ProfileActivity.this, "Load more activities Error: " + message, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * Handle the result from the ActivityService
     * Fill the content of list of HistoryActivities
     * @param moreActivitiesResult The result from the ActivityService
     */
    private void loadHistoryActivitiesHandler(ModelResult<ArrayList<SActivityOutline>> moreActivitiesResult) {
        // handle the result of request here
        String message = moreActivitiesResult.getMessage();
        Boolean status = moreActivitiesResult.isStatus();

        if (status){
            //if successfully get Activities, get the data
            ArrayList<SActivityOutline> moreSAs = moreActivitiesResult.getModel();
            int size = moreSAs.size();
            historyCount += size;
            if (size < REFRESH_LIMIT){
                historyFinished = true;
            }
            if (size > 0) {
                historyListAdapter.appendList(moreSAs);
                historyListAdapter.notifyDataSetChanged();
                //set the height of the listview
                MyActivityAdapter.setListViewHeightBasedOnChildren(historyActivityList);
            }
        } else {
            //if failure, show a toast
            Toast toast = Toast.makeText(ProfileActivity.this, "Load more activities Error: " + message, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * Refresh: get the content of the list
     * Sent the corresponding request to the server
     * If not reach the end of the Upcoming Activity, get the content of Upcoming Activity
     * Else, get the content of Recommend Activity
     */
    private void refresh(){
        if (!upcommingFinished) {
            //get upcomming activities
            ActivityService.getUpcomingActivities(this, usermail, REFRESH_LIMIT, upcommingCount,
                    new ActivityCallBack<ArrayList<SActivityOutline>>(){
                        @Override
                        public void getModelOnSuccess(ModelResult<ArrayList<SActivityOutline>> result){
                            loadUpcommingActivitiesHandler(result);
                        }
                    });
        } else if (!historyFinished){
            history.setVisibility(View.VISIBLE);
            ActivityService.getHistoryActivities(this, usermail, REFRESH_LIMIT, historyCount,
                    new ActivityCallBack<ArrayList<SActivityOutline>>(){
                        @Override
                        public void getModelOnSuccess(ModelResult<ArrayList<SActivityOutline>> result){
                            loadHistoryActivitiesHandler(result);
                        }
                    });
        } else {
            Toast toast = Toast.makeText(ProfileActivity.this, "no more activities to load", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Assign the setOnLoadmoreListener to the current Layout
     * Set the Animate for the refresh
     * In the Listener, call refresh() function
     */
    private void setRefresh(){
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setAnimatingColor(getResources().getColor(R.color.background_blue)));
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refresh();
                refreshlayout.finishLoadmore(100);

            }
        });
    }

    /**
     * Set the onClick action to the edit button--go to EditProfileActivity
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.toolbar_edit:
                Intent intent = new Intent(this, EditProfileActivity.class);
                intent.putExtra("interest",sports);
                intent.putExtra("profile",profile);
                this.startActivity(intent);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    /**
     * Set the visibility of the Edit button on the toolbar to visible
     * @param menu The menu on the top right of the toolbar
     * @return True if success
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //change the visibility of toolbar edit button
        MenuItem editItem = menu.getItem(0);
        editItem.setVisible(true);
        return true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

}



