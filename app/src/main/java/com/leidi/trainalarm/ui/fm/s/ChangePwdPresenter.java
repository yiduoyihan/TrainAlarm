package com.leidi.trainalarm.ui.fm.s;

import com.leidi.trainalarm.v.ChangePwdView;

/**
 * @author 阎
 * @date 2020/5/15
 * @description
 */
public class ChangePwdPresenter implements ChangePwdView.Presenter {


    private ChangePwdView.Result changeView;

    ChangePwdPresenter(ChangePwdView.Result loginView) {
        this.changeView = changeView;
    }


    @Override
    public void submitMessage(String oldPwd, String newPWd) {
            changeView.requestSuccess();
    }

    @Override
    public void onDestory() {
        changeView = null;
    }
}
