package org.eshow.demo.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.bangqu.lib.utils.AppUtils;

/**
 * Created by Administrator on 2018-6-8 0008.
 */

public class LetterNavigation extends View {

    /*绘制的列表导航字母*/
    private String words[] = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private String letterStr = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /*字母画笔*/
    private Paint wordsPaint;
    /*字母背景画笔*/
    private Paint bgPaint;
    /*每一个字母的宽度*/
    private int itemWidth;
    /*每一个字母的高度*/
    private int itemHeight;
    /*手指按下的字母索引*/
    private int touchIndex = 0;
    /*手指按下的字母改变接口*/
    private OnLetterChangeListener onLetterChangeListener;
    /*绘制top边距，高度27的余数/2*/
    private int topPadding = 0;

    public LetterNavigation(Context context) {
        super(context);
        init(context);
    }

    public LetterNavigation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LetterNavigation(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LetterNavigation(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        itemWidth = getMeasuredWidth();
        int height = getMeasuredHeight();
        itemHeight = height / 27;
        topPadding = (height % 27) / 2;
    }

    private void init(Context context) {
        wordsPaint = new Paint();
        wordsPaint.setColor(Color.DKGRAY);
        wordsPaint.setAntiAlias(false);//设置画笔为无锯齿
        wordsPaint.setTextAlign(Paint.Align.CENTER);
        wordsPaint.setTextSize(AppUtils.sp2px(context, 12));
        bgPaint = new Paint();
        bgPaint.setAntiAlias(false);
        bgPaint.setColor(Color.parseColor("#66ccf5"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < words.length; i++) {
            //判断是不是我们按下的当前字母
            if (touchIndex == i) {
                //绘制文字圆形背景
                canvas.drawCircle(itemWidth / 2, topPadding + itemHeight / 2 + i * itemHeight, itemHeight / 2, bgPaint);
                wordsPaint.setColor(Color.WHITE);
            } else {
                wordsPaint.setColor(Color.DKGRAY);
            }
            //获取文字的宽高
            Rect rect = new Rect();
            wordsPaint.getTextBounds(words[i], 0, 1, rect);
            //绘制字母
            float wordX = itemWidth / 2;
            float wordY = topPadding + i * itemHeight + (itemHeight + rect.height()) / 2;
            canvas.drawText(words[i], wordX, wordY, wordsPaint);
        }
    }

    /**
     * 当手指触摸按下的时候改变字母背景颜色
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                //获得我们按下的是那个索引(字母)
                int index = (int) ((y - topPadding) / itemHeight);
                if (index != touchIndex)
                    touchIndex = index;
                //防止数组越界
                if (onLetterChangeListener != null && 0 <= touchIndex && touchIndex <= words.length - 1) {
                    //回调按下的字母
                    onLetterChangeListener.letterChanged(words[touchIndex]);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //手指抬起,不做任何操作
                break;
        }
        return true;
    }

    public void setLetterFocused(String letter) {
        this.touchIndex = letterStr.indexOf(letter);
        invalidate();
    }

    public void setOnLetterChangeListener(OnLetterChangeListener onLetterChangeListener) {
        this.onLetterChangeListener = onLetterChangeListener;
    }

    /*手指按下了哪个字母的回调接口*/
    public interface OnLetterChangeListener {
        void letterChanged(String letter);
    }
}
