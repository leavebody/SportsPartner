package com.sportspartner.util.adapter;

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.adapter.ImageRecyclerAdapter;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageBaseActivity;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.util.Utils;
import com.lzy.imagepicker.view.SuperCheckBox;

import com.sportspartner.R;

import java.util.ArrayList;

/**
 * @author Xiaochen Li
 * Extends ImageRecyclerAdapter to translate the original text to English
 */

public class MyImageRecyclerAdapter extends ImageRecyclerAdapter{
    private static final int ITEM_TYPE_CAMERA = 0;
    private static final int ITEM_TYPE_NORMAL = 1;
    private ImagePicker imagePicker;
    private Activity mActivity;
    private ArrayList<ImageItem> images;
    private ArrayList<ImageItem> mSelectedImages;
    private boolean isShowCamera;
    private int mImageSize;
    private LayoutInflater mInflater;
    private ImageRecyclerAdapter.OnImageItemClickListener listener;

    public void setOnImageItemClickListener(ImageRecyclerAdapter.OnImageItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnImageItemClickListener {
        void onImageItemClick(View view, ImageItem imageItem, int position);
    }

    public void refreshData(ArrayList<ImageItem> images) {
        if (images == null || images.size() == 0) this.images = new ArrayList<>();
        else this.images = images;
        notifyDataSetChanged();
    }

    /**
     *
     */
    public MyImageRecyclerAdapter(Activity activity, ArrayList<ImageItem> images) {
        super(activity, images);
        this.mActivity = activity;
        if (images == null || images.size() == 0) this.images = new ArrayList<>();
        else this.images = images;

        mImageSize = Utils.getImageItemWidth(mActivity);
        imagePicker = ImagePicker.getInstance();
        isShowCamera = imagePicker.isShowCamera();
        mSelectedImages = imagePicker.getSelectedImages();
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_CAMERA){
            return new MyImageRecyclerAdapter.CameraViewHolder(mInflater.inflate(R.layout.adapter_camera_item,parent,false));
        }
        return new MyImageRecyclerAdapter.ImageViewHolder(mInflater.inflate(com.lzy.imagepicker.R.layout.adapter_image_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyImageRecyclerAdapter.CameraViewHolder){
            ((MyImageRecyclerAdapter.CameraViewHolder)holder).bindCamera();
        }else if (holder instanceof MyImageRecyclerAdapter.ImageViewHolder){
            ((MyImageRecyclerAdapter.ImageViewHolder)holder).bind(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowCamera) return position == 0 ? ITEM_TYPE_CAMERA : ITEM_TYPE_NORMAL;
        return ITEM_TYPE_NORMAL;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return isShowCamera ? images.size() + 1 : images.size();
    }

    public ImageItem getItem(int position) {
        if (isShowCamera) {
            if (position == 0) return null;
            return images.get(position - 1);
        } else {
            return images.get(position);
        }
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        ImageView ivThumb;
        View mask;
        View checkView;
        SuperCheckBox cbCheck;


        ImageViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            ivThumb = (ImageView) itemView.findViewById(com.lzy.imagepicker.R.id.iv_thumb);
            mask = itemView.findViewById(com.lzy.imagepicker.R.id.mask);
            checkView=itemView.findViewById(com.lzy.imagepicker.R.id.checkView);
            cbCheck = (SuperCheckBox) itemView.findViewById(com.lzy.imagepicker.R.id.cb_check);
            itemView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mImageSize));
        }

        void bind(final int position){
            final ImageItem imageItem = getItem(position);
            ivThumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) listener.onImageItemClick(rootView, imageItem, position);
                }
            });
            checkView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cbCheck.setChecked(!cbCheck.isChecked());
                    int selectLimit = imagePicker.getSelectLimit();

                        imagePicker.addSelectedImageItem(position, imageItem, cbCheck.isChecked());
                        mask.setVisibility(View.VISIBLE);

                }
            });
            if (imagePicker.isMultiMode()) {
                cbCheck.setVisibility(View.VISIBLE);
                boolean checked = mSelectedImages.contains(imageItem);
                if (checked) {
                    mask.setVisibility(View.VISIBLE);
                    cbCheck.setChecked(true);
                } else {
                    mask.setVisibility(View.GONE);
                    cbCheck.setChecked(false);
                }
            } else {
                cbCheck.setVisibility(View.GONE);
            }
            imagePicker.getImageLoader().displayImage(mActivity, imageItem.path, ivThumb, mImageSize, mImageSize);
        }

    }

    private class CameraViewHolder extends RecyclerView.ViewHolder {

        View mItemView;

        CameraViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
        }

        void bindCamera(){
            mItemView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mImageSize));
            mItemView.setTag(null);
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!((ImageBaseActivity) mActivity).checkPermission(Manifest.permission.CAMERA)) {
                        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, ImageGridActivity.REQUEST_PERMISSION_CAMERA);
                    } else {
                        imagePicker.takePicture(mActivity, ImagePicker.REQUEST_CODE_TAKE);
                    }
                }
            });
        }
    }
}
