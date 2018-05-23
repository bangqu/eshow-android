package org.eshow.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bangqu.lib.base.BaseSimpleAdapter;
import com.bangqu.photos.util.ImageSelect;
import com.bumptech.glide.Glide;

import org.eshow.demo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 15052286501 on 2017/8/29.
 */
public class PhotosAdapter extends BaseSimpleAdapter<String> {

    public PhotosAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    protected View getViewAtPosition(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_photos, null);
        viewHolder = new ViewHolder(convertView);
        if (position < getCount() - 1) {
            Glide.with(mContext).load(getItem(position)).into(viewHolder.photosPic);
            viewHolder.photosDel.setVisibility(View.VISIBLE);
        } else {
            Glide.with(mContext).load(R.mipmap.btn_star_addstar).into(viewHolder.photosPic);
            viewHolder.photosDel.setVisibility(View.GONE);
        }
        viewHolder.photosDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                notifyDataSetChanged();
                ImageSelect.mSelectedImage.remove(position);
            }
        });
        return convertView;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photos_pic)
        ImageView photosPic;
        @BindView(R.id.photos_del)
        ImageView photosDel;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
