package com.sportspartner.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.models.SActivity;
import com.sportspartner.models.SActivityOutline;
import com.sportspartner.models.UserOutline;
import com.sportspartner.models.UserReview;
import com.sportspartner.service.ActivityCallBack;
import com.sportspartner.service.ActivityService;
import com.sportspartner.service.FacilityService;
import com.sportspartner.service.ModelResult;
import com.sportspartner.service.ResourceService;
import com.sportspartner.util.DBHelper.LoginDBHelper;
import com.sportspartner.util.adapter.Divider;
import com.sportspartner.util.adapter.ReviewMembersAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @author  yujiaxiao
 * @author Xiaochen Li
 */

public class ReviewSaActivity extends BasicActivity {
    private String activityId;
    private String myEmail;

    private ReviewMembersAdapter reviewMembersAdapter;
    private ArrayList<UserOutline> listMembers;

    private RecyclerView reviewRecycler;
    private View activityCardView;

    private View titleRateMember;
    private View titleRateFacility;

    private View rateFacilityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup content = (ViewGroup) findViewById(R.id.layout_home);
        getLayoutInflater().inflate(R.layout.activity_review, content, true);

        //set title of toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Review an Activity");

        reviewRecycler = (RecyclerView) findViewById(R.id.recycler_evaluate);
        activityCardView = (View) findViewById(R.id.reviewed_activity);
        titleRateMember = findViewById(R.id.title_evaluate);
        titleRateFacility = findViewById(R.id.title_rate_facility);
        rateFacilityView = findViewById(R.id.facility_review);

