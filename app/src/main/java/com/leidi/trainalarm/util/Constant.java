package com.leidi.trainalarm.util;

import android.os.Environment;

/**
 * 常量参数
 * Created by YGQ on 2016/9/5 0005.
 */
public class Constant {

    public static final int EVENT_RESTART_PUSH = 0;

    public static final int PHONE_NUMBER_LENGTH = 11;
    public static final int MESSAGE_CAPTCHA = 6;
    public static final int PASSWORD_LENGTH = 6;
    public static final int ACCOUNT_LENGTH = 5;
    public static final int SUCCESS_CODE = 200;
    public static final int ONE_SECOND = 60;
    //定位间隔为10秒
    public static final int LOCATION_SPAN = 30 * 1000;

    /**
     * ------------------SharedPreference相关---------------------
     */
    public static final String TOKEN = "token";
    public static final String PASSWORD = "password";
    public static final String USERNAME = "userName";
    public static final String USER_ID = "user_id";
    public static final String PUSH_URL = "push_url";
    public static final String IS_LOGIN_SUCCESS = "is_first_login";
    public static final String EMPLOYEE_NAME = "employee_name";
    public static final String USER_FLAG = "user_flag";

    /*网络请求标志*/
    public static final String print = "=====print=====";

    /**
     * ---------------百度SDK参数相关--------------------
     */
    //可选，设置返回经纬度坐标类型，默认GCJ02
    //GCJ02：国测局坐标；
    //BD09ll：百度经纬度坐标；
    //BD09：百度墨卡托坐标；
    //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标
    public static final String LOCATION_TYPE = "bd09ll";

    public static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    public static String fileName = "location.txt";

    public static final String MSG_NOTIFICATION = "notice";
    public static final String MSG_HOME_REFUSH = "alarm";
    public static final String MSG_TRAIN_LOCATION = "train";
    public static final String MSG_CARD = "tab";
}
