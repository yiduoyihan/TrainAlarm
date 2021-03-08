package com.leidi.trainalarm.ui.main;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.leidi.trainalarm.BuildConfig;
import com.leidi.trainalarm.R;
import com.leidi.trainalarm.adapter.MainPagerAdapter;
import com.leidi.trainalarm.android.MPush;
import com.leidi.trainalarm.base.BaseActivity;
import com.leidi.trainalarm.bean.MainBean;
import com.leidi.trainalarm.notification.NotificationChannels;
import com.leidi.trainalarm.push.MyLog;
import com.leidi.trainalarm.ui.fm.HomeFragment;
import com.leidi.trainalarm.ui.fm.NotificationFragment;
import com.leidi.trainalarm.ui.fm.SelfFragment;
import com.leidi.trainalarm.util.AppUtil;
import com.leidi.trainalarm.util.CommonDialog;
import com.leidi.trainalarm.util.Constant;
import com.leidi.trainalarm.util.FcfrtAppBhUtils;
import com.leidi.trainalarm.util.SelfViewPager;
import com.mpush.client.ClientConfig;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * @author 阎
 * @date 2020/5/7
 */
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.vp_main)
    SelfViewPager viewPager;
    @BindView(R.id.rg_main_bottom)
    RadioGroup radioGroup;
    public CommonDialog dialog = null;
    PowerManager.WakeLock wl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void initView() {
        initRadioGroup();
        viewPager.addOnPageChangeListener(this);
        setupViewPager(viewPager);

        //去判断横幅是否打开
        checkBanner();

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
        wl.acquire();

        if (!FcfrtAppBhUtils.isIgnoringBatteryOptimizations(this)) {
            FcfrtAppBhUtils.requestIgnoreBatteryOptimizations(this);
        }
//        startPush();
    }

    private void checkBanner() {
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        //Android 8.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //CHANNEL_ID是自己定义的渠道ID
            NotificationChannel channel = mNotificationManager.getNotificationChannel(NotificationChannels.CRITICAL);
            //未开启
            if (channel.getImportance() == NotificationManager.IMPORTANCE_DEFAULT) {
                // 跳转到设置页面
                showEnsureDialog();
            }
        }
    }

    void showEnsureDialog() {
        dialog = new CommonDialog(this, R.layout.out_login_dialog);
        if (dialog.isShowing()) {
            return;
        }
        dialog.setTitle("请前往打开横幅通知")
                .setWight(true)
                .setNegative("取消").setPositive("确定")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                            intent.putExtra(Settings.EXTRA_CHANNEL_ID, NotificationChannels.CRITICAL);
                            startActivityForResult(intent, Constant.SUCCESS_CODE);
                        }
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();
                    }
                })
                .show();
        dialog.setCanceledOnTouchOutside(true);
    }


    public void startPush() {
        String allocServer = SPUtils.getInstance().getString(Constant.PUSH_URL);

        initPush(allocServer, "android_" + DeviceUtils.getAndroidID());

        MPush.I.checkInit(this.getApplication()).startPush();

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
//                .setServerHost(allocServer)
                .setDeviceId(deviceID)
                .setClientVersion(BuildConfig.VERSION_NAME)
                .setLogger(new MyLog())
                .setLogEnabled(BuildConfig.DEBUG)
                .setEnableHttpProxy(true)
                .setUserId(deviceID);

        MPush.I.checkInit(getApplicationContext()).setClientConfig(cc);
    }


    /**
     * Fragment和Viewpager联动
     */
    private void setupViewPager(SelfViewPager viewPager) {
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(HomeFragment.getInstance());
        adapter.addFragment(NotificationFragment.getInstance());
        adapter.addFragment(SelfFragment.getInstance());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

    }

    private void initRadioGroup() {
        viewPager.setOffscreenPageLimit(2);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_main_bottom1:
                    //首页
                    viewPager.setCurrentItem(0);
                    radioGroup.check(R.id.rb_main_bottom1);
                    break;
                case R.id.rb_main_bottom2:
                    //消息
                    viewPager.setCurrentItem(1);
                    radioGroup.check(R.id.rb_main_bottom2);
                    break;
                case R.id.rb_main_bottom3:
                    //我的
                    viewPager.setCurrentItem(2);
                    radioGroup.check(R.id.rb_main_bottom3);
                    break;
                default:
                    break;
            }
        });
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                radioGroup.check(R.id.rb_main_bottom1);
                break;
            case 1:
                radioGroup.check(R.id.rb_main_bottom2);
                break;
            case 2:
                radioGroup.check(R.id.rb_main_bottom3);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.removeOnPageChangeListener(this);
        if (wl != null) {
            wl.release();
            wl = null;
        }
        AppUtil.print("onDestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        AppUtil.print("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!MPush.I.hasRunning()) {
            //如果注册则直接启动。如果没注册则调用本页面中的startPush先注册再启动
            if (MPush.I.hasInit()) {
                AppUtil.print("onResume：startPush");
                MPush.I.checkInit(this.getApplication()).startPush();
            } else {
                MPush.I.stopPush();
                AppUtil.print("onResume：init+start Push");
                startPush();
            }
        } else {
            AppUtil.print("onResume：resumePush");
            MPush.I.resumePush();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        AppUtil.print("onStop");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDestroySelf(MainBean bean) {
        viewPager.clearOnPageChangeListeners();
        finish();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRestartPush(int flag) {
        if (flag == Constant.EVENT_RESTART_PUSH) {
            startPush();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 0) {
            if (dialog != null) {
                dialog.dismiss();
            }
        } else if (requestCode == 201) {
            AppUtil.print(String.valueOf(resultCode));
            if (!FcfrtAppBhUtils.isIgnoringBatteryOptimizations(this)) {
                AppUtil.print("已经同意忽略电池优化");
            }
        }
    }
}
