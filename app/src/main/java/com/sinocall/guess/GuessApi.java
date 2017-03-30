package com.sinocall.guess;

/**
 * Created by Administrator on 2017/2/14.
 */

public class GuessApi {
    //private static final String BASE_URL="http://192.168.10.200:8088/api";//200服务器
    //private static final String BASE_URL="http://192.168.20.13:8080/api";//后台本机
    private static final String BASE_URL="http://www.4008365777.com/api";//线上环境
    /*发送手机验证码*/
    public  static  final String SEND_CODE=BASE_URL+"/common/sendSms";
    /*登录*/
    public  static  final String LOGIN=BASE_URL+"/user/login";
    /*获取用户信息*/
    public  static  final String USER_INFO=BASE_URL+"/user/userInfo";
    /*首页列表数据*/
    public  static  final String MAIN_LIST=BASE_URL+"/sysChildTopic/list";
    /*充值中心*/
    public  static  final String PAY_CENTER=BASE_URL+"/userCharge/chargeCenter";
    /*支付接口*/
    public  static  final String PAY=BASE_URL+"/userCharge/payInit";
    /*我参与的*/
    public  static  final String MINE_JOIN=BASE_URL+"/stTakePart/takePartTopic";
    /*我发布的*/
    public  static  final String MINE_RELEASE=BASE_URL+"/sysChildTopic/createList";
    /*是否有新建盘口权限*/
    public  static  final String HAVE_POWER=BASE_URL+"/sysPrivilege/enableCreate";
    /*购买参与次数、额度、发布次数*/
    public  static  final String BUY_SERVICE=BASE_URL+"/sysExchange/buy";
    /*兑换明细*/
    public  static  final String CONVERT_DETAILS=BASE_URL+"/userExchange/list";
    /*设置*/
    public  static  final String MINE_SETTINGS=BASE_URL+"/user/update";
    /*我的流水*/
    public  static  final String MINE_WATER=BASE_URL+"/userTakePart/takePartList";
    /*登出*/
    public  static  final String LOGIN_OUT=BASE_URL+"/user/logout";
    /*上传图片*/
    public  static  final String UPLOAD_IMAGE=BASE_URL+"/user/updateLogo";
    /*修改密码*/
    public  static  final String UPDATE_PASSWORD=BASE_URL+"/user/updatePayPassword";
    /*会员中心*/
    public  static  final String VIP_CENTER=BASE_URL+"/user/privilege";
    /*会员等级数据请求*/
    public  static  final String VIP_CENTER_DATA=BASE_URL+"/sysPrivilege/levelDetail";
    /*会员等级数据请求*/
    public  static  final String LABLE_LIST=BASE_URL+"/sysTopicTag/list";
    /*下注额度列表*/
    public  static  final String AWARD_LIST=BASE_URL+"/sysPrivilege/topicLimitList";
    /*3.17.	参与次数、额度、发布次数购买选项*/
    public  static  final String SELECT_LIST=BASE_URL+"/sysExchange/list";
    /*新建盘口*/
    public  static  final String CREATE_NEW=BASE_URL+"/sysChildTopic/create";
    /*充值明细*/
    public  static  final String PAY_DETAILS=BASE_URL+"/userIncreaseConsume/chargeList";
    /*下注*/
    public  static  final String MAIN_CACHE=BASE_URL+"/userTakePart/takePart";
    /*第三方登录*/
    public static  final String METHOD_LOGIN=BASE_URL+"/user/thirdLogin";
    /*举报列表*/
    public static  final String REPORT_LIST=BASE_URL+"/sysReport/list";
    /*举报*/
    public static  final String REPORT=BASE_URL+"/sysReport/report";
    /*初始化配置*/
    public static  final String INIT=BASE_URL+"/common/initConfig";
    /*开奖盘口详情*/
    public static  final String HANDICAP_DETAILS=BASE_URL+"/sysChildTopic/historyTopicDetail";
    /*分享成功之后回调*/
    public static  final String SHARE_SUCCESS=BASE_URL+"/userPoint/share";
    /*充值之后回调*/
    public static  final String PAY_SUCCESS=BASE_URL+"/userCharge/check";
    /*盘缠余额*/
    public static  final String MONEY_LESS=BASE_URL+"/user/refresh";
}
