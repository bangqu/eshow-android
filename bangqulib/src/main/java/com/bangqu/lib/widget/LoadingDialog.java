package com.bangqu.lib.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.bangqu.lib.R;

/**
 * Created by Administrator on 2016/7/4.
 */
public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context, R.style.menu_dialog_style);
        setContentView(R.layout.dialog_loading);
        getWindow().setGravity(Gravity.CENTER);
    }

}
