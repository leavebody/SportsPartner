package com.sportspartner.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.sportspartner.models.UserOutline;
import com.sportspartner.service.ActivityCallBack;
import com.sportspartner.service.ActivityService;
import com.sportspartner.service.ModelResult;
import com.sportspartner.service.ResourceService;
import com.sportspartner.util.PickPlaceResult;
import com.sportspartner.util.adapter.Divider;
import com.sportspartner.util.adapter.MemberPhotoAdapter;
import com.sportspartner.util.listener.MyPickDateListener;
import com.sportspartner.util.listener.MyPickTimeListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditSaActivity extends BasicActivity implements NumberPicker.OnValueChangeListener {

    private TextView sport;
    private TextView startDate;
    private TextView endDate;
    private TextView startTime;
    private TextView endTime;
    private TextView location;
    private TextView capacity;
    //private TextView member
    private EditText description;
    //recyclerView
    private RecyclerView recyclerView;
    private ArrayList<UserOutline> memberInfo = new ArrayList<>();
    private MemberPhotoAdapter memberAdapter;
    // The object being sent and received from map
    private PickPlaceResult pickPlaceResult;

    //object
    private ArrayList<Sport> listSports = new ArrayList<Sport>();
    private SActivity activityDetail = new SActivity();
    private Menu myMenu;
    private int sportPosition;
    private String id;
    private Double longitude;
    private Double latitude;
    private String zipcode;
    private String address;

    //Calendar
    private Calendar myStratTime;
    private Calendar myEndTime;

    /**
     * OnCreate of this Activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup content = (ViewGroup) findViewById(R.id.layout_home);
        getLayoutInflater().inflate(R.layout.activity_edit_sa, content, true);

        //set title of toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Activity");

        //get all sports
        getAllSports();

        // get the extra from the previous intent
        final Intent myIntent = getIntent();
        activityDetail = (SActivity) myIntent.getSerializableExtra("activity");
        memberInfo = (ArrayList<UserOutline>) myIntent.getSerializableExtra("members");


        //find widget by ID
        ViewGroup detail = (ViewGroup) findViewById(R.id.detail);
        sport = (TextView) detail.findViewById(R.id.text_sport);
        startDate = (TextView) detail.findViewById(R.id.text_startDate);
        startTime = (TextView) detail.findViewById(R.id.text_startTime);
        endDate = (TextView) detail.findViewById(R.id.text_endDate);
        endTime = (TextView) detail.findViewById(R.id.text_endTime);
        location = (TextView) detail.findViewById(R.id.text_location);
        capacity = (TextView) detail.findViewById(R.id.text_capacity);
        description = (EditText) detail.findViewById(R.id.text_description);
        description.setFocusable(true);

        //set member recyclerView
        recyclerView = (RecyclerView) detail.findViewById(R.id.RecyclerView);

        memberAdapter = new MemberPhotoAdapter(memberInfo);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(memberAdapter);

        //get Calendar instance
        myStratTime = Calendar.getInstance();
        myEndTime = Calendar.getInstance();

        //set onCLick Listener
        sport.setOnClickListener(mySportListener);
        startDate.setOnClickListener(new MyPickDateListener(EditSaActivity.this, myStratTime, startDate));
        endDate.setOnClickListener(new MyPickDateListener(EditSaActivity.this, myEndTime, endDate));
        startTime.setOnClickListener(new MyPickTimeListener(EditSaActivity.this, myStratTime, startTime));
        endTime.setOnClickListener(new MyPickTimeListener(EditSaActivity.this, myEndTime, endTime));
        location.setOnClickListener(myLocationListener);
        capacity.setOnClickListener(myCapacityListener);

        pickPlaceResult = new PickPlaceResult();

        //fill the content
        setActivityDetail();
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
            for (int i = 0; i < listSports.size(); i++) {
                sports[i] = listSports.get(i).getSportName();
            }
            showDialog(sports, sport);
        }
    };

    /**
     * Show the NumberPicker Dialog
     * Set the content of the textView according to selection result of the user
     *
     * @param strings  The String shows in the NumberPicker
     * @param textView The textView which should be changed
     */
    //all types of listener
    private void showDialog(final String[] strings, final TextView textView) {
        final Dialog d = new Dialog(EditSaActivity.this);
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.layout_dialog);
        final Button b1 = (Button) d.findViewById(R.id.dialog_button_set);
        Button b2 = (Button) d.findViewById(R.id.dialog_button_cancel);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.dialog_numPicker);
        np.setDisplayedValues(strings);
        np.setMinValue(0);
        np.setMaxValue(strings.length - 1);
        np.setWrapSelectorWheel(true);
        np.setOnValueChangedListener(EditSaActivity.this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(np.getDisplayedValues()[np.getValue()]);
                sportPosition = np.getValue() % listSports.size();
                Log.d("listSport", String.valueOf(listSports.size()));
                Log.d("strings", String.valueOf(strings.length));
                Log.d("sportPosition", String.valueOf(sportPosition));
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();

    }

    /**
     * myLocationListener:
     * An object of OnClickListener,
     * Call GOOGLE MAP API
     */
    private View.OnClickListener myLocationListener = new View.OnClickListener() {
        public void onClick(View v) {
            // start the map activity
            Intent intent = new Intent(EditSaActivity.this, MapActivity.class);
            intent.putExtra("PickPlaceResult", pickPlaceResult);
            startActivityForResult(intent, 1);
        }
    };

    /**
     * get the data from the inner activity
     *
     * @param requestCode
     * @param resultCode
     * @param data        data from the inner activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();
                if (b != null) {
                    pickPlaceResult = (PickPlaceResult) b.getSerializable("PickPlaceResult");

                    if (pickPlaceResult.isFacility()) {
                        //Todo get id
                        id = "NULL";
                        latitude = 0.0;
                        longitude = 0.0;
                        zipcode = "00000";
                        address = pickPlaceResult.getName();
                        location.setText(address);
                    } else {
                        zipcode = pickPlaceResult.getZipCode();
                        id = "NULL";
                        latitude = pickPlaceResult.getLatLng().latitude;
                        longitude = pickPlaceResult.getLatLng().longitude;
                        address = pickPlaceResult.getName();
                        location.setText(address);

                    }
                    //set the detail
                    activityDetail.setFacilityId(id);
                    activityDetail.setAddress(address);
                    activityDetail.setLatitude(latitude);
                    activityDetail.setLongitude(longitude);
                    activityDetail.setZipcode(zipcode);
                }
            } else if (resultCode == 0) {
                Toast.makeText(this, "RESULT CANCELLED", Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * myCapacityListener:
     * An object of OnClickListener,
     * Set the content of the string, which will be shown in the Dialog
     * Show a dialog
     */
    private View.OnClickListener myCapacityListener = new View.OnClickListener() {
        public void onClick(View v) {
            String[] capacityString = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
            showDialog(capacityString, capacity);
        }
    };

    /**
     * send the request to get all sports from the server
     */
    private void getAllSports() {
        ResourceService.getAllSports(this, new ActivityCallBack<ArrayList<Sport>>() {
            @Override
            public void getModelOnSuccess(ModelResult<ArrayList<Sport>> result) {
                // handle the result of request here
                String message = result.getMessage();
                Boolean status = result.isStatus();

                if (status) {
                    //if successfully get Activity, get the data
                    listSports = new ArrayList<>(result.getModel());
                } else {
                    //if failure, show a toast
                    Toast toast = Toast.makeText(EditSaActivity.this, "Load sports Error: " + message, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }


    /**
     * set the content of the UI
     */
    private void setActivityDetail() {
        //set data to Android Widget
        sport.setText(activityDetail.getSportName());
        location.setText(activityDetail.getAddress());
        description.setText(activityDetail.getDetail());
        capacity.setText(String.valueOf(activityDetail.getCapacity()));

        //Time and date
        Date start = activityDetail.getStartTime();
        Date end = activityDetail.getEndTime();
        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy.MM.dd", Locale.US);
        startDate.setText(sdf0.format(start.getTime()));
        endDate.setText(sdf0.format(end.getTime()));
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a", Locale.US);
        startTime.setText(sdf1.format(start.getTime()));
        endTime.setText(sdf1.format(end.getTime()));

        //update member adapter
        memberAdapter.updateMember(memberInfo);
    }

    /**
     * check whether all the fields are valid
     *
     * @return true, if the above statement is true
     */
    private boolean checkAllFileds() {
        boolean isFull = checkIfNull();
        if (isFull) {
            try {
                boolean isTimeValid = checkTime();
                if (isTimeValid) {
                    return true;
                } else {
                    return false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * check whether all fields have been filled.
     *
     * @return True if filled
     */
    private Boolean checkIfNull() {
        if (!sport.getText().equals("")
                & !startDate.getText().equals("") & !endDate.getText().equals("")
                & !startTime.getText().equals("") & !endTime.getText().equals("")
                & !location.getText().equals("") & !capacity.getText().equals("")) {
            return true;
        } else {
            Toast.makeText(this, "Please fill all the content!", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    /**
     * Check whether startTime is before EndTime
     *
     * @return True if the above statement is true
     */
    private boolean checkTime() throws ParseException {
        Date start;
        Date end;

        String startString = (String) startDate.getText() + " " + (String) startTime.getText();
        String endString = (String) endDate.getText() + " " + (String) endTime.getText();

        Log.d("startString",startString);
        Log.d("endString",endString);

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
        if (start.before(end)) {
            activityDetail.setStartTime(start);
            activityDetail.setEndTime(end);
            return true;
        } else {
            Toast.makeText(this, "The startTime should before the endTime!", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    /**
     * set new activity detail object
     * call the request to update activity detail
     */
    private void updateSaDetail() {
        boolean isValid = checkAllFileds();
        if (isValid){
            String detail;

            //set detail
            if(description.getText().equals("")){
                detail = "NULL";
            }
            else {
                detail = String.valueOf(description.getText());
            }

            //set activity
            activityDetail.setSportId(listSports.get(sportPosition).getSportId());
            activityDetail.setDescription(detail);
            activityDetail.setCapacity(Integer.parseInt((String) capacity.getText()));
            activityDetail.setSize(memberInfo.size());

            ActivityService.updateActivity(this, activityDetail, new ActivityCallBack() {
                public void getModelOnSuccess(ModelResult result) {
                    updateSADetailHandler(result);
                }
            });
        }
    }

    /**
     * handle the result from the server
     * If success, return to the detail Page
     * else, show error message
     *
     * @param result
     */
    private void updateSADetailHandler(ModelResult result) {
        // handle the result here
        String message = result.getMessage();
        if (result.isStatus()) {
            Toast.makeText(EditSaActivity.this, "Update Success!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, SactivityDetailActivity.class);
            intent.putExtra("activityId", activityDetail.getActivityId());
            this.startActivity(intent);
            finish();
        } else {
            Toast.makeText(EditSaActivity.this, message, Toast.LENGTH_LONG).show();
        }

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
                updateSaDetail();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }
}
