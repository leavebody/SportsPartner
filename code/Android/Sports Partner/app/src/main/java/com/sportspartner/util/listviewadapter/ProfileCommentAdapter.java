package com.sportspartner.util.listviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sportspartner.R;
import com.sportspartner.models.ProfileComment;

import java.util.ArrayList;

/**
 * Created by yujiaxiao on 10/24/17.
 */

public class ProfileCommentAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ProfileComment> commentItems;

    public ProfileCommentAdapter(Context context, ArrayList<ProfileComment> items) {
        context = context;
        commentItems = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return commentItems.size();
    }

    @Override
    public Object getItem(int position) {
        return commentItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get view for row item
        View rowView = inflater.inflate(R.layout.layout_comment, parent, false);

        // Get view for row item
        //if (convertView == null)
        //convertView = inflater.inflate(R.layout.layout_activity, parent, false);

        //get element from the layout
        TextView  profileComment= (TextView) rowView.findViewById(R.id.profile_comment);

        //populate each element with relevant data
        ProfileComment comment = (ProfileComment) getItem(position);

        //TODO picture,time
        //Picasso.with(context).load(activity.getSportPic()).placeholder(R.mipmap.ic_launcher).into(activityPhoto);

        //Todo time
        profileComment.setText(comment.getContent());

        return rowView;
    }
}
