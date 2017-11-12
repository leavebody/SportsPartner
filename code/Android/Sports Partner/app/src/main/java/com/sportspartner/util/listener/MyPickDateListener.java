package com.sportspartner.util.listener;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by yujiaxiao on 10/28/17.
 */

public class MyPickDateListener extends MyonClickListener {
    /**
     * Constructor
     * @param context The Activity which calls this listener
     * @param myCalendar The Calendar object which the DatePickerDialog will depend on
     *                   and updates the value of it.
     * @param textView The textView in the Activity which should be updated
     */
    public MyPickDateListener(Context context, Calendar myCalendar, TextView textView){
        super(context, myCalendar, textView);
    }

    /**
     * OnClick listener: will show a DatePickerDialog if is onclick
     * @param v
     */
    @Override
    public void onClick(View v){
        new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * The object of DatePickerDialog
     * Set the onDateSet Listener:
     * Change the value of myCalendar
     * Update the textView
     */
    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateView(textView, myCalendar);
        }

    };

    private void updateView(TextView textView, Calendar myCalendar) {
        String myFormat = "yyyy.MM.dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        textView.setText(sdf.format(myCalendar.getTime()));
        Log.d("DatePicker",sdf.format(myCalendar.getTime()));
        Log.d("DatePicker",myCalendar.getTime().toString());
    }
}
