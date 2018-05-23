package org.eshow.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bangqu.lib.base.BaseSimpleAdapter;

import org.eshow.demo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 15052286501 on 2017/8/29.
 */
public class FunctionAdapter extends BaseSimpleAdapter<String> {

    public FunctionAdapter(Context mContext, List<String> mData) {
        super(mContext, mData);
    }

    @Override
    protected View getViewAtPosition(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_function, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mData.get(position));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.function_name)
        TextView name;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
