package com.example.bottomtools.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

public class WindowResizeLayout extends ConstraintLayout {
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

    public boolean isSoftInputShow() {
        return isSoftInputShow;
    }

    public void setLayoutChangeCallBack(LayoutChangeCallBack mLayoutChangeCallBack) {
        this.mLayoutChangeCallBack = mLayoutChangeCallBack;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        int statusHeight = getResources().getIdentifier("status_bar_height", "dimen", "android");
//        statusHeight = statusHeight > 0 ? getResources().getDimensionPixelSize(statusHeight) : 0;
//        h = h + statusHeight;
        int tHeight = (int) (getResources().getDisplayMetrics().heightPixels * 0.3f);
        int bHeight = (int) (getResources().getDisplayMetrics().heightPixels * 0.6f);
        if (h > oldh && h - oldh > tHeight && h - oldh < bHeight) {
            isSoftInputShow = false;
        } else if (h < oldh && oldh - h > tHeight && oldh - h < bHeight) {
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
