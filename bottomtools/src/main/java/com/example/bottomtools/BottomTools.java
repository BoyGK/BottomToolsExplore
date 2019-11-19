package com.example.bottomtools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.IdRes;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.bottomtools.widget.WindowResizeLayout;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class BottomTools {

    private static final String TAG = BottomTools.class.getSimpleName();

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;

    private Activity mActivity;
    private ViewGroup mParentLayout;

    private Tools mTools;

    private boolean mFixSoftInput = false;
    private boolean needShow = false;

    private int mToolsHeight = 0;
    private static final int DEFAULT_HEIGHT = 220;

    private BottomTools(Activity activity, PagerAdapter adapter) {
        mTools = new Tools(activity, adapter);
        init(activity);
        initListener();
        initView();
    }

    private BottomTools(Activity activity, View view) {
        this(activity, new BasePagerAdapter(new View[]{view}));
    }

    private BottomTools(Activity activity, int resId) {
        this(activity, activity.findViewById(resId));
    }

    private void init(Activity activity) {
        this.mActivity = activity;
        mParentLayout = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        mWindowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();
        if (mParentLayout instanceof WindowResizeLayout) {
            ((WindowResizeLayout) mParentLayout).setFitStatusBar(
                    activity.getWindow().getStatusBarColor() == Color.TRANSPARENT
                            || activity.getWindow().getAttributes().flags == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            );
        }
    }

    private void initListener() {
        if (mParentLayout instanceof WindowResizeLayout) {
            ((WindowResizeLayout) mParentLayout).setLayoutChangeCallBack(new WindowResizeLayout.LayoutChangeCallBack() {
                @Override
                public void layoutChange(boolean isSoftInputShow, int height) {
                    if (mFixSoftInput && isSoftInputShow) {
                        if (checkForUpDateView()) {
                            mToolsHeight = height;
                            upDateView();
                        }
                        if (needShow) {
                            mTools.getView().setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }
    }

    private void initView() {
        mLayoutParams.gravity = Gravity.BOTTOM;
        mLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mLayoutParams.height = mToolsHeight = defaultHeight();
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.windowAnimations = R.style.bottomTools;
        mWindowManager.addView(mTools.getView(), mLayoutParams);
    }

    private void upDateView() {
        mLayoutParams.height = mToolsHeight;
        mWindowManager.updateViewLayout(mTools.getView(), mLayoutParams);
    }

    private boolean checkForUpDateView() {
        return (mFixSoftInput && mToolsHeight == defaultHeight())
                || (!mFixSoftInput && mToolsHeight != defaultHeight());
    }

    private ViewPager show() {
        if (mFixSoftInput && !((WindowResizeLayout) mParentLayout).isSoftInputShow()) {
            needShow = true;
            openSoftInput();
        } else {
            mTools.getView().setVisibility(View.VISIBLE);
        }
        return mTools.getView();
    }

    private void dismiss() {
        dismiss(true);
    }

    private void dismiss(boolean withSoftInput) {
        if (mFixSoftInput && withSoftInput) {
            closeSoftInput();
        }
        mTools.getView().setVisibility(View.GONE);
        if (!withSoftInput) {
            //纠正软键盘状态
            ((WindowResizeLayout) mParentLayout).setSoftInputShow(true);
        }
    }

    private void setFixSoftInput(boolean fixSoftInput) {
        this.mFixSoftInput = fixSoftInput;
    }

    private void openSoftInput() {
        if (!((WindowResizeLayout) mParentLayout).isSoftInputShow()) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.toggleSoftInput(0, 0);
            }
        }
    }

    private void closeSoftInput() {
        if (((WindowResizeLayout) mParentLayout).isSoftInputShow()) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.toggleSoftInput(0, 0);
            }
        }
    }

    private int defaultHeight() {
        return (int) (mActivity.getResources().getDisplayMetrics().density * DEFAULT_HEIGHT);
    }

    public static class Builder {

        private BottomTools mBottomTools;

        public Builder(Activity activity, View view) {
            mBottomTools = new BottomTools(activity, view);
        }

        public Builder(Activity activity, @IdRes int resId) {
            mBottomTools = new BottomTools(activity, resId);
        }

        public Builder(Activity activity, PagerAdapter adapter) {
            mBottomTools = new BottomTools(activity, adapter);
        }

        public Builder setFixSoftInputHeight(boolean fixSoftInput) {
            mBottomTools.setFixSoftInput(fixSoftInput);
            return this;
        }

        public ViewPager show() {
            return mBottomTools.show();
        }

        public void dismiss() {
            mBottomTools.dismiss();
        }

        public void dismiss(boolean withSoft) {
            mBottomTools.dismiss(withSoft);
        }
    }
}
