package com.leidi.trainalarm.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * @author 阎
 * @date 2020/8/14
 * @description
 */
public class SelfViewPager extends ViewPager {
    public SelfViewPager(Context context) {
        super(context);
    }

    public SelfViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if ("com.baidu.mapapi.map.TextureMapView".equals(v.getClass().getName())) {
            AppUtil.print("true");
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}