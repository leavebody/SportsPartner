package com.sportspartner.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.view.CropImageView;
import com.sportspartner.R;
import com.sportspartner.models.Profile;
import com.sportspartner.models.Sport;
import com.sportspartner.service.ProfileService;
import com.sportspartner.service.ResourceService;
import com.sportspartner.service.serviceresult.BooleanResult;
import com.sportspartner.util.ActivityCallBack;
import com.sportspartner.util.LoginDBHelper;
import com.sportspartner.util.PicassoImageLoader;

import java.io.File;
import java.util.ArrayList;

public class EditProfileActivity extends BasicActivity {
    //Image
    private ImagePicker imagePicker;

    private final int cropWidth = 280;
    private final int cropHeight = 280;
    private final int outputX = 800;
    private final int outputY = 800;

    private ArrayList<ImageItem> images = null;

    //profile
    private Profile profile = new Profile();
    private ArrayList<Sport> interests = new ArrayList<Sport>();
    private String userEmail;

    //widget
    private ImageView photoView;

    /**
     * Load the EditProfileActivity
     * Find Widgets by Id
     * Set onClick listener
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup content = (ViewGroup) findViewById(R.id.layout_home);
        getLayoutInflater().inflate(R.layout.activity_edit_profile, content, true);

        //set title of toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Profile");

        Intent intent = getIntent();
        profile = (Profile) intent.getSerializableExtra("profile");
        interests = (ArrayList<Sport>) intent.getSerializableExtra("interest");

        photoView = findViewById(R.id.profile_photo);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfileActivity.this.onPhotoClick();
            }
        });

    }

    /**
     * go to ImageGridActivity when the user click his profile photo
     */
    public void onPhotoClick(){

        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());
        imagePicker.setImageLoader(new PicassoImageLoader());
        imagePicker.setMultiMode(false);
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(true);
        imagePicker.setSaveRectangle(true);
        imagePicker.setSelectLimit(1);

        // square image
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        Integer width = cropWidth;
        Integer height = cropHeight;
        width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(width);
        imagePicker.setFocusHeight(height);

        // set output size
        imagePicker.setOutPutX(outputX);
        imagePicker.setOutPutY(outputY);

        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(com.lzy.imagepicker.ui.ImageGridActivity.EXTRAS_IMAGES, new ArrayList< ImageItem >());
        startActivityForResult(intent, 100);

    }

    /**
     * upload bitmap when back to this activity from ImageGridActivity activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images.size()>0) {
                    String imagePath = images.get(0).path;
                    File imgFile = new File(imagePath);
                    Log.d("imageTest", "imagePath: "+imagePath);
                    if(imgFile.exists()){

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        updateIcon(myBitmap);

                    }
                }
            } else {
                Toast.makeText(this, "No data sent back", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Update user profile photo
     * @param bitmap The bitmap of the new photo
     */
    private void updateIcon(Bitmap bitmap) {
        photoView.setImageBitmap(bitmap);
        ResourceService.uploadUserIcon(this, bitmap, new ActivityCallBack(){
            @Override
            public void getBooleanOnSuccess(BooleanResult booleanResult){
                if (!booleanResult.isStatus()){
                    Toast.makeText(EditProfileActivity.this, booleanResult.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
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
     * Set the onClick action to the save button--update profile
     * back to the profile activity
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.toolbar_edit:
                updateProfile();
                Intent intent = new Intent(this, ProfileActivity.class);
                this.startActivity(intent);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void updateProfile() {
        ProfileService.updateProfile(this, userEmail, profile, new ActivityCallBack(){
            @Override
            public void getBooleanOnSuccess(BooleanResult booleanResult) {
                updateProfileHandler(booleanResult);
            }
        });
    }

    private void updateProfileHandler(BooleanResult booleanResult) {
        // handle the result here
        String message = booleanResult.getMessage();
        if (booleanResult.isStatus()) {
            Toast toast = Toast.makeText(EditProfileActivity.this, "Update Success!", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_LONG);
            toast.show();
        }

    }

    /**
     * When user push the hardware back button on the photo
     * Call finish()
     */
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

}
