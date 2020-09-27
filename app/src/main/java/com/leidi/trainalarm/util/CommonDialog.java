package com.leidi.trainalarm.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.leidi.trainalarm.R;

/**
 * description:自定义dialog
 * @author caiwu
 */

public class CommonDialog extends Dialog {
    /**
     * 显示的图片
     */
    private ImageView imageIv;

    /**
     * 显示的标题
     */
    private TextView titleTv;

    /**
     * 显示的消息
     */
    private TextView messageTv;

    /**
     * 确认和取消按钮
     */
    private Button negativeBtn, positiveBtn;

    /**
     * 按钮之间的分割线
     */
    private View columnLineView;
    /**
     * 填充占位的view
     */
    private View weigthView;

    private int layoutID = 0;

    public CommonDialog(Context context) {
        super(context, R.style.CustomDialog);
    }

    public CommonDialog(Context context, int layoutID) {
        super(context, R.style.CustomDialog);
        this.layoutID = layoutID;
    }

    /**
     * 都是内容数据
     */
    private String message;
    private String title;
    private String positive, negative;
    private int imageResId = -1;

    /**
     * 底部是否只有一个按钮
     */
    private boolean isSingle = false;
    /**
     * 是否显示占位
     */
    private boolean isWeight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (layoutID == 0) {
            setContentView(R.layout.dialog_notification);
        } else {
            setContentView(layoutID);
        }

        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        refreshView();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBottomListener != null) {
                    onClickBottomListener.onPositiveClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBottomListener != null) {
                    onClickBottomListener.onNegativeClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void refreshView() {
        //如果用户自定了title和message
        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
            titleTv.setVisibility(View.VISIBLE);
        } else {
            titleTv.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(message)) {
            messageTv.setText(message);
        }
        //如果设置按钮的文字
        if (!TextUtils.isEmpty(positive)) {
            positiveBtn.setText(positive);
        } else {
            positiveBtn.setText("确定");
        }
        if (!TextUtils.isEmpty(negative)) {
            negativeBtn.setText(negative);
        } else {
            negativeBtn.setText("取消");
        }

        if (imageResId != -1) {
            imageIv.setImageResource(imageResId);
            imageIv.setVisibility(View.VISIBLE);
        } else {
            imageIv.setVisibility(View.GONE);
        }
        /*
         * 只显示一个按钮的时候隐藏取消按钮，回掉只执行确定的事件
         */
        if (isSingle) {
            columnLineView.setVisibility(View.GONE);
            negativeBtn.setVisibility(View.GONE);
        } else {
            negativeBtn.setVisibility(View.VISIBLE);
            columnLineView.setVisibility(View.GONE);
        }

        if (isWeight) {
            weigthView.setVisibility(View.VISIBLE);
        } else {
            weigthView.setVisibility(View.GONE);
        }
    }

    @Override
    public void show() {
        super.show();
        refreshView();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        negativeBtn = (Button) findViewById(R.id.negative);
        positiveBtn = (Button) findViewById(R.id.positive);
        titleTv = (TextView) findViewById(R.id.title);
        messageTv = (TextView) findViewById(R.id.message);
        imageIv = (ImageView) findViewById(R.id.image);
        columnLineView = findViewById(R.id.column_line);
        weigthView = findViewById(R.id.view_make_button_on_left);
    }

    /**
     * 设置确定取消按钮的回调
     */
    public OnClickBottomListener onClickBottomListener;

    public CommonDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }

    public interface OnClickBottomListener {
        /**
         *点击确定按钮事件
         */
        void onPositiveClick();

        /**
         *点击取消按钮事件
         */
        void onNegativeClick();
    }

    public String getMessage() {
        return message;
    }

    public CommonDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CommonDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPositive() {
        return positive;
    }

    public CommonDialog setPositive(String positive) {
        this.positive = positive;
        return this;
    }

    public String getNegative() {
        return negative;
    }

    public CommonDialog setNegative(String negative1) {
        this.negative = negative1;
        return this;
    }

    public int getImageResId() {
        return imageResId;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public CommonDialog setSingle(boolean single) {
        isSingle = single;
        return this;
    }


    public boolean isWeight() {
        return isWeight;
    }

    public CommonDialog setWight(boolean single) {
        isWeight = single;
        return this;
    }

    public CommonDialog setImageResId(int imageResId) {
        this.imageResId = imageResId;
        return this;
    }

}
