package org.eshow.demo.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bangqu.lib.utils.TimerThread;
import com.bangqu.lib.volley.ResponseCallBack;
import com.bangqu.lib.widget.ClearableEditText;

import org.eshow.demo.R;
import org.eshow.demo.base.BaseActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginCodeActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.login_code_mobile)
    ClearableEditText loginCodeMobile;
    @BindView(R.id.login_code_token)
    ClearableEditText loginCodeToken;
    @BindView(R.id.login_get_code)
    Button loginGetCode;

    private final int message_what = 1;
    private final int sleep_time = 1000;
    private TimerThread timerThread;
    private int times = 60;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_logincode);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        title.setText("验证码登录");
    }

    @Override
    protected void addViewListener() {
        loginCodeMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && s.toString().length() == 11) {
                    loginGetCode.setEnabled(true);
                } else {
                    loginGetCode.setEnabled(false);
                }
            }
        });
    }

    @OnClick({R.id.login_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_pass:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (timerThread != null) {
            timerThread.pauseThread();
            timerThread = null;
        }
        super.onDestroy();
    }
}
