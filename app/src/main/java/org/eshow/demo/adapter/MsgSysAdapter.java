package org.eshow.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.eshow.demo.R;
import org.eshow.demo.model.MsgModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 15052286501 on 2017/8/29.
 */
public class MsgSysAdapter extends BaseAdapter {

    private List<MsgModel.ContentBean> mList;
    private Context mContext;

    public MsgSysAdapter(Context context, List<MsgModel.ContentBean> list) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_msgsys, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MsgModel.ContentBean model = mList.get(position);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.msgsys_title)
        TextView title;
        @BindView(R.id.msgsys_context)
        TextView context;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
