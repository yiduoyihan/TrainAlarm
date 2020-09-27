package com.leidi.trainalarm.ui.login.forget;

import android.view.View;
import android.widget.EditText;

import com.leidi.trainalarm.R;
import com.leidi.trainalarm.base.BaseActivity;
import com.leidi.trainalarm.base.BaseBean;
import com.leidi.trainalarm.util.AppUtil;
import com.leidi.trainalarm.util.Constant;
import com.leidi.trainalarm.util.ToastUtil;
import com.leidi.trainalarm.util.Url;
import com.rxjava.rxlife.RxLife;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;

/**
 * @author 阎
 * 忘记密码的修改界面
 */
public class ForgetPwdActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText etPhoneNumber;
    @BindView(R.id.et_captcha)
    EditText etCaptcha;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_new_pwd_again)
    EditText etNewPwdAgain;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void initView() {
        setToolbar(View.VISIBLE, "忘记密码", View.INVISIBLE);
    }

    /**
     * 获取验证码
     */
    @OnClick(R.id.btn_get_captcha)
    public void onGetCaptchaClick(View view) {
        if (etPhoneNumber.getText().toString().trim().isEmpty()) {
            showToast("请填写您的手机号");
            return;
        }
        if (etPhoneNumber.getText().toString().length() != Constant.PHONE_NUMBER_LENGTH) {
            showToast("您输入的手机号码长度有误");
            return;
        }
        if (AppUtil.isMobileNO(etPhoneNumber.getText().toString())) {
            showToast("您输入的手机号码不可用");
            return;
        }
    }

    /**
     * 提交
     */
    @OnClick(R.id.btn_submit_new_pwd)
    public void onSubmitClick(View view) {
        if (etCaptcha.getText().toString().length() != Constant.MESSAGE_CAPTCHA) {
            showToast("验证码不正确");
            return;
        }
        if (etNewPwd.getText().toString().length() < Constant.PASSWORD_LENGTH) {
            showToast("密码长度不能少于6位");
            return;
        }
        if (etNewPwdAgain.getText().toString().length() < Constant.PASSWORD_LENGTH) {
            showToast("密码长度不能少于6位");
            return;
        }
        if (etNewPwd.getText().toString().equals(etNewPwdAgain.getText().toString())) {
            submitNewPwd();
        } else {
            showToast("两次输入的密码不一致");
        }
    }

    /**
     * 提交至服务器
     */
    private void submitNewPwd() {
        RxHttp.postForm("url")
                .add("phoneNumber", etPhoneNumber.getText().toString().trim())
                .add("password", etNewPwd.getText().toString().trim())
                .add("captcha", etCaptcha.getText().toString().trim())
                .asClass(BaseBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .to(RxLife.to(this))
                .subscribe(bean -> {
                    //请求成功
                    ToastUtil.showToast(this, "修改成功");
                    finish();
                }, throwable -> {
                    //请求失败
                    ToastUtil.showToast(this, "请重试");
                });

    }
}
