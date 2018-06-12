package com.bangqu.lib.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bangqu.lib.listener.RecyclerViewItemClickListener;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Administrator on 2018-5-23 0023.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mData;
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected RecyclerViewItemClickListener<T> recyclerViewItemClickListener;

    public void setRecyclerViewItemClickListener(RecyclerViewItemClickListener<T> recyclerViewItemClickListener) {
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    public BaseRecyclerAdapter(Context mContext, List<T> mData) {
        this.mData = mData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return initViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        bindingViewHolder(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(mData.get(position), position);
                if (recyclerViewItemClickListener != null) {
                    recyclerViewItemClickListener.onItemClick(position, mData.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == mData ? 0 : mData.size();
    }

    protected abstract RecyclerView.ViewHolder initViewHolder(ViewGroup parent, int viewType);

    protected abstract void bindingViewHolder(RecyclerView.ViewHolder holder, int position);

    protected void onItemClick(T value, int position) {
    }
}
