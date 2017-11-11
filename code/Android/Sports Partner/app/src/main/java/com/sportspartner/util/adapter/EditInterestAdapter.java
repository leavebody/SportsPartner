package com.sportspartner.util.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.models.Sport;
import com.sportspartner.service.ResourceService;
import com.sportspartner.service.serviceresult.ModelResult;
import com.sportspartner.util.ActivityCallBack;

import java.util.ArrayList;

/**
 * Created by yujiaxiao on 11/10/17.
 */

public class EditInterestAdapter extends InterestAdapter {

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView photo;
        public TextView name;
        public ImageView selectedIcon;
        private Context context;

        public MyViewHolder(View view, Context context) {
            super(view);
            this.photo = (ImageView) view.findViewById(R.id.sportIcon);
            this.name = (TextView) view.findViewById(R.id.sportName);
            this.selectedIcon = (ImageView) view.findViewById(R.id.selectedIcon);
            this.context = context;
            view.setOnClickListener(this);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                Sport sport = listInterests.get(position);
                if (sport.getSelected()){
                    sport.setSelected(false);
                    selectedIcon.setVisibility(View.INVISIBLE);
                }
                else{
                    sport.setSelected(true);
                    selectedIcon.setVisibility(View.VISIBLE);
                }
            }

        }

    }

    public EditInterestAdapter(){}


    public EditInterestAdapter(ArrayList<Sport> listInterests, Context context) {
        super();
    }

    @Override
    public void onBindViewHolder(final InterestAdapter.MyViewHolder holder, int position) {
        Sport sport = listInterests.get(position);

        if (sport.getSelected())
            holder.selectedIcon.setVisibility(View.VISIBLE);

        holder.name.setText(sport.getSportName());
        //set photo
        String iconUUID = sport.getSportIconUUID();
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

    }

}
