package org.eshow.demo.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.bangqu.lib.base.EshowActivity;
import com.bangqu.lib.widget.LoadingDialog;

import org.eshow.demo.DemoApplication;
import org.eshow.demo.util.SharedPref;

import butterknife.ButterKnife;

/*
* 方法执行顺序
*  1、setLayoutView(savedInstanceState);
*  2、initView();
*  3、requestData();
*  4、addViewListener();
* */
public class BaseActivity extends EshowActivity {

    protected SharedPref sharedPref;
    private Dialog loadingDialog;
    protected Context mContext;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        DemoApplication.getInstance().addStackActivity(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        sharedPref = new SharedPref(this);
        mContext = this;
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void addViewListener() {

    }

    protected void setTextValue(TextView tv, String value) {
        if (!TextUtils.isEmpty(value)) {
            tv.setText(value);
        }
    }

    protected void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    protected void dismissLoading() {
        if (loadingDialog != null)
            loadingDialog.dismiss();
    }

    protected <T> void addToRequestQueue(Request<T> req) {
        req.setTag(getLocalClassName());
        DemoApplication.getInstance().getRequestQueue().add(req);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelAllRequest();
    }

    protected void cancelAllRequest() {
        DemoApplication.getInstance().getRequestQueue().cancelAll(getLocalClassName());
    }

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
