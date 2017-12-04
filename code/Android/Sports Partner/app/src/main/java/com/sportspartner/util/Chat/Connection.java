package com.sportspartner.util.Chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.sportspartner.activity.HomeActivity;
import com.sportspartner.models.UserOutline;
import com.sportspartner.service.ActivityCallBack;
import com.sportspartner.service.ModelResult;
import com.sportspartner.service.ProfileService;
import com.sportspartner.service.ResourceService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by zhxiao on 12/1/17.
 */

public class Connection {

    public static void connectSendBird(final Context context,final String usermail){

        ProfileService.getProfileOutline(context, usermail, new ActivityCallBack<UserOutline>(){
            @Override
            public void getModelOnSuccess(ModelResult<UserOutline> modelResult){
                if (modelResult.isStatus()){
                    final String userName = modelResult.getModel().getUserName();
                    ResourceService.getImage(context,modelResult.getModel().getIconUUID(),ResourceService.IMAGE_SMALL,new ActivityCallBack<Bitmap>(){
                        @Override
                        public void getModelOnSuccess(ModelResult<Bitmap> modelResult){
                            if (modelResult.isStatus()){
                                Bitmap profileImage = modelResult.getModel();
                                Connection.connectToSendBird(context,usermail,userName,profileImage);
                            }
                        }

                    });

                }
            }
        });
    }

    public static void updateSendBird(final Context context,final String usermail){

        ProfileService.getProfileOutline(context, usermail, new ActivityCallBack<UserOutline>(){
            @Override
            public void getModelOnSuccess(ModelResult<UserOutline> modelResult){
                if (modelResult.isStatus()){
                    final String userName = modelResult.getModel().getUserName();
                    ResourceService.getImage(context,modelResult.getModel().getIconUUID(),ResourceService.IMAGE_SMALL,new ActivityCallBack<Bitmap>(){
                        @Override
                        public void getModelOnSuccess(ModelResult<Bitmap> modelResult){
                            if (modelResult.isStatus()){
                                Bitmap profileImage = modelResult.getModel();
                                Connection.connectToSendBird(context,usermail,userName,profileImage);
                                Connection.updateCurrentUserInfo(context,userName);
                                Connection.updateCurrentUserProfileImage(context,profileImage);
                            }
                        }

                    });

                }
            }
        });
    }

    public static void connectToSendBird(final Context context, final String userId, final String userNickname, final Bitmap profileImage) {
        // Show the loading indicator


        SendBird.connect(userId, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                // Callback received; hide the progress bar.

                if (e != null) {
                    // Error!
                    Toast.makeText(
                            context, "" + e.getCode() + ": " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();

                    // Show login failure snackbar
                    return;
                }

                PreferenceUtils.setNickname(context, user.getNickname());
                PreferenceUtils.setProfileUrl(context, user.getProfileUrl());
                PreferenceUtils.setConnected(context, true);

                // Update the user's nickname
                updateCurrentUserInfo(context,userNickname);
                updateCurrentUserProfileImage(context,profileImage);


                updateCurrentUserPushToken(context);
//
//                // Proceed to MainActivity
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
    }

    public static void updateCurrentUserInfo(final Context context,final String userNickname) {
        SendBird.updateCurrentUserInfo(userNickname, null, new SendBird.UserInfoUpdateHandler() {
            @Override
            public void onUpdated(SendBirdException e) {
                if (e != null) {
                    // Error!

                    // Show update failed snackbar

                    return;
                }

                PreferenceUtils.setNickname(context, userNickname);
            }
        });
    }
    public static void updateCurrentUserPushToken(final Context context) {
        PushUtils.registerPushTokenForCurrentUser(context, null);
    }

    public static void updateCurrentUserProfileImage(final Context context, final Bitmap profileImage) {
        final String nickname = PreferenceUtils.getNickname(context);

        try {
            File f = new File(context.getCacheDir(), "temp");
            f.createNewFile();

//Convert bitmap to byte array
            Bitmap bitmap = profileImage;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            SendBird.updateCurrentUserInfoWithProfileImage(nickname, f, new SendBird.UserInfoUpdateHandler() {
                @Override
                public void onUpdated(SendBirdException e) {
                    if (e != null) {
                        // Error!
                        Toast.makeText(context, "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        // Show update failed snackbar
                        return;
                    }
                    try {
                        PreferenceUtils.setProfileUrl(context, SendBird.getCurrentUser().getProfileUrl());
                        //ImageUtils.displayRoundImageFromUrlWithoutCache(context, Uri.fromFile(profileImage).toString(), imageView);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
        catch (java.io.IOException ioexception){
            Toast.makeText(
                    context,   ioexception.getMessage(),
                    Toast.LENGTH_SHORT)
                    .show();
        }






    }
}
