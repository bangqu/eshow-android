package org.eshow.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bangqu.photos.util.ImageSelect;
import com.bumptech.glide.Glide;

import org.eshow.demo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 15052286501 on 2017/8/29.
 */
public class PhotosAdapter extends BaseAdapter {

    private List<String> mList;
    private Context mContext;

    public PhotosAdapter(Context context, List<String> list) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_photos, null);
        viewHolder = new ViewHolder(convertView);
        if (position < mList.size()) {
            Glide.with(mContext).load(mList.get(position)).into(viewHolder.photosPic);
            viewHolder.photosDel.setVisibility(View.VISIBLE);
        } else {
            Glide.with(mContext).load(R.mipmap.btn_star_addstar).into(viewHolder.photosPic);
//            viewHolder.photosPic.setImageResource(R.mipmap.btn_star_addstar);
            viewHolder.photosDel.setVisibility(View.GONE);
        }
        viewHolder.photosDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
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
