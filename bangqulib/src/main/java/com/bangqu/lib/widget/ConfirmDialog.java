package com.bangqu.lib.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bangqu.lib.R;
import com.bangqu.lib.listener.DialogConfirmListener;
import com.bangqu.lib.utils.AppUtils;

/**
 * Created by Administrator on 2016/7/4.
 * 操作确认提示框
 */
public class ConfirmDialog extends Dialog {

    private TextView tv_title;
    private TextView tv_message;
    private DialogConfirmListener onConfrimClickedListener;

    public ConfirmDialog(Context context, String message, DialogConfirmListener listener) {
        this(context, "提示", message, listener);
    }

    public ConfirmDialog(Context context, String title, String message, DialogConfirmListener listener) {
        this(context, title, message, "确定", "取消", listener);
    }

    public ConfirmDialog(Context context, String title, String message, String ok, String cancel, DialogConfirmListener listener) {
        super(context, R.style.menu_dialog_style);
        onConfrimClickedListener = listener;
        setContentView(R.layout.layout_confirm_dialog);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (AppUtils.getDisplayMetrics(context).widthPixels * 0.8); //
        window.setAttributes(p);
        tv_title = findViewById(R.id.confirm_title);
        tv_message = findViewById(R.id.confirm_message);
        tv_title.setText(title);
        tv_message.setText(message);
        Button btnCancel = findViewById(R.id.confirm_cancel);
        btnCancel.setText(cancel);
        Button btnOk = findViewById(R.id.confirm_ok);
        btnOk.setText(ok);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.confirm_ok) {
                    if (onConfrimClickedListener != null) {
                        onConfrimClickedListener.onDialogConfirm(true, null);
                    }
                    dismiss();
                } else if (i == R.id.confirm_cancel) {
                    if (onConfrimClickedListener != null) {
                        onConfrimClickedListener.onDialogConfirm(false, null);
                    }
                    dismiss();
                }
            }
        };
        findViewById(R.id.confirm_ok).setOnClickListener(onClickListener);
        findViewById(R.id.confirm_cancel).setOnClickListener(onClickListener);
    }

}
