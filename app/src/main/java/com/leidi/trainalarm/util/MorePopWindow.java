package com.leidi.trainalarm.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.leidi.trainalarm.R;
import com.leidi.trainalarm.v.PopView;

/**
 * Created by Administrator on 2018/1/30 0030.
 */

public class MorePopWindow extends PopupWindow {
    private View conentView;
    private String userId;
    PopView homeView;

    public MorePopWindow(final PopView homeView, final Activity context) {
        this.homeView = homeView;
        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popup_window_chat, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        this.setContentView(conentView);
        this.setWidth(LayoutParams.WRAP_CONTENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置点击隐藏的属性
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable dw = new ColorDrawable(80000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.AnimationPreview);
        TextView cancel = conentView.findViewById(R.id.cancel);
        TextView ensure = conentView.findViewById(R.id.ensure);
        cancel.setOnClickListener(v -> {
            homeView.onCancelClick();
            MorePopWindow.this.dismiss();
        });
        ensure.setOnClickListener(v -> {
            homeView.onPopEnsureClick();
            MorePopWindow.this.dismiss();
        });
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
//            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 30);
            this.showAtLocation(parent, Gravity.CENTER, 0, 0);
        } else {
            this.dismiss();
        }
    }
}