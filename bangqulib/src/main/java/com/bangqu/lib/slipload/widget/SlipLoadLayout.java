package com.bangqu.lib.slipload.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by renruigang on 2016/1/5.
 */
public class SlipLoadLayout extends ViewGroup {

    private static final int DRAG_MAX_DISTANCE = 64;
    private static final int DRAG_MIN_DISTANCE = 8;
    private static final int INVALID_POINTER = -1;
    private static final float DRAG_RATE = .5f;

    private static final int STATE_NORMAL = 1;
    private static final int STATE_READY = 2;
    private static final int STATE_REFRESHING = 3;
    private static final int STATE_COMPLETE = 4;

    /*最大滑动距离*/
    private int mDragMaxDistance;
    /*最小滑动距离*/
    private int mDragMinDistance;

    private int mActivePointerId;
    private boolean isBeingDragged;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private boolean isCanScroll;
    /*Y轴偏移量*/
    private int mOffsetY;
    /*目标View*/
    private View mTargetView;
    /*下拉刷新View*/
    private SlipLoadHeader refreshHeader;
    private RelativeLayout headerView;
    /*加载更多View*/
    private SlipLoadFooter refreshFooter;
    private RelativeLayout footerView;
    /*默认刷新配置*/
    private boolean isPullToRefresh = true;
    private boolean isLoadingMore = false;
    private boolean isAutoLoading = false;

    private int mState = -1;
    private OnRefreshListener mListener;
    private static Handler mHandler = new Handler();

    public void setLoadingMore(boolean loadingMore) {
        isLoadingMore = loadingMore;
        if (loadingMore) {
            isAutoLoading = false;
        }
    }

    public void setPullToRefresh(boolean pullToRefresh) {
        isPullToRefresh = pullToRefresh;
    }

    public void setAutoLoading(boolean autoLoading) {
        isAutoLoading = autoLoading;
        if (autoLoading) {
            isLoadingMore = false;
        }
    }

    public SlipLoadLayout(Context context) {
        this(context, null);
    }

    public SlipLoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDragMaxDistance = dp2px(DRAG_MAX_DISTANCE);
        mDragMinDistance = dp2px(DRAG_MIN_DISTANCE);
        /*下拉刷新View*/
        refreshHeader = new SlipLoadHeader(context);
        headerView = refreshHeader.getHeaderView();
        addView(headerView);
        /*加载更多View*/
        refreshFooter = new SlipLoadFooter(context);
        footerView = refreshFooter.getFooterView();
        addView(footerView);

