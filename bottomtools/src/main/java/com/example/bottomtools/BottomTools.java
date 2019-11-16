package com.example.bottomtools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.bottomtools.widget.WindowResizeLayout;

import java.util.Objects;

public class BottomTools {

    private static final String TAG = BottomTools.class.getSimpleName();

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;

    private Activity mActivity;
    private WindowResizeLayout mParentLayout;

    private Tools tools;
    private View mToolsView;

    private boolean mFixSoftInput = true;
    private int mSoftInputHeight = 0;

    private BottomTools(Activity activity, WindowResizeLayout parentLayout) {
        this.mActivity = activity;
        this.mParentLayout = parentLayout;
        mWindowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();
        tools = new Tools();
        initListener();
        initView();
    }

    private void initListener() {
        mParentLayout.setLayoutChangeCallBack(new WindowResizeLayout.LayoutChangeCallBack() {
            @Override
            public void layoutChange(boolean isSoftInputShow, int height) {
                if (isSoftInputShow)
            }
        });
    }

    private void initView() {
        mLayoutParams.gravity = Gravity.BOTTOM;
        mLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    public class Builder {

        private BottomTools mBottomTools;

        public Builder(Activity activity) {
            mBottomTools = new BottomTools();
        }

        public void show() {

        }
    }
}
