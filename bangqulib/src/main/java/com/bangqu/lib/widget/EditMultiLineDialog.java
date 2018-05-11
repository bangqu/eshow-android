package com.bangqu.lib.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bangqu.lib.R;
import com.bangqu.lib.utils.AppUtils;

/**
 * Created by Administrator on 2016/7/4.
 */
public class EditMultiLineDialog extends Dialog {

    private TextView tv_title;
    private EditText et_value;
    private Context mContext;
    private OnOperaClickedListener onOperaClickedListener;

    public EditMultiLineDialog(Context context, String title, OnOperaClickedListener listener) {
        this(context, title, -1, listener);
    }

    public EditMultiLineDialog(Context context, String title, String hint, OnOperaClickedListener listener) {
        this(context, title, hint, -1, listener);
    }

    public EditMultiLineDialog(Context context, String title, int inputType, OnOperaClickedListener listener) {
        this(context, title, null, null, inputType, listener);
    }

    public EditMultiLineDialog(Context context, String title, String hint, int inputType, OnOperaClickedListener listener) {
        this(context, title, null, hint, inputType, listener);
    }

    public EditMultiLineDialog(Context context, String title, String value, String hint, int inputType, OnOperaClickedListener listener) {
        super(context, R.style.menu_dialog_style);
        mContext = context;
        onOperaClickedListener = listener;
        setContentView(R.layout.dialog_editmultiline);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (AppUtils.getDisplayMetrics(context).widthPixels * 0.8); //
        window.setAttributes(p);
        tv_title = findViewById(R.id.edittext_title);
        et_value = findViewById(R.id.edittext_value);
        tv_title.setText(title);
        if (inputType != -1)
            et_value.setInputType(inputType);
        if (!TextUtils.isEmpty(hint))
            et_value.setHint(hint);
        if (!TextUtils.isEmpty(value))
            et_value.setText(value);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.edittext_ok) {
                    if (onOperaClickedListener != null) {
                        if (TextUtils.isEmpty(et_value.getText())) {
                            onOperaClickedListener.onNullInputNotice();
                            return;
                        }
                        onOperaClickedListener.operaClickedListener(et_value.getText().toString(), true);
                    }
                    AppUtils.hideSoftInput(mContext, et_value);
                    dismiss();
                } else if (i == R.id.edittext_cancel) {
                    AppUtils.hideSoftInput(mContext, et_value);
                    if (onOperaClickedListener != null) {
                        onOperaClickedListener.operaClickedListener(null, false);
                    }
                    dismiss();
                }
            }
        };
        findViewById(R.id.edittext_ok).setOnClickListener(onClickListener);
        findViewById(R.id.edittext_cancel).setOnClickListener(onClickListener);
    }

    public interface OnOperaClickedListener {
        void operaClickedListener(String value, boolean result);

        void onNullInputNotice();
    }

}
