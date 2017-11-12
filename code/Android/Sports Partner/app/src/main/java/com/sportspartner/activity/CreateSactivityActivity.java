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
import com.sportspartner.service.ActivityService;
import com.sportspartner.service.ResourceService;
import com.sportspartner.service.ModelResult;
import com.sportspartner.service.ActivityCallBack;
import com.sportspartner.util.LoginDBHelper;
import com.sportspartner.util.PickPlaceResult;
import com.sportspartner.util.listener.MyPickDateListener;
import com.sportspartner.util.listener.MyPickTimeListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateSactivityActivity extends BasicActivity implements NumberPicker.OnValueChangeListener {
    //Android widget
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

    //myEmail
    private String myEmail;

    /**
     * Load the Create Sactivity Activities
     * Find widget by Id
     * Set the listener
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup content = (ViewGroup) findViewById(R.id.layout_home);
        getLayoutInflater().inflate(R.layout.activity_create_sactivity, content, true);

        //set title of toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Create a New Activity");

        // get all sports
        ResourceService.getAllSports(this, new ActivityCallBack<ArrayList<Sport>>() {
            @Override
            public void getModelOnSuccess(ModelResult<ArrayList<Sport>> result) {
                loadAllSportsHandler(result);
            }
        });

        //set the title
        View viewSmilar = (View) findViewById(R.id.title_similar_result);
        TextView titleupComming = (TextView) viewSmilar.findViewById(R.id.title);
        titleupComming.setText("Similar Activity");

        myStratTime = Calendar.getInstance();
        myEndTime = Calendar.getInstance();

        DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String date123 = df.format(Calendar.getInstance().getTime());

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
        textStartDate.setOnClickListener(new MyPickDateListener(CreateSactivityActivity.this, myStratTime, textStartDate));
        textEndDate.setOnClickListener(new MyPickDateListener(CreateSactivityActivity.this, myEndTime, textEndDate));
        textStartTime.setOnClickListener(new MyPickTimeListener(CreateSactivityActivity.this, myStratTime, textStartTime) );
        textEndTime.setOnClickListener(new MyPickTimeListener(CreateSactivityActivity.this, myEndTime, textEndTime));
        textLocation.setOnClickListener(myLocationListener);
        textCapacity.setOnClickListener(myCapacityListener);

        pickPlaceResult = new PickPlaceResult();

    }

    private void loadAllSportsHandler(ModelResult<ArrayList<Sport>> result) {
        // handle the result of request here
        String message = result.getMessage();
        Boolean status = result.isStatus();

        if (status){
            //if successfully get Activity, get the data
            listSports = new ArrayList<>(result.getModel());
        }
        else {
            //if failure, show a toast
            Toast toast = Toast.makeText(CreateSactivityActivity.this, "Load sports Error: " + message, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * Show the NumberPicker Dialog
     * Set the content of the textView according to selection result of the user
     * @param strings    The String shows in the NumberPicker
     * @param textView  The textView which should be changed
     */
    //all types of listener
    private void showDialog(String[] strings, final TextView textView){
        final Dialog d = new Dialog(CreateSactivityActivity.this);
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
                sportPosition = np.getValue()%listSports.size();
                d.dismiss();
                Log.d("CreateActivity sportPos",String.valueOf(sportPosition));
                Log.d("CreateActivity sport",String.valueOf(listSports.get(sportPosition).getSportName()));
                Log.d("CreateActivity sportId",String.valueOf(listSports.get(sportPosition).getSportId()));
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

    /**
     * A function needed by the setOnValueChangedListener function
     * which is in side the showDialog function
     * @param picker
     * @param oldVal
     * @param newVal
     */
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
            //String[] sports = new String[] { "Football", "Basketball", "Badminton", "Lacrosse", "Swimming", "Soccer", "Climbing", "Running"};
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
            String[] capacity = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
            showDialog(capacity, textCapacity);
        }
    };

    /**
     * myLocationListener:
     * An object of OnClickListener,
     * Call GOOGLE MAP API
     */
    private View.OnClickListener myLocationListener = new View.OnClickListener() {
        public void onClick(View v) {
            // start the map activity
            Intent intent = new Intent(CreateSactivityActivity.this, MapActivity.class);
            intent.putExtra("PickPlaceResult", pickPlaceResult);
            startActivityForResult(intent, 1);
        }
    };

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
                        //Todo Zipcode
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
     * On click listener of the Create button
     * Send the request to create the sports activity
     * @param v The Create layout
     */
    public void CreateActivity(View v) throws ParseException {
        boolean isValid = checkAllFileds();

        if(isValid){
            //ToDO faclity ID LO La Zipcode
            String detail;

            //get creatorId
            LoginDBHelper dbHelper = LoginDBHelper.getInstance(this);
            myEmail= dbHelper.getEmail();

            //set detail
            if(editDescription.getText().equals("")){
                detail = "NULL";
            }
            else {
                detail = String.valueOf(editDescription.getText());
            }

            //set the fields of sActivity
            sActivity.setActivityId("NULL");
            sActivity.setStatus("OPEN");
            sActivity.setSportId(listSports.get(sportPosition).getSportId());
            sActivity.setCapacity(Integer.parseInt((String)textCapacity.getText()));
            sActivity.setSize(1);
            sActivity.setCreatorId(myEmail);
            sActivity.setDescription(detail);
            sActivity.setFacilityId(id);
            sActivity.setLatitude(latitude);
            sActivity.setLongitude(longitude);
            sActivity.setZipcode(zipcode);
            sActivity.setAddress(address);

            ActivityService.createActivity(this, sActivity, new ActivityCallBack<String>(){
                @Override
                public void getModelOnSuccess(ModelResult<String> result) {
                    loadCreateActivityHandler(result);
                }
            });
        }
    }

    /**
     * Handle the create SActivity result from the server
     * @param result The result from the server
     */
    private void loadCreateActivityHandler(ModelResult<String> result) {
        // handle the result of request here
        String message = result.getMessage();
        Boolean status = result.isStatus();

        if (status){
            //Todo
            //if successfully get Activity, get the data
            String id = result.getModel();
            Toast.makeText(CreateSactivityActivity.this, "Create Sports Activity Success!", Toast.LENGTH_LONG).show();
        }
        else {
            //if failure, show a toast
            Toast toast = Toast.makeText(CreateSactivityActivity.this, "Create SActivity Error: " + message, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * check whether all the fields are valid
     * @return true, if the above statement is true
     */
    private boolean checkAllFileds(){
        boolean isFull = checkIfNull();
        if (isFull){
            try {
                boolean isTimeValid = checkTime();
                if (isTimeValid){
                    return true;
                }
                else {
                    return false;
                }
            }
            catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }
        else {
            return false;
        }
    }

    /**
     * check whether all fields have been filled.
     * @return True if filled
     */
    private Boolean checkIfNull() {
        if (!textSport.getText().equals("")
                & !textStartDate.getText().equals("") & !textEndDate.getText().equals("")
                & !textStartTime.getText().equals("") & !textEndTime.getText().equals("")
                & !textLocation.getText().equals("") & !textCapacity.getText().equals("")){
            return true;
        }
        else {
            Toast.makeText(this,"Please fill all the content!", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    /**
     * Check whether startTime is before EndTime
     * @return True if the above statement is true
     */
    private boolean checkTime() throws ParseException {
        Date start;
        Date end;

        String startString = (String) textStartDate.getText() + " " + (String) textStartTime.getText();
        String endString = (String) textEndDate.getText() + " " + (String) textEndTime.getText();

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy.MM.dd hh:mm a");
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");

        try {
            start = formatDate.parse(startString);
            end = formatDate.parse(endString);
        } catch (ParseException e) {
            Toast.makeText(this, "Time Parse error:" + "You should choose both Date and Time\n" + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
        String startDateString = format.format(start);
        String endDateString = format.format(end);
        if (start.before(end)){
            sActivity.setStartTime(start);
            sActivity.setEndTime(end);
            return true;
        }
        else{
            Toast.makeText(this,"The startTime should before the endTime!", Toast.LENGTH_LONG).show();
            return false;
        }
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

}


