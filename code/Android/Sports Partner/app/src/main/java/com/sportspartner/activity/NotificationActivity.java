package com.sportspartner.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.models.Notification;
import com.sportspartner.util.DBHelper.NotificationDBHelper;
import com.sportspartner.util.adapter.Divider;
import com.sportspartner.util.adapter.NotificationAdapter;

import java.text.ParseException;
import java.util.ArrayList;

public class NotificationActivity extends BasicActivity {
    private RecyclerView recyclerView;
    private NotificationAdapter notiAdapter;
    private ArrayList<Notification> listNoti = new ArrayList<>();

    /**
     * OnCreate method for this Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup content = (ViewGroup) findViewById(R.id.layout_home);
        getLayoutInflater().inflate(R.layout.activity_notification, content, true);

        //set title of toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Notification");

        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        notiAdapter = new NotificationAdapter(listNoti, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(notiAdapter);

        try {
            prepareNotiData();
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(NotificationActivity.this, "Load Notification Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void prepareNotiData() throws ParseException {
        //get data from SqLite
        NotificationDBHelper dbHelper = NotificationDBHelper.getInstance(this);
        Log.d("dbHelper", String.valueOf(dbHelper.getAllUUID().size()));
        //Todo test data
        //dbHelper.insert("01", "Friend Request", "u2 has send a friend request to you",
        //        "u2", "INTERACTION", "2001.07.04 AD at 12:08:56 PDT", 1);
        listNoti = new ArrayList<>(dbHelper.getAll());
        notiAdapter.updateNotiList(listNoti);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish(){
        NotificationDBHelper dbHelper = NotificationDBHelper.getInstance(this);
        for (Notification noti : listNoti){
            if(noti.getRead())
                dbHelper.deleteById(noti.getUuid());
        }
        super.finish();
    }

    @Override
    public void onPause(){
        super.onPause();
        finish();
    }

}
