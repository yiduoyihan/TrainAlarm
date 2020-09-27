package com.leidi.trainalarm.ui.fm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.leidi.trainalarm.R;
import com.leidi.trainalarm.adapter.HomeCardAdapter;
import com.leidi.trainalarm.android.MPush;
import com.leidi.trainalarm.base.BaseFragment;
import com.leidi.trainalarm.bean.AlarmSetBean;
import com.leidi.trainalarm.bean.CardMsgBean;
import com.leidi.trainalarm.bean.HomeCardBean;
import com.leidi.trainalarm.bean.NotificationEvent;
import com.leidi.trainalarm.bean.RailWayBean;
import com.leidi.trainalarm.bean.TrainLocationDetailBean;
import com.leidi.trainalarm.ui.fm.n.AlarmDetailActivity;
import com.leidi.trainalarm.ui.login.LoginActivity;
import com.leidi.trainalarm.ui.user_list.UserListActivity;
import com.leidi.trainalarm.util.AppUtil;
import com.leidi.trainalarm.util.Constant;
import com.leidi.trainalarm.util.MaxHeightRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 阎
 * @date 2020/5/7
 */
public class NotificationFragment extends BaseFragment implements MKOfflineMapListener,
        NotificationFmView.Result {

    @BindView(R.id.bmapView)
    TextureMapView mMapView;
    @BindView(R.id.layout_map)
    LinearLayout layoutMap;
    @BindView(R.id.tv_distance_and_time)
    TextView tvDistanceAndTime;
    @BindView(R.id.rv_list)
    MaxHeightRecyclerView recyclerView;
    @BindView(R.id.layout_list)
    LinearLayout layoutList;
    @BindView(R.id.tv_rv_title)
    TextView tvAlarmTitle;
    @BindView(R.id.iv_max_or_min)
    ImageView ivMaxOrMin;

    private BaiduMap mBaiduMap = null;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private LatLng personLocation = null;
    private MKOfflineMap mOffline = null;
    private List<String> trainNo = new ArrayList<>();
    private List<String> cardTrainNo = new ArrayList<>();
    List<LatLng> points = new ArrayList<>();
    BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.train);
    //设置折线的属性
    List<OverlayOptions> mOverlayOptions = new ArrayList<>();
    // 用于设置个性化地图的样式文件
    private static final String CUSTOM_FILE_NAME_CX = "8e77901507dedef802869499006206b7.sty";

    @SuppressLint("SimpleDateFormat")
    public SimpleDateFormat formatter;
    List<TrainLocationDetailBean> dataList = new ArrayList<>();
    List<CardMsgBean> cardMsgBeanList = new ArrayList<>();
    private NotificationFmPresenter presenter;
    HomeCardAdapter adapter;
    AlarmSetBean.DataBean alarmDataBean;
    private float maxWeight = 4.7f;


    @Override
    protected int getLayoutId() {
        return R.layout.fm_notification;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, View view) {
        initToolbar();
        initSetting();
        initLocationAndMap();
        initRecyclerView();

        presenter = new NotificationFmPresenter(this);
        presenter.getRailWay(SPUtils.getInstance().getString(Constant.TOKEN), DeviceUtils.getAndroidID(), this);
        presenter.getAlarmSet(SPUtils.getInstance().getString(Constant.TOKEN), DeviceUtils.getAndroidID(), this);
    }

    /**
     * 初始化Toolbar相关操作
     */
    private void initToolbar() {
        setToolbar(View.VISIBLE, "列车接近告警系统", View.VISIBLE);
        tvTitleLeftButton.setImageResource(R.mipmap.alarm_detail);
        tvTitleLeftButton.setOnClickListener(v -> startActivity(new Intent(getActivity(), AlarmDetailActivity.class)));

        //右边按钮的点击事件
        tvTitleRightButton.setImageResource(R.mipmap.guiji_icon);
        tvTitleRightButton.setOnClickListener(v -> startActivity(new Intent(getActivity(), UserListActivity.class)
                .putExtra("lon", personLocation.longitude)
                .putExtra("lat", personLocation.latitude)));
    }

    /**
     * 初始化一些必要的设置
     */
    private void initSetting() {
        //加上判断
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
//        swipeRefreshLayout.setEnabled(false);
    }

    /**
     * 初始化定位和地图相关操作
     */
    private void initLocationAndMap() {
        //初始化定位
        mLocationClient = new LocationClient(Objects.requireNonNull(getContext()).getApplicationContext());
        AppUtil.initBDLocationClient(myListener, mLocationClient);
        mBaiduMap = mMapView.getMap();
        //初始化离线地图
        initOfflineMap();
        mBaiduMap.setMyLocationEnabled(true);
        startLocation();

        // 获取.sty文件路径
        String customStyleFilePath = AppUtil.getCustomStyleFilePath(Objects.requireNonNull(getActivity()), CUSTOM_FILE_NAME_CX);
        // 设置个性化地图样式文件的路径和加载方式
        mMapView.setMapCustomStylePath(customStyleFilePath);
        // 动态设置个性化地图样式是否生效
        mMapView.setMapCustomStyleEnable(true);
    }

    /**
     * 初始化recyclerview
     */
    private void initRecyclerView() {
        adapter = new HomeCardAdapter(cardMsgBeanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.bindToRecyclerView(recyclerView);
        recyclerView.setVisibility(View.GONE);
    }

    /**
     * 获取铁轨坐标点成功后在地图上划线出来
     */
    @Override
    public void addRailWayOnMap(RailWayBean bean) {
        if (bean.getData().size() == 0) {
            return;
        }
        mOverlayOptions.clear();
        int[] colors = {0xAA21A609, 0xAAF47B09, 0xAADA5830, 0xAAD319E5};
        for (int a = 0; a < bean.getData().size(); a++) {
            List<RailWayBean.DataBean.JjgjRailwaylineEntityBean> railList = bean.getData().get(a).getJjgjRailwaylineEntity();
            List<LatLng> points = new ArrayList<>();
            for (int i = 0; i < railList.size(); i++) {
                LatLng point = new LatLng(Double.parseDouble(railList.get(i).getLatitudeBD()),
                        Double.parseDouble(railList.get(i).getLongitudeBD()));
                points.add(point);
            }
            //设置折线的属性
            OverlayOptions mOverlayOption = new PolylineOptions()
                    .width(7)
                    .color(colors[a])
                    .points(points)
                    .dottedLine(true);
            mOverlayOptions.add(mOverlayOption);
        }
        //在地图上绘制折线
        //mPloyline 折线对象
        addLineOnMap();
    }

    @Override
    public void getAlarmSettingSuccess(AlarmSetBean bean) {
        //获取报警等级成功
        alarmDataBean = bean.getData().get(0);
    }

    /**
     * token过期  退出APP
     */
    @Override
    public void exitApp() {
        mLocationClient.stop();
        SPUtils.getInstance().clear();
        MPush.I.pausePush();
        MPush.I.unbindAccount();
        MPush.I.stopPush();
        startActivity(new Intent(getActivity(), LoginActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        Objects.requireNonNull(getActivity()).finish();
    }

    /**
     * 统一的请求失败处理
     */
    @Override
    public void requestFailed(String string) {
        AppUtil.print(string);
    }

    /**
     * 离线地图相关操作
     */
    private void initOfflineMap() {
        mOffline = new MKOfflineMap();
        mOffline.init(this);
        // 获取已下过的离线地图信息
        // 已下载的离线地图信息列表
        ArrayList<MKOLUpdateElement> localMapList = mOffline.getAllUpdateInfo();
        if (localMapList == null) {
            localMapList = new ArrayList<>();
        }

        if (localMapList.size() > 0 && !mOffline.update(1)) {
            AppUtil.print("大于0 无更新");
        } else {
            mOffline.remove(1);
            int cityid = 1;
            mOffline.start(cityid);
        }
    }


    /**
     * 定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @SuppressLint("SimpleDateFormat")
        @Override
        public void onReceiveLocation(BDLocation location) {
            personLocation = new LatLng(location.getLatitude(), location.getLongitude());
            //离线定位
            if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                AppUtil.setUserMapCenter(location.getLatitude(), location.getLongitude(), mBaiduMap);
            } else if (location.getLocType() == BDLocation.TypeOffLineLocationFail) {
                AppUtil.print("定位失败了");
            } else {
                //在线定位
                if (formatter == null) {
                    formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                }
                AppUtil.setUserMapCenter(location.getLatitude(), location.getLongitude(), mBaiduMap);
                String dateString = formatter.format(new Date());
                presenter.uploadLocation(location.getLatitude(), location.getLongitude(), dateString, location.getRadius(), location.getSpeed());
            }
        }
    }

    /**
     * 离线地图的回调处理
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void onGetOfflineMapState(int type, int state) {
        switch (type) {
            case MKOfflineMap.TYPE_DOWNLOAD_UPDATE:
                MKOLUpdateElement update = mOffline.getUpdateInfo(state);
                // 处理下载进度更新提示
                if (update != null) {
                    AppUtil.print(String.format("%s : %d%%", update.cityName, update.ratio));
                }
                break;
            case MKOfflineMap.TYPE_NEW_OFFLINE:
                // 有新离线地图安装
                Log.d("OfflineDemo", String.format("add offlinemap num:%d", state));
                break;
            case MKOfflineMap.TYPE_VER_UPDATE:
                // 版本更新提示  MKOLUpdateElement e =
                mOffline.getUpdateInfo(state);
                break;
            default:
                break;
        }
    }


    private void stopLocation() {
        mLocationClient.stop();
    }

    private void startLocation() {
        mLocationClient.start();
    }

    public static NotificationFragment getInstance() {
        return new NotificationFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
//        mMapView.onResume();
        if (presenter != null) {
            presenter.getAlarmSet(SPUtils.getInstance().getString(Constant.TOKEN), DeviceUtils.getAndroidID(), this);
        } else {
            presenter = new NotificationFmPresenter(this);
            presenter.getAlarmSet(SPUtils.getInstance().getString(Constant.TOKEN), DeviceUtils.getAndroidID(), this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        //在Fragment 中如果使用mMapView.onPause(); 和mMapView.onResume();的话，那么在viewpager切换几次的
        //情况下，会到底中间fragment中的地图卡死，原因暂时未知，只在百度官方API中看到说onPause和onResume两个
        //状态是在Activity中的对应生命周期中执行，没有提及fragment中如何处理，暂时屏蔽掉两个状态解决了此问题。
//        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        stopLocation();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 暂时没用，等待后面需要的时候再扩展吧
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReloadData(NotificationEvent message) {
//        pageSize = 0;
//        //收到推送延迟1S刷新数
//        new Handler().postDelayed(() -> presenter.getListData(pageSize, pageNum, regId, NotificationFragment.this), 1000);
    }

    /**
     * 开始接收推送的消息后，从Receiver中接收到数据，通过EventBus发送数据过来处理后，将火车图标添加到map中
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReloadData(TrainLocationDetailBean bean) {
        //如果等于0 相当于第一条数据 直接添加，否则需要判断是否是已经有的数据
        if (dataList.size() == 0) {
            if (bean.getLat() != null && bean.getLon() != null) {
                dataList.add(bean);
                trainNo.add(bean.getNum());
            }
        }
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getNum().equals(bean.getNum())) {
                dataList.get(i).setLat(bean.getLat());
                dataList.get(i).setLon(bean.getLon());
                dataList.get(i).setName(bean.getName());
                dataList.get(i).setTime(bean.getTime());
                dataList.get(i).setType(bean.getType());
                dataList.get(i).setSpeed(bean.getSpeed());
                dataList.get(i).setActions(bean.getActions());
                dataList.get(i).setLocation(bean.getLocation());
            }
        }

        if (!trainNo.contains(bean.getNum())) {
            dataList.add(bean);
            trainNo.add(bean.getNum());
        }
        //先要初始化points集合，此代表dateList中所有的点的经纬度的封装类的集合
        points.clear();
        for (int i = dataList.size() - 1; i >= 0; i--) {
            //初始化时候的经纬度
            //如果是add才加到显示的points集合中
            if ("add".equals(dataList.get(i).getActions())) {
                LatLng latLng = new LatLng(
                        Double.parseDouble(dataList.get(i).getLat()),
                        Double.parseDouble(dataList.get(i).getLon()));
                //将所有点加到points
                points.add(latLng);
            } else {
                trainNo.remove(dataList.get(i).getNum());
                dataList.remove(i);
            }
        }
        //将所有的点加到地图上去
        List<OverlayOptions> optionsList = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            OverlayOptions option = new MarkerOptions()
                    .position(points.get(i))
                    .icon(bitmap);
            optionsList.add(option);
        }
        mBaiduMap.clear();
        //mPloyline 折线对象
        if (null != mOverlayOptions) {
            addLineOnMap();
        } else if (null != presenter) {
            presenter.getRailWay(SPUtils.getInstance().getString(Constant.TOKEN), DeviceUtils.getAndroidID(), this);
        } else {
            presenter = new NotificationFmPresenter(this);
            presenter.getRailWay(SPUtils.getInstance().getString(Constant.TOKEN), DeviceUtils.getAndroidID(), this);
        }
        mBaiduMap.addOverlays(optionsList);
//        if (dataList.size() == 0) {
//            tvDistanceAndTime.setVisibility(View.GONE);
//        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCardMsg(CardMsgBean cardMsgBean) {
        if (cardMsgBeanList.size() == 0) {
            cardMsgBeanList.add(cardMsgBean);
            cardTrainNo.add(cardMsgBean.getNum());
        }
        for (int i = 0; i < cardMsgBeanList.size(); i++) {
            if (cardMsgBeanList.get(i).getNum().equals(cardMsgBean.getNum())) {
                cardMsgBeanList.get(i).setDirection(cardMsgBean.getDirection());
                cardMsgBeanList.get(i).setDistance(cardMsgBean.getDistance());
                cardMsgBeanList.get(i).setName(cardMsgBean.getName());
                cardMsgBeanList.get(i).setTime(cardMsgBean.getTime());
                cardMsgBeanList.get(i).setStationLast(cardMsgBean.getStationLast());
                cardMsgBeanList.get(i).setStationNext(cardMsgBean.getStationNext());
                cardMsgBeanList.get(i).setSpeed(cardMsgBean.getSpeed());
                cardMsgBeanList.get(i).setActions(cardMsgBean.getActions());
                cardMsgBeanList.get(i).setLocation(cardMsgBean.getLocation());
                cardMsgBeanList.get(i).setIsNearPeople(cardMsgBean.getIsNearPeople());
                cardMsgBeanList.get(i).setTime(cardMsgBean.getTime());
            }
        }
        if (!cardTrainNo.contains(cardMsgBean.getNum())) {
            cardMsgBeanList.add(cardMsgBean);
            cardTrainNo.add(cardMsgBean.getNum());
        }
        for (int i = cardMsgBeanList.size() - 1; i >= 0; i--) {
            //初始化时候的经纬度
            //如果是add才加到显示的points集合中
            if ("false".equals(cardMsgBeanList.get(i).getIsNearPeople())) {
                cardTrainNo.remove(cardMsgBeanList.get(i).getNum());
                cardMsgBeanList.remove(i);
            }
        }

        tvAlarmTitle.setText("可视化展示(" + cardMsgBeanList.size() + "条)");
        if (cardMsgBeanList.size() > 0 && ivMaxOrMin.getVisibility() == View.GONE) {
            layoutList.setVisibility(View.VISIBLE);
            ivMaxOrMin.setVisibility(View.VISIBLE);
            ivMaxOrMin.setImageResource(R.mipmap.iv_max);
        } else if (cardMsgBeanList.size() == 0) {
            layoutList.setVisibility(View.GONE);
            ivMaxOrMin.setVisibility(View.GONE);
        }
        //处理datalist数据然后显示到RecycleView中
        dataToRecyclerView(cardMsgBeanList);
    }

    @OnClick(R.id.iv_max_or_min)
    public void onMaxOrMinClick() {
        if (layoutList.getVisibility() == View.VISIBLE && cardMsgBeanList.size() > 0) {
            layoutList.setVisibility(View.GONE);
            ivMaxOrMin.setImageResource(R.mipmap.iv_min);
        } else {
            layoutList.setVisibility(View.VISIBLE);
            ivMaxOrMin.setImageResource(R.mipmap.iv_max);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void dataToRecyclerView(List<CardMsgBean> cardMsgBeanList) {
        //这方法需要jdk1.8以上
        cardMsgBeanList.sort((x, y) -> Double.compare(Double.parseDouble(x.getDistance()), Double.parseDouble(y.getDistance())));
//        Collections.sort(cardMsgBeanList, (o1, o2) -> {
//            //对List按距离升序排列
//            return o1.getDistance().compareTo(o2.getDistance());
//        });
//        cardMsgBeanList.sort(Comparator.comparing(CardMsgBean::getDistance));

        for (int i = 0; i < cardMsgBeanList.size(); i++) {
            //控制标题是否显示（告警展示）
            System.out.print("=====" + cardMsgBeanList.get(i).getDistance() + " ");
            if (i == 0) {
                cardMsgBeanList.get(i).setHasTitle(true);
            } else {
                cardMsgBeanList.get(i).setHasTitle(false);
            }
//            double dis = DistanceUtil.getDistance(personLocation, new LatLng(Double.parseDouble(dataList.get(i).getLat()), Double.parseDouble(dataList.get(i).getLon())));
//            cardMsgBeanList.get(i).setDistance(AppUtil.formatDate(String.valueOf(dis / 1000)));
            //1 上行， 2下行
            if ("1".equals(cardMsgBeanList.get(i).getDirection())) {
                cardMsgBeanList.get(i).setItemType(HomeCardBean.ITEM_UP);
            } else {
                cardMsgBeanList.get(i).setItemType(HomeCardBean.ITEM_DOWN);
            }

            if (null != alarmDataBean) {
                cardMsgBeanList.get(i).setFirstAlarm(alarmDataBean.getFirstAlarm());
                cardMsgBeanList.get(i).setSecondAlarm(alarmDataBean.getSecondAlarm());
                cardMsgBeanList.get(i).setThiredAlarm(alarmDataBean.getThirdAlarm());
                //只有一级告警
                if (null != alarmDataBean.getFirstAlarm() && null == alarmDataBean.getSecondAlarm() && null == alarmDataBean.getThirdAlarm()) {
                    oneAlarmMethod(cardMsgBeanList, i, 1);
                }
                if (null == alarmDataBean.getFirstAlarm() && null != alarmDataBean.getSecondAlarm() && null == alarmDataBean.getThirdAlarm()) {
                    oneAlarmMethod(cardMsgBeanList, i, 2);
                }
                if (null == alarmDataBean.getFirstAlarm() && null == alarmDataBean.getSecondAlarm() && null != alarmDataBean.getThirdAlarm()) {
                    oneAlarmMethod(cardMsgBeanList, i, 3);
                }
                //二级告警
                if (null != alarmDataBean.getFirstAlarm() && null != alarmDataBean.getSecondAlarm() && null == alarmDataBean.getThirdAlarm()) {
                    twoAlarmMethod(cardMsgBeanList, i, 3);
                }
                if (null != alarmDataBean.getFirstAlarm() && null == alarmDataBean.getSecondAlarm() && null != alarmDataBean.getThirdAlarm()) {
                    twoAlarmMethod(cardMsgBeanList, i, 2);
                }
                if (null == alarmDataBean.getFirstAlarm() && null != alarmDataBean.getSecondAlarm() && null != alarmDataBean.getThirdAlarm()) {
                    twoAlarmMethod(cardMsgBeanList, i, 1);
                }
                //三级告警
                if (null != alarmDataBean.getFirstAlarm() && null != alarmDataBean.getSecondAlarm() && null != alarmDataBean.getThirdAlarm()) {
                    cardMsgBeanList.get(i).setAlarmLv(3);
                    threeAlarmMethod(cardMsgBeanList, i);

                }

            }
//            if (null == alarmDataBean.getSecondAlarm() || null == alarmDataBean.getThirdAlarm()) {
//                //只有两级告警
//            }

        }
        AppUtil.print("");
        if (cardMsgBeanList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }


    /**
     * 只设了一级告警
     */
    private void oneAlarmMethod(List<CardMsgBean> dataList, int i, int which) {
        dataList.get(i).setAlarmLv(1);
        //which代表 123级报警中的哪级是设置过了的
        float[] floatStart = {1f, 1.85f, 2.9f, 4.0f};
        double oneAlarm;
        if (which == 1) {
            oneAlarm = Double.parseDouble(dataList.get(i).getFirstAlarm());
        } else if (which == 2) {
            oneAlarm = Double.parseDouble(dataList.get(i).getSecondAlarm());
        } else if (which == 3) {
            oneAlarm = Double.parseDouble(dataList.get(i).getThiredAlarm());
        } else {
            oneAlarm = 0;
        }

        if (Double.parseDouble(dataList.get(i).getDistance()) > (oneAlarm + 0.5)) {
            dataList.get(i).setStartWeight(floatStart[0]);
            dataList.get(i).setEndWeight(maxWeight - floatStart[0]);
            allWhite(dataList, i);
        } else if (Double.parseDouble(dataList.get(i).getDistance()) < (oneAlarm + 0.5) && Double.parseDouble(dataList.get(i).getDistance()) > (oneAlarm - 0.5)) {
            dataList.get(i).setStartWeight(floatStart[1]);
            dataList.get(i).setEndWeight(maxWeight - floatStart[1]);
            dataList.get(i).setPoint1(R.drawable.circle_shape_default);
            dataList.get(i).setPoint2(R.drawable.circle_shape_default);
            dataList.get(i).setPoint3(R.drawable.circle_shape_red);
            dataList.get(i).setLine1(R.drawable.circle_shape_default);
            dataList.get(i).setLine2(R.drawable.line_shape_default);
            dataList.get(i).setLine3(R.drawable.line_shape_red);
        } else if (Double.parseDouble(dataList.get(i).getDistance()) < (oneAlarm - 0.5) && Double.parseDouble(dataList.get(i).getDistance()) > 0.5) {
            dataList.get(i).setStartWeight(floatStart[2]);
            dataList.get(i).setEndWeight(maxWeight - floatStart[2]);
            dataList.get(i).setPoint1(R.drawable.circle_shape_default);
            dataList.get(i).setPoint2(R.drawable.circle_shape_default);
            dataList.get(i).setPoint3(R.drawable.circle_shape_red);
            dataList.get(i).setLine1(R.drawable.line_shape_default);
            dataList.get(i).setLine2(R.drawable.line_shape_default);
            dataList.get(i).setLine3(R.drawable.line_shape_red);
        } else if (Double.parseDouble(dataList.get(i).getDistance()) < 0.5) {
            dataList.get(i).setStartWeight(floatStart[3]);
            dataList.get(i).setEndWeight(maxWeight - floatStart[3]);
            dataList.get(i).setPoint1(R.drawable.circle_shape_default);
            dataList.get(i).setPoint2(R.drawable.circle_shape_default);
            dataList.get(i).setPoint3(R.drawable.circle_shape_red);
            dataList.get(i).setLine1(R.drawable.line_shape_default);
            dataList.get(i).setLine2(R.drawable.line_shape_default);
            dataList.get(i).setLine3(R.drawable.line_shape_red);
        }
    }


    private void twoAlarmMethod(List<CardMsgBean> dataList, int i, int which) {
        dataList.get(i).setAlarmLv(2);
        //which代表 123级报警中的哪级是没有设置过的
        float[] floatStart = {0.5f, 1.25f, 1.8f, 2.6f, 3.3f, 4.0f};
        double oneAlarm;
        double twoAlarm;
        if (which == 1) {
            oneAlarm = Double.parseDouble(dataList.get(i).getSecondAlarm());
            twoAlarm = Double.parseDouble(dataList.get(i).getThiredAlarm());
        } else if (which == 2) {
            oneAlarm = Double.parseDouble(dataList.get(i).getFirstAlarm());
            twoAlarm = Double.parseDouble(dataList.get(i).getThiredAlarm());
        } else if (which == 3) {
            oneAlarm = Double.parseDouble(dataList.get(i).getFirstAlarm());
            twoAlarm = Double.parseDouble(dataList.get(i).getSecondAlarm());
        } else {
            oneAlarm = 0;
            twoAlarm = 0;
        }

        if (Double.parseDouble(dataList.get(i).getDistance()) > (twoAlarm + 0.5)) {
            dataList.get(i).setStartWeight(floatStart[0]);
            dataList.get(i).setEndWeight(maxWeight - floatStart[0]);
            allWhite(dataList, i);
        } else if (Double.parseDouble(dataList.get(i).getDistance()) < (twoAlarm + 0.5) && Double.parseDouble(dataList.get(i).getDistance()) > (twoAlarm - 0.5)) {
            dataList.get(i).setStartWeight(floatStart[1]);
            dataList.get(i).setEndWeight(maxWeight - floatStart[1]);
            dataList.get(i).setPoint1(R.drawable.circle_shape_default);
            dataList.get(i).setPoint2(R.drawable.circle_shape_orange);
            dataList.get(i).setPoint3(R.drawable.circle_shape_orange);
            dataList.get(i).setLine1(R.drawable.line_shape_default);
            dataList.get(i).setLine2(R.drawable.line_shape_orange);
            dataList.get(i).setLine3(R.drawable.line_shape_orange);
        } else if (Double.parseDouble(dataList.get(i).getDistance()) < (twoAlarm - 0.5) && Double.parseDouble(dataList.get(i).getDistance()) > (oneAlarm + 0.5)) {
            dataList.get(i).setStartWeight(floatStart[2]);
            dataList.get(i).setEndWeight(maxWeight - floatStart[2]);
            dataList.get(i).setPoint1(R.drawable.circle_shape_default);
            dataList.get(i).setPoint2(R.drawable.circle_shape_orange);
            dataList.get(i).setPoint3(R.drawable.circle_shape_orange);
            dataList.get(i).setLine1(R.drawable.line_shape_default);
            dataList.get(i).setLine2(R.drawable.line_shape_orange);
            dataList.get(i).setLine3(R.drawable.line_shape_orange);
        } else if (Double.parseDouble(dataList.get(i).getDistance()) < (oneAlarm + 0.5) && Double.parseDouble(dataList.get(i).getDistance()) > (oneAlarm - 0.5)) {
            dataList.get(i).setStartWeight(floatStart[3]);
            dataList.get(i).setEndWeight(maxWeight - floatStart[3]);
            dataList.get(i).setPoint1(R.drawable.circle_shape_default);
            dataList.get(i).setPoint2(R.drawable.circle_shape_orange);
            dataList.get(i).setPoint3(R.drawable.circle_shape_red);
            dataList.get(i).setLine1(R.drawable.line_shape_default);
            dataList.get(i).setLine2(R.drawable.line_shape_orange);
            dataList.get(i).setLine3(R.drawable.line_shape_red);
        } else if (Double.parseDouble(dataList.get(i).getDistance()) < (oneAlarm - 0.5) && Double.parseDouble(dataList.get(i).getDistance()) > 0.5) {
            dataList.get(i).setStartWeight(floatStart[4]);
            dataList.get(i).setEndWeight(maxWeight - floatStart[4]);
            dataList.get(i).setPoint1(R.drawable.circle_shape_default);
            dataList.get(i).setPoint2(R.drawable.circle_shape_orange);
            dataList.get(i).setPoint3(R.drawable.circle_shape_red);
            dataList.get(i).setLine1(R.drawable.line_shape_default);
            dataList.get(i).setLine2(R.drawable.line_shape_orange);
            dataList.get(i).setLine3(R.drawable.line_shape_red);
        } else if (Double.parseDouble(dataList.get(i).getDistance()) < 0.5) {
            dataList.get(i).setStartWeight(floatStart[5]);
            dataList.get(i).setEndWeight(maxWeight - floatStart[5]);
            dataList.get(i).setPoint1(R.drawable.circle_shape_default);
            dataList.get(i).setPoint2(R.drawable.circle_shape_orange);
            dataList.get(i).setPoint3(R.drawable.circle_shape_red);
            dataList.get(i).setLine1(R.drawable.line_shape_default);
            dataList.get(i).setLine2(R.drawable.line_shape_orange);
            dataList.get(i).setLine3(R.drawable.line_shape_red);
        } else {
            allWhite(dataList, i);
        }
    }

    private void threeAlarmMethod(List<CardMsgBean> dataList, int i) {
        float[] floatStart = {0.3f, 0.8f, 1.4f, 1.9f, 2.4f, 3.0f, 3.5f, 4.0f};
        double threeAlarm = Double.parseDouble(dataList.get(i).getThiredAlarm());
        double twoAlarm = Double.parseDouble(dataList.get(i).getSecondAlarm());
        double oneAlarm = Double.parseDouble(dataList.get(i).getFirstAlarm());

        if (Double.parseDouble(dataList.get(i).getDistance()) > (threeAlarm + 0.5)) {
            dataList.get(i).setStartWeight(floatStart[0]);
            dataList.get(i).setEndWeight(maxWeight - floatStart[0]);
            allWhite(dataList, i);
        } else if (Double.parseDouble(dataList.get(i).getDistance()) <= (threeAlarm + 0.5) && Double.parseDouble(dataList.get(i).getDistance()) >= (threeAlarm - 0.5)) {
            dataList.get(i).setStartWeight(floatStart[1]);
            dataList.get(i).setEndWeight(maxWeight - floatStart[1]);
            allYellow(dataList, i);
        } else if (Double.parseDouble(dataList.get(i).getDistance()) < (threeAlarm - 0.5) && Double.parseDouble(dataList.get(i).getDistance()) >= (twoAlarm + 0.5)) {
            dataList.get(i).setStartWeight(floatStart[2]);
            dataList.get(i).setEndWeight(maxWeight - floatStart[2]);
            allYellow(dataList, i);
        } else if (Double.parseDouble(dataList.get(i).getDistance()) < (twoAlarm + 0.5) && Double.parseDouble(dataList.get(i).getDistance()) >= (twoAlarm - 0.5)) {
            dataList.get(i).setStartWeight(floatStart[3]);
            dataList.get(i).setEndWeight(maxWeight - floatStart[3]);
            dataList.get(i).setPoint1(R.drawable.circle_shape_yellow);
            dataList.get(i).setPoint2(R.drawable.circle_shape_orange);
            dataList.get(i).setPoint3(R.drawable.circle_shape_orange);
            dataList.get(i).setLine1(R.drawable.line_shape_yellow);
            dataList.get(i).setLine2(R.drawable.line_shape_orange);
            dataList.get(i).setLine3(R.drawable.line_shape_orange);
        } else if (Double.parseDouble(dataList.get(i).getDistance()) <= (twoAlarm - 0.5) && Double.parseDouble(dataList.get(i).getDistance()) >= (oneAlarm + 0.5)) {
            dataList.get(i).setStartWeight(floatStart[4]);
            dataList.get(i).setEndWeight(maxWeight - floatStart[4]);
            dataList.get(i).setPoint1(R.drawable.circle_shape_yellow);
            dataList.get(i).setPoint2(R.drawable.circle_shape_orange);
            dataList.get(i).setPoint3(R.drawable.circle_shape_orange);
            dataList.get(i).setLine1(R.drawable.line_shape_yellow);
            dataList.get(i).setLine2(R.drawable.line_shape_orange);
            dataList.get(i).setLine3(R.drawable.line_shape_orange);
        } else if (Double.parseDouble(dataList.get(i).getDistance()) <= (oneAlarm + 0.5) && Double.parseDouble(dataList.get(i).getDistance()) >= (oneAlarm - 0.5)) {
            dataList.get(i).setStartWeight(floatStart[5]);
            dataList.get(i).setEndWeight(maxWeight - floatStart[5]);
            dataList.get(i).setPoint1(R.drawable.circle_shape_yellow);
            dataList.get(i).setPoint2(R.drawable.circle_shape_orange);
            dataList.get(i).setPoint3(R.drawable.circle_shape_red);
            dataList.get(i).setLine1(R.drawable.line_shape_yellow);
            dataList.get(i).setLine2(R.drawable.line_shape_orange);
            dataList.get(i).setLine3(R.drawable.line_shape_red);
        } else if (Double.parseDouble(dataList.get(i).getDistance()) <= (oneAlarm - 0.5) && Double.parseDouble(dataList.get(i).getDistance()) >= 0.5) {
            dataList.get(i).setStartWeight(floatStart[6]);
            dataList.get(i).setEndWeight(maxWeight - floatStart[6]);
            dataList.get(i).setPoint1(R.drawable.circle_shape_yellow);
            dataList.get(i).setPoint2(R.drawable.circle_shape_orange);
            dataList.get(i).setPoint3(R.drawable.circle_shape_red);
            dataList.get(i).setLine1(R.drawable.line_shape_yellow);
            dataList.get(i).setLine2(R.drawable.line_shape_orange);
            dataList.get(i).setLine3(R.drawable.line_shape_red);
        } else if (Double.parseDouble(dataList.get(i).getDistance()) <= 0.5 && Double.parseDouble(dataList.get(i).getDistance()) >= 0) {
            dataList.get(i).setStartWeight(floatStart[7]);
            dataList.get(i).setEndWeight(maxWeight - floatStart[7]);
            dataList.get(i).setPoint1(R.drawable.circle_shape_yellow);
            dataList.get(i).setPoint2(R.drawable.circle_shape_orange);
            dataList.get(i).setPoint3(R.drawable.circle_shape_red);
            dataList.get(i).setLine1(R.drawable.line_shape_yellow);
            dataList.get(i).setLine2(R.drawable.line_shape_orange);
            dataList.get(i).setLine3(R.drawable.line_shape_red);
        }
//        else if (Double.parseDouble(dataList.get(i).getDistance()) < 0) {
//            dataList.get(i).setStartWeight(floatStart[maxWeight]);
//            dataList.get(i).setEndWeight(maxWeight - floatStart[maxWeight]);
//
//            dataList.get(i).setPoint1(R.drawable.circle_shape_default);
//            dataList.get(i).setPoint2(R.drawable.circle_shape_default);
//            dataList.get(i).setPoint3(R.drawable.circle_shape_default);
//            dataList.get(i).setLine1(R.drawable.line_shape_default);
//            dataList.get(i).setLine2(R.drawable.line_shape_default);
//            dataList.get(i).setLine3(R.drawable.line_shape_default);
//        }
    }

    private void allYellow(List<CardMsgBean> dataList, int i) {
        dataList.get(i).setPoint1(R.drawable.circle_shape_yellow);
        dataList.get(i).setPoint2(R.drawable.circle_shape_yellow);
        dataList.get(i).setPoint3(R.drawable.circle_shape_yellow);
        dataList.get(i).setLine1(R.drawable.line_shape_yellow);
        dataList.get(i).setLine2(R.drawable.line_shape_yellow);
        dataList.get(i).setLine3(R.drawable.line_shape_yellow);
    }

    private void allWhite(List<CardMsgBean> dataList, int i) {
        dataList.get(i).setPoint1(R.drawable.circle_shape_default);
        dataList.get(i).setPoint2(R.drawable.circle_shape_default);
        dataList.get(i).setPoint3(R.drawable.circle_shape_default);
        dataList.get(i).setLine1(R.drawable.line_shape_default);
        dataList.get(i).setLine2(R.drawable.line_shape_default);
        dataList.get(i).setLine3(R.drawable.line_shape_default);
    }

    private void addLineOnMap() {
        List<Overlay> mPolyline = mBaiduMap.addOverlays(mOverlayOptions);
        for (int i = 0; i < mPolyline.size(); i++) {
            //设置折线显示为虚线
            ((Polyline) mPolyline.get(i)).setDottedLine(true);
        }
    }
}