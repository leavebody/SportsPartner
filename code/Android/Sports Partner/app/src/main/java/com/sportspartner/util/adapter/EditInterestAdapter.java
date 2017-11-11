package com.sportspartner.util.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class EditInterestAdapter extends RecyclerView.Adapter<EditInterestAdapter.MyViewHolder> {
    protected ArrayList<Sport> listInterests;
    protected Intent myIntent;
    protected Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView photo;
        public TextView name;
        public ImageView selectedIcon;
        public RelativeLayout sportRelativeLayout;
        private Context context;

        public MyViewHolder(View view, Context context) {
            super(view);
            this.photo = (ImageView) view.findViewById(R.id.sportIcon);
            this.name = (TextView) view.findViewById(R.id.sportName);
            this.selectedIcon = (ImageView) view.findViewById(R.id.selectedIcon);
            this.context = context;
            this.sportRelativeLayout = (RelativeLayout) view.findViewById(R.id.sport_RelativeLayout);
            sportRelativeLayout.setClickable(true);

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
        this.listInterests = new ArrayList<>(listInterests);
        this.context = context;
    }

    @Override
    public EditInterestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_interest, parent, false);

        return new EditInterestAdapter.MyViewHolder(itemView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final EditInterestAdapter.MyViewHolder holder, int position) {
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

    @Override
    public int getItemCount() {
        return listInterests.size();
    }

    public void updateInterests(ArrayList<Sport> sports) {
        this.listInterests = sports;
        notifyDataSetChanged();
    }

}
