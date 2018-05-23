package com.bangqu.lib.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bangqu.lib.R;
import com.bangqu.lib.listener.DialogConfirmListener;

/**
 * Created by Administrator on 2016/7/4.
 */
public class UpdateAppDialog extends Dialog {

    private TextView title, content;
    private DialogConfirmListener onConfrimClickedListener;

    public UpdateAppDialog(Context context, String version, String notice, DialogConfirmListener listener) {
        super(context, R.style.menu_dialog_style);
        this.onConfrimClickedListener = listener;
        setContentView(R.layout.dialog_updateapp);
        getWindow().setGravity(Gravity.CENTER);
        setCancelable(false);
        title = findViewById(R.id.update_title);
        content = findViewById(R.id.update_content);
        title.setText("发现新版本" + version);
        content.setText(notice);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.update_ok) {
                    if (onConfrimClickedListener != null) {
                        onConfrimClickedListener.onDialogConfirm(true, null);
                    }
                    dismiss();
                } else if (i == R.id.update_cancel) {
                    if (onConfrimClickedListener != null) {
                        onConfrimClickedListener.onDialogConfirm(false, null);
                    }
                    dismiss();
                }
            }
        };
        findViewById(R.id.update_ok).setOnClickListener(onClickListener);
        findViewById(R.id.update_cancel).setOnClickListener(onClickListener);
    }

}
