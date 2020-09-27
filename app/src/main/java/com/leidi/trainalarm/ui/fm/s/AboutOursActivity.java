package com.leidi.trainalarm.ui.fm.s;


import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.leidi.trainalarm.R;
import com.leidi.trainalarm.base.BaseActivity;

import butterknife.BindView;

/**
 * @author caiwu
 */
public class AboutOursActivity extends BaseActivity {
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_ours;
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void initView() {
        setToolbar(View.VISIBLE,"关于我们",View.INVISIBLE);
        tvVersion.setText(String.format("%s%s", getString(R.string.version), AppUtils.getAppVersionName()));
    }
}
