package org.eshow.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bangqu.lib.base.BaseRecyclerAdapter;

import org.eshow.demo.R;
import org.eshow.demo.model.ContactItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 15052286501 on 2017/8/29.
 */
public class ContactsAdapter extends BaseRecyclerAdapter<ContactItem> {

    public ContactsAdapter(Context mContext, List<ContactItem> mData) {
        super(mContext, mData);
    }

    @Override
    protected RecyclerView.ViewHolder initViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_contacts, null);
        return new ViewHolder(view);
    }

    @Override
    protected void bindingViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.itemView.setTag(position);
        final ContactItem model = mData.get(position);
        viewHolder.name.setText(model.name);
        viewHolder.mobile.setText(model.mobile);
        viewHolder.index.setText(String.valueOf(model.index).toUpperCase());
        if (position > 0 && mData.get(position - 1).index == model.index) {
            viewHolder.index.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.index.setVisibility(View.VISIBLE);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.contacts_name)
        TextView name;
        @BindView(R.id.contacts_mobile)
        TextView mobile;
        @BindView(R.id.contacts_index)
        TextView index;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
