package com.bangqu.lib.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bangqu.lib.R;

/**
 * Created by Administrator on 2016/7/4.
 */
public class NoticeDialog extends Dialog {

    private TextView title;

    public NoticeDialog(final Context context, String notice, final NoticeConfirmListener listener) {
        super(context, R.style.menu_dialog_style);
        setContentView(R.layout.layout_notice_dialog);
        getWindow().setGravity(Gravity.CENTER);
        title = findViewById(R.id.confirm_title);
        title.setText(notice);
        findViewById(R.id.confirm_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.onConfirmClick();
                }
            }
        });
    }

    public interface NoticeConfirmListener {
        void onConfirmClick();
    }
}
