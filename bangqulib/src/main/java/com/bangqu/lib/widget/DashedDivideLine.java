package com.bangqu.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.bangqu.lib.R;

/**
 * 虚实分割线
 * Created by renruigang on 2015/12/22.
 */
public class DashedDivideLine extends View {

	public static final int DEFAULT_DASHWIDTH = 2;
	public static final int DEFAULT_SOLIDWIDTH = 3;
	private int solidWidth;
	private int dashWidth;
	private int lineColor;
	/**
	 * view的宽度
	 */
	private int mWidth;
	private int mHeight;

	public DashedDivideLine(Context context) {
		super(context);
	}

	public DashedDivideLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public DashedDivideLine(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// super一定要在我们的代码之前配置文件
		init(context, attrs, defStyle);
	}

	/**
	 * 初始化读取参数
	 */
	private void init(Context context, AttributeSet attrs, int defStyle) {
		// TypeArray中含有我们需要使用的参数
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DashedDivideLine, defStyle, 0);
		if (a != null) {
			dashWidth = a.getDimensionPixelSize(R.styleable.DashedDivideLine_dashWidth,
					(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_DASHWIDTH,
							getResources().getDisplayMetrics()));
			solidWidth = a.getDimensionPixelSize(R.styleable.DashedDivideLine_solidWidth,
					(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_SOLIDWIDTH,
							getResources().getDisplayMetrics()));
			lineColor = a.getColor(R.styleable.DashedDivideLine_lineColor, Color.BLACK);
			a.recycle();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = getMeasuredWidth();
		mHeight = getMeasuredHeight();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(lineColor);// 颜色可以自己设置
		Path path = new Path();
		path.moveTo(0, 0);// 起始坐标
		if (mWidth > mHeight) {
			paint.setStrokeWidth(mHeight);
			path.lineTo(mWidth, 0);// 终点坐标
		} else {
			paint.setStrokeWidth(mWidth);
			path.lineTo(0, mHeight);// 终点坐标
		}
		PathEffect effects = new DashPathEffect(new float[] { solidWidth, dashWidth, solidWidth, dashWidth }, 1);// 设置虚线的间隔和点的长度
		paint.setPathEffect(effects);
		canvas.drawPath(path, paint);
	}
}