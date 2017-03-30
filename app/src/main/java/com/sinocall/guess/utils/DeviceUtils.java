package com.sinocall.guess.utils;

/**
 * Created by Administrator on 2016/6/22.
 */

public class DeviceUtils {
    //did  android:imei+"_"+androidid
    public static String did;
    //channelId  渠道ID
    public static String channelId;
    //操作系统：0 android，1 ios
    public static int os;
    //操作系统版本
    public static String osv;
    //软件版本
    public static String ver;
    //android:imei
    public static String deviceId;
    //mac地址
    public static String mac;
    //apn：WIFI热点名称
    public static String apn;
    //维度：取不到填0
    public static double lat;
    //经度：取不到填0
    public static double lon;
    // 网络类型：wifi/mobile/unicom
    public static String net;
    //屏幕分辨率
    public static String r;
    //手机品牌
    public static String brand;
    //用户ID：未登录设置 为0
    public static long userId;
    //标识登陆状态：未登录设置为空字符串
    public static String lId;
    //设备信息
    public static String ua;
    // API 版本号，为日期形式：YYYYMMDD
    public static String v = "20160616";
    //签名结果串，关于签名的计算方法，请参见签名机制
    public static String s;
    //请求的时间戳
    public static long t;
    //platform  0app；1微信；2微博；3QQ；4PC；5M
    public static int platform;



}
