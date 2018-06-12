package com.bangqu.lib;

import android.app.Activity;
import android.app.Application;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;
import com.bangqu.lib.volley.OkHttp3Stack;

import java.io.IOException;
import java.util.Map;
import java.util.Stack;

import okhttp3.OkHttpClient;

/**
 * Created by renruigang on 2017/11/6.
 */
public class EshowApplication extends Application {

    private Stack<Activity> activityStack = new Stack<>();
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void exitApplication() {
        clearStackActivity();
        System.exit(0);
    }

    public void addStackActivity(Activity context) {
        activityStack.add(context);
    }

    public void clearStackActivity() {
        for (Activity context : activityStack) {
            context.finish();
        }
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            synchronized (this) {
                if (mRequestQueue == null) {
                    mRequestQueue = Volley
                            .newRequestQueue(getApplicationContext(), new OkHttp3Stack());
                }
            }
        }
        return mRequestQueue;
    }
}
