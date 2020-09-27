package com.leidi.trainalarm.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ToastUtils;
import com.leidi.trainalarm.R;
import com.leidi.trainalarm.bean.EventBean;
import com.leidi.trainalarm.bean.ReceiverBean;
import com.leidi.trainalarm.net.NetStateChangeObserver;
import com.leidi.trainalarm.net.NetStateChangeReceiver;
import com.leidi.trainalarm.net.NetworkType;
import com.leidi.trainalarm.util.AppUtil;
import com.leidi.trainalarm.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 阎国庆
 * @date 2017/1/12 0012
 * 功能作用：fragment 基类
 */

public abstract class BaseFragment extends Fragment {

    @BindView(R.id.tv_title_left)
    public ImageView tvTitleLeftButton;
    @BindView(R.id.tv_title_right)
    public ImageView tvTitleRightButton;
    @BindView(R.id.tv_title_center)
    public TextView tvTitleCenter;
    @BindView(R.id.tv_net_disconnection)
    TextView tvNetDisconnection;
    private View view;
    private Bundle savedInstanceState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppUtil.print("onCreateView");
        this.savedInstanceState = savedInstanceState;
        view = inflater.inflate(getLayoutId(), null);
        ButterKnife.bind(this, view);
        initView(savedInstanceState, view);
        return view;
    }

    /**
     * 添加布局
     *
     * @return 布局ID
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     *
     * @param savedInstanceState 保存的一些状态
     * @param view               页面的view
     */
    protected abstract void initView(@Nullable Bundle savedInstanceState, View view);

    /**
     * Toast消息提示的方法
     */
    protected void showToast(String str) {
        ToastUtil.showToast(getActivity(), str);
    }

    /**
     * 刚进页面时候判断是否有网
     */
//    public void netWorkFistIn(NetworkEvent event) {
//        if (!AppUtil.isNetworkAvailable(getActivity())) {
//
//        } else {
//            initView(savedInstanceState, view);
//        }
//        tvTitleCenter.setVisibility(View.INVISIBLE);
//    }

    /**
     * 对公共标题的初始化(重载来判断传参是否为3个)
     */
    protected void setToolbar(String str) {
        tvTitleCenter.setText(str);
    }

    /**
     * 对公共标题的初始化(重载来判断传参是否为3个)
     *
     * @param left  左边返回图标
     * @param str   标题内容
     * @param right 控制是否显示右边图标
     */
    protected void setToolbar(int left, String str, int right) {
        tvTitleLeftButton.setVisibility(left);
        tvTitleRightButton.setVisibility(right);
        tvTitleCenter.setText(str);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(String string) {
        if (string.equals("网络已连接")) {
            tvNetDisconnection.setVisibility(View.GONE);
        } else {
            tvNetDisconnection.setVisibility(View.VISIBLE);
        }

    }
}
