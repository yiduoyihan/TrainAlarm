package com.leidi.trainalarm.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.leidi.trainalarm.R;
import com.leidi.trainalarm.bean.MsgBean;
import com.leidi.trainalarm.bean.NotificationEvent;
import com.leidi.trainalarm.bean.ReceiverBean;
import com.leidi.trainalarm.net.NetStateChangeObserver;
import com.leidi.trainalarm.net.NetStateChangeReceiver;
import com.leidi.trainalarm.net.NetworkType;
import com.leidi.trainalarm.notification.Notificaitons;
import com.leidi.trainalarm.util.AppUtil;
import com.leidi.trainalarm.util.Constant;
import com.leidi.trainalarm.util.ToastUtil;
import com.leidi.trainalarm.util.Url;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;

/**
 * @author yan
 * @description 基类
 */
public abstract class BaseActivity extends FragmentActivity implements TextToSpeech.OnInitListener, NetStateChangeObserver {
    @BindView(R.id.tv_title_left)
    ImageView tvTitleLeftButton;
    @BindView(R.id.tv_title_right)
    ImageView tvTitleRightButton;
    @BindView(R.id.tv_title_center)
    TextView tvTitleCenter;
    Bundle savedInstanceState;
    @BindView(R.id.tv_net_disconnection)
    TextView tvNetDisconnection;

    private TextToSpeech tts;
    private NotificationManager mNM;

    private List<MsgBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //注册网络变化监听
        NetStateChangeReceiver.registerReceiver(this);
        // 竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(getLayoutId());
        //创建时注册EventBus
        EventBus.getDefault().register(this);

        ButterKnife.bind(this);
        initView();

        tts = new TextToSpeech(this, this);
        //TTS最大可读长度为4000的字符串，一个文字算1，标点也算1.
    }

    /**
     * 对公共标题的初始化
     */
    protected void setToolbar(String str) {
        tvTitleCenter.setText(str);
    }

    /**
     * 控制标题显示的内容，左边按钮和右边按钮是否显示
     */
    protected void setToolbar(int left, String str, int right) {
        tvTitleLeftButton.setVisibility(left);
        tvTitleRightButton.setVisibility(right);
        tvTitleCenter.setText(str);
    }


    /**
     * Toast消息提示的方法
     */
    protected void showToast(String str) {
        ToastUtil.showToast(this, str);
    }

    protected void print(String msg) {
        AppUtil.print(msg);
    }


    protected Bundle getSavedInstanceState() {
        return savedInstanceState;
    }


    /**
     * 添加布局
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    @OnClick(R.id.tv_title_left)
    protected void onBackClick() {
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void showNotificationDialog(ReceiverBean bean) {
        if (tts == null) {
            tts = new TextToSpeech(this, this);
        }
        //解析我们自己写的布局
        @SuppressLint("InflateParams") View dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialog, null);
        //创建一个dialog
        final Dialog dialog = new AlertDialog.Builder(this).create();
        //此处dialog应该先show然后再加载布局，否则会报错
        dialog.show();
        dialog.setContentView(dialogView);
        //初始化控件
        ImageView mImage = (ImageView) dialogView.findViewById(R.id.dialog_title_image);
        TextView mTitle = (TextView) dialogView.findViewById(R.id.dialog_title_content);
        TextView mMessage = (TextView) dialogView.findViewById(R.id.dialog_message_text);
        MsgBean bean1 = new Gson().fromJson(bean.getMsg(), MsgBean.class);
        list.add(bean1);
        mMessage.setText(bean1.getAlarmMsg());
//        mTitle.setOnClickListener(v -> dialog.dismiss());
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitle.setEnabled(false);
                RxHttp.postForm(Url.callbackAlarm)
                        .add("trainMileage", list.get(list.size() - 1).getTrainMileage())
                        .add("direction", list.get(list.size() - 1).getAlarmMsg())
                        .add("lineNo", list.get(list.size() - 1).getLineNo())
                        .add("trainNo", list.get(list.size() - 1).getTrainNo())
                        .add("alarmLevel", list.get(list.size() - 1).getAlarmLevel())
                        .add("alarmDistance", list.get(list.size() - 1).getDistance())
                        .add("token", SPUtils.getInstance().getString(Constant.TOKEN))
                        .add("deviceNo", DeviceUtils.getAndroidID())
                        .asClass(BaseBean.class)
                        .subscribe(s -> {
                            if (s.getCode() == 200) {
                                print("关闭成功");
                            } else {
                                print("关闭失败");
                            }
                            list.remove(list.size() - 1);
                            dialog.dismiss();
                            mTitle.setEnabled(true);

                        });

            }
        });
        dialog.setCanceledOnTouchOutside(false);
        if (bean.getType().equals(Constant.MSG_NOTIFICATION)) {
            //如果是公告的话，不执行下面文字转语音的过程
            Notificaitons.getInstance().sendMessagingStyleNotification(
                    bean1, this, (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(TextToSpeech.Engine.KEY_PARAM_VOLUME, "1");
        tts.speak(AppUtil.numberToChinese(bean1.getAlarmMsg()), TextToSpeech.QUEUE_ADD, bundle, "只读");
        AppUtil.print("tts度过了");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //页面销毁时必须解除绑定
        EventBus.getDefault().unregister(this);
        tts.stop();
        tts.shutdown();
        NetStateChangeReceiver.unRegisterReceiver(this);
        AppUtils.unregisterAppStatusChangedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetStateChangeReceiver.registerObserver(this);
    }

    /**
     * 接收其他页面发送的弹出通知的提示，弹出通知的dialog
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(ReceiverBean message) {
        showNotificationDialog(message);
    }

    @Override
    public void onInit(int status) {
        // 判断是否转化成功
        if (status == TextToSpeech.SUCCESS) {
            //默认设定语言为中文，原生的android貌似不支持中文。
            int result = tts.setLanguage(Locale.CHINESE);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "不支持的语言", Toast.LENGTH_SHORT).show();

            } else {
                //不支持中文就将语言设置为英文
                //正常不支持走上面选项不会走到这里来
                tts.setLanguage(Locale.US);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onNetDisconnected() {
        EventBus.getDefault().post("当前网络不可用，请检查您的网络连接");
    }

    @Override
    public void onNetConnected(NetworkType networkType) {
        EventBus.getDefault().post("网络已连接");
        EventBus.getDefault().post(Constant.EVENT_RESTART_PUSH);
    }
}
