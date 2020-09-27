package com.leidi.trainalarm.ui.fm.s;


import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.leidi.trainalarm.R;
import com.leidi.trainalarm.base.BaseActivity;
import com.leidi.trainalarm.base.BaseBean;
import com.leidi.trainalarm.bean.MainBean;
import com.leidi.trainalarm.ui.login.LoginActivity;
import com.leidi.trainalarm.util.Constant;
import com.leidi.trainalarm.util.Url;
import com.rxjava.rxlife.RxLife;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;

/**
 * @author yan
 * 修改密码
 */
public class ChangePwdActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_new_pwd_again)
    EditText etNewPwdAgain;

    @BindView(R.id.tv_submit_pwd)
    TextView tvButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void initView() {
        setToolbar(View.VISIBLE, "修改密码", View.INVISIBLE);
        tvButton.setEnabled(false);
        etOldPwd.addTextChangedListener(this);
        etNewPwd.addTextChangedListener(this);
        etNewPwdAgain.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        tvButton.setEnabled(false);//在这里重复设置，以保证清除任意EditText中的内容，按钮重新变回不可点击状态
        tvButton.setBackground(getDrawable(R.drawable.forget_submit_shape));
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!(etOldPwd.getText().toString().equals("") || etNewPwdAgain.getText().toString().equals("") || etNewPwd.getText().toString().equals(""))) {
            tvButton.setEnabled(true);
            tvButton.setBackground(getDrawable(R.drawable.forget_captcha_shape));
        }
    }

    @OnClick(R.id.tv_submit_pwd)
    public void onSubmitButtonClick() {
        if (etOldPwd.getText().toString().trim().isEmpty()) {
            showToast("请输入您的旧密码");
            return;
        }
        if (etNewPwd.getText().toString().trim().isEmpty()) {
            showToast("请输入您的新密码");
            return;
        }
        if (etNewPwdAgain.getText().toString().trim().isEmpty()) {
            showToast("请再次输入您的新密码");
            return;
        }
        if (etNewPwd.getText().toString().trim().length() < Constant.PASSWORD_LENGTH) {
            showToast("新密码长度不能少于6位");
            return;
        }
        if (etNewPwdAgain.getText().toString().trim().length() < Constant.PASSWORD_LENGTH) {
            showToast("新密码长度不能少于6位");
            return;
        }

        /*提交请求**/
        RxHttp.postForm(Url.change_pwd)
                .add("token", SPUtils.getInstance().getString(Constant.TOKEN))
                .add("deviceNo", DeviceUtils.getAndroidID())
                .add("userId", SPUtils.getInstance().getInt(Constant.USER_ID))
                .add("password", etOldPwd.getText().toString().trim())
                .add("newPassword", etNewPwd.getText().toString().trim())
                .add("confirmPassword", etNewPwdAgain.getText().toString().trim())
                .asClass(BaseBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .to(RxLife.to(this))
                .subscribe(bean -> {
                    //请求成功
                    ToastUtils.showShort(bean.getMsg());
                    if (bean.getCode() == Constant.SUCCESS_CODE) {
                        SPUtils.getInstance().clear();
                        startActivity(new Intent(ChangePwdActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK));
                        EventBus.getDefault().post(new MainBean());
                        finish();
                    }
                }, throwable -> {
                    //请求失败
                    print(throwable.getMessage());
                });
//        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
