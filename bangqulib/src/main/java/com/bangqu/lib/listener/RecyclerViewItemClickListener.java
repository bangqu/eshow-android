package com.bangqu.lib.listener;

/**
 * Created by Administrator on 2018-5-23 0023.
 */

public interface RecyclerViewItemClickListener<T> {
    void onItemClick(int position, T value);
}
