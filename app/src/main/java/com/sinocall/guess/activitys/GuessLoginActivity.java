package com.sinocall.guess.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.GuessApi;
import com.sinocall.guess.R;
import com.sinocall.guess.http.CckHttpClient;
import com.sinocall.guess.model.GuessLoginModel;
import com.sinocall.guess.model.SimPleModel;
import com.sinocall.guess.utils.Common;
import com.sinocall.guess.utils.RegexUtil;
import com.sinocall.guess.utils.SPUtils;
import com.sinocall.guess.utils.SystemUtils;
import com.sinocall.guess.utils.ToastUtils;
import com.sinocall.guess.view.MyEditText;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.xutils.common.Callback;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sinocall.guess.R.id.send_code;

/**
 * 登录页面
 * Created by Administrator on 2017/2/10.
 */

public class GuessLoginActivity extends BaseActivity {

    @BindView(R.id.user_phone)
    MyEditText userPhone;
    @BindView(R.id.user_code)
    MyEditText userCode;
    @BindView(send_code)
    TextView sendCode;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_linear)
    PercentLinearLayout loginLinear;
    @BindView(R.id.sino_login)
    ImageView sinoLogin;
    @BindView(R.id.weachat_login)
    ImageView weachatLogin;
    @BindView(R.id.qq_login)
    ImageView qqLogin;
    @BindView(R.id.login_user_agree)
    TextView loginUserAgree;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_activity_login);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        //点击空白处隐藏键盘
        loginLinear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideSoftInputView();
                return false;
            }
        });
    }

    private int platForm = 0;//用来记录用户登录的平台
    private SHARE_MEDIA loginPlat = null;

    @OnClick({send_code, R.id.login_btn, R.id.sino_login, R.id.weachat_login, R.id.qq_login,R.id.login_user_agree})
    public void onClick(View view) {
        switch (view.getId()) {
            case send_code:
                /*发送验证码*/
                sendCode();
                break;
            case R.id.login_btn:
                /*登录*/
                login();
                break;
            case R.id.sino_login:
                //新浪登录
                platForm = 2;
                loginPlat = SHARE_MEDIA.SINA;
                loginMethod();
                break;
            case R.id.weachat_login:
                //微信登录
                loginPlat = SHARE_MEDIA.WEIXIN;
                platForm = 1;
                loginMethod();
                break;
            case R.id.qq_login:
                //qq登录
                loginPlat = SHARE_MEDIA.QQ;
                platForm = 3;
                loginMethod();
                break;
            case R.id.login_user_agree:
                Bundle bundle=new Bundle();
                bundle.putString("name","用户服务协议");
                if(Common.initModel!=null){
                    bundle.putString("url",Common.initModel.getData().getUserAgreement());
                }else{
                    bundle.putString("url", (String) SPUtils.get(this,"userAgree",""));
                }
                SystemUtils.StartByParams(this,GuessWebViewActivity.class,bundle);
                break;
        }
    }

    private void loginMethod() {
        umShareAPI.doOauthVerify(this, loginPlat, umAuthListener);
    }

    //第三方登录回调
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> deviceInfo) {
            if (deviceInfo != null) {
                Bundle bundle = new Bundle();
                for (Map.Entry<String, String> map : deviceInfo.entrySet()) {
                    if (map.getKey().equals("openid")) {
                        bundle.putString("openId", map.getValue());
                    } else if (map.getKey().equals("uid") && loginPlat == SHARE_MEDIA.SINA) {
                        bundle.putString("openId", map.getValue());
                    }else if (map.getKey().equals("unionid") && loginPlat == SHARE_MEDIA.QQ) {
                        bundle.putString("unionId", map.getValue());
                    }
                    if (map.getKey().equals("access_token")) {
                        bundle.putString("accessToken", map.getValue());
                    }
                }
                bundle.putString("type", platForm + "");
                bundle.putString("oauthVersion", "2.0");
                if (!TextUtils.isEmpty(Common.cid)) bundle.putString("getuiToken", Common.cid);
                loginRequest(bundle);
            }

        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            ToastUtils.showToast(GuessLoginActivity.this, "授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            ToastUtils.showToast(GuessLoginActivity.this, "取消登录授权");
        }
    };


    /**
     * 第三方登录请求
     */
    private void loginRequest(Bundle bundle) {
        showLoaddingDialog("正在登录...");
        CckHttpClient.get(GuessApi.METHOD_LOGIN, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessLoginModel loginModel = JSON.parseObject(result, GuessLoginModel.class);
                dealData(loginModel, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessLoginActivity.this, ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dismissLoaddingDialog();
            }
        });
    }

    //代表验证码发送成功
    private boolean isSuccess = false;

    private void sendCode() {
        if (!RegexUtil.checkMobile(userPhone.getText().toString())) {
            ToastUtils.showToast(this, "手机格式不正确");
            return;
        }
        sendCode.setClickable(false);//设置为不可点击
        isSuccess = false;
        showLoaddingDialog("正在发送验证码");
        Bundle bundle = new Bundle();
        bundle.putString("mobile", userPhone.getText().toString());
        bundle.putString("type", 1 + "");
        CckHttpClient.get(GuessApi.SEND_CODE, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SimPleModel model = JSON.parseObject(result, SimPleModel.class);
                if (model.getErrorVo().getCode().equals("1000")) {
                    //发送成功
                    isSuccess = true;
                    ToastUtils.showToast(GuessLoginActivity.this, "验证码发送成功");
                    timeStep();//1分钟之后才可以再次发送验证码
                } else {
                    //发生错误
                    ToastUtils.showToast(GuessLoginActivity.this, model.getErrorVo().getMsg());
                    sendCode.setClickable(true);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessLoginActivity.this, ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dismissLoaddingDialog();
                if (!isSuccess) {
                    sendCode.setClickable(true);
                }
            }
        });
    }


    private void login() {
        if (!RegexUtil.checkMobile(userPhone.getText().toString())) {
            ToastUtils.showToast(this, "手机格式不正确");
            return;
        }
        if (TextUtils.isEmpty(userCode.getText().toString())) {
            ToastUtils.showToast(this, "验证码不能为空");
            return;
        }
        showLoaddingDialog("正在登录...");
        Bundle bundle = new Bundle();
        bundle.putString("mobile", userPhone.getText().toString());
        bundle.putString("validateCode", userCode.getText().toString());
        if (!TextUtils.isEmpty(Common.cid)) bundle.putString("getuiToken", Common.cid);
        bundle.putString("jpushTocken", "");
        CckHttpClient.get(GuessApi.LOGIN, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessLoginModel loginModel = JSON.parseObject(result, GuessLoginModel.class);
                dealData(loginModel, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessLoginActivity.this, ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dismissLoaddingDialog();
            }
        });
    }

    /**
     * 登录成功后处理逻辑
     *
     * @param loginModel
     * @param result
     */
    private void dealData(GuessLoginModel loginModel, String result) {
        if (TextUtils.equals(loginModel.getErrorVo().getCode(), "1000")) {
            //登录成功
            SPUtils.put(this, "userId", loginModel.getData().getUserId());//将用户id存储在本地
            SPUtils.put(this, "lId", loginModel.getData().getLId());//将登录信息存储在本地，表示已登录
             /*用户头像*/
            SPUtils.put(this, "headImage", loginModel.getData().getLogo());
            /*用户昵称*/
            SPUtils.put(this, "nickName", loginModel.getData().getNickName());
            //用户性别
            SPUtils.put(this, "gender", loginModel.getData().getGender());
            //用户绑定手机号码
            SPUtils.put(this, "mobile", loginModel.getData().getMobile());
            //用户是否已设置支付密码
            SPUtils.put(this, "password", loginModel.getData().getHasPayPass());
            startActivity(new Intent(this, MainActivity.class));
            if(platForm!=0){
                //第三方登录统计
                switch (platForm){
                    case 1:
                        MobclickAgent.onProfileSignIn("WX",loginModel.getData().getUserId()+"");
                        break;
                    case 2:
                        MobclickAgent.onProfileSignIn("WB",loginModel.getData().getUserId()+"");
                        break;
                    case 3:
                        MobclickAgent.onProfileSignIn("QQ",loginModel.getData().getUserId()+"");
                        break;
                }
            }else{
                //友盟统计自有账号
                MobclickAgent.onProfileSignIn(loginModel.getData().getUserId()+"");
            }
            this.finish();
        } else {
            getErrorMessage(result);
        }
    }

    /*倒计时*/
    private Timer timer;
    private TimerTask task;
    private int second;

    /**
     * 倒计时开始
     */
    private void timeStep() {
        timer = new Timer();
        second = 60;
        task = new TimerTask() {
            @Override
            public void run() {
                second--;
                Message message = new Message();
                message.obj = second;
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(task, 0, 1000);
    }

    /*handle处理倒计时*/
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int time = (int) msg.obj;
            if (time > 0) {
                sendCode.setText(time + "s");
            } else {
                sendCode.setText("发送验证码");
                sendCode.setClickable(true);
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                    task = null;
                }
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁正在处理的消息和线程,防止内存泄漏
        if (timer != null) {
            timer.cancel();
            timer = null;
            task = null;
        }
        handler.removeMessages(1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        umShareAPI.onActivityResult(requestCode, resultCode, data);
    }

}
