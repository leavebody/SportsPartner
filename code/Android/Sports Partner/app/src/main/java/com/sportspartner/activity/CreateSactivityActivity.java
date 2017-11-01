package com.sportspartner.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sportspartner.R;
import com.sportspartner.models.SActivity;
import com.sportspartner.util.listener.MyPickDateListener;
import com.sportspartner.util.listener.MyPickTimeListener;
import com.sportspartner.util.listener.MyonClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
            String[] sports = new String[] { "Football", "Basketball", "Badminton", "Lacrosse", "Swimming", "Soccer", "Climbing", "Running"};
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
           //TODO GOOGLE MAP API
        }
    };


    /**
     * On click listener of the Create button
     * Send the request to create the sports activity
     * @param v The Create layout
     */
    public void CreateActivity(View v){
        //TODO
    }

    /**
     * On click listener of the Cancel button
     * Reset all the related content of textView
     * @param v The Cancel layout
     */
    public void CreateCancel(View v){
        //TODO
    }



}


