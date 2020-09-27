package com.leidi.trainalarm.ui.fm;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.leidi.trainalarm.base.BaseBean;
import com.leidi.trainalarm.bean.AlarmSetBean;
import com.leidi.trainalarm.bean.LocationBean;
import com.leidi.trainalarm.bean.RailWayBean;
import com.leidi.trainalarm.util.AppUtil;
import com.leidi.trainalarm.util.Constant;
import com.leidi.trainalarm.util.FileUtils;
import com.leidi.trainalarm.util.Url;
import com.rxjava.rxlife.RxLife;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;

/**
 * @author caiwu
 */
public class NotificationFmPresenter implements NotificationFmView.Presenter {

    private NotificationFmView.Result result;

    public NotificationFmPresenter(NotificationFmView.Result result) {
        this.result = result;
    }

    @Override
    public void getRailWay(String token, String deviceId, Fragment fragment) {
        RxHttp.get(Url.get_railway)
                .add("token", token)
                .add("deviceNo", deviceId)
                .asClass(RailWayBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .to(RxLife.to(fragment))
                .subscribe(bean -> {
                    if (bean.getCode() == 200) {
                        result.addRailWayOnMap(bean);
                    } else {
                        result.requestFailed(bean.getCode() + bean.getMsg());
                    }
                }, throwable -> {
                });
    }


    @Override
    public void getAlarmSet(String token, String deviceId, Fragment fragment) {
        RxHttp.get(Url.get_alarmSet)
                .add("token", token)
                .add("deviceNo", deviceId)
                .asClass(AlarmSetBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .to(RxLife.to(fragment))
                .subscribe(bean -> {
                   if (bean.getCode() == 200){
                       result.getAlarmSettingSuccess(bean);
                   }else {
                       result.requestFailed(bean.getMsg());
                   }
                }, throwable -> {
                });
    }


    @Override
    public void uploadLocation(double lat, double lng, String time, float radius, float speed) {
        RxHttp.postForm(Url.upload_location)
                .add("token", SPUtils.getInstance().getString(Constant.TOKEN))
                .add("deviceNo", DeviceUtils.getAndroidID())
                .add("longitude", lng)
                .add("latitude", lat)
                .add("pickPointTime", time)
                .add("accuracy", radius)
                .add("speed", speed)
                .asClass(BaseBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .to(RxLife.to(NotificationFragment.getInstance()))
                .subscribe(bean -> {
                    //请求成功
                    if (bean.getCode() == Constant.SUCCESS_CODE) {
                        //上传成功
//                        AppUtil.print("上传成功");
                        //判断txt里面有没有需要上传的内容 如果有上传 如果没有作罢
//                                uploadHistroyLocation();
//                        result.uploadHistoryLocation();
                    } else {
                        AppUtil.print("上传失败");
                        AppUtil.print(bean.getCode() + bean.getMsg());
                        if (bean.getMsg().startsWith("token过期") || bean.getMsg().equals("Token is required")) {
                            result.exitApp();
                        } else {
                            saveUploadFailedLocation(lat, lng, speed, radius, time);
                        }
                    }
                }, throwable -> {
                    //请求失败
//                    result.uploadLocationFailed(throwable.toString());
                });
    }

    @Override
    public void uploadHistoryLocation() {
//        String jsonlist = FileUtils.getFileContent(new File(Constant.filePath + Constant.fileName));
//        if (jsonlist.length() > 1) {
//            RxHttp.postForm(Url.upload_history_location)
//                    .add("token", SPUtils.getInstance().getString(Constant.TOKEN))
//                    .add("deviceNo", DeviceUtils.getAndroidID())
//                    .add("josnlist", "[" + jsonlist.substring(0, jsonlist.length() - 2) + "]")
//                    .asClass(BaseBean.class)
//                    .to(RxLife.to(this))
//                    .subscribe(bean -> {
//                        //请求成功
//                        FileUtils.clearFileContent(Constant.filePath + Constant.fileName);
//                    }, throwable -> {
//                        //请求失败
//                    });
//
//        } else {
//            //么得内容不需要操作
//        }
    }

    /**
     * 上传失败后 将数据保存到本地。后面可能需要有网时候上传数据
     */
    private void saveUploadFailedLocation(double lat, double lng, float speed, float radius, String time) {
        LocationBean resbean = new LocationBean();
        resbean.setLatitude(String.valueOf(lat));
        resbean.setLongitude(String.valueOf(lng));
        resbean.setSpeed(speed);
        resbean.setAccuracy(radius);
        resbean.setPickPointTime(time);
        Gson gson = new Gson();
        FileUtils.writeTxtToFile(gson.toJson(resbean), Constant.filePath, Constant.fileName);
    }


    @Override
    public void onDestroy() {
        result = null;
    }

}
