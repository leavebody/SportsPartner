package com.sportspartner.util.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.activity.NotificationActivity;
import com.sportspartner.activity.ProfileActivity;
import com.sportspartner.activity.SactivityDetailActivity;
import com.sportspartner.models.Notification;
import com.sportspartner.models.Profile;
import com.sportspartner.service.ActivityCallBack;
import com.sportspartner.service.FriendService;
import com.sportspartner.service.ModelResult;
import com.sportspartner.service.ProfileService;
import com.sportspartner.service.ResourceService;
import com.sportspartner.util.DBHelper.LoginDBHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yujiaxiao on 11/11/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private String myEmail;
    private ArrayList<Notification> notiList;
    private Intent myIntent;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView photo;
        public TextView title;
        public TextView detail;
        public LinearLayout outline;
        public RelativeLayout relativeLayout;
        private Context context;

        public MyViewHolder(View view, Context context) {
            super(view);
            this.photo = (ImageView) view.findViewById(R.id.noti_icon);
            this.title = (TextView) view.findViewById(R.id.noti_title);
            this.detail = (TextView) view.findViewById(R.id.noti_detail);
            this.outline = (LinearLayout) view.findViewById(R.id.noti_outline);
            this.relativeLayout = (RelativeLayout) view.findViewById(R.id.noti_RelativeLayout);
            this.context = context;
            view.setOnClickListener(this);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                Notification noti = notiList.get(position);
                if (!noti.getRead()) {
                    relativeLayout.setBackgroundResource(R.color.white);
                    photo.setBackgroundResource(R.drawable.read);
                    noti.setRead(true);
                }
                try {
                    if(noti.getPriority()==0 || noti.getPriority()==21 || noti.getPriority()==22){
                        // if the notification is one of upcoming activity notification(0), accept activity application(21), decline activity application(22)
                        // click will lead to the activity detail page
                        final Intent intent = new Intent(context, SactivityDetailActivity.class);
                        String activityId = new JSONObject(noti.getDetail()).getString("activityId");
                        intent.putExtra("activityId",activityId);
                        context.startActivity(intent);
                    }
                    noti.showDialog(context, myEmail);
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }

    }


    public NotificationAdapter(ArrayList<Notification> notiList, Context context) {
        this.notiList = notiList;
        this.context = context;
        LoginDBHelper dbHelper = LoginDBHelper.getInstance(context);
        this.myEmail = dbHelper.getEmail();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_notification, parent, false);

        return new MyViewHolder(itemView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Notification noti = notiList.get(position);

        holder.title.setText(noti.getTitle());
        try {
            holder.detail.setText(new JSONObject(noti.getDetail()).getString("detail"));
        }catch(JSONException ex){
            ex.printStackTrace();
        }
        holder.photo.setBackgroundResource(R.drawable.message);

    }

    @Override
    public int getItemCount() {
        return notiList.size();
    }

    public void updateNotiList(ArrayList<Notification> sports) {
        this.notiList = sports;
        notifyDataSetChanged();
    }
}
