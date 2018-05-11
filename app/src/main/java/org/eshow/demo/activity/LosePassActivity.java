package org.eshow.demo.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.bangqu.lib.utils.TimerThread;
import com.bangqu.lib.widget.ClearableEditText;

import org.eshow.demo.R;
import org.eshow.demo.base.BaseActivity;

import butterknife.BindView;

public class LosePassActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.losepass_mobile)
    ClearableEditText losepassMobile;
    @BindView(R.id.losepass_token)
    ClearableEditText losepassToken;
    @BindView(R.id.losepass_get_code)
    Button losepassGetCode;
    @BindView(R.id.losepass_password)
    ClearableEditText losepassPassword;
    @BindView(R.id.losepass_password_again)
    ClearableEditText losepassPasswordAgain;

    private final int message_what = 1;
    private final int sleep_time = 1000;
    private TimerThread timerThread;
    private int times = 60;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_losepass);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        title.setText("忘记密码");
    }

    @Override
    protected void addViewListener() {
        losepassMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && s.toString().length() == 11) {
                    losepassGetCode.setEnabled(true);
                } else {
                    losepassGetCode.setEnabled(false);
                }
            }
        });
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