        setWillNotDraw(false);
        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        int finalHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                finalHeight += child.getMeasuredHeight();
            }
        }
        setMeasuredDimension(width, finalHeight);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //此方法返回false，不接受动作序列中的后续事件;返回true，继续接受动作序列中的后续事件，如move、move、up
        if (!isEnabled()) {
            return super.dispatchTouchEvent(ev);
        }
        if (mState == STATE_REFRESHING) {
            return false;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    return super.dispatchTouchEvent(ev);
                }
                final float x = ev.getX(pointerIndex);
                final float xDiff = x - mInitialMotionX;
                final float y = ev.getY(pointerIndex);
                final float yDiff = y - mInitialMotionY;
                if (Math.abs(xDiff) > Math.abs(yDiff) && !isBeingDragged) {
                    isCanScroll = true;
                    return super.dispatchTouchEvent(ev);
                }
                final float scrollTop = yDiff * DRAG_RATE;
                float originalDragPercent = scrollTop / mDragMaxDistance;
                float dragPercent = Math.min(1f, Math.abs(originalDragPercent));
                float extraOS = Math.abs(scrollTop) - mDragMaxDistance;
                float slingshotDist = mDragMaxDistance;
                float tensionSlingshotPercent = Math.max(0,
                        Math.min(extraOS, slingshotDist * 2) / slingshotDist);
                float tensionPercent = (float) ((tensionSlingshotPercent / 4) - Math.pow(
                        (tensionSlingshotPercent / 4), 2)) * 2f;
                float extraMove = (slingshotDist) * tensionPercent * 2;
                int targetY = (int) ((slingshotDist * dragPercent) + extraMove);
                if (yDiff > 0 && isPullToRefresh) {
                    if (canChildScrollUp(mTargetView) || isCanScroll) { //控件是否可以下拉
                        isBeingDragged = false;
                        isCanScroll = true;
                    } else {
                        moveView(targetY - mOffsetY, true);
                        if (mOffsetY > 0) {
                            isBeingDragged = true;
                        } else {
                            isBeingDragged = false;
                        }
                    }
                } else if (yDiff < 0) {
                    if (canChildScrollDown(mTargetView) || isCanScroll) {
                        isBeingDragged = false;
                        isCanScroll = true;
                    } else {
                        if (isLoadingMore) {
                            moveView(-targetY - mOffsetY, true);
                            isBeingDragged = true;
                        } else if (isAutoLoading) {
                            moveView(-targetY - mOffsetY, false);
                            if (targetY > mDragMinDistance) {
                                setLoading(true);
                            }
                        }
                    }
                }
                break;
            }
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                final float initialMotionY = getMotionEventY(ev, mActivePointerId, false);
                final float initialMotionX = getMotionEventY(ev, mActivePointerId, true);
                if (initialMotionY == -1) {
                    return super.dispatchTouchEvent(ev);
                }
                mInitialMotionY = initialMotionY;
                mInitialMotionX = initialMotionX;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                final int index = ev.getActionIndex();
                mActivePointerId = ev.getPointerId(index);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                isCanScroll = false; //初始化标志位
                if (mActivePointerId == INVALID_POINTER || !isBeingDragged) {
                    moveView(-mOffsetY, false);
                    return super.dispatchTouchEvent(ev);
                }
                isBeingDragged = false;
                final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                final float y = ev.getY(pointerIndex);
                final float overscrollTop = (y - mInitialMotionY) * DRAG_RATE;
                if (overscrollTop > 0) {
                    setRefreshing(overscrollTop > mDragMaxDistance);
                } else {
                    setLoading((Math.abs(overscrollTop) > mDragMaxDistance) && isLoadingMore);
                }
                mActivePointerId = INVALID_POINTER;
                break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //此方法返回false，则手势事件会向子控件传递；返回true，则调用onTouchEvent方法。
        if (!isEnabled()) {
            return false;
        }
        return isBeingDragged;
    }

    private void moveView(int offset, boolean isUpdate) {
        mOffsetY += offset;
        if (isUpdate) {
            judgeRefreshState();
        }
        headerView.offsetTopAndBottom(offset);
        mTargetView.offsetTopAndBottom(offset);
        footerView.offsetTopAndBottom(offset);
        invalidate();
    }

    private void judgeRefreshState() {
        if (mOffsetY < 0 && refreshFooter.isComplete()) {
            return;
        }
        if (Math.abs(mOffsetY) < mDragMaxDistance) {
            if (mState != STATE_NORMAL) {
                refreshHeader.onStateNormal();
                refreshFooter.onStateNormal();
                mState = STATE_NORMAL;
            }
        } else {
            if (mState != STATE_READY) {
                refreshHeader.onStateReady();
                refreshFooter.onStateReady();
                mState = STATE_READY;
            }
        }
    }

    private void setRefreshing(boolean isRefreshing) {
        if (isRefreshing) {
            mState = STATE_REFRESHING;
            refreshHeader.onStateRefreshing();
            if (mListener != null)
                mListener.onRefreshing();
            moveView(mDragMaxDistance - mOffsetY, false);
        } else {
            mState = STATE_NORMAL;
            refreshHeader.onStateNormal();
            moveView(-mOffsetY, false);
        }
    }

    private void setLoading(boolean isLoading) {
        if (isLoading && !refreshFooter.isComplete()) {
            mState = STATE_REFRESHING;
            refreshFooter.onStateLoading();
            if (mListener != null)
                mListener.onLoadingMore();
            moveView(-mOffsetY - mDragMaxDistance, false);
        } else {
            mState = STATE_NORMAL;
            refreshHeader.onStateNormal();
            moveView(-mOffsetY, false);
        }
    }

    /*加载完成后调用*/
    public void onLoadingComplete(boolean isSuccess) {
        mState = STATE_COMPLETE;
        if (mOffsetY > 0) { //下拉刷新
            if (isSuccess) {
                refreshHeader.onStateSuccess();
            } else {
                refreshHeader.onStateFail();
            }
        } else {
            if (isSuccess) {
                refreshFooter.onStateSuccess();
            } else {
                refreshFooter.onStateFail();
            }
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                moveView(-mOffsetY, false);
            }
        }, 1000);
    }

    /*加载更多无数据时设置为true*/
    public void setNoMoreData(boolean isComplete) {
        refreshFooter.setIsComplete(isComplete);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        ensureTargetView();
        if (mTargetView == null)
            return;
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();
        int addHeight = top + mOffsetY;
        headerView.layout(left, addHeight - headerView.getMeasuredHeight(), left + width - right, addHeight);
        int mTargetHeight = mTargetView.getMeasuredHeight();
//        Log.e("RGRefresh", mTargetHeight + "//" + height);
//        mTargetHeight = Math.min(mTargetHeight, height);
        mTargetView.layout(left, addHeight, left + width - right, addHeight + mTargetHeight);
        addHeight += mTargetHeight;
        footerView.layout(left, addHeight, left + width - right, addHeight + footerView.getMeasuredHeight() - bottom);
    }

    /*是否可以向下滑动*/
    public boolean canChildScrollUp(View view) {
        if (mOffsetY > 0) {
            return false;
        }
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return view.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(view, -1);
        }
    }

    /*是否可以向上滑动*/
    public boolean canChildScrollDown(View view) {
//        if (view instanceof ScrollView) {
//            final ScrollView scrollView = (ScrollView) view;
//            View childView = scrollView.getChildAt(0);
//            return scrollView.getScrollY() + scrollView.getHeight() <= childView.getMeasuredHeight();
//        }
        if (mOffsetY < 0) {
            return false;
        }
        return ViewCompat.canScrollVertically(view, 1);
    }

    private void ensureTargetView() {
        if (mTargetView != null)
            return;
        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child != headerView)
                    mTargetView = child;
            }
        }
    }

    private float getMotionEventY(MotionEvent ev, int activePointerId, Boolean isX) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        if (isX) {
            return MotionEventCompat.getX(ev, index);
        } else {
            return MotionEventCompat.getY(ev, index);
        }
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    public interface OnRefreshListener {
        /*下拉刷新*/
        void onRefreshing();

        /*加载更多*/
        void onLoadingMore();
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

}
