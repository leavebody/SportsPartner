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

    public MyPickTimeListener(Context context, Calendar myCalendar, TextView textView){
        super(context, myCalendar, textView);
    }

    @Override
    public void onClick(View v){
        new TimePickerDialog(context, time, myCalendar
                .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),
                true).show();
    }

    final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hour);
            myCalendar.set(Calendar.MINUTE, minute);
            updateView(textView, myCalendar);
        }
    };

    private void updateView(TextView textView, Calendar myCalendar) {
        String myFormat = "h:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        textView.setText(sdf.format(myCalendar.getTime()));
    }


}
