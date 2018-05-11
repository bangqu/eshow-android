package com.bangqu.lib.slipload.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bangqu.lib.R;
import com.bangqu.lib.slipload.callback.IFooterCallBack;


public class SlipLoadFooter implements IFooterCallBack {

    private RelativeLayout mFooterView;
    private TextView mHintTextView;
    private RelativeLayout mLoadingView;
    private boolean isComplete = false;

    public boolean isComplete() {
        return isComplete;
    }

    /**
     * @param context
     */
    public SlipLoadFooter(Context context) {
        initView(context);
    }

    private void initView(Context context) {
        mFooterView = (RelativeLayout) LayoutInflater.from(context).inflate(
                R.layout.layout_slipload_footer, null);

        mHintTextView = (TextView) mFooterView.findViewById(R.id.refresh_footer_hint);
        mLoadingView = (RelativeLayout) mFooterView.findViewById(R.id.refresh_footer_loading);
    }

    public RelativeLayout getFooterView() {
        return mFooterView;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
        if (isComplete) {
            onStateComplete();
        } else {
            onStateNormal();
        }
    }

    @Override
    public void onStateNormal() {
        mLoadingView.setVisibility(View.GONE);
        mHintTextView.setVisibility(View.VISIBLE);
        mHintTextView.setText(R.string.refreshview_footer_hint_normal);
    }

    @Override
    public void onStateReady() {
        mLoadingView.setVisibility(View.GONE);
        mHintTextView.setVisibility(View.VISIBLE);
        mHintTextView.setText(R.string.refreshview_footer_hint_ready);
    }

    @Override
    public void onStateLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mHintTextView.setVisibility(View.GONE);
    }

    @Override
    public void onStateSuccess() {
        mLoadingView.setVisibility(View.GONE);
        mHintTextView.setVisibility(View.VISIBLE);
        mHintTextView.setText(R.string.refreshview_footer_hint_success);
    }

    @Override
    public void onStateFail() {
        mLoadingView.setVisibility(View.GONE);
        mHintTextView.setVisibility(View.VISIBLE);
        mHintTextView.setText(R.string.refreshview_footer_hint_fail);
    }

    @Override
    public void onStateComplete() {
        mLoadingView.setVisibility(View.GONE);
        mHintTextView.setVisibility(View.VISIBLE);
        mHintTextView.setText(R.string.refreshview_footer_hint_complete);
    }

}
