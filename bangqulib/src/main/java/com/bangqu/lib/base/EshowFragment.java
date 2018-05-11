package com.bangqu.lib.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.bangqu.lib.volley.FileParams;
import com.bangqu.lib.volley.VolleyCallBack;
import com.bangqu.lib.volley.ResponseCallBack;

import java.util.HashMap;
import java.util.Map;


public abstract class EshowFragment extends Fragment {

    protected String getParamsToUrl(Map<String, String> keyMaps) {
        return ((EshowActivity) getActivity()).getParamsToUrl(keyMaps);
    }

    protected void getData(String url, HashMap<String, String> params, VolleyCallBack callBack) {
        ((EshowActivity) getActivity()).getData(url, params, callBack);
    }

    protected void postData(String url, HashMap<String, String> params, VolleyCallBack callBack) {
        ((EshowActivity) getActivity()).postData(url, params, callBack);
    }

    protected boolean checkPermission(String permission, int requestCode) {
        //再onCreate方法当中加入以下代码，判断系统是否需要动态获取权限。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{permission}, requestCode);
                return false;
            }
        }
        return true;
    }

    protected void showToast(final String info) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void showToast(final int resId) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void goToActivity(Class activity) {
        startActivity(new Intent(getActivity(), activity));
    }

    protected void goToActivity(Class activity, boolean isFinish) {
        startActivity(new Intent(getActivity(), activity));
        if (isFinish) getActivity().finish();
    }

    protected void goToActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(getActivity(), activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void goToActivityForResult(Class activity, int code) {
        startActivityForResult(new Intent(getContext(), activity), code);
    }

    protected void goToActivityForResult(Class activity, Bundle bundle, int code) {
        Intent intent = new Intent(getActivity(), activity);
        intent.putExtras(bundle);
        startActivityForResult(intent, code);
    }

}