package com.sportspartner.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.models.NightMode;
import com.sportspartner.service.ResourceService;
import com.sportspartner.util.DBHelper.LoginDBHelper;
import com.sportspartner.util.DBHelper.NightModeDBHelper;
import com.sportspartner.util.listener.MyPickTimeListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SettingActivity extends BasicActivity {
    //android widget
    private LinearLayout linearLayoutTimeSet;
    private TextView textStart;
    private TextView textEnd;
    private Switch switch1;
    private TextView clearCache;

    //time
    private Date startDate;
    private Date endDate;
    //Calendar
    private Calendar myStratCalendar;
    private Calendar myEndCalendar;

    //UserEmail
    private String myEmail;

    //time String
    private String startString = "23:00 PM";
    private String endString = "07:00 AM";

    //menu
    private Menu myMenu;

    //NightModeDBHelper
    private NightModeDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup content = (ViewGroup) findViewById(R.id.layout_home);
        getLayoutInflater().inflate(R.layout.activity_setting, content, true);

        //set title of toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Setting");

        //get Calendar instance
        myStratCalendar = Calendar.getInstance();
        myEndCalendar = Calendar.getInstance();

        //dbHelper
        dbHelper = NightModeDBHelper.getInstance(SettingActivity.this);

        //find widget by Id
        linearLayoutTimeSet = (LinearLayout) findViewById(R.id.Linear_timeSet);
        textStart = (TextView) findViewById(R.id.startTime);
        textEnd = (TextView) findViewById(R.id.endTime);

        //email
        LoginDBHelper loginDBHelper = LoginDBHelper.getInstance(getApplicationContext());
        myEmail = loginDBHelper.getEmail();

        switch1 = (Switch) findViewById(R.id.switch1);

        try {
            setSwitch();
        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), "Time parse Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    Toast.makeText(SettingActivity.this,"enable", Toast.LENGTH_SHORT).show();
                    linearLayoutTimeSet.setVisibility(View.VISIBLE);

                    try {
                        setTime(startString, endString);
                    } catch (ParseException e) {
                        Toast.makeText(getApplicationContext(), "Time parse Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    //insert into dataBase
                    dbHelper.insert(myEmail, startDate, endDate);
                } else {
                    dbHelper.deleteAllrows();
                    linearLayoutTimeSet.setVisibility(View.INVISIBLE);
                    Toast.makeText(SettingActivity.this,"Disable", Toast.LENGTH_SHORT).show();

                }
            }
        });

        textStart.setOnClickListener(new MyPickTimeListener(SettingActivity.this, myStratCalendar, textStart) );
        textEnd.setOnClickListener(new MyPickTimeListener(SettingActivity.this, myEndCalendar, textEnd));

        clearCache = (TextView) findViewById(R.id.clear_cache);
        clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", ""+ResourceService.clearCache(SettingActivity.this));

            }
        });

    }

    private void setSwitch() throws ParseException {
        if (dbHelper.isEmpty()){
            switch1.setChecked(false);
            linearLayoutTimeSet.setVisibility(View.INVISIBLE);
        }
        else {
            switch1.setChecked(true);
            linearLayoutTimeSet.setVisibility(View.VISIBLE);
            ArrayList<NightMode> modes = dbHelper.getAll();
            SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
            String start = format.format(modes.get(0).getStartTime());
            String end = format.format(modes.get(0).getEndTime());
            setTime(start, end);
        }
    }

    private void setTime(String start1, String end1) throws ParseException {
        //parse time
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        startDate = format.parse(start1);
        endDate = format.parse(end1);

        textStart.setText(start1);
        textEnd.setText(end1);
    }

    /**
     * get the menu object of the toolBar
     *
     * @param menu The menu on the top right of the toolbar
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.myMenu = menu;
        MenuItem editItem = myMenu.getItem(0);
        editItem.setIcon(R.drawable.save);
        editItem.setVisible(true);
        return true;
    }

    /**
     * set the onclick method of the buttons on toolBar
     *
     * @param item item of the toolBar menu
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_edit:
                try {
                    updateSetting();
                } catch (ParseException e) {
                    Toast.makeText(getApplicationContext(),"Parse Error: " + e.getMessage(),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;

    }

    private void updateSetting() throws ParseException {
        if (switch1.isChecked()){
            SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
            startDate = format.parse((String) textStart.getText());
            endDate = format.parse((String) textEnd.getText());
            NightModeDBHelper dbHelper = NightModeDBHelper.getInstance(getApplicationContext());
            dbHelper.updateById(myEmail, startDate, endDate);
        }
        Toast.makeText(getApplicationContext(), "Update Setting Successfully!", Toast.LENGTH_SHORT).show();

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
