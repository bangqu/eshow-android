package com.bangqu.photos.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bangqu.photos.R;
import com.bangqu.photos.listener.OnPictureSelectedListener;
import com.bangqu.photos.util.ImageSelect;
import com.bangqu.photos.util.ViewHolder;

public class ImageAdapter extends CommonAdapter<String> {

    private OnPictureSelectedListener onPictureSelectedListener;
    private String colorFilter = "#4C000000";
    private boolean isSingle = false;

    public ImageAdapter(Context context, List<String> mDatas, boolean single, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.isSingle = single;
    }

    public void setOnPictureSelectedListener(OnPictureSelectedListener onPictureSelectedListener) {
        this.onPictureSelectedListener = onPictureSelectedListener;
    }

    @Override
    public void convert(final ViewHolder helper, final String item) {
        //设置no_pic
        helper.setImageResource(R.id.item_picture, R.mipmap.default_picture);
        //设置图片
        helper.setImageByUrl(R.id.item_picture, item);

        final ImageView mImageView = helper.getView(R.id.item_picture);
        final CheckBox mSelect = helper.getView(R.id.item_selected);
        if (isSingle) {
            mSelect.setVisibility(View.GONE);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onPictureSelectedListener != null){
                        onPictureSelectedListener.onSingleChoice(item);
                    }
                }
            });
            return;
        }
        /**
         * 已经选择过的图片，显示出选择过的效果
         */
        if (ImageSelect.mSelectedImage.contains(item)) {
            mSelect.setChecked(true);
            mImageView.setColorFilter(Color.parseColor(colorFilter));
        } else {
            mSelect.setChecked(false);
            mImageView.setColorFilter(null);
        }
        //设置ImageView的点击事件
        mSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed() && onPictureSelectedListener != null) {
                    if (isChecked) {
                        if (!ImageSelect.addSelectedImage(item)) {
                            Toast.makeText(mContext, "最多选择" + ImageSelect.MAX_SIZE + "张图片", Toast.LENGTH_SHORT).show();
                            buttonView.setChecked(false);
                        } else {
                            mImageView.setColorFilter(Color.parseColor(colorFilter));
                        }
                    } else {
                        mImageView.setColorFilter(null);
                        ImageSelect.mSelectedImage.remove(item);
                    }
                    onPictureSelectedListener.onPictureSelected();
                }
            }
        });
    }
}
