package com.bangqu.lib.slipload.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangqu.lib.R;
import com.bangqu.lib.slipload.callback.IHeaderCallBack;

public class SlipLoadHeader2 implements IHeaderCallBack {

    private LinearLayout mHeaderView;
    private ImageView xlistview_header_arrow, xlistview_header_progressbar;
    private TextView xlistview_header_hint_textview;

    public LinearLayout getHeaderView() {
        return mHeaderView;
    }

    public SlipLoadHeader2(Context context) {
        initView(context);
    }

    private void initView(Context context) {
        mHeaderView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_slipload_header2, null);
        xlistview_header_arrow = mHeaderView.findViewById(R.id.xlistview_header_arrow);
        xlistview_header_progressbar = mHeaderView.findViewById(R.id.xlistview_header_progressbar);
        xlistview_header_hint_textview = mHeaderView.findViewById(R.id.xlistview_header_hint_textview);
        AnimationDrawable animationDrawable = (AnimationDrawable) xlistview_header_progressbar.getDrawable();
        animationDrawable.start();
    }

    @Override
    public void onStateNormal() {
        xlistview_header_hint_textview.setText(R.string.refreshview_header_hint_normal);
        xlistview_header_arrow.setVisibility(View.VISIBLE);
        xlistview_header_progressbar.setVisibility(View.GONE);
    }

    @Override
    public void onStateReady() {
        xlistview_header_hint_textview.setText(R.string.refreshview_header_hint_ready);
    }

    @Override
    public void onStateRefreshing() {
        xlistview_header_hint_textview.setText(R.string.refreshview_header_hint_refreshing);
        xlistview_header_arrow.setVisibility(View.GONE);
        xlistview_header_progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStateSuccess() {
        xlistview_header_hint_textview.setText(R.string.refreshview_footer_hint_success);
        xlistview_header_progressbar.setVisibility(View.GONE);
        xlistview_header_arrow.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStateFail() {
        xlistview_header_hint_textview.setText(R.string.refreshview_footer_hint_fail);
        xlistview_header_progressbar.setVisibility(View.GONE);
        xlistview_header_arrow.setVisibility(View.VISIBLE);
    }

}
