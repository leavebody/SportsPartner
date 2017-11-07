package com.sportspartner.util.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.activity.ProfileActivity;
import com.sportspartner.models.UserOutline;

import java.util.ArrayList;

/**
 * Created by yujiaxiao on 11/5/17.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MyViewHolder> {

    private ArrayList<UserOutline> friendsList;
    private Intent myIntent;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView photo;
        public TextView name;
        private Context context;

        public MyViewHolder(View view, Context context) {
            super(view);
            this.photo = (ImageView) view.findViewById(R.id.friend_photo);
            this.name = (TextView) view.findViewById(R.id.friend_name);
            this.context = context;
            view.setOnClickListener(this);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                UserOutline friend = friendsList.get(position);
                myIntent = new Intent(context, ProfileActivity.class);
                myIntent.putExtra("userId",friend.getUserId());
                context.startActivity(myIntent);
                }
        }
    }


    public FriendAdapter(ArrayList<UserOutline> friendsList) {
        this.friendsList = friendsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_friends, parent, false);

        return new MyViewHolder(itemView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UserOutline friend = friendsList.get(position);
        //TODO photo
        //holder.photo.setBackgroundResource(friend.getIconUUID());
        holder.name.setText(friend.getUserName());
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

}
