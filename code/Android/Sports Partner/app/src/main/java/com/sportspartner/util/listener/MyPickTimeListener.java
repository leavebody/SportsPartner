package com.sportspartner.util.listener;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sportspartner.activity.CreateSactivityActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by yujiaxiao on 10/28/17.
 */

public class MyPickTimeListener extends MyonClickListener {

    /**
     * Constructor of MyPickTimeListener
     * @param context The Activity which calls this listener
     * @param myCalendar The Calendar object which the DatePickerDialog will depend on
     *                   and updates the value of it.
     * @param textView The textView in the Activity which should be updated
     */
    public MyPickTimeListener(Context context, Calendar myCalendar, TextView textView){
        super(context, myCalendar, textView);
    }

    /**
     * OnClick Listener: Show a TimePickerDialog
     * @param v
     */
    @Override
    public void onClick(View v){
        new TimePickerDialog(context, time, myCalendar
                .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),
                true).show();
    }

    /**
     * An object of TimePickerDialog.OnTimeSetListener
     * onTimeSet Listener:
     * update the value of myCalendar
     * Update the content of the textView
     */
    final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hour);
            myCalendar.set(Calendar.MINUTE, minute);
            updateView(textView, myCalendar);
        }
    };

    /**
     * Update the value of myCalendar
     * Update the content of the textView
     * @param textView The textView which will show the selected time
     * @param myCalendar The Calendar object which will save the selected time
     */
    private void updateView(TextView textView, Calendar myCalendar) {
        String myFormat = "h:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        textView.setText(sdf.format(myCalendar.getTime()));
    }


}
