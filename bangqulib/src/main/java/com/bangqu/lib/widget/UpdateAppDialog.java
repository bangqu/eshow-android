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
public class UpdateAppDialog extends Dialog {

    private TextView title, content;

    public UpdateAppDialog(Context context, String version, String notice, final OnUpdateListener listener) {
        super(context, R.style.menu_dialog_style);
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
                    if (listener != null) {
                        listener.onUpdate(true);
                    }
                    dismiss();
                } else if (i == R.id.update_cancel) {
                    if (listener != null) {
                        listener.onUpdate(false);
                    }
                    dismiss();
                }
            }
        };
        findViewById(R.id.update_ok).setOnClickListener(onClickListener);
        findViewById(R.id.update_cancel).setOnClickListener(onClickListener);
    }

    public interface OnUpdateListener {
        void onUpdate(boolean value);
    }

}
