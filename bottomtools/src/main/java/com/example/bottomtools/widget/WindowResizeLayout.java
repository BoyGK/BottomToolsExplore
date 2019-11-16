package com.example.bottomtools.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class WindowResizeLayout extends RelativeLayout {
    public WindowResizeLayout(Context context) {
        super(context);
    }

    public WindowResizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WindowResizeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean isSoftInputShow = false;
    private LayoutChangeCallBack mLayoutChangeCallBack;

    public void setLayoutChangeCallBack(LayoutChangeCallBack mLayoutChangeCallBack) {
        this.mLayoutChangeCallBack = mLayoutChangeCallBack;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int statusHeight = getResources().getIdentifier("status_bar_height", "dimen", "android");
        statusHeight = statusHeight > 0 ? getResources().getDimensionPixelSize(statusHeight) : 0;
        h = h + statusHeight;
        int tbHeight = (int) (getResources().getDisplayMetrics().heightPixels * 0.3f);
        if (h > oldh && h - oldh > tbHeight) {
            isSoftInputShow = true;
        } else if (h < oldh && oldh - h > tbHeight) {
            isSoftInputShow = false;
        }

        if (mLayoutChangeCallBack != null) {
            if (Math.abs(oldh - h) > tbHeight) {
                mLayoutChangeCallBack.layoutChange(isSoftInputShow, oldh - h);
            }
        }

    }

    public interface LayoutChangeCallBack {
        void layoutChange(boolean isSoftInputShow, int height);
    }
}
