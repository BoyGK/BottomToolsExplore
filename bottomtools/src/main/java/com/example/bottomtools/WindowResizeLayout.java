package com.example.bottomtools;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

public class WindowResizeLayout extends ConstraintLayout {
    public WindowResizeLayout(Context context) {
        this(context, null);
    }

    public WindowResizeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WindowResizeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean isSoftInputShow = false;
    private LayoutChangeCallBack mLayoutChangeCallBack;
    private boolean isFitStatusBar = false;
    private int mSoftInputHeight = 0;

    /**
     * 判断软键盘状态
     */
    public boolean isSoftInputShow() {
        return isSoftInputShow;
    }

    /**
     * 主动设置软键盘状态，用于纠正软键盘状态
     * May be useful
     */
    public void setSoftInputShow(boolean softInputShow) {
        isSoftInputShow = softInputShow;
    }

    /**
     * 用于判断是否进行了标题栏适配
     */
    public void setFitStatusBar(boolean fitStatusBar) {
        isFitStatusBar = fitStatusBar;
    }

    public void setLayoutChangeCallBack(LayoutChangeCallBack mLayoutChangeCallBack) {
        this.mLayoutChangeCallBack = mLayoutChangeCallBack;
    }

    /**
     * 获取软键盘高度
     *
     * @return px
     */
    public int getSoftInputHeight() {
        return mSoftInputHeight;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (isFitStatusBar) {
            int statusHeight = getResources().getIdentifier("status_bar_height", "dimen", "android");
            statusHeight = statusHeight > 0 ? getResources().getDimensionPixelSize(statusHeight) : 0;
            h = h + statusHeight;
        }
        int tHeight = (int) (getResources().getDisplayMetrics().heightPixels * 0.25f);
        int bHeight = (int) (getResources().getDisplayMetrics().heightPixels * 0.55f);
        if (h > oldh && h - oldh > tHeight && h - oldh < bHeight) {
            isSoftInputShow = false;
        } else if (h < oldh && oldh - h > tHeight && oldh - h < bHeight) {
            mSoftInputHeight = oldh - h;
            isSoftInputShow = true;
        }

        if (mLayoutChangeCallBack != null) {
            if (Math.abs(oldh - h) > tHeight && Math.abs(oldh - h) < bHeight) {
                mLayoutChangeCallBack.layoutChange(isSoftInputShow, oldh - h);
            }
        }

    }

    public interface LayoutChangeCallBack {
        void layoutChange(boolean isSoftInputShow, int height);
    }
}
