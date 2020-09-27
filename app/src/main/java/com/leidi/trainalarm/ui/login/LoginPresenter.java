package com.leidi.trainalarm.ui.login;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.leidi.trainalarm.util.Constant;
import com.leidi.trainalarm.v.LoginView;

/**
 * @author 阎
 * @date 2020/5/15
 * @description
 */
public class LoginPresenter implements LoginView.Presenter {


    private LoginView.Result loginView;

    LoginPresenter(LoginView.Result loginView) {
        this.loginView = loginView;
    }


    @Override
    public void login(String username, String password, String regId) {
        if (loginView == null) {
            return;
        }

        if (TextUtils.isEmpty(username)) {
            loginView.loginFailed("请输入账号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            loginView.loginFailed("请输入密码");
            return;
        }
        if (username.trim().length() < Constant.PASSWORD_LENGTH) {
            loginView.loginFailed("账号长度非法");
            return;
        }
        if (password.trim().length() < Constant.PASSWORD_LENGTH) {
            loginView.loginFailed("您输入的密码长度有误");
            return;
        }

        if (username.equals("123456") && password.equals("123456")) {
            SPUtils.getInstance().put(Constant.PASSWORD, password.trim());
            loginView.navigateToHome();
        } else {
            loginView.loginFailed("账号密码不正确");
        }

    }

    @Override
    public void onDestroy() {
        loginView = null;
    }
}
