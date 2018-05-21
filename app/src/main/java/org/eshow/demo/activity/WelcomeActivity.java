package org.eshow.demo.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.eshow.demo.R;
import org.eshow.demo.adapter.WelcomeAdapter;
import org.eshow.demo.base.BaseActivity;
import org.eshow.demo.transfer.DepthPageTransformer;

import butterknife.BindView;
import butterknife.OnClick;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.welcome_viewpager)
    ViewPager welcomeViewPager;
    @BindView(R.id.welcome_dots)
    LinearLayout welcomeDots;
    @BindView(R.id.iv_light_dots)
    ImageView mLightDots;

    private int[] images = new int[]{
            R.mipmap.indicator1, R.mipmap.indicator2, R.mipmap.indicator3};
    private int mDistance;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void initView() {
        super.initView();
        welcomeViewPager.setAdapter(new WelcomeAdapter(this, images));
        welcomeViewPager.setPageTransformer(true, new DepthPageTransformer());
        addGrayDots();
    }

    @Override
    protected void addViewListener() {
        mLightDots.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获得两个圆点之间的距离
                mDistance = welcomeDots.getChildAt(1).getLeft() - welcomeDots.getChildAt(0).getLeft();
                mLightDots.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        welcomeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //页面滚动时小白点移动的距离，并通过setLayoutParams(params)不断更新其位置
                startMoveDot(position, mDistance * (position + positionOffset));
            }

            @Override
            public void onPageSelected(int position) {
                //页面跳转时，设置小圆点的margin
                startMoveDot(position, mDistance * position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.btn_register, R.id.btn_login, R.id.btn_skip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                goToActivity(LoginActivity.class);
                break;
            case R.id.btn_register:
                goToActivity(RegisterActivity.class);
                break;
            case R.id.btn_skip:
                goToActivity(MainActivity.class);
                break;
        }
    }

    private void startMoveDot(int position, float leftMargin) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLightDots.getLayoutParams();
        params.leftMargin = (int) leftMargin;
        mLightDots.setLayoutParams(params);
    }

    private void addGrayDots() {
        for (int i = 0; i < 3; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ImageView grayDot = new ImageView(this);
            grayDot.setImageResource(R.drawable.gray_dot);
            if (i > 0)
                layoutParams.leftMargin = 40;
            welcomeDots.addView(grayDot, layoutParams);
        }
    }
}
