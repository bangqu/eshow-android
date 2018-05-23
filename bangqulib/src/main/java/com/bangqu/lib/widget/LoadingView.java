package com.bangqu.lib.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bangqu.lib.R;

/**
 * Created by Administrator on 2017-12-13 0013.
 * 列表加载提示View
 * 包含加载中、加载失败、无数据等状态
 */

public class LoadingView extends RelativeLayout {

    public final static int LOADING = 1;
    public final static int NO_DATA = 2;
    public final static int SHOW_DATA = 3;
    public final static int NET_ERROR = 4;

    private ProgressBar progressBar;
    private ImageView imageView;
    private TextView textView;
    private int EMPTY_IMAGE = R.mipmap.request_empty;
    private int ERROR_IMAGE = R.mipmap.request_error;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_loading, this);
        progressBar = findViewById(R.id.loading_progress);
        imageView = findViewById(R.id.loading_image);
        textView = findViewById(R.id.loading_state);
    }

    public void setLoadingState(int state) {
        setLoadingState(state, null);
    }

    public void setLoadingState(int state, String notice) {
        switch (state) {
            case LOADING:
                setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                textView.setText(R.string.loading);
                break;
            case NO_DATA:
                setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(EMPTY_IMAGE);
                textView.setText(R.string.no_data);
                break;
            case NET_ERROR:
                setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(ERROR_IMAGE);
                textView.setText(R.string.net_error);
                break;
            case SHOW_DATA:
                setVisibility(View.GONE);
                break;
        }
        if (!TextUtils.isEmpty(notice)) {
            textView.setText(notice);
        }
    }
}
