package com.bangqu.lib.widget;

/**
 * Created by Administrator on 2018-4-25 0025.
 * 带一键清空操作的输入框
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.bangqu.lib.R;
import com.bangqu.lib.utils.AppUtils;

public class ClearableEditText extends AppCompatEditText implements View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {

    private Drawable clearTextIcon;

    public ClearableEditText(Context context) {
        this(context, null);
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {

    }

    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {

    }

    private void init(Context context) {
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        super.addTextChangedListener(this);
        Drawable drawable = ContextCompat.getDrawable(context, R.mipmap.ic_clear_edittext);
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, getCurrentHintTextColor());
        int size = AppUtils.dp2px(context, 24);
        clearTextIcon = wrappedDrawable;
        clearTextIcon.setBounds(0, 0, size, size);
        setClearIconVisible(false);
    }

    @Override
    public void onFocusChange(final View view, final boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    @Override
    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        final int x = (int) motionEvent.getX();
        if (x > getWidth() - clearTextIcon.getIntrinsicWidth()) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (clearTextIcon.isVisible()) {
                    setError(null);
                    setText("");
                    return true;
                }
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public final void onTextChanged(final CharSequence s, final int start, final
    int before, final int count) {
        if (isFocused()) {
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private void setClearIconVisible(final boolean visible) {
        clearTextIcon.setVisible(visible, false);
        Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], visible ? clearTextIcon : null, compoundDrawables[3]);
    }

}
