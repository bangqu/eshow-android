package org.eshow.demo.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bangqu.lib.volley.ResponseCallBack;
import com.bangqu.lib.widget.ClearableEditText;
import com.google.gson.JsonObject;

import org.eshow.demo.R;
import org.eshow.demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.login_mobile)
    ClearableEditText loginMobile;
    @BindView(R.id.login_password)
    ClearableEditText loginPassword;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        title.setText("密码登录");
        rightTv.setText(R.string.register);
        rightTv.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.right_tv, R.id.login_confirm, R.id.login_code, R.id.lose_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.right_tv:
                goToActivity(RegisterActivity.class);
                break;
            case R.id.login_confirm:
                break;
            case R.id.login_code:
                goToActivity(LoginCodeActivity.class);
                break;
            case R.id.lose_password:
                goToActivity(LosePassActivity.class);
                break;
        }

    }

    private ResponseCallBack responseCallBack = new ResponseCallBack() {
        @Override
        public void onResponseSuccess(String tag, JsonObject response, String code, String msg) {

        }

        @Override
        public void onResponseNoData(String tag, String code, String msg) {

        }

        @Override
        public void onResponseError(String tag, String code, String msg) {

        }

        @Override
        public void onResponseOverDue(String tag, String code, String msg) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
