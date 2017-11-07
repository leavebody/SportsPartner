package com.sportspartner.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.sportspartner.R;
import com.sportspartner.models.UserOutline;
import com.sportspartner.util.adapter.Divider;
import com.sportspartner.util.adapter.FriendAdapter;

import java.util.ArrayList;

public class FriendListActivity extends BasicActivity {

    private ArrayList<UserOutline> friendList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FriendAdapter friendAdapter;

    /**
     * Load the FriendListActivity
     * Find Widgets by Id
     * Set onClick listener
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup content = (ViewGroup) findViewById(R.id.layout_home);
        getLayoutInflater().inflate(R.layout.activity_friend_list, content, true);

        //set title of toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Friend List");

        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        friendAdapter = new FriendAdapter(friendList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(friendAdapter);

        preparefriendData();

    }



    private void preparefriendData() {
        UserOutline friend1 = new UserOutline("p1", "Xuan Zhang", "1234");
        friendList.add(friend1);

        UserOutline friend2 = new UserOutline("p2", "Xiaochen Li", "1234");
        friendList.add(friend2);

        UserOutline friend3 = new UserOutline("p3", "Zihao Xiao", "1234");
        friendList.add(friend3);

        UserOutline friend4 = new UserOutline("p4", "Yujia Xiao", "1234");
        friendList.add(friend4);

        friendAdapter.notifyDataSetChanged();
    }

}
