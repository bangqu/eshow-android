package org.eshow.demo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import org.eshow.demo.base.BaseActivity;
import org.eshow.demo.comm.Constants;


public class SplashActivity extends BaseActivity {

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goToActivity(WelcomeActivity.class);
                finish();
            }
        }, Constants.SPLASH_TIME);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
