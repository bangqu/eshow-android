package com.bangqu.download.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bangqu.download.R;

import java.io.File;

/**
 * Created by Administrator on 2016/7/4.
 */
public class DownloadDialog extends Dialog {

    private TextView tv_title;
    private NumberProgressBar progressBar;
    private Button btnOk, btnCancel;
    private File downloadFile;

    public DownloadDialog(Context context, String title, DialogConfirmListener listener) {
        this(context, title, "后台下载", "取消", listener);
    }

    public void setProgress(int progress) {
        progressBar.setProgress(progress);
        if (progress >= 100) {
            tv_title.setText("下载完成");
            btnOk.setText("立即安装");
        }
    }

    public DownloadDialog(Context context, String title, String ok, String cancel, final DialogConfirmListener listener) {
        super(context, R.style.download_dialog_style);
        setContentView(R.layout.dialog_download);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.8); //
        window.setAttributes(p);
        tv_title = findViewById(R.id.progressbar_title);
        progressBar = findViewById(R.id.progressbar_pb);
        tv_title.setText(title);
        btnCancel = findViewById(R.id.progressbar_cancel);
        btnCancel.setText(cancel);
        btnOk = findViewById(R.id.progressbar_ok);
        btnOk.setText(ok);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.progressbar_ok) {
                    if (listener != null && downloadFile != null) {
                        listener.onDialogConfirm(true, downloadFile);
                    }
                    dismiss();
                } else if (i == R.id.progressbar_cancel) {
                    if (listener != null) {
                        listener.onDialogConfirm(false, null);
                    }
                    dismiss();
                }
            }
        };
        btnCancel.setOnClickListener(onClickListener);
        btnOk.setOnClickListener(onClickListener);
    }

    public void setDownloadFile(File downloadFile) {
        this.downloadFile = downloadFile;
    }

    public interface DialogConfirmListener {
        void onDialogConfirm(boolean result, Object value);
    }

}
