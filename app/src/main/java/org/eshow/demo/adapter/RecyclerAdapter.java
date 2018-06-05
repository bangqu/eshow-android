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

public class RecyclerAdapter extends BaseRecyclerAdapter<Person> {

    protected enum ITEM_TYPE {
        ITEM_TYPE_SELECTED,
        ITEM_TYPE_NORMAL
    }

    protected HashSet<Long> choiceSet = new HashSet<>();

    public HashSet<Long> getChoiceSet() {
        return choiceSet;
    }

    public RecyclerAdapter(Context mContext, List<Person> mData) {
        super(mContext, mData);
    }

    @Override
    public int getItemViewType(int position) {
        return choiceSet.contains(mData.get(position).getId()) ?
                ITEM_TYPE.ITEM_TYPE_SELECTED.ordinal() : ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
    }

    @Override
    protected void onItemClick(Person value, int position) {
        super.onItemClick(value, position);
        if (choiceSet.contains(value.getId())) {
            choiceSet.remove(value.getId());
        } else {
            choiceSet.add(value.getId());
        }
        notifyItemChanged(position);
    }

    @Override
    protected RecyclerView.ViewHolder initViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recycler, null);
        if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
            view.setBackgroundResource(R.drawable.shape_item_normal);
        } else {
            view.setBackgroundResource(R.drawable.shape_item_selected);
        }
        return new ViewHolder(view);
    }

    @Override
    protected void bindingViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.name.setText(mData.get(position).getName());
        viewHolder.mobile.setText(mData.get(position).getMobile());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recycler_name)
        TextView name;
        @BindView(R.id.recycler_mobile)
        TextView mobile;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
