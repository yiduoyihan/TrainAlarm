package com.leidi.trainalarm.ui.fm.n;

import android.app.Activity;

import androidx.lifecycle.LifecycleOwner;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.leidi.trainalarm.base.BaseBean;
import com.leidi.trainalarm.bean.NotificationBean;
import com.leidi.trainalarm.ui.fm.NotificationFragment;
import com.leidi.trainalarm.util.Constant;
import com.leidi.trainalarm.util.Url;
import com.rxjava.rxlife.RxLife;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;

/**
 * @author caiwu
 */
public class AlarmDetailPresenter implements AlarmDetailView.Presenter {

    private AlarmDetailView.Result result;

    public AlarmDetailPresenter(AlarmDetailView.Result result) {
        this.result = result;
    }

    @Override
    public void getListData(int pageSize, int pageNum, int regId,String date, Activity activity) {
        RxHttp.get(Url.notification_list)
                .add("pageSize", pageSize)
                .add("pageNum", pageNum)
                .add("alarmInfoIds", regId)
                .add("token", SPUtils.getInstance().getString(Constant.TOKEN))
                .add("deviceNo", DeviceUtils.getAndroidID())
                .add("startTime", date + " 00:00:00")
                .add("endTime", date + " 23:59:59")
                .asClass(NotificationBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .to(RxLife.to((LifecycleOwner) activity))
                .subscribe(bean -> {
                    //请求成功
                    if (bean.getCode() == Constant.SUCCESS_CODE) {
                        result.getListSuccess(bean);
                    } else {
                        result.getListFailed(bean.getMsg());
                    }
                }, throwable -> {
                    //不加runOnUiThread会报CalledFromWrongThreadException 异常处理
                });
    }

    @Override
    public void deleteItem(int alarmInfoId, int position, Activity fragment) {
        RxHttp.postForm(Url.DELETE_NOTIFICATION_ITEM)
                .add("alarmInfoIds", alarmInfoId)
                .add("token", SPUtils.getInstance().getString(Constant.TOKEN))
                .add("deviceNo", DeviceUtils.getAndroidID())
                .asClass(BaseBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .to(RxLife.to(NotificationFragment.getInstance()))
                .subscribe(bean -> {
                    //请求成功
                    if (bean.getCode() == Constant.SUCCESS_CODE) {
                        result.deleteSuccess(bean, position);
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
