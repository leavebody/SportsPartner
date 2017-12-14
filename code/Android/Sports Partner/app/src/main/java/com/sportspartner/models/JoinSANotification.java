package com.sportspartner.models;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.service.ActivityCallBack;
import com.sportspartner.service.ActivityService;
import com.sportspartner.service.FriendService;
import com.sportspartner.service.ModelResult;
import com.sportspartner.service.ProfileService;
import com.sportspartner.service.ResourceService;
import com.sportspartner.util.adapter.MyActivityAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by xuanzhang on 12/1/17.
 */

public class JoinSANotification extends Notification {

    public JoinSANotification(String uuid, String title, String detail, String sender, String type, Date date, int priority, Boolean isread){
        super(uuid, title, detail, sender, type, date, priority, isread);
    }

    private MyActivityAdapter activityAdapter;

    public void showDialog(final Context context, final String myEmail) throws JSONException{
        final Dialog dialog = new Dialog(context);
        //set content
        dialog.setTitle(getTitle());
        dialog.setContentView(R.layout.layout_dialog_jsa_notification);

        //find widgets
        activityAdapter = new MyActivityAdapter(context);
        final TextView contentText = (TextView) dialog.findViewById(R.id.noti_detail);
        final ImageView userPhoto = (ImageView) dialog.findViewById(R.id.noti_icon);
        final View activity_outline = (View) dialog.findViewById(R.id.noti_outline);
        final Button ok = (Button) dialog.findViewById(R.id.save);
        final Button cancel = (Button) dialog.findViewById(R.id.cancel);
        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.RecyclerView);

        //set detail and photos
        String jsaDetail = new JSONObject(getDetail()).getString("detail");
        contentText.setText(jsaDetail);

        ProfileService.getProfileInfo(context, getSender(), new ActivityCallBack<Profile>() {
            @Override
            public void getModelOnSuccess(ModelResult<Profile> userProfileResult) {
                // handle the result of request here
                String message = userProfileResult.getMessage();
                Boolean status = userProfileResult.isStatus();
                Profile profile;

                if (status) {
                    //if successfully get the data, then set the data
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


        // set activity outline
        final String activityId = new JSONObject(getDetail()).getString("activityId");
        ActivityService.getSActivityOutline(context, activityId, new ActivityCallBack<SActivityOutline>(){
            @Override
            public void getModelOnSuccess(ModelResult<SActivityOutline> result){
                if(result.isStatus()) {
                    activityAdapter.setView(activity_outline, result.getModel());
                }else{
                    //if failure, show a toast
                    Toast toast = Toast.makeText(context, "Load activity outline error: " + result.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        if (getType().equals("INTERACTION")) {
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
                if (getType().equals("INTERACTION")) {
                    //accept the activity joining application
                    ActivityService.acceptJoinActivity(context, activityId, getSender(), new ActivityCallBack() {
                        @Override
                        public void getModelOnSuccess(ModelResult modelResult) {
                            if(!modelResult.isStatus()){
                                Toast toast = Toast.makeText(context,
                                        "Accept Join Activity error: " + modelResult.getMessage(), Toast.LENGTH_LONG);
                                toast.show();
                            }
                            else{
                                Toast.makeText(context, "Accepted the application ", Toast.LENGTH_LONG).show();
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
                //decline join activity application
                ActivityService.declineJoinActivity(context, activityId, getSender(), new ActivityCallBack(){
                    @Override
                    public void getModelOnSuccess(ModelResult modelResult){
                        if(!modelResult.isStatus()){
                            Toast toast = Toast.makeText(context,
                                    "Decline Activity Application error: " + modelResult.getMessage(), Toast.LENGTH_LONG);
                            toast.show();
                        }else{
                            Toast.makeText(context, "Declined the application ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
