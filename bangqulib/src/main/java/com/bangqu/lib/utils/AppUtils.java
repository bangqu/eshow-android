package com.bangqu.lib.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Administrator on 2017-12-26 0026.
 */

public class AppUtils {

    /**
     * @param context
     * @return 当前屏幕像素
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * @param context
     * @param dipValue
     * @return 将dp 装换成PX
     */
    public static int dp2px(Context context, float dipValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, context.getResources().getDisplayMetrics());
    }

    //跳转到拨号界面，同时传递电话号码
    public static void dialPhone(Context context, String phone) {
        if (!phone.startsWith("tel:")) {
            phone = "tel:" + phone;
        }
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(phone)));
    }

    // 隐藏软键盘
    public static void hideSoftInput(Context context, View view) {
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
