package com.sportspartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.sportspartner.R;
import com.sportspartner.models.SActivityOutline;
import com.sportspartner.service.ActivityService;
import com.sportspartner.service.ModelResult;
import com.sportspartner.service.ActivityCallBack;
import com.sportspartner.util.DBHelper.LoginDBHelper;
import com.sportspartner.util.adapter.MyActivityAdapter;

import java.util.ArrayList;

/**
 * @author yujia xiao
 * @author Xiaochen Li
 */
public class HomeActivity extends BasicActivity {
    //userEmail
    private String usermail;

    //refresh
    RefreshLayout refreshLayout;

    //widget
    private View viewRecommend;
    private ListView listHomeRecommend;
    private ListView listCommingActivity;

    //ListView adapters
    private MyActivityAdapter recommendListAdapter;
    private MyActivityAdapter upcommingListAdapter;

    //refresh helper data
    private int upcommingCount = 0; // the count of loaded upcomming activities
    private int recommendCount = 0; // the count of loaded history activities
    private boolean upcommingFinished = false; // no more upcomming activity to load
    private boolean recommendFinished = false; // no more history activity to load
    private final int REFRESH_LIMIT = 3;

    /**
     * OnCreate function for this Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup content = (ViewGroup) findViewById(R.id.layout_home);
        getLayoutInflater().inflate(R.layout.activity_home, content, true);
        refreshLayout = (RefreshLayout) findViewById(R.id.home) ;

        //set title of toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sports Partner");

        // get the userEmail from SQLite
        LoginDBHelper dbHelper = LoginDBHelper.getInstance(HomeActivity.this);
        usermail= dbHelper.getEmail();

        listCommingActivity = (ListView) findViewById(R.id.list_home_upcoming);
        listHomeRecommend = (ListView) findViewById(R.id.list_home_recommend);
        final Intent intent = new Intent(this, SactivityDetailActivity.class);

        //set List onClick Listener
        listCommingActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                //activityItems
                SActivityOutline activityOutline = upcommingListAdapter.getActivityByindex(position);
                String activityId = activityOutline.getActivityId();

                intent.putExtra("activityId",activityId);
                startActivity(intent);
            }

        });

        listHomeRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                //activityItems
                SActivityOutline activityOutline = recommendListAdapter.getActivityByindex(position);
                String activityId = activityOutline.getActivityId();

                intent.putExtra("activityId",activityId);
                startActivity(intent);
            }

        });

        setTitle();
        setListCommingActivity();
        setListRecommend();
        setRefresh();
        refresh();
    }

    /**
     * Set the content of textView for two small titles in this page
     */
    private void setTitle(){
        View viewComming = (View) findViewById(R.id.home_title_upcomming_activity);
        TextView titleupComming = (TextView) viewComming.findViewById(R.id.title);
        titleupComming.setText("UpComing Activity");

        viewRecommend = (View) findViewById(R.id.home_title_recommend);
        TextView titleRecommend = (TextView) viewRecommend.findViewById(R.id.title);
        titleRecommend.setText("Recommend For You");
    }

    /**
     * Set the content of UpComing Activity
     */
    private void setListCommingActivity(){
        upcommingListAdapter = new MyActivityAdapter(this, new ArrayList<SActivityOutline>());
        listCommingActivity.setAdapter(upcommingListAdapter);
    }

    /**
     * Set the content of UpComing Activity
     */
    private void setListRecommend(){
        recommendListAdapter = new MyActivityAdapter(this, new ArrayList<SActivityOutline>());
        listHomeRecommend.setAdapter(recommendListAdapter);
    }

    /**
     * Handle the result of the respond from the server
     * set the content of list of UpcommingActivity
     * @param moreActivitiesResult
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
                MyActivityAdapter.setListViewHeightBasedOnChildren(listCommingActivity);
            }
        } else {
            //if failure, show a toast
            Toast toast = Toast.makeText(HomeActivity.this, "Load more activities Error: " + message, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * Handle the result of the respond from the server
     * set the content of list of Recommend
     * @param moreActivitiesResult
     */
    private void loadRecommendHandler(ModelResult<ArrayList<SActivityOutline>> moreActivitiesResult) {
        // handle the result of request here
        String message = moreActivitiesResult.getMessage();
        Boolean status = moreActivitiesResult.isStatus();

        if (status){
            //if successfully get Activities, get the data
            ArrayList<SActivityOutline> moreSAs = moreActivitiesResult.getModel();
            int size = moreSAs.size();
            recommendCount += size;
            if (size < REFRESH_LIMIT){
                recommendFinished = true;
            }
            if (size > 0) {
                recommendListAdapter.appendList(moreSAs);
                recommendListAdapter.notifyDataSetChanged();
                //set the height of the listview
                MyActivityAdapter.setListViewHeightBasedOnChildren(listHomeRecommend);
            }
        } else {
            //if failure, show a toast
            Toast toast = Toast.makeText(HomeActivity.this, "Load more activities Error: " + message, Toast.LENGTH_LONG);
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
            //get upcoming activities
            ActivityService.getUpcomingActivities(this, usermail, REFRESH_LIMIT, upcommingCount,
                    new ActivityCallBack<ArrayList<SActivityOutline>>(){
                        @Override
                        public void getModelOnSuccess(ModelResult<ArrayList<SActivityOutline>> result){
                            loadUpcommingActivitiesHandler(result);
                        }
                    });
        } else if (!recommendFinished){
            viewRecommend.setVisibility(View.VISIBLE);
            ActivityService.getRecommendActivities(this, usermail, REFRESH_LIMIT, recommendCount,
                    new ActivityCallBack<ArrayList<SActivityOutline>>(){
                        @Override
                        public void getModelOnSuccess(ModelResult<ArrayList<SActivityOutline>> result){
                            loadRecommendHandler(result);
                        }
                    });
        } else {
            Toast toast = Toast.makeText(HomeActivity.this, "no more activities to load", Toast.LENGTH_SHORT);
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
     * Onclick listener for Create button
     * Go to the CreateSactivityActivity
     * @param v
     */
    public void GotoCreateActivity(View v){
        Intent intent = new Intent(this, CreateSactivityActivity.class);
        startActivity(intent);
    }

    /**
     * Onclick listener for Find button
     * Go to the SearchSactivityActivity
     * @param v
     */
    public void GotoFindActivity(View v){
        Intent intent = new Intent(this, SearchSactivityActivity.class);
        startActivity(intent);
    }

}
