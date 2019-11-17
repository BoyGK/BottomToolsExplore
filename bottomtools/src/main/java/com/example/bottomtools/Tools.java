package com.example.bottomtools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

class Tools {

    private Activity mActivity;

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;


    Tools(Activity activity, PagerAdapter adapter) {
        this.mActivity = activity;
        this.mAdapter = adapter;
        initView();
    }

    @SuppressLint("InflateParams")
    private void initView() {
        mViewPager = (ViewPager) LayoutInflater.from(mActivity).inflate(R.layout.tools, null);
        mViewPager.setAdapter(mAdapter);
    }

    ViewPager getView() {
        return mViewPager;
    }

}
