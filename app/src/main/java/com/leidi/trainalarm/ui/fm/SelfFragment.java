package com.leidi.trainalarm.ui.fm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.leidi.trainalarm.R;
import com.leidi.trainalarm.base.BaseFragment;
import com.leidi.trainalarm.ui.fm.s.AboutOursActivity;
import com.leidi.trainalarm.ui.fm.s.SettingActivity;
import com.leidi.trainalarm.util.Constant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的设置页面
 * @author yan
 */
public class SelfFragment extends BaseFragment {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_emp_name)
    TextView tvEmpName;
    @Override
    protected int getLayoutId() {
        return R.layout.fm_self;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, View view) {
        setToolbar(View.GONE, "我的", View.GONE);
        tvEmpName.setVisibility(View.GONE);
        tvName.setText(SPUtils.getInstance().getString(Constant.USERNAME));
        tvEmpName.setText(SPUtils.getInstance().getString(Constant.EMPLOYEE_NAME));
    }

    @OnClick(R.id.fm_layout_item1)
    public void onAboutOursClick() {
        startActivity(new Intent(getActivity(), AboutOursActivity.class));
    }

    @OnClick(R.id.fm_layout_item2)
    public void onSettingClick() {
        startActivity(new Intent(getActivity(), SettingActivity.class));
    }

    public static SelfFragment getInstance() {
        return new SelfFragment();
    }

}
