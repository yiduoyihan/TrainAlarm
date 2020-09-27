package com.leidi.trainalarm.ui.login;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.leidi.trainalarm.R;
import com.leidi.trainalarm.android.MPush;
import com.leidi.trainalarm.base.BaseActivity;
import com.leidi.trainalarm.bean.LoginBean;
import com.leidi.trainalarm.ui.login.forget.ForgetPwdActivity;
import com.leidi.trainalarm.ui.main.MainActivity;
import com.leidi.trainalarm.util.Constant;
import com.leidi.trainalarm.util.Url;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;
import com.rxjava.rxlife.RxLife;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;


/**
 * @author 阎
 * @date 2020/5/7
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login_progress)
    ProgressBar progressBar;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private static BDLocation successLocation;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        findViewById(R.id.layout_explain).setVisibility(View.GONE);
        findViewById(R.id.tv_forget_pw).setVisibility(View.GONE);
        //登陆成功后记录状态 后面启动不需要再次登陆直接进入主界面
        if (SPUtils.getInstance().getBoolean(Constant.IS_LOGIN_SUCCESS, false)) {
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        } else {
            initBDSDK();
            initPermission();

            SPUtils.getInstance().clear();
            MPush.I.unbindAccount();
            MPush.I.stopPush();
        }


    }

    /**
     * 请求定位权限
     */
    private void initPermission() {
        PermissionX.init(this)
                .permissions(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .onExplainRequestReason(new ExplainReasonCallbackWithBeforeParam() {
                    @Override
                    public void onExplainReason(ExplainScope scope, List<String> deniedList, boolean beforeRequest) {
                        scope.showRequestReasonDialog(deniedList, "即将申请的权限是程序必须依赖的权限", "我已明白");
                    }
                })
                .onForwardToSettings(new ForwardToSettingsCallback() {
                    @Override
                    public void onForwardToSettings(ForwardScope scope, List<String> deniedList) {
                        scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "我已明白");
                    }
                })
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if (allGranted) {
                            startLocation();
                        } else {
                            Toast.makeText(LoginActivity.this, "您拒绝了如下权限：" + deniedList, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //初始化定位
    private void initBDSDK() {
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD09ll：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标
        option.setScanSpan(0);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setLocationNotify(false);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位
        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        option.setNeedNewVersionRgc(true);
        //可选，设置是否需要最新版本的地址信息。默认需要，即参数为true
        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }

    public static class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            successLocation = location;
        }
    }

    private void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
    }

    private void startLocation() {
        mLocationClient.start();
    }


    @OnClick(R.id.btn_login)
    public void onLoginBtnClick() {
//        if (username.getText().toString().trim().length() < Constant.ACCOUNT_LENGTH) {
//            showToast("账号长度不正确");
//            return;
//        }
//        if (password.getText().toString().trim().length() < Constant.PASSWORD_LENGTH) {
//            showToast("密码长度有误");
//            return;
//        }
        KeyboardUtils.hideSoftInput(this);
        if (successLocation == null) {
            showToast("定位失败无法登陆");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        RxHttp.postForm(Url.login)
                .add("username", username.getText().toString().trim())
                .add("password", password.getText().toString().trim())
                .add("deviceNo", DeviceUtils.getAndroidID())
                .add("latitude", successLocation.getLatitude())
                .add("longitude", successLocation.getLongitude())
                .asClass(LoginBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .to(RxLife.to(this))
                .subscribe(bean -> {
                    //请求成功
                    if (bean.getCode() == Constant.SUCCESS_CODE) {
                        SPUtils.getInstance().put(Constant.IS_LOGIN_SUCCESS, true);
                        SPUtils.getInstance().put(Constant.TOKEN, bean.getData().getToken());
                        SPUtils.getInstance().put(Constant.USERNAME, bean.getData().getUserInfo().getUserName());
                        SPUtils.getInstance().put(Constant.PASSWORD, password.getText().toString().trim());
                        SPUtils.getInstance().put(Constant.USER_ID, bean.getData().getUserInfo().getId());
                        SPUtils.getInstance().put(Constant.PUSH_URL, bean.getData().getMpushServerIp());
                        SPUtils.getInstance().put(Constant.USER_FLAG, bean.getData().getUserInfo().getRoleId());
                        SPUtils.getInstance().put(Constant.EMPLOYEE_NAME, "" + bean.getData().getUserInfo().getDeptName());

                        MPush.I.pausePush();
                        MPush.I.unbindAccount();
                        MPush.I.stopPush();


                        navigateToHome();
                    } else {
                        ToastUtils.showShort(bean.getMsg());
                    }
                    progressBar.setVisibility(View.GONE);
                }, throwable -> {
                    //请求失败
                    print(throwable.getMessage());
                    progressBar.setVisibility(View.GONE);
                });

    }

    @OnClick(R.id.tv_forget_pw)
    public void onForgetPasswordClick(View view) {
        startActivity(new Intent(this, ForgetPwdActivity.class));
    }

    @OnClick(R.id.tv_login_agreement)
    public void onAgreementClick() {
        showToast("点击了用户协议");
    }

    /**
     * 跳转到主页
     */
    public void navigateToHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /**
     * 页面销毁同时停止定位
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocation();
    }

}