        titleRateFacility.setVisibility(View.INVISIBLE);
        rateFacilityView.setVisibility(View.INVISIBLE);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        reviewRecycler.setLayoutManager(mLayoutManager);
        reviewRecycler.setItemAnimator(new DefaultItemAnimator());
        reviewRecycler.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));


        // get the activity id from intent
        try {
            Intent myIntent = getIntent();
            activityId = myIntent.getStringExtra("activityId");
        } catch (Exception e) {
            Toast.makeText(this, "intent extra not sent to ReviewSaActivity", Toast.LENGTH_LONG).show();
            Log.d("ReviewSaActivity", "intent extra not sent to ReviewSaActivity");
            onBackPressed();
        }

        // get current user email from local database
        try {
            LoginDBHelper dbHelper = LoginDBHelper.getInstance(this);
            myEmail = dbHelper.getEmail();
        } catch (Exception e) {
            Toast.makeText(this, "login DB error", Toast.LENGTH_LONG).show();
            Log.d("ReviewSaActivity", "login DB error");
            onBackPressed();
        }

        setTitle();
        setActivityOutline();
        getActivity();

    }

    private void setTitle() {
        TextView title1Text = (TextView) titleRateMember.findViewById(R.id.title);
        title1Text.setText("Evaluate your teammates");

        TextView title2Text = (TextView) titleRateFacility.findViewById(R.id.title);
        title2Text.setText("Rate the facility");
    }

    private void setActivityOutline(){
        ActivityService.getSActivityOutline(this, activityId, new ActivityCallBack<SActivityOutline>() {
            @Override
            public void getModelOnSuccess(ModelResult<SActivityOutline> modelResult) {
                if (!modelResult.isStatus()) {
                    Log.e("ReviewAct", modelResult.getMessage());
                    return;
                }
                SActivityOutline outline = modelResult.getModel();

                activityCardView.setBackgroundColor(getResources().getColor(R.color.background_blue));

                //get element from the layout
                final ImageView activityPhoto = (ImageView) activityCardView.findViewById(R.id.activity_photo);
                TextView sportName = (TextView) activityCardView.findViewById(R.id.sport_name);
                TextView activityDate = (TextView) activityCardView.findViewById(R.id.activity_date);
                TextView activityTime = (TextView) activityCardView.findViewById(R.id.activity_time);
                TextView activityLocation = (TextView) activityCardView.findViewById(R.id.activity_location);
                TextView activityMember = (TextView) activityCardView.findViewById(R.id.activity_member);

                sportName.setTextColor(getResources().getColor(R.color.white));
                activityDate.setTextColor(getResources().getColor(R.color.white));
                activityTime.setTextColor(getResources().getColor(R.color.white));
                activityLocation.setTextColor(getResources().getColor(R.color.white));
                activityMember.setTextColor(getResources().getColor(R.color.white));

                sportName.setText(outline.getSportName());

                //Parse time
                SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.US);
                String startDate = df.format(outline.getStartTime());
                String endDate = df.format(outline.getEndTime());

                activityDate.setText(startDate);
                activityTime.setText(endDate);

                activityLocation.setText(outline.getAddress());
                String curCapacity = String.valueOf(outline.getSize()) + "/" + String.valueOf(outline.getCapacity());
                activityMember.setText(curCapacity);

                ResourceService.getImage(ReviewSaActivity.this, outline.getSportIconUUID(), ResourceService.IMAGE_SMALL,
                        new ActivityCallBack<Bitmap>(){
                            @Override
                            public void getModelOnSuccess(ModelResult<Bitmap> modelResult) {
                                if (modelResult.isStatus()) {
                                    activityPhoto.setImageBitmap(modelResult.getModel());
                                }
                                else{
                                    Toast.makeText(ReviewSaActivity.this, "Load Activity icon error: "+modelResult.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });
    }

    private void getActivity() {
        ActivityService.getSActivity(this, activityId, new ActivityCallBack<SActivity>() {
            @Override
            public void getModelOnSuccess(ModelResult<SActivity> modelResult) {
                if (!modelResult.isStatus()) {
                    Log.e("ReviewAct", modelResult.getMessage());
                    return;
                }
                SActivity sActivity = modelResult.getModel();
                listMembers = new ArrayList<>();
                ArrayList<UserOutline> allMembers = sActivity.getMembers();
                for (UserOutline outline: allMembers) {
                    if (!outline.getUserId().equals(myEmail)){
                        listMembers.add(outline);
                    }
                }
                setReviewList();

                if (sActivity.getFacilityId()!=null){
                    setFacility(sActivity.getFacilityId());
                }

            }
        });
    }

    private void setFacility(String facilityId){
        //todo

        titleRateFacility.setVisibility(View.VISIBLE);
        rateFacilityView.setVisibility(View.VISIBLE);
    }

    private void setReviewList(){
        reviewMembersAdapter = new ReviewMembersAdapter(listMembers, ReviewSaActivity.this);
        reviewRecycler.setAdapter(reviewMembersAdapter);
    }

    /**
     * Set the visibility of the Edit button on the toolbar to visible
     * set the icon to save
     * @param menu The menu on the top right of the toolbar
     * @return True if success
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //change the visibility of toolbar edit button
        MenuItem editItem = menu.getItem(0);
        editItem.setVisible(true);
        editItem.setIcon(getResources().getDrawable(R.drawable.save));
        return true;
    }

    /**
     * Set the onClick action to the save button
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.toolbar_edit:
                sendRating();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void sendRating(){
        ArrayList<UserReview> userReviews = new ArrayList<>();
        for (int i = 0; i < reviewMembersAdapter.getItemCount(); i++) {
            UserReview userReview = new UserReview();
            ReviewMembersAdapter.MyViewHolder viewHolder =
                    (ReviewMembersAdapter.MyViewHolder) reviewRecycler.findViewHolderForAdapterPosition(i);
            userReview.setActivityid(activityId);
            userReview.setComments(viewHolder.getComment());
            userReview.setParticipation(viewHolder.getParticipation());
            userReview.setPunctuality(viewHolder.getPunctuality());
            userReview.setReviewee(reviewMembersAdapter.getUserIdByPosition(i));
            userReview.setReviewer(myEmail);

            userReviews.add(userReview);
        }
        // TODO facility

        ActivityService.reviewActivity(this, activityId, userReviews, null, new ActivityCallBack(){
            public void getModelOnSuccess(ModelResult modelResult){
                if (modelResult.isStatus()) {
                    Toast.makeText(ReviewSaActivity.this.getApplicationContext(), "review success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReviewSaActivity.this.getApplicationContext(), modelResult.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
