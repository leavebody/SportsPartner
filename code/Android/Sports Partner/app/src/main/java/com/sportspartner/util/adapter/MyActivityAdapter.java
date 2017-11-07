package com.sportspartner.util.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sportspartner.models.SActivityOutline;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.sportspartner.R;

/**
 * Created by yujiaxiao on 10/21/17.
 */

public class MyActivityAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<SActivityOutline> activityItems;

    public ArrayList<SActivityOutline> getActivityItems() {
        return activityItems;
    }

    public SActivityOutline getActivityByindex(int position){
        return activityItems.get(position);
    }

    /**
     * The constructor of MyActivityAdapter
     * @param context The Activity which calls this method
     * @param items The context which will be filled into the list
     */
    public MyActivityAdapter(Context context, ArrayList<SActivityOutline> items) {
        context = context;
        activityItems = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Set the height of ListView
     * @param listView The listView of which we wants to change height
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    /**
     * Get the size of the content
     * @return size
     */
    @Override
    public int getCount() {
        return activityItems.size();
    }

    /**
     * Get the item in the specific position
     * @param position The position of the item
     * @return The item object
     */
    @Override
    public Object getItem(int position) {
        return activityItems.get(position);
    }

    /**
     * Get the ItemId
     * @param position The position of the item
     * @return The item id
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Fill the content of the list
     * @param position position of the content
     * @param convertView
     * @param parent The parent View
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get view for row item
        View rowView = inflater.inflate(R.layout.layout_activity, parent, false);

        // Get view for row item
        //if (convertView == null)
            //convertView = inflater.inflate(R.layout.layout_activity, parent, false);

        //get element from the layout
        //TODO picture, time
        //ImageView activityPhoto = (ImageView) convertView.findViewById(R.id.activity_photo);
        TextView sportName = (TextView) rowView.findViewById(R.id.sport_name);
        TextView activityDate = (TextView) rowView.findViewById(R.id.activity_date);
        TextView activityTime = (TextView) rowView.findViewById(R.id.activity_time);
        TextView activityLocation = (TextView) rowView.findViewById(R.id.activity_location);
        TextView activityMember = (TextView) rowView.findViewById(R.id.activity_member);

        //populate each element with relevant data
        SActivityOutline activity = (SActivityOutline) getItem(position);

        //TODO picture
        //Picasso.with(context).load(activity.getSportPic()).placeholder(R.mipmap.ic_launcher).into(activityPhoto);
        sportName.setText(activity.getSportName());

        //Parse time
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.US);
        String startDate = df.format(activity.getStartTime());
        String endDate = df.format(activity.getEndTime());

        activityDate.setText(startDate);
        activityTime.setText(endDate);
        //TODO wait the change of backend
        //activityLocation.setText(activity.getLocation());
        activityLocation.setText("JHU RECREATION CENTER");
        String curCapacity = String.valueOf(activity.getSize()) + "/" + String.valueOf(activity.getCapacity());
        activityMember.setText(curCapacity);

        //TODO move to create activity whether the endDate is after StartDate
        /*SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date strDate = sdf.parse(valid_until);
        if (new Date().after(strDate)) {
            catalog_outdated = 1;
        }*/

        return rowView;
    }

    /**
     * Add the item to the list
     * @param added The item we want to add
     */
    public void appendList(ArrayList<SActivityOutline> added){
        for (SActivityOutline sa : added) {
            activityItems.add(sa);
        }
    }

}
