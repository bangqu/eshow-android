package org.eshow.demo.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bangqu.lib.utils.BadgeUtils;

import org.eshow.demo.R;
import org.eshow.demo.base.BaseActivity;
import org.eshow.demo.util.LogInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.OnClick;

public class BadgeActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.badge_manufacturer)
    TextView badgeManufacturer;
    @BindView(R.id.badge_count)
    EditText badgeCount;

    private String deviceManufacturer;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_badge);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        title.setText("桌面角标");
        deviceManufacturer = Build.MANUFACTURER.toUpperCase();
        badgeManufacturer.setText("手机厂商：" + deviceManufacturer);
    }

    @OnClick({R.id.show_badge, R.id.clear_badge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.show_badge:
                if (TextUtils.isEmpty(badgeCount.getText())) {
                    showToast("请输入角标数字");
                    return;
                }
                int count = Integer.parseInt(badgeCount.getText().toString());
                switch (deviceManufacturer) {
                    case "XIAOMI":
                        showNotice(count);
                        break;
                    case "HUAWEI":
                        BadgeUtils.showHuaweiBadge(getApplicationContext(), "org.eshow.demo", count);
                        break;
                }
                break;
            case R.id.clear_badge:
                switch (deviceManufacturer) {
                    case "XIAOMI":
                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.cancel(0);
                        break;
                    case "HUAWEI":
                        BadgeUtils.showHuaweiBadge(getApplicationContext(), "org.eshow.demo", 0);
                        break;
                }
                break;
        }
    }

    private void showNotice(int count) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("title").setContentText("text").setSmallIcon(R.mipmap.ic_launcher);
        Notification notification = builder.build();
        try {
            Field field = notification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(notification);
            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
            method.invoke(extraNotification, count);
        } catch (Exception e) {
            e.printStackTrace();
            LogInfo.e(e);
        }
        mNotificationManager.notify(0, notification);
    }

}
