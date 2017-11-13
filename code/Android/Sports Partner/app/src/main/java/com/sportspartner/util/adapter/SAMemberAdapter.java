package com.sportspartner.util.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.models.UserOutline;
import com.sportspartner.service.ActivityCallBack;
import com.sportspartner.service.ModelResult;
import com.sportspartner.service.ResourceService;

import java.util.ArrayList;

/**
 * Created by yujiaxiao on 11/12/17.
 */

public class SAMemberAdapter extends RecyclerView.Adapter<SAMemberAdapter.MyViewHolder>{

    protected ArrayList<UserOutline> listMember;
    protected Intent myIntent;
    protected Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView photo;
        public ImageView selectedIcon;
        private Context context;

        public MyViewHolder(View view, Context context) {
            super(view);
            this.photo = (ImageView) view.findViewById(R.id.interest_sportIcon);
            this.selectedIcon = (ImageView) view.findViewById(R.id.selectedIcon);
            this.context = context;
            view.setOnClickListener(this);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {

        }
    }

    public SAMemberAdapter(){}


    public SAMemberAdapter(ArrayList<UserOutline> listMember, Context context) {
        this.listMember = new ArrayList<>(listMember);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_interest, parent, false);

        return new MyViewHolder(itemView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        UserOutline member = listMember.get(position);

        //set photo
        String iconUUID = member.getIconUUID();
        Log.d("Member UUID", String.valueOf(iconUUID));
        ResourceService.getImage(context, iconUUID, ResourceService.IMAGE_SMALL, new ActivityCallBack<Bitmap>(){
            @Override
            public void getModelOnSuccess(ModelResult<Bitmap> result){
                if (result.isStatus()) {
                    holder.photo.setImageBitmap(result.getModel());
                } else{
                    //if failure, show a toast
                    Toast.makeText(context,
                            "Load Member icon error: "+result.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listMember.size();
    }

    public void updateMembers(ArrayList<UserOutline> sports) {
        this.listMember = sports;
        notifyDataSetChanged();
    }
}

