package com.bangqu.lib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bangqu.lib.R;

/**
 * custom ratingbaar
 * Created by  11/3/16.
 * 评分条
 */
public class SuperRatingBar extends LinearLayout {

    public static final int START_COUNT_FIVE = 5;
    public static final int START_MAX_COUNT = 10;

    private final int DEFAULT_IMAGE_SIZE = 54;
    private final int DEFAULT_IMAGE_PADDING = 8;
    private final int DEFAULT_RATING = 0;
    private final int DEFAULT_STAR = R.mipmap.icon_ratingbar_normal;
    private final int DEFAULT_STAR_SELECTED = R.mipmap.icon_ratingbar_checked;
    private final float DEFAULT_RATING_STEP = 0.5f;
    private final boolean DEFAULT_IS_INDICATOR = true;

    private int numStar;
    private float imageSize;
    private float imagePadding;
    private float rating;
    private float rateStep;
    private int drawableNormal;
    private int drawableSelected;
    private boolean isIndicator;
    private Drawable[] halfSelected;

    private Context mContext;

    public SuperRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SuperRatingBar);
        numStar = a.getInt(R.styleable.SuperRatingBar_numStars, START_COUNT_FIVE);
        imageSize = a.getDimension(R.styleable.SuperRatingBar_imageSize, DEFAULT_IMAGE_SIZE);
        imagePadding = a.getDimension(R.styleable.SuperRatingBar_imagePadding, DEFAULT_IMAGE_PADDING);
        drawableNormal = a.getResourceId(R.styleable.SuperRatingBar_drawableNormal, DEFAULT_STAR);
        drawableSelected = a.getResourceId(R.styleable.SuperRatingBar_drawableSelected, DEFAULT_STAR_SELECTED);
        rating = a.getFloat(R.styleable.SuperRatingBar_rate, DEFAULT_RATING);
        rateStep = a.getFloat(R.styleable.SuperRatingBar_rateStep, DEFAULT_RATING_STEP);
        isIndicator = a.getBoolean(R.styleable.SuperRatingBar_isIndicator, DEFAULT_IS_INDICATOR);
        setOrientation(LinearLayout.HORIZONTAL);
        Resources r = getResources();
        halfSelected = new Drawable[2];
        halfSelected[0] = r.getDrawable(drawableNormal);
        halfSelected[1] = new ClipDrawable(getResources().getDrawable(drawableSelected), Gravity.LEFT, ClipDrawable.HORIZONTAL);
        iniView();
    }

    public SuperRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SuperRatingBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs);
    }


    public SuperRatingBar(Context context) {
        super(context);
    }

    private void iniView() {
        removeAllViews();
        for (int i = 0; i < numStar; i++) {
            addChild(i);
        }
        setRating(rating);
    }


    private void addChild(int position) {
        ImageView imageView = new ImageView(mContext);
        LayoutParams params = new LayoutParams((int) imageSize, (int) imageSize);
        if (position != numStar - 1) {
            params.rightMargin = (int) imagePadding;
        }
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(drawableNormal);
        if (!isIndicator) {
            imageView.setOnTouchListener(new StarTouchListener(position));
        }
        addView(imageView);
    }

    public void setNumStar(int num) {
        if (num <= 0 || num > START_MAX_COUNT) {
            numStar = START_COUNT_FIVE;
        } else {
            numStar = num;
        }
        iniView();
    }

    public int getNumStar() {
        return numStar;
    }

    public void setRating(float rating) {
        if (rating < 0 || rating > numStar) {
            this.rating = numStar;
        } else {
            this.rating = rating;
        }
        updateRating();
    }

    public float getRating() {
        return rating;
    }

    public void updateRating() {
        for (int i = 0; i < numStar; i++) {
            int rat = (int) rating;
            if (i < rat) {
                ((ImageView) getChildAt(i)).setImageResource(drawableSelected);
            } else if (i == rat) {
                float level = (rating - rat) * 10000;
                halfSelected[1].setLevel((int) level);
                ((ImageView) getChildAt(i)).setImageDrawable(new LayerDrawable(halfSelected));
            } else {
                ((ImageView) getChildAt(i)).setImageResource(drawableNormal);
            }
        }
    }

    private void setDrawLevel(ImageView iv, float lv) {
        halfSelected[1].setLevel((int) lv);
        iv.setImageDrawable(new LayerDrawable(halfSelected));
    }

    private class StarTouchListener implements OnTouchListener {

        private int position;

        public StarTouchListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int action = MotionEventCompat.getActionMasked(event);
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    float x = event.getX();
                    int num = (int) ((x / v.getWidth()) / rateStep);
                    rating = position + (num + 1) * rateStep;
                    updateRating();
                    break;
            }
            return true;
        }
    }

}
