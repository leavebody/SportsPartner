package com.sportspartner.util.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sportspartner.R;
import java.util.ArrayList;

/**
 * @author Xiaochen Li
 */

public class AddressesListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<String> addresses;
    public AddressesListViewAdapter(Context context, ArrayList<String> addresses) {
        context = context;
        this.addresses = addresses;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    /**
     * Get the size of the content
     * @return size
     */
    @Override
    public int getCount() {
        return addresses.size();
    }

    /**
     * Get the item in the specific position
     * @param position The position of the item
     * @return The item object
     */
    @Override
    public Object getItem(int position) {
        return addresses.get(position);
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
        View rowView = inflater.inflate(R.layout.address_item, parent, false);

        TextView sportName = (TextView) rowView.findViewById(R.id.address_item);

        sportName.setText((String) getItem(position));

        return rowView;
    }

    public String getText(int position){
        return (String) getItem(position);
    }

}
