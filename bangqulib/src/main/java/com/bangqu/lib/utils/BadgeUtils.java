package com.bangqu.lib.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by Administrator on 2018-6-1 0001.
 */

public class BadgeUtils {
    public static void showHuaweiBadge(Context context, String packageName, int count) {
        Bundle extra = new Bundle();
        extra.putString("package", packageName);
        extra.putString("class", packageName + ".activity.SplashActivity");
        extra.putInt("badgenumber", count);
        context.getContentResolver().
                call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, extra);
    }
}
