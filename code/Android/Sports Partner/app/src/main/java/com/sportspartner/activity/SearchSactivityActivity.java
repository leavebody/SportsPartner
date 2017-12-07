package com.sportspartner.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.models.SActivity;
import com.sportspartner.models.Sport;
import com.sportspartner.service.ActivityCallBack;
import com.sportspartner.service.ModelResult;
import com.sportspartner.service.ResourceService;
import com.sportspartner.util.PickPlaceResult;
import com.sportspartner.util.listener.MyPickDateListener;
import com.sportspartner.util.listener.MyPickTimeListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author yujiaxiao
 * @author Xiaochen Li
 */
public class SearchSactivityActivity extends BasicActivity implements NumberPicker.OnValueChangeListener {
    //widget
    private TextView textSport;
    private TextView textStartDate;
    private TextView textEndDate;
    private TextView textStartTime;
    private TextView textEndTime;
    private TextView textLocation;
    private TextView textCapacity;
    private EditText editDescription;

    //Calendar
    private Calendar myStratTime;
    private Calendar myEndTime;
    private Calendar myCalendar = Calendar.getInstance();

    //Activity Object
    private SActivity sActivity= new SActivity();
    private ArrayList<Sport> listSports = new ArrayList<Sport>();
    private int sportPosition;
    private String id;
    private Double longitude;
    private Double latitude;
    private String zipcode;
    private String address;


    // The object being sent and received from map
    private PickPlaceResult pickPlaceResult;

    /**
     * OnCreate Method of thie Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup content = (ViewGroup) findViewById(R.id.layout_home);
        getLayoutInflater().inflate(R.layout.activity_search_sportsactivity, content, true);

        //set title of toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Search an Activity");

        //set the title
        View viewSmilar = (View) findViewById(R.id.title_search_result);
        TextView titleupComming = (TextView) viewSmilar.findViewById(R.id.title);
        titleupComming.setText("Search Results");

        myStratTime = Calendar.getInstance();
        myEndTime = Calendar.getInstance();

        DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String date123 = df.format(Calendar.getInstance().getTime());

        pickPlaceResult = new PickPlaceResult();

        // get all sports
        ResourceService.getAllSports(this, new ActivityCallBack<ArrayList<Sport>>() {
            @Override
            public void getModelOnSuccess(ModelResult<ArrayList<Sport>> result) {
                if (!result.isStatus()) {
                    Log.e("SearchActivity","Load sports Error: " + result.getMessage());
                    listSports = new ArrayList<>();
                    return;
                }
                listSports = result.getModel();
            }
        });


        //find widget by Id
        textSport= (TextView) findViewById(R.id.edit_sport);
        textStartDate= (TextView) findViewById(R.id.edit_create_startDate);
        textEndDate= (TextView) findViewById(R.id.edit_create_endDate);
        textStartTime= (TextView) findViewById(R.id.edit_create_starttime);
        textEndTime= (TextView) findViewById(R.id.edit_create_endtime);
        textLocation= (TextView) findViewById(R.id.edit_create_location);
        textCapacity= (TextView) findViewById(R.id.edit_create_capacity);
        editDescription= (EditText) findViewById(R.id.edit_create_discription);

        //set onCLick Listener
        textSport.setOnClickListener(mySportListener);
        textStartDate.setOnClickListener(new MyPickDateListener(SearchSactivityActivity.this, myStratTime, textStartDate));
        textEndDate.setOnClickListener(new MyPickDateListener(SearchSactivityActivity.this, myEndTime, textEndDate));
        textStartTime.setOnClickListener(new MyPickTimeListener(SearchSactivityActivity.this, myStratTime, textStartTime) );
        textEndTime.setOnClickListener(new MyPickTimeListener(SearchSactivityActivity.this, myEndTime, textEndTime));
        textLocation.setOnClickListener(myLocationListener);
        textCapacity.setOnClickListener(myCapacityListener);

    }

     /**
     * Show the NumberPicker Dialog
     * Set the content of the textView according to selection result of the user
     * @param strings    The String shows in the NumberPicker
     * @param textView  The textView which should be changed
     */
    //all types of listener
    private void showDialog(String[] strings, final TextView textView){
        final Dialog d = new Dialog(SearchSactivityActivity.this);
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.layout_dialog);
        final Button b1 = (Button) d.findViewById(R.id.dialog_button_set);
        Button b2 = (Button) d.findViewById(R.id.dialog_button_cancel);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.dialog_numPicker);
        np.setDisplayedValues(strings);
        np.setMinValue(0);
        np.setMaxValue(strings.length - 1);
        np.setWrapSelectorWheel(true);
        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                textView.setText(np.getDisplayedValues()[np.getValue()]);
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }

    /**
     * mySportListener:
     * An object of OnClickListener,
     * Set the content of the string, which will be shown in the Dialog
     * Show a dialog
     */
    private View.OnClickListener mySportListener = new View.OnClickListener() {
        public void onClick(View v) {
            String[] sports = new String[listSports.size()];
            for (int i = 0; i < listSports.size(); i++){
                sports[i] = listSports.get(i).getSportName();
            }
            showDialog(sports, textSport);
        }
    };

    /**
     * myCapacityListener:
     * An object of OnClickListener,
     * Set the content of the string, which will be shown in the Dialog
     * Show a dialog
     */
    private View.OnClickListener myCapacityListener = new View.OnClickListener() {
        public void onClick(View v) {
            String[] capacity = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
            showDialog(capacity, textCapacity);
        }
    };

    /**
     * myLocationListener:
     * Call the GOOGLE MAP API
     */
    private View.OnClickListener myLocationListener = new View.OnClickListener() {
        public void onClick(View v) {
            // start the map activity
            Intent intent = new Intent(SearchSactivityActivity.this, MapActivity.class);
            intent.putExtra("PickPlaceResult", pickPlaceResult);
            startActivityForResult(intent, 1);
        }
    };
    /**
     * get the data from the inner activity
     * @param requestCode
     * @param resultCode
     * @param data data from the inner activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();
                if (b != null) {
                    pickPlaceResult = (PickPlaceResult) b.getSerializable("PickPlaceResult");

                    if (pickPlaceResult.isFacility()){
                        //Todo get id
                        id = "NULL";
                        latitude = 0.0;
                        longitude = 0.0;
                        zipcode = "00000";
                        address = pickPlaceResult.getName();
                        textLocation.setText(address);
                    } else {
                        zipcode = pickPlaceResult.getZipCode();
                        id = "NULL";
                        latitude = pickPlaceResult.getLatLng().latitude;
                        longitude = pickPlaceResult.getLatLng().longitude;
                        address = pickPlaceResult.getName();
                        textLocation.setText(address);
                    }
                }
            } else if (resultCode == 0) {
                Toast.makeText(this,"RESULT CANCELLED", Toast.LENGTH_LONG).show();
            }
        }
    }
    /**
     * Onclick Listener of "Search" button
     * @param v
     */
    public void SearchActivity(View v){
        //TODO
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    @Override
    public void onPause(){
        super.onPause();
        finish();
    }

}
