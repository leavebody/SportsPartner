package com.sportspartner.util.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sportspartner.models.SActivityOutline;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.sportspartner.R;
import com.sportspartner.service.ActivityCallBack;
import com.sportspartner.service.ModelResult;
import com.sportspartner.service.ResourceService;

/**
 * Created by yujiaxiao on 10/21/17.
 */

public class MyActivityAdapter extends BaseAdapter {
    private Context myContext;
    private LayoutInflater inflater;
    private ArrayList<SActivityOutline> activityItems;

    public MyActivityAdapter(){}

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
        this.myContext = context;
        activityItems = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public MyActivityAdapter(Context context) {
        this.myContext = context;
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

        //populate each element with relevant data
        SActivityOutline activity = (SActivityOutline) getItem(position);

        return setView(rowView, activity);
    }

    /**
     * Set the content of the activity outline view.
     * @param rowView The view of the activity outline.
     * @param activity The ActivityOutline object.
     * @return
     */
    public View setView(View rowView, SActivityOutline activity){
        //get element from the layout
        final ImageView activityPhoto = (ImageView) rowView.findViewById(R.id.activity_photo);
        TextView sportName = (TextView) rowView.findViewById(R.id.sport_name);
        TextView activityDate = (TextView) rowView.findViewById(R.id.activity_date);
        TextView activityTime = (TextView) rowView.findViewById(R.id.activity_time);
        TextView activityLocation = (TextView) rowView.findViewById(R.id.activity_location);
        TextView activityMember = (TextView) rowView.findViewById(R.id.activity_member);

        ResourceService.getImage(myContext, activity.getSportIconUUID(), ResourceService.IMAGE_ORIGIN,
                new ActivityCallBack<Bitmap>(){
                    @Override
                    public void getModelOnSuccess(ModelResult<Bitmap> modelResult) {
                        if (modelResult.isStatus()) {
                            activityPhoto.setImageBitmap(modelResult.getModel());
                        }
                        else{
                            //if failure, show a toast
                            Toast.makeText(myContext, "Load Activity icon error: "+modelResult.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

        sportName.setText(activity.getSportName());

        //Parse time
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.US);
        String startDate = df.format(activity.getStartTime());
        String endDate = df.format(activity.getEndTime());

        activityDate.setText(startDate);
        activityTime.setText(endDate);

        activityLocation.setText(activity.getAddress());
        String curCapacity = String.valueOf(activity.getSize()) + "/" + String.valueOf(activity.getCapacity());
        activityMember.setText(curCapacity);


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
