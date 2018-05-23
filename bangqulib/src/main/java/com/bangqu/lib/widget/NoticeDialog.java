package com.bangqu.lib.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bangqu.lib.R;
import com.bangqu.lib.utils.AppUtils;

/**
 * Created by Administrator on 2016/7/4.
 */
public class NoticeDialog extends Dialog {

    private TextView tv_title;
    private TextView tv_message;

    public NoticeDialog(Context context, String message) {
        this(context, "提示", message);
    }

    public NoticeDialog(Context context, String title, String notice) {
        super(context, R.style.menu_dialog_style);
        setContentView(R.layout.layout_notice_dialog);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (AppUtils.getDisplayMetrics(context).widthPixels * 0.8); //
        window.setAttributes(p);
        tv_title = findViewById(R.id.notice_title);
        tv_message = findViewById(R.id.notice_message);
        tv_title.setText(title);
        tv_message.setText(notice);
        findViewById(R.id.notice_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
