package com.sportspartner.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sportspartner.R;
import com.sportspartner.models.UserOutline;
import com.sportspartner.service.ProfileService;
import com.sportspartner.service.ResourceService;
import com.sportspartner.service.UserService;
import com.sportspartner.service.serviceresult.BooleanResult;
import com.sportspartner.service.serviceresult.ModelResult;
import com.sportspartner.util.ActivityCallBack;
import com.sportspartner.util.LoginDBHelper;

import java.util.ArrayList;

public class BasicActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Load the Navigation Bar and the ToolBar
     * Find the widget by Id
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        View homeLayout= (View) findViewById(R.id.layout_home);
        View toolbarLayout= (View) homeLayout.findViewById(R.id.toolbar_home);
        Toolbar toolbar = (Toolbar) toolbarLayout.findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        // load drawer layout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_home);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //load nav bar
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setUserOutline();
    }

    /**
     * Get the userEmail from database,
     * send the request to get ProfileOutline.
     * When the response comes back form the server,
     * it will call the userOutlineHandler
     */
    private void setUserOutline(){
        LoginDBHelper dbHelper = LoginDBHelper.getInstance(this);
        ArrayList<String> list =  dbHelper.getAll();
        System.out.println("list size:"+ String.valueOf(list.size()));
        String email = dbHelper.getEmail();
        ProfileService.getProfileOutline(this, email, new ActivityCallBack<UserOutline>(){
            @Override
            public void getModelOnSuccess(ModelResult<UserOutline> result){
                userOutlineHandler(result);
            }
        });
    }

    /**
     * Fill the content of Navigation Bar according to the UserOutline
     * @param result The response object returned from the server
     */
    private void userOutlineHandler(ModelResult<UserOutline> result){
        String userName ;

        // handle the result of request here
        String message = result.getMessage();
        Boolean status = result.isStatus();

        if (status){
            //if successfully get the data, then get the data
            NavigationView navigation = (NavigationView) findViewById(R.id.nav_view);
            View headerView = navigation.getHeaderView(0); // 0-index header
            TextView userNameView = (TextView) headerView.findViewById(R.id.nav_bar_username);
            final ImageView iconView = (ImageView) headerView.findViewById(R.id.nav_bar_usericon);
            UserOutline userOutline = result.getModel();
            userName = userOutline.getUserName();
            String iconUUID = userOutline.getIconUUID();

            userNameView.setText(userName);

            ResourceService.getImage(this, iconUUID, ResourceService.IMAGE_SMALL, new ActivityCallBack<Bitmap>(){
                @Override
                public void getModelOnSuccess(ModelResult<Bitmap> result){
                    if (result.isStatus()) {
                        iconView.setImageBitmap(result.getModel());
                    } else{
                        //if failure, show a toast
                        Toast toast = Toast.makeText(BasicActivity.this,
                                "Load user icon error: "+result.getMessage(), Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            });
        }
        else{
            //if failure, show a toast
            Toast toast = Toast.makeText(this, "Load user outline Error: " + message, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * Load the ToolBar
     * @param menu The menu on the top right of the toolbar
     * @return If load success, return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.toolbar_edit:
                Intent intent = new Intent(this, EditProfileActivity.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    /**
     * The onPressed action of the Drawer
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_home);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * The action of the Navigation Item if selected
     * @param item The item of the Navigation Bar
     * @return Return true if success.
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Goto the Homepage
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            // Goto the ProfilePage
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_friends) {
            // Goto the FriendList Page
            Intent intent = new Intent(this, FriendListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_moments) {
            // Goto the Moment Page
            Intent intent = new Intent(this, MomentActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_noti) {
            // Goto the Notification Page
            Intent intent = new Intent(this, NotificationActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_signout) {
            signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_home);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * SignOut function: send the signOut request,
     * Show the "success" toast if success.
     */
    private void signOut(){
        UserService.logOut(this, new ActivityCallBack(){
            @Override
            public void getBooleanOnSuccess(BooleanResult result){
                if (!result.isStatus()){
                    //if failure, show a toast
                    Toast toast = Toast.makeText(BasicActivity.this,
                            result.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}
