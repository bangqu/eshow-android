package org.eshow.demo.activity;

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
import com.bangqu.lib.volley.VolleyCallBack;
import com.bangqu.lib.widget.ClearableEditText;
import com.google.gson.JsonObject;

import org.eshow.demo.R;
import org.eshow.demo.base.BaseActivity;
import org.eshow.demo.comm.HttpConfig;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.register_mobile)
    ClearableEditText registerMobile;
    @BindView(R.id.register_check)
    ClearableEditText registerCheck;
    @BindView(R.id.register_get_code)
    Button registerGetCode;
    @BindView(R.id.register_password)
    ClearableEditText registerPassword;

    private final int message_what = 1;
    private final int sleep_time = 1000;
    private TimerThread timerThread;
    private int times = 60;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        title.setText("注册");
    }

    @Override
    protected void addViewListener() {
        registerMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && s.toString().length() == 11) {
                    registerGetCode.setEnabled(true);
                } else {
                    registerGetCode.setEnabled(false);
                }
            }
        });
    }

    @OnClick({R.id.register_get_code, R.id.register_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_get_code:
                if (TextUtils.isEmpty(registerMobile.getText())) {
                    showToast("请输入手机号");
                } else if (registerMobile.getText().toString().length() != 11) {
                    showToast("手机号位数错误");
                } else {
                    startCountDown();
                    requestToken(registerMobile.getText().toString());
                }
                break;
            case R.id.register_confirm:
                break;
        }
    }

    private void startCountDown() {
        if (timerThread == null) {
            timerThread = new TimerThread(handler, message_what, sleep_time);
            timerThread.start();
        } else {
            timerThread.resumeThread();
        }
        registerGetCode.setEnabled(false);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == message_what) {
                timerGetToken();
            }
            super.handleMessage(msg);
        }
    };

    private void timerGetToken() {
        if (times > 0) {
            registerGetCode.setText("重新获取" + times--);
        } else {
            registerGetCode.setText("重新获取");
            timerThread.pauseThread();
            registerGetCode.setEnabled(true);
        }
    }

    private void requestToken(String mobile) {
        HashMap<String, String> params = new HashMap<>();
        params.put("captcha.mobile", mobile);
        params.put("captcha.type", "signup");//	类型（signup注册验证，login登录验证，找回密码password，identity身份验证）
        getData(HttpConfig.GET_CAPTCHA, params, new VolleyCallBack("getcode", responseCallBack));
    }

    private ResponseCallBack responseCallBack = new ResponseCallBack() {
        @Override
        public void onResponseSuccess(String tag, JsonObject response, String code, String msg) {
            switch (tag) {
                case "getcode":
                    showToast(msg);
                    break;
            }
        }

        @Override
        public void onResponseNoData(String tag, String code, String msg) {

        }

        @Override
        public void onResponseError(String tag, String code, String msg) {
            showToast(msg);
        }

        @Override
        public void onResponseOverDue(String tag, String code, String msg) {

        }
    };

    @Override
    protected void onDestroy() {
        if (timerThread != null) {
            timerThread.pauseThread();
            timerThread = null;
        }
        super.onDestroy();
    }

}
