package org.eshow.demo.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bangqu.lib.listener.DialogConfirmListener;
import com.bangqu.lib.widget.ConfirmDialog;

import org.eshow.demo.R;
import org.eshow.demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void initView() {
        super.initView();
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        title.setText("设置");
    }

    @OnClick({R.id.setting_account_safe, R.id.setting_feedback, R.id.setting_aboutus, R.id.setting_question, R.id.setting_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_account_safe:
//                goToActivity(AccountSafeActivity.class);
                break;
            case R.id.setting_feedback:
//                Bundle bundle2 = new Bundle();
//                bundle2.putInt("title", R.string.setting_feedback);
//                bundle2.putString("url", HttpConfig.FEED_BACK);
//                goToActivity(WebActivity.class, bundle2);
                break;
            case R.id.setting_aboutus:
//                Bundle bundle = new Bundle();
//                bundle.putInt("title", R.string.setting_aboutus);
//                bundle.putString("url", HttpConfig.ABOUT_US);
//                goToActivity(WebActivity.class, bundle);
                break;
            case R.id.setting_question:
//                Bundle bundle1 = new Bundle();
//                bundle1.putInt("title", R.string.setting_question);
//                bundle1.putString("url", HttpConfig.QUESTION);
//                goToActivity(WebActivity.class, bundle1);
                break;
            case R.id.setting_logout:
                showExitDialog();
                break;
        }
    }

    private void showExitDialog() {
        new ConfirmDialog(this, "提示", "确认退出登录？", new DialogConfirmListener() {
            @Override
            public void onDialogConfirm(boolean value, Object v) {
                if (value) {
                    goToActivity(LoginActivity.class);
                    finish();
                }
            }
        }).show();
    }
}
