package com.bangqu.lib.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018-5-22 0022.
 */

public abstract class BaseSimpleAdapter<T> extends BaseAdapter {

    protected List<T> mData;
    protected LayoutInflater mInflater;
    protected Context mContext;

    public BaseSimpleAdapter(Context mContext, List<T> mData) {
        this.mData = mData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return null == mData ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = getViewAtPosition(position, convertView, parent);
        return convertView;
    }

    protected abstract View getViewAtPosition(int position, View convertView, ViewGroup parent);
}
