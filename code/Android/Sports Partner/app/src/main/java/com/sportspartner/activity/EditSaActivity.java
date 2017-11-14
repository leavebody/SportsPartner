package com.sportspartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.models.SActivity;
import com.sportspartner.models.UserOutline;
import com.sportspartner.service.ActivityService;
import com.sportspartner.service.ModelResult;
import com.sportspartner.util.PickPlaceResult;
import com.sportspartner.util.adapter.Divider;
import com.sportspartner.util.adapter.MemberPhotoAdapter;
import com.sportspartner.util.listener.MyPickDateListener;
import com.sportspartner.util.listener.MyPickTimeListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class EditSaActivity extends BasicActivity {

    private TextView sport;
    private TextView startDate;
    private TextView endDate;
    private TextView startTime;
    private TextView endTime;
    private TextView location;
    private TextView capacity;
    //private TextView member
    private TextView description;
    //recyclerView
    private RecyclerView recyclerView;
    private ArrayList<UserOutline> memberInfo = new ArrayList<>();
    private MemberPhotoAdapter memberAdapter;
    // The object being sent and received from map
    private PickPlaceResult pickPlaceResult;

    //SActivity object
    private SActivity activityDetail = new SActivity();
    private Menu myMenu;


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

        // get the extra from the previous intent
        final Intent myIntent = getIntent();
        activityDetail = (SActivity) myIntent.getSerializableExtra("activityDetail");
        memberInfo = (ArrayList<UserOutline>) myIntent.getSerializableExtra("memberInfo");


        //find widget by ID
        ViewGroup detail = (ViewGroup) findViewById(R.id.activity_detail);
        sport = (TextView) detail.findViewById(R.id.text_sport);
        startDate = (TextView) detail.findViewById(R.id.text_startDate);
        startTime = (TextView) detail.findViewById(R.id.text_startTime);
        endDate = (TextView) detail.findViewById(R.id.text_endDate);
        endTime = (TextView) detail.findViewById(R.id.text_endTime);
        location = (TextView) detail.findViewById(R.id.text_location);
        capacity = (TextView) detail.findViewById(R.id.text_capacity);
        description = (TextView) detail.findViewById(R.id.text_description);

        //set member recyclerView
        recyclerView = (RecyclerView) detail.findViewById(R.id.RecyclerView);

        memberAdapter = new MemberPhotoAdapter(memberInfo);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(memberAdapter);

        //set onCLick Listener
        sport.setOnClickListener(mySportListener);
        /*startDate.setOnClickListener(new MyPickDateListener(EditSaActivity.this, myStratTime, startDate));
        endDate.setOnClickListener(new MyPickDateListener(EditSaActivity.this, myEndTime, endDate));
        startTime.setOnClickListener(new MyPickTimeListener(EditSaActivity.this, myStratTime, startTime) );
        endTime.setOnClickListener(new MyPickTimeListener(EditSaActivity.this, myEndTime, endTime));
        location.setOnClickListener(myLocationListener);
        capacity.setOnClickListener(myCapacityListener);*/

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
           /* String[] sports = new String[listSports.size()];
            for (int i = 0; i < listSports.size(); i++){
                sports[i] = listSports.get(i).getSportName();
            }
            showDialog(sports, sport);*/
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
            Intent intent = new Intent(EditSaActivity.this, MapActivity.class);
            intent.putExtra("PickPlaceResult", pickPlaceResult);
            startActivityForResult(intent, 1);
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
           /* String[] capacityString = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
            showDialog(capacityString, this.capacity);*/
        }
    };


    private void setActivityDetail() {
        //set data to Android Widget
        sport.setText(activityDetail.getSportName());
        location.setText(activityDetail.getAddress());
        description.setText(activityDetail.getDetail());
        String size = activityDetail.getSize() + "/" + activityDetail.getCapacity();
        capacity.setText(size);

        //Time and date
        Date start = activityDetail.getStartTime();
        Date end = activityDetail.getEndTime();
        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy.MM.dd", Locale.US);
        startDate.setText(sdf0.format(start.getTime()));
        endDate.setText(sdf0.format(end.getTime()));
        SimpleDateFormat sdf1 = new SimpleDateFormat("h:mm", Locale.US);
        startTime.setText(sdf1.format(start.getTime()));
        endTime.setText(sdf1.format(end.getTime()));

        //update member adapter
        memberAdapter.updateMember(memberInfo);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.myMenu = menu;
        return true;
    }


    /**
     * Set the visibility of the button on the toolbar to visible
     * set different icon according the userType
     */
    @Override
    public void invalidateOptionsMenu() {
        //change the visibility of toolbar edit button
        MenuItem editItem = myMenu.getItem(0);

        //set the edit button
        editItem.setIcon(R.drawable.edit);
        editItem.setVisible(true);
        onPrepareOptionsMenu(myMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.toolbar_edit:
                updateSaDetail();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;

    }

    /**
     * set new activity detail object
     * call the request to update activity detail
     */
    private void updateSaDetail() {
        //set activity
        /*profile.setUserName(userName.getText().toString());
        profile.setGender(gender.getText().toString());
        profile.setAge(Integer.parseInt(age.getText().toString()));
        profile.setAddress(city.getText().toString());*/

        //Todo update
        /*ActivityService.(this, userEmail, profile, new ActivityCallBack(){
            public void getModelOnSuccess(ModelResult result) {
                updateSADetailHandler(result);
            }
        });*/
    }

    /**
     * handle the result from the server
     * If success, return to the detail Page
     * else, show error message
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
        }
        else{
            Toast.makeText(EditSaActivity.this, message, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
