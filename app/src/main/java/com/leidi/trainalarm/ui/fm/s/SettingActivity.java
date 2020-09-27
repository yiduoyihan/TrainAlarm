package com.leidi.trainalarm.ui.fm.s;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.leidi.trainalarm.R;
import com.leidi.trainalarm.android.MPush;
import com.leidi.trainalarm.base.BaseActivity;
import com.leidi.trainalarm.base.BaseBean;
import com.leidi.trainalarm.bean.EventBean;
import com.leidi.trainalarm.bean.MainBean;
import com.leidi.trainalarm.ui.login.LoginActivity;
import com.leidi.trainalarm.ui.main.MainActivity;
import com.leidi.trainalarm.util.AppUtil;
import com.leidi.trainalarm.util.CommonDialog;
import com.leidi.trainalarm.util.Constant;
import com.leidi.trainalarm.util.Url;
import com.leidi.trainalarm.v.PopView;
import com.rxjava.rxlife.RxLife;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;

/**
 * @author yan
 * 设置页面
 */
public class SettingActivity extends BaseActivity{

    @BindView(R.id.base_layout)
    LinearLayout layout;

    CommonDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        setToolbar(View.VISIBLE,"设置",View.INVISIBLE);
    }

    @OnClick(R.id.item_change_pwd)
    public void onItemChangePwdClick() {
        startActivity(new Intent(this, ChangePwdActivity.class));
    }

    @OnClick(R.id.item_login_out)
    public void onItemLoginOutClick() {
        showSelfDialog();
    }

    /**
     * 退出登录
     * */
    void showSelfDialog() {
         dialog = new CommonDialog(this, R.layout.out_login_dialog);
        if (dialog.isShowing()){
            return;
        }
        dialog.setTitle("您确定要退出程序吗？")
                .setWight(true)
                .setNegative("取消").setPositive("确定")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        exitAPP();
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();
                    }
                })
                .show();
        dialog.setCanceledOnTouchOutside(true);
    }

    private void exitAPP() {
        RxHttp.postForm(Url.login_out)
                .add("token",SPUtils.getInstance().getString(Constant.TOKEN,""))
                .add("deviceNo", DeviceUtils.getAndroidID())
                .asClass(BaseBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .to(RxLife.to(SettingActivity.this))
                .subscribe(bean -> {
                    //请求成功
                    if (bean.getCode() == Constant.SUCCESS_CODE){
                        SPUtils.getInstance().clear();
                        startActivity(new Intent(SettingActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK));
                        dialog.dismiss();
                        EventBus.getDefault().post(new MainBean());

                        MPush.I.pausePush();
                        MPush.I.unbindAccount();
                        MPush.I.stopPush();
                        finish();
                    }else {
                        showToast("退出登录失败");
                    }
                }, throwable -> {
                    //请求失败
                });
    }


}


