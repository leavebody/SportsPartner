package com.sportspartner.util.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.models.Notification;
import com.sportspartner.models.Profile;
import com.sportspartner.service.ActivityCallBack;
import com.sportspartner.service.FriendService;
import com.sportspartner.service.ModelResult;
import com.sportspartner.service.ProfileService;
import com.sportspartner.service.ResourceService;
import com.sportspartner.util.DBHelper.LoginDBHelper;

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
        public RelativeLayout relativeLayout;
        private Context context;

        public MyViewHolder(View view, Context context) {
            super(view);
            this.photo = (ImageView) view.findViewById(R.id.noti_icon);
            this.title = (TextView) view.findViewById(R.id.noti_title);
            this.detail = (TextView) view.findViewById(R.id.noti_detail);
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
                showDialog(noti);
            }
        }

        private void showDialog(final Notification noti) {
            final Dialog dialog = new Dialog(context);
            //set content
            dialog.setTitle(noti.getTitle());
            dialog.setContentView(R.layout.layout_dialog_notification);

            //find widgets
            final TextView contentText = (TextView) dialog.findViewById(R.id.noti_detail);
            final ImageView userPhoto = (ImageView) dialog.findViewById(R.id.noti_icon);
            final Button ok = (Button) dialog.findViewById(R.id.save);
            final Button cancel = (Button) dialog.findViewById(R.id.cancel);
            final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.RecyclerView);

            //set detail and photo
            contentText.setText(noti.getDetail());

            ProfileService.getProfileInfo(context, noti.getSender(), new ActivityCallBack<Profile>() {
                @Override
                public void getModelOnSuccess(ModelResult<Profile> userProfileResult) {
                    // handle the result of request here
                    String message = userProfileResult.getMessage();
                    Boolean status = userProfileResult.isStatus();
                    Profile profile;

                    if (status) {
                        //if successfully get the data, then get the data
                        profile = userProfileResult.getModel();

                        //set profile photo
                        String iconUUID = profile.getIconUUID();
                        ResourceService.getImage(context, iconUUID, ResourceService.IMAGE_SMALL, new ActivityCallBack<Bitmap>() {
                            @Override
                            public void getModelOnSuccess(ModelResult<Bitmap> result) {
                                if (result.isStatus()) {
                                    userPhoto.setImageBitmap(result.getModel());
                                } else {
                                    //if failure, show a toast
                                    Toast toast = Toast.makeText(context,
                                            "Load user icon error: " + result.getMessage(), Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }
                        });
                    } else {
                        //if failure, show a toast
                        Toast toast = Toast.makeText(context, "Load ProfileInfo Error: " + message, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            });

            //Todo switch dialog according to the priority
            if (noti.getType().equals("INTERACTION")) {
                ok.setText("Accept");
                cancel.setText("Reject");
                ok.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
            } else {
                ok.setText("Ok");
                ok.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.INVISIBLE);
            }

            //Set ClickListener
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (noti.getType().equals("INTERACTION")) {
                        //accept the friend request
                        FriendService.acceptFriendRequest(context, myEmail, noti.getSender(), new ActivityCallBack() {
                            @Override
                            public void getModelOnSuccess(ModelResult modelResult) {
                                if (!modelResult.isStatus()) {
                                    Toast toast = Toast.makeText(context,
                                            "Accept Friend Request error: " + modelResult.getMessage(), Toast.LENGTH_LONG);
                                    toast.show();
                                }
                                else {
                                    Toast.makeText(context, "Add a new friend successfully! ", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    dialog.dismiss();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //decline friend request
                    FriendService.declineFriendRequest(context, noti.getSender(), myEmail, new ActivityCallBack() {
                        @Override
                        public void getModelOnSuccess(ModelResult modelResult) {
                            if (!modelResult.isStatus()) {
                                Toast toast = Toast.makeText(context,
                                        "Decline Friend Request error: " + modelResult.getMessage(), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    });
                    dialog.dismiss();
                }
            });
            dialog.show();
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
        holder.detail.setText(noti.getDetail());
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
