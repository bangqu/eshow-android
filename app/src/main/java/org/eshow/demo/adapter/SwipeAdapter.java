package org.eshow.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bangqu.greendao.entity.Person;
import com.bangqu.lib.base.BaseRecyclerAdapter;

import org.eshow.demo.R;

import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-5-23 0023.
 */

public class SwipeAdapter extends BaseRecyclerAdapter<String> {

    public SwipeAdapter(Context mContext, List<String> mData) {
        super(mContext, mData);
    }

    @Override
    protected RecyclerView.ViewHolder initViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_swipe, null);
        return new ViewHolder(view);
    }

    @Override
    protected void bindingViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.name.setText(mData.get(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.swipe_name)
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
