package com.leidi.trainalarm.ui.user_list;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.bigkoo.pickerview.TimePickerView;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.leidi.trainalarm.R;
import com.leidi.trainalarm.base.BaseActivity;
import com.leidi.trainalarm.bean.AllUserListBean;
import com.leidi.trainalarm.bean.TrailBean;
import com.leidi.trainalarm.ui.fm.HomeFragment;
import com.leidi.trainalarm.util.AppUtil;
import com.leidi.trainalarm.util.Constant;
import com.leidi.trainalarm.util.Url;
import com.rxjava.rxlife.RxLife;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;

/**
 * @author Administrator
 * @date 2017/5/25 0025
 * 车辆轨迹
 */

public class UserListActivity extends BaseActivity {

    @BindView(R.id.tv_start_date)
    TextView tvStartDate;
    @BindView(R.id.tv_end_date)
    TextView tvEndDate;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.bmapView)
    TextureMapView mMapView = null;
    @BindView(R.id.loading)
    ProgressBar pb;

    @BindView(R.id.tv_person_list)
    TextView tvPersonList;

    private BaiduMap mBaiduMap = null;
    private List<AllUserListBean.DataBean> beanList = new ArrayList<>();
    private List<String> list = new ArrayList<>();

    private ArrayAdapter adapter1;
    int pos;
    String userFlag;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_userlist;
    }

    @Override
    protected void initView() {
        mBaiduMap = mMapView.getMap();
        // 开启定位图层(显示定位小蓝点)
        mBaiduMap.setMyLocationEnabled(true);

        setToolbar(View.VISIBLE, "轨迹回放", View.INVISIBLE);
        userFlag = SPUtils.getInstance().getString(Constant.USER_FLAG, "0");
        //userFlag值为1是标识超级管理员，能查看所有人轨迹，其他用户只能查询自己的轨迹
        AppUtil.setUserMapCenter(getIntent().getDoubleExtra("lat", 0),
                getIntent().getDoubleExtra("lon", 0),
                mBaiduMap, 2);

        if (userFlag.equals("1")) {
            initData();
            initSpinner();
        } else {
            spinner.setVisibility(View.GONE);
            tvPersonList.setVisibility(View.GONE);
        }

    }

    private void initSpinner() {
        //将可选内容与ArrayAdapter连接
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        //设置下拉列表风格
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setDropDownHorizontalOffset(0);
        //将adapter添加到spinner中
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                AppUtil.print(pos + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void getTrailActivity(String userid) {
        mBaiduMap.clear();
        pb.setVisibility(View.VISIBLE);
        RxHttp.get(Url.get_track)
                .add("userId", userid)
                .add("token", SPUtils.getInstance().getString(Constant.TOKEN))
                .add("deviceNo", DeviceUtils.getAndroidID())
                .add("startTime", tvStartDate.getText().toString())
                .add("endTime", tvEndDate.getText().toString())
                .asClass(TrailBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .to(RxLife.to(HomeFragment.getInstance()))
                .subscribe(bean -> {
                    //请求成功
                    if (bean.getCode() == Constant.SUCCESS_CODE) {
                        addLineOnMap(bean);
                    } else {
                        requestFailed();
                    }
                }, throwable -> {
                    requestFailed();
                });
    }

    private void requestFailed() {
        AppUtil.print("查询失败");
        pb.setVisibility(View.GONE);
    }

    private void addLineOnMap(TrailBean bean) {
        pb.setVisibility(View.GONE);
        if (bean.getData().size() == 0) {
            ToastUtils.showShort("您查询的时间段内无轨迹");
            return;
        }
        List<LatLng> points = new ArrayList<LatLng>();
        for (int i = 0; i < bean.getData().size(); i++) {
            LatLng point = new LatLng(Double.parseDouble(bean.getData().get(i).getLatitude()),
                    Double.parseDouble(bean.getData().get(i).getLongitude()));
            points.add(point);
        }
        //设置折线的属性
        OverlayOptions mOverlayOptions = new PolylineOptions()
                .width(10)
                .color(0xAAFF0000)
                .points(points);
        //在地图上绘制折线
        //mPloyline 折线对象
        Overlay mPolyline = mBaiduMap.addOverlay(mOverlayOptions);
//        if (points.size() > 0) {
//
//        }
        //所有轨迹点显示在地图可视范围内
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng p : points) {
            builder = builder.include(p);
        }
        LatLngBounds latlngBounds = builder.build();
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(latlngBounds, mMapView.getWidth() - 100, mMapView.getHeight() - 100);
        mBaiduMap.animateMapStatus(u);
    }

    private void initData() {
        RxHttp.get(Url.get_all_user)
                .add("token", SPUtils.getInstance().getString(Constant.TOKEN))
                .add("deviceNo", DeviceUtils.getAndroidID())
                .add("userName", SPUtils.getInstance().getString(Constant.USERNAME))
                .add("userId", SPUtils.getInstance().getString(Constant.USER_FLAG, "0"))
                .asClass(AllUserListBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .to(RxLife.to(this))
                .subscribe(bean -> {
                    //请求成功
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (bean.getCode() == Constant.SUCCESS_CODE) {
                                beanList.addAll(bean.getData());
                                for (int i = 0; i < bean.getData().size(); i++) {
                                    list.add(bean.getData().get(i).getUserName());
                                }
                                adapter1.notifyDataSetChanged();
                            } else {
                                ToastUtils.showShort(bean.getMsg());
                            }
                        }
                    });


                }, throwable -> {
                    //请求失败
                });
    }

    @OnClick({R.id.tv_start_date, R.id.tv_end_date})
    public void onClickChooseDate(View view) {
        if (view.getId() == R.id.tv_start_date) {
            initDateTime(0);
        } else if (view.getId() == R.id.tv_end_date) {
            initDateTime(1);
        }
    }


    private void initDateTime(int flag) {
        Calendar startDate = Calendar.getInstance();
        //正确设置方式 原因：注意事项有说明
        startDate.set(startDate.get(Calendar.YEAR) - 2, startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH));
        Calendar endDate = Calendar.getInstance();
        endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH));
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                if (flag == 0) {
                    tvStartDate.setText(format.format(date));
                } else {
                    tvEndDate.setText(format.format(date));
                }
            }
        }).setType(new boolean[]{true, true, true, true, true, false})// 默认全部显示
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    @OnClick(R.id.tvSearch)
    public void onClick() {
        if (tvStartDate.getText().toString().equals("请选择开始日期") || tvEndDate.getText().toString().equals("请选择开始日期")) {
            ToastUtils.showShort("请选择查询日期");
        } else if (AppUtil.dateToStamp(tvStartDate.getText().toString()) >= AppUtil.dateToStamp(tvEndDate.getText().toString())) {
            ToastUtils.showShort("查询的开始时间必须早于结束时间");
        } else {

            if (userFlag.equals("1")) {
                getTrailActivity(beanList.get(pos).getUserId() + "");
            } else {
                getTrailActivity("" + SPUtils.getInstance().getInt(Constant.USER_ID));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时必须调用mMapView. onResume ()
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时必须调用mMapView. onPause ()
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时必须调用mMapView.onDestroy()
        mMapView.onDestroy();
    }

}
