package com.sportspartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.sportspartner.R;
import com.sportspartner.models.SActivityOutline;
import com.sportspartner.service.ActivityService;
import com.sportspartner.service.serviceresult.ModelResult;
import com.sportspartner.util.ActivityCallBack;
import com.sportspartner.util.LoginDBHelper;
import com.sportspartner.util.listviewadapter.MyActivityAdapter;

import java.util.ArrayList;
import java.util.Date;

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

        setTitle();
        setListCommingActivity();
        setListRecommend();
        setRefresh();
        refresh();
        refresh();
    }

    private void setTitle(){
        View viewComming = (View) findViewById(R.id.home_title_upcomming_activity);
        TextView titleupComming = (TextView) viewComming.findViewById(R.id.title);
        titleupComming.setText("UpComing Activity");

        viewRecommend = (View) findViewById(R.id.home_title_recommend);
        TextView titleRecommend = (TextView) viewRecommend.findViewById(R.id.title);
        titleRecommend.setText("Recommend For You");
    }

    private void setListCommingActivity(){
        upcommingListAdapter = new MyActivityAdapter(this, new ArrayList<SActivityOutline>());
        listCommingActivity.setAdapter(upcommingListAdapter);
    }

    private void setListRecommend(){
        recommendListAdapter = new MyActivityAdapter(this, new ArrayList<SActivityOutline>());
        listHomeRecommend.setAdapter(recommendListAdapter);
    }

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

    /*public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }*/

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
     * Onclick listener for login button
     * @param v
     */
    public void GotoCreateActivity(View v){
        Intent intent = new Intent(this, CreateSactivityActivity.class);
        startActivity(intent);
    }

    public void GotoFindActivity(View v){
        Intent intent = new Intent(this, SearchSactivityActivity.class);
        startActivity(intent);
    }

}
