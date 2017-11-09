package com.sportspartner.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.view.CropImageView;
import com.sportspartner.R;
import com.sportspartner.util.PicassoImageLoader;

import java.io.File;
import java.util.ArrayList;

public class EditProfileActivity extends BasicActivity {


    private ImagePicker imagePicker;

    private final int cropWidth = 280;
    private final int cropHeight = 280;
    private final int outputX = 800;
    private final int outputY = 800;
    ArrayList<ImageItem> images = null;

    ImageView photoView;

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

        photoView = findViewById(R.id.profile_photo);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfileActivity.this.onPhotoClick();
            }
        });
    }

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
                        photoView.setImageBitmap(myBitmap);

                    }
                }
            } else {
                Toast.makeText(this, "No data sent back", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
