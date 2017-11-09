package com.sportspartner.util.listener;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import java.util.Calendar;

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
    public void onClick(View v){}

}
