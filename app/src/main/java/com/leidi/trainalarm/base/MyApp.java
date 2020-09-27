package com.leidi.trainalarm.base;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.Gravity;

import androidx.annotation.RequiresApi;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.fanjun.keeplive.KeepLive;
import com.fanjun.keeplive.config.ForegroundNotification;
import com.fanjun.keeplive.config.ForegroundNotificationClickListener;
import com.fanjun.keeplive.config.KeepLiveService;
import com.leidi.trainalarm.BuildConfig;
import com.leidi.trainalarm.R;
import com.leidi.trainalarm.android.MPush;
import com.leidi.trainalarm.notification.Notificaitons;
import com.leidi.trainalarm.notification.NotificationChannels;
import com.leidi.trainalarm.push.MyLog;
import com.leidi.trainalarm.ui.login.LoginActivity;
import com.leidi.trainalarm.ui.main.MainActivity;
import com.leidi.trainalarm.util.AppUtil;
import com.leidi.trainalarm.util.Constant;
import com.leidi.trainalarm.util.FcfrtAppBhUtils;
import com.mpush.client.ClientConfig;

import rxhttp.wrapper.param.RxHttp;

import static com.leidi.trainalarm.notification.Notificaitons.NOTIFICATION_MEDIA_STYLE;

/**
 * @author 阎
 * @date 2020/5/7
 */
public class MyApp extends Application {


    public static Application instance;
    private NotificationManager mNM;

    public static Application getInstance() {
        return instance;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        SDKInitializer.initialize(getApplicationContext());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        RxHttp.init(AppUtil.getDefaultOkHttpClient(), true);

        NotificationChannels.createAllNotificationChannels(this);

        if (FcfrtAppBhUtils.isHuawei()) {
            if (mNM == null) {
                mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            }
            AppUtils.registerAppStatusChangedListener(this, listener);
        } else {
            //保活
            keepLive();
        }
    }

    Utils.OnAppStatusChangedListener listener = new Utils.OnAppStatusChangedListener() {
        @Override
        public void onForeground() {
            Notificaitons.getInstance().clearOneNotification(mNM, NOTIFICATION_MEDIA_STYLE);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onBackground() {
            Notificaitons.getInstance().sendMediaStyleNotification(
                    MyApp.getInstance(), mNM, "列车接近告警", "程序正在后台运行", true);
        }
    };

    private void keepLive() {
        //定义前台服务的默认样式。即标题、描述和图标
        ForegroundNotification foregroundNotification = new ForegroundNotification("列车接近靠近系统", "后台运行中", R.mipmap.icon,
                //定义前台服务的通知点击事件
                new ForegroundNotificationClickListener() {

                    @Override
                    public void foregroundNotificationClick(Context context, Intent intent) {
                        if (SPUtils.getInstance().getBoolean(Constant.IS_LOGIN_SUCCESS, false)) {
                            intent.setClass(context, LoginActivity.class);
                        } else {
                            intent.setClass(context, MainActivity.class);
                        }
                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                        intent.setAction(Intent.ACTION_MAIN);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        startActivity(intent);
                    }
                });
        //启动保活服务
        KeepLive.startWork(this, KeepLive.RunMode.ROGUE, foregroundNotification,
                //你需要保活的服务，如socket连接、定时任务等，建议不用匿名内部类的方式在这里写
                new KeepLiveService() {
                    /**
                     * 运行中
                     * 由于服务可能会多次自动启动，该方法可能重复调用
                     */
                    @Override
                    public void onWorking() {
                        AppUtil.print("keepLive: onWorking");
                        if(!SPUtils.getInstance().getBoolean(Constant.IS_LOGIN_SUCCESS,false)){
                            return;
                        }
                        if (!MPush.I.hasRunning()) {
                            //如果注册则直接启动。如果没注册则调用本页面中的startPush先注册再启动
                            if (MPush.I.hasInit()) {
                                AppUtil.print("keepLive：startPush");
                                MPush.I.checkInit(MyApp.this).startPush();
                            } else {
                                MPush.I.stopPush();
                                AppUtil.print("keepLive：init+start Push");
                                startPush();
                            }
                        } else {
                            AppUtil.print("推送服务已经运行了");
                        }
                    }

                    /**
                     * 服务终止
                     * 由于服务可能会被多次终止，该方法可能重复调用，需同onWorking配套使用，如注册和注销broadcast
                     */
                    @Override
                    public void onStop() {
                        AppUtil.print("keepLive: stop");
                        MPush.I.pausePush();
                        MPush.I.unbindAccount();
                        MPush.I.stopPush();
                    }
                }
        );
    }

    public void startPush() {
        String allocServer = SPUtils.getInstance().getString(Constant.PUSH_URL);
        if (allocServer.length() < 10) {
            return;
        }
        initPush(allocServer, "android_" + DeviceUtils.getAndroidID());

        MPush.I.checkInit(this).startPush();

        AppUtil.print(allocServer + "   android_" + DeviceUtils.getAndroidID());
    }

    private void initPush(String allocServer, String deviceID) {
        //绑定用户
        MPush.I.bindAccount(deviceID, "mpush:" + (int) (Math.random() * 10));
        //公钥有服务端提供和私钥对应
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCghPCWCobG8nTD24juwSVataW7iViRxcTkey/B792VZEhuHjQvA3cAJgx2Lv8GnX8NIoShZtoCg3Cx6ecs+VEPD2fBcg2L4JK7xldGpOJ3ONEAyVsLOttXZtNXvyDZRijiErQALMTorcgi79M5uVX9/jMv2Ggb2XAeZhlLD28fHwIDAQAB";

        ClientConfig cc = ClientConfig.build()
                .setPublicKey(publicKey)
                .setAllotServer(allocServer)
                .setDeviceId(deviceID)
                .setClientVersion(BuildConfig.VERSION_NAME)
                .setLogger(new MyLog())
                .setLogEnabled(BuildConfig.DEBUG)
                .setEnableHttpProxy(true)
                .setUserId(deviceID);

        MPush.I.checkInit(getApplicationContext()).setClientConfig(cc);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}

