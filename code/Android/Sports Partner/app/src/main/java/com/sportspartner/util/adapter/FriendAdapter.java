package com.sportspartner.util.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sendbird.android.GroupChannel;
import com.sendbird.android.SendBirdException;
import com.sportspartner.R;
import com.sportspartner.activity.ProfileActivity;
import com.sportspartner.models.UserOutline;
import com.sportspartner.service.ResourceService;
import com.sportspartner.service.ModelResult;
import com.sportspartner.service.ActivityCallBack;
import com.sportspartner.service.groupchannel.GroupChannelActivity;
import com.sportspartner.util.DBHelper.LoginDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yujiaxiao on 11/5/17.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MyViewHolder> {

    private ArrayList<UserOutline> friendsList;
    private Intent myIntent;
    private Context context;

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

                //Get 2 user ID
                List<String> userIds = new ArrayList<>();
                userIds.add(friend.getUserId());
                LoginDBHelper dbHelper = LoginDBHelper.getInstance(context);
                userIds.add(dbHelper.getEmail());
                GroupChannel.createChannelWithUserIds(userIds,true, new GroupChannel.GroupChannelCreateHandler() {
                    @Override
                    public void onResult(GroupChannel groupChannel, SendBirdException e) {
                        if (e != null) {
                            // Error!
                            return;
                        }
                        myIntent = new Intent(context, GroupChannelActivity.class);
                        myIntent.putExtra("groupChannelUrl",groupChannel.getUrl());
                        //myIntent.putExtra("isGroupChannel","666");
                        context.startActivity(myIntent);

                    }
                });



                }
        }
    }


    public FriendAdapter(ArrayList<UserOutline> friendsList, Context context) {
        this.friendsList = friendsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_friends, parent, false);

        return new MyViewHolder(itemView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        UserOutline friend = friendsList.get(position);

        holder.name.setText(friend.getUserName());

        //set photo
        String iconUUID = friend.getIconUUID();
        ResourceService.getImage(context, iconUUID, ResourceService.IMAGE_SMALL, new ActivityCallBack<Bitmap>(){
            @Override
            public void getModelOnSuccess(ModelResult<Bitmap> result){
                if (result.isStatus()) {
                    holder.photo.setImageBitmap(result.getModel());
                } else{
                    //if failure, show a toast
                    Toast toast = Toast.makeText(context,
                            "Load user icon error: "+result.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        //
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    public void updateFriendList(ArrayList<UserOutline> sports) {
        this.friendsList = sports;
        notifyDataSetChanged();
    }
}
