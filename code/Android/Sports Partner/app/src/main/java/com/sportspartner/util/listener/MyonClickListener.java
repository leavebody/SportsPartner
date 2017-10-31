package com.sportspartner.util.listener;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by yujiaxiao on 10/28/17.
 */

public class MyonClickListener implements View.OnClickListener{

    Calendar myCalendar;
    Context context;
    TextView textView;
    public MyonClickListener(Context context, Calendar myCalendar, TextView textView){
        this.context = context;
        this.myCalendar = myCalendar;
        this.textView = textView;
    }

    @Override
    public void onClick(View v){
        new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

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
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        textView.setText(sdf.format(myCalendar.getTime()));
    }
}
