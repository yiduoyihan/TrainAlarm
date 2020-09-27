package com.leidi.trainalarm.v;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.leidi.trainalarm.base.BaseBean;
import com.leidi.trainalarm.bean.HomeListBean;
import com.leidi.trainalarm.bean.LocationBean;
import com.leidi.trainalarm.bean.NotificationBean;
import com.leidi.trainalarm.ui.fm.HomeFragment;
import com.leidi.trainalarm.ui.fm.NotificationFragment;
import com.leidi.trainalarm.util.AppUtil;
import com.leidi.trainalarm.util.Constant;
import com.leidi.trainalarm.util.FileUtils;
import com.leidi.trainalarm.util.Url;
import com.rxjava.rxlife.RxLife;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;

/**
 * @author caiwu
 */
public class HomePresenter implements HomeView.Presenter {

    private HomeView.Result result;

    public HomePresenter(HomeView.Result result) {
        this.result = result;
    }

    @Override
    public void getListData(int pageSize, int pageNum, String date, HomeFragment fragment) {
        RxHttp.get(Url.home_list)
                .add("pageSize", pageSize)
                .add("pageNum", pageNum)
                .add("token", SPUtils.getInstance().getString(Constant.TOKEN))
                .add("deviceNo", DeviceUtils.getAndroidID())
                .add("startTime", date + " 00:00:00")
                .add("endTime", date + " 23:59:59")
                .asClass(HomeListBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .to(RxLife.to(HomeFragment.getInstance()))
                .subscribe(bean -> {
                    //请求成功
                    if (bean.getCode() == Constant.SUCCESS_CODE) {
                        result.getListSuccess(bean);
                    } else {
                        result.getListFailed(bean.getMsg());
                    }
                }, throwable -> {
                    result.getListFailed(throwable.toString());
                });


    }

    @Override
    public void deleteItem(int alarmInfoId) {
        RxHttp.postForm(Url.delete_home_item)
                .add("alarmInfoIds", alarmInfoId)
                .add("token", SPUtils.getInstance().getString(Constant.TOKEN))
                .add("deviceNo", DeviceUtils.getAndroidID())
                .asClass(BaseBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .to(RxLife.to(NotificationFragment.getInstance()))
                .subscribe(bean -> {
                    //请求成功
                    if (bean.getCode() == Constant.SUCCESS_CODE) {
                        result.deleteSuccess(bean);
                    } else {
                        result.deleteFailed(bean.getMsg());
                    }
                }, throwable -> {
                    //请求失败
                    result.deleteFailed(throwable.toString());
                });

    }

    @Override
    public void onDestroy() {
        result = null;
    }

}
