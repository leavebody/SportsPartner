package com.sportspartner.util.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.activity.BasicActivity;
import com.sportspartner.activity.ProfileActivity;
import com.sportspartner.models.UserOutline;
import com.sportspartner.service.ResourceService;
import com.sportspartner.service.serviceresult.ModelResult;
import com.sportspartner.util.ActivityCallBack;

import java.util.ArrayList;

/**
 * Created by yujiaxiao on 11/8/17.
 */

public class MemberPhotoAdapter extends RecyclerView.Adapter<MemberPhotoAdapter.MyViewHolder> {

private ArrayList<UserOutline> memberList;
private Intent myIntent;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView photo;
    private Context context;

    public MyViewHolder(View view, Context context) {
        super(view);
        this.photo = (ImageView) view.findViewById(R.id.memberPhoto);
        this.context = context;
        view.setOnClickListener(this);
    }

    // Handles the row being being clicked
    @Override
    public void onClick(View view) {
        int position = getAdapterPosition(); // gets item position
        if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
            UserOutline member = memberList.get(position);
            myIntent = new Intent(context, ProfileActivity.class);
            myIntent.putExtra("userId",member.getUserId());
            context.startActivity(myIntent);
        }
    }
}

    public MemberPhotoAdapter(ArrayList<UserOutline> memberList) {
        this.memberList = memberList;
    }

    @Override
    public MemberPhotoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_member, parent, false);

        return new MemberPhotoAdapter.MyViewHolder(itemView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final MemberPhotoAdapter.MyViewHolder holder, int position) {
        UserOutline member = memberList.get(position);
        //TODO photo
        String iconUUID = member.getIconUUID();
        ResourceService.getImage(holder.context, iconUUID, ResourceService.IMAGE_SMALL, new ActivityCallBack<Bitmap>(){
            @Override
            public void getModelOnSuccess(ModelResult<Bitmap> result){
                if (result.isStatus()) {
                    holder.photo.setImageBitmap(result.getModel());
                } else{
                    //if failure, show a toast
                    Toast toast = Toast.makeText(holder.context,
                            "Load user icon error: "+result.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

}