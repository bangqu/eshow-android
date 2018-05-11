package com.bangqu.lib.slipload.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bangqu.lib.R;
import com.bangqu.lib.slipload.callback.IHeaderCallBack;

public class SlipLoadHeader implements IHeaderCallBack {

    private RelativeLayout mHeaderView;
    private ImageView mArrowImageView;
    private ImageView mOkImageView, mFailImageView;
    private ProgressBar mProgressBar;
    private TextView mHintTextView;
    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;
    private final int ROTATE_ANIM_DURATION = 180;

    /**
     * @param context
     */
    public SlipLoadHeader(Context context) {
        initView(context);
    }

    private void initView(Context context) {
        mHeaderView = (RelativeLayout) LayoutInflater.from(context).inflate(
                R.layout.layout_slipload_header, null);
        mArrowImageView = (ImageView) mHeaderView.findViewById(R.id.refresh_header_arrow);
        mOkImageView = (ImageView) mHeaderView.findViewById(R.id.refresh_header_ok);
        mFailImageView = (ImageView) mHeaderView.findViewById(R.id.refresh_header_fail);
        mHintTextView = (TextView) mHeaderView.findViewById(R.id.refresh_header_hint);
        mProgressBar = (ProgressBar) mHeaderView.findViewById(R.id.refresh_header_progressbar);

        mRotateUpAnim = new RotateAnimation(0.0f, 180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
    }

    public RelativeLayout getHeaderView() {
        return mHeaderView;
    }

    @Override
    public void onStateNormal() {
        mProgressBar.setVisibility(View.GONE);
        mArrowImageView.setVisibility(View.VISIBLE);
        mOkImageView.setVisibility(View.GONE);
        mFailImageView.setVisibility(View.GONE);
        mArrowImageView.startAnimation(mRotateDownAnim);
        mHintTextView.setText(R.string.refreshview_header_hint_normal);
    }

    @Override
    public void onStateReady() {
        mProgressBar.setVisibility(View.GONE);
        mOkImageView.setVisibility(View.GONE);
        mFailImageView.setVisibility(View.GONE);
        mArrowImageView.setVisibility(View.VISIBLE);
        mArrowImageView.clearAnimation();
        mArrowImageView.startAnimation(mRotateUpAnim);
        mHintTextView.setText(R.string.refreshview_header_hint_ready);
    }

    @Override
    public void onStateRefreshing() {
        mArrowImageView.clearAnimation();
        mArrowImageView.setVisibility(View.GONE);
        mOkImageView.setVisibility(View.GONE);
        mFailImageView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mHintTextView.setText(R.string.refreshview_header_hint_refreshing);
    }

    @Override
    public void onStateSuccess() {
        mArrowImageView.setVisibility(View.GONE);
        mOkImageView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mFailImageView.setVisibility(View.GONE);
        mHintTextView.setText(R.string.refreshview_header_hint_success);
    }

    @Override
    public void onStateFail() {
        mArrowImageView.setVisibility(View.GONE);
        mOkImageView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mFailImageView.setVisibility(View.VISIBLE);
        mHintTextView.setText(R.string.refreshview_header_hint_fail);
    }

}
