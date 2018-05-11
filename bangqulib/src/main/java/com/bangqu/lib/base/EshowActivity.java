package com.bangqu.lib.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.bangqu.lib.utils.AppUtils;
import com.bangqu.lib.volley.FileParams;
import com.bangqu.lib.volley.FileRequest;
import com.bangqu.lib.volley.JsonRequest;
import com.bangqu.lib.volley.VolleyCallBack;
import com.bangqu.lib.volley.ResponseCallBack;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by renruigang on 2017/11/6.
 */
public abstract class EshowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutView(savedInstanceState);
        initView();
        requestData();
        addViewListener();
    }

    protected abstract void setLayoutView(Bundle savedInstanceState);

    protected abstract void initView();

    protected abstract void requestData();

    protected abstract void addViewListener();

    protected abstract <T> void addToRequestQueue(Request<T> req);

    protected void goToActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }

    protected void goToActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void goToActivityForResult(Class activity, int code) {
        startActivityForResult(new Intent(this, activity), code);
    }

    protected void goToActivityForResult(Class activity, Bundle bundle, int code) {
        Intent intent = new Intent(this, activity);
        intent.putExtras(bundle);
        startActivityForResult(intent, code);
    }

    protected String getData(String url, HashMap<String, String> params, VolleyCallBack callBack) {
        String api_url = url;
        if (params != null) {
            api_url = url + "?" + getParamsToUrl(params);
        }
        JsonRequest gsonRequest = new JsonRequest(api_url, callBack);
        gsonRequest.setShouldCache(false);
        addToRequestQueue(gsonRequest);
        return gsonRequest.getCacheKey();
    }

    protected String postData(String url, HashMap<String, String> params, VolleyCallBack callBack) {
        params = params != null ? params : new HashMap<String, String>();
        JsonRequest gsonRequest = new JsonRequest(url, params, callBack);
        gsonRequest.setShouldCache(false);
        addToRequestQueue(gsonRequest);
        return gsonRequest.getUrl();
    }

    protected String getParamsToUrl(Map<String, String> keyMaps) {
        StringBuilder result = new StringBuilder();
        if (keyMaps == null) {
            return "";
        }
        for (ConcurrentHashMap.Entry<String, String> entry : keyMaps.entrySet()) {
            if (result.length() > 0) {
                result.append("&");
            }
            if (TextUtils.isEmpty(entry.getValue())) {
                continue;
            }
            result.append(entry.getKey());
            result.append("=");
            try {
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                result.append(entry.getValue());
            }
        }
        return result.toString();
    }

    protected void showToast(final String msg) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(EshowActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void showToast(final int resId) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(EshowActivity.this, resId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*动态权限声明*/
    protected boolean checkPermission(String permission, int requestCode) {
        //再onCreate方法当中加入以下代码，判断系统是否需要动态获取权限。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{permission}, requestCode);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppUtils.hideSoftInput(this, getCurrentFocus());
    }
}

