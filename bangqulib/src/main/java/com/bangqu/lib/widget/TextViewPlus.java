package com.bangqu.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.bangqu.lib.R;

/**
 * 添加可以设置drawable大小的功能
 *
 * @author Daniel
 * @version 创建时间: Jul 26, 2012 5:28:59 PM
 */
public class TextViewPlus extends AppCompatTextView {

    // 需要从xml中读取的各个方向图片的宽和高
    private int drawHeight = -1;
    private int drawWidth = -1;

    public TextViewPlus(Context context) {
        super(context);
    }

    public TextViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        // super一定要在我们的代码之前配置文件
        init(context, attrs, 0);
    }

    public TextViewPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // super一定要在我们的代码之前配置文件
        init(context, attrs, defStyle);
    }

    /**
     * 初始化读取参数
     */
    private void init(Context context, AttributeSet attrs, int defStyle) {
        // TypeArray中含有我们需要使用的参数
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextViewPlus, defStyle, 0);
        if (a != null) {
            drawHeight = a.getDimensionPixelSize(R.styleable.TextViewPlus_drawHeight, -1);
            drawWidth = a.getDimensionPixelSize(R.styleable.TextViewPlus_drawWidth, -1);
            a.recycle();
            // 获取各个方向的图片，按照：左-上-右-下 的顺序存于数组中
            Drawable[] drawables = getCompoundDrawables();
            // 0-left; 1-top; 2-right; 3-bottom;
            for (Drawable drawable : drawables) {
                // 设定图片大小
                setImageSize(drawable);
            }
            // 将图片放回到TextView中
            setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        }
    }

    /**
     * 设定图片的大小
     */
    private void setImageSize(Drawable d) {
        if (d == null) {
            return;
        }
        // 如果有某个方向的宽或者高没有设定值，则不去设定图片大小
        if (drawWidth != -1 && drawHeight != -1) {
            d.setBounds(0, 0, drawWidth, drawHeight);
        }
    }
}