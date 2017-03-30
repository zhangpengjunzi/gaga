package com.sinocall.guess.activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.GuessApi;
import com.sinocall.guess.R;
import com.sinocall.guess.dialog.GuessBindPhoneUtils;
import com.sinocall.guess.dialog.GuessPayPassUtils;
import com.sinocall.guess.dialog.GuessQuestionUtils;
import com.sinocall.guess.dialog.GuessResetNameUtils;
import com.sinocall.guess.dialog.GuessSelectUtils;
import com.sinocall.guess.http.CckHttpClient;
import com.sinocall.guess.model.GuessUploadModel;
import com.sinocall.guess.model.SimPleModel;
import com.sinocall.guess.utils.Common;
import com.sinocall.guess.utils.DESUtils;
import com.sinocall.guess.utils.MD5Utils;
import com.sinocall.guess.utils.RegexUtil;
import com.sinocall.guess.utils.SPUtils;
import com.sinocall.guess.utils.ToastUtils;
import com.sinocall.guess.view.CircleImageView;
import com.zhy.android.percent.support.PercentFrameLayout;

import org.xutils.common.Callback;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sinocall.guess.utils.Common.HEAD_PATH;



/**
 * 个人设置
 * Created by Administrator on 2017/2/21.
 */

public class GuessMineSettingsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.user_head_image)
    CircleImageView userHeadImage;
    @BindView(R.id.user_head_frame)
    PercentFrameLayout userHeadFrame;
    @BindView(R.id.user_nickname_text)
    TextView userNicknameText;
    @BindView(R.id.user_nickname_frame)
    PercentFrameLayout userNicknameFrame;
    @BindView(R.id.user_gender_text)
    TextView userGenderText;
    @BindView(R.id.user_gender_frame)
    PercentFrameLayout userGenderFrame;
    @BindView(R.id.user_phone_text)
    TextView userPhoneText;
    @BindView(R.id.user_phone_frame)
    PercentFrameLayout userPhoneFrame;
    @BindView(R.id.user_paypass_text)
    TextView userPaypassText;
    @BindView(R.id.user_paypass_frame)
    PercentFrameLayout userPaypassFrame;
    @BindView(R.id.about_us_frame)
    PercentFrameLayout aboutUsFrame;
    @BindView(R.id.out_login_frame)
    PercentFrameLayout outLoginFrame;

    //是否退出登录
    private GuessQuestionUtils questionUtils;
    //选择性别
    private GuessSelectUtils selectUtils;
    //绑定手机
    private GuessBindPhoneUtils phoneUtils;
    //支付密码
    private GuessPayPassUtils payPassUtils;
    //修改昵称dialog
    private GuessResetNameUtils resetNameUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_activity_mine_settings);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initTitle("设置","");
        //设置裁剪类型
        cropType=2;
    }


    @Override
    protected void onResume() {
        super.onResume();
        //头像
        if (!SPUtils.get(this, "headImage", "").equals("")) {
            Glide.with(this).load((String) SPUtils.get(this, "headImage", "")).into(userHeadImage);
        } else {
            Glide.with(this).load(R.mipmap.avatar_set).into(userHeadImage);
        }
        //用户昵称
        if (!TextUtils.isEmpty((String)SPUtils.get(this, "nickName", ""))) {
            userNicknameText.setText((String) SPUtils.get(this, "nickName", ""));
            userNicknameText.setTextColor(getResources().getColor(R.color.actionbar_color));
        } else {
            userNicknameText.setTextColor(getResources().getColor(R.color.none_settings));
            userNicknameText.setText("未设置");
        }
        //性别
        if ((Integer) SPUtils.get(this, "gender", 0) == 1) {
            userGenderText.setText("男");
            userGenderText.setTextColor(getResources().getColor(R.color.actionbar_color));
        } else if ((Integer) SPUtils.get(this, "gender", 0) == 2) {
            userGenderText.setText("女");
            userGenderText.setTextColor(getResources().getColor(R.color.actionbar_color));
        } else {
            userGenderText.setText("未设置");
            userGenderText.setTextColor(getResources().getColor(R.color.none_settings));
        }
        //绑定手机号码
        if (!SPUtils.get(this, "mobile", "").equals("")) {
            userPhoneText.setText((String) SPUtils.get(this, "mobile", ""));
            userPhoneText.setTextColor(getResources().getColor(R.color.actionbar_color));
        } else {
            userPhoneText.setText("未绑定");
            userPhoneText.setTextColor(getResources().getColor(R.color.none_settings));
        }
        //支付密码
        if ((Integer) SPUtils.get(this, "password", 0) == 1) {
            userPaypassText.setText("已设置");
            userPaypassText.setTextColor(getResources().getColor(R.color.actionbar_color));
        } else {
            userPaypassText.setText("未设置");
            userPaypassText.setTextColor(getResources().getColor(R.color.none_settings));
        }
    }

    private int select = 0;//当前选择的是性别还是头像

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.user_head_frame, R.id.user_nickname_frame, R.id.user_gender_frame, R.id.user_phone_frame, R.id.user_paypass_frame, R.id.about_us_frame, R.id.out_login_frame})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_head_frame:
                //设置头像方法
                if (selectUtils == null) {
                    selectUtils = new GuessSelectUtils(this, this);
                }
                selectUtils.setSelectContent("拍摄", "从手机相册选择");
                selectUtils.show();
                select = 1;
                break;
            case R.id.user_nickname_frame:
                //修改昵称
                if (resetNameUtils == null) {
                    resetNameUtils = new GuessResetNameUtils(this, this);
                }
                if (userNicknameText.getText().toString().equals("未设置")) {
                    resetNameUtils.showResetDiaolog("");
                } else {
                    resetNameUtils.showResetDiaolog(userNicknameText.getText().toString());
                }
                break;
            case R.id.nick_done_btn:
                //确定修改昵称
                if (TextUtils.isEmpty(resetNameUtils.getName().trim())) {
                    ToastUtils.showToast(this, "昵称不能为空");
                    return;
                }
                if (resetNameUtils.getName().trim().length()>6) {
                    ToastUtils.showToast(this, "昵称长度不能大于6");
                    return;
                }
                resetNameUtils.dismiss();
                updateUserInfo(resetNameUtils.getName().trim(), 0, "", "", "");
                break;
            case R.id.user_gender_frame:
                //选择男女
                if (selectUtils == null) {
                    selectUtils = new GuessSelectUtils(this, this);
                }
                selectUtils.setSelectContent("男", "女");
                selectUtils.show();
                select = 2;
                break;
            case R.id.user_phone_frame:
                //绑定手机号码
                if (!userPhoneText.getText().equals("未绑定")) {
                    ToastUtils.showToast(this, "暂不支持修改绑定手机操作");
                    return;//暂无提供修改绑定手机操作
                }
                phoneUtils = new GuessBindPhoneUtils(this, this);
                phoneUtils.show();
                break;
            case R.id.dialog_send_code:
                //发送验证码
                sendCode();
                break;
            case R.id.dialog_done_btn:
                //绑定手机号码
                if (TextUtils.isEmpty(phoneUtils.getPhoneNumber())) {
                    ToastUtils.showToast(this, "手机号码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(phoneUtils.getCode())) {
                    ToastUtils.showToast(this, "验证码不能为空");
                    return;
                }
                phoneUtils.dismiss();
                updateUserInfo("", 0, phoneUtils.getPhoneNumber(), "", phoneUtils.getCode());
                break;
            case R.id.user_paypass_frame:
                //设置支付密码
                payPassUtils = new GuessPayPassUtils(this, this);
                payPassUtils.setType((Integer) SPUtils.get(this, "password", 0));
                payPassUtils.show();
                break;
            case R.id.paypass_done_btn:
                //支付密码
                if (TextUtils.isEmpty(payPassUtils.getPassOne()) || TextUtils.isEmpty(payPassUtils.getPassTwo()) || payPassUtils.getPassTwo().length() < 6 || payPassUtils.getPassOne().length() < 6) {
                    ToastUtils.showToast(this, "密码格式不正确");
                    return;
                }

                if ((Integer) SPUtils.get(this, "password", 0) == 0) {
                    //设置支付密码
                    if (!TextUtils.equals(payPassUtils.getPassOne(), payPassUtils.getPassTwo())) {
                        ToastUtils.showToast(this, "两次密码输入不一致");
                        return;
                    }
                    payPassUtils.dismiss();
                    updateUserInfo("", 0, "", payPassUtils.getPassTwo(), "");
                } else {
                    //修改支付密码
                    payPassUtils.dismiss();
                    updatePayPassword(payPassUtils.getPassTwo(), payPassUtils.getPassOne());
                }
                break;
            case R.id.about_us_frame:
                startActivity(new Intent(this, GuessAboutUsActivity.class));
                break;
            case R.id.out_login_frame:
                if (questionUtils == null) {
                    questionUtils = new GuessQuestionUtils(this, this);
                }
                questionUtils.setTitle("确认退出登录吗？");
                questionUtils.show();
                break;
            case R.id.btn_cancle:
                questionUtils.dismiss();
                break;
            case R.id.btn_sure:
                questionUtils.dismiss();
                //执行退出登录
                loginOut();
                break;
            case R.id.select_one:
                selectUtils.dismiss();
                if (select == 1) {
                    selectPhoto(this);
                } else {
                    updateUserInfo("", 1, "", "", "");
                }
                break;
            case R.id.select_two:
                selectUtils.dismiss();
                if (select == 1) {
                    //选择本地图库选择
                    pickFromGallery(this);
                } else {
                    updateUserInfo("", 2, "", "", "");
                }
                break;
        }
    }

    @Override
    public void cropAfter() {
        super.cropAfter();
         /*上传图片*/
        uploadHeadImage(getFilesDir()+"/"+HEAD_PATH);
    }

    private void uploadHeadImage(String photoPath) {
        showLoaddingDialog("正在上传头像图片...");
        CckHttpClient.post(GuessApi.UPLOAD_IMAGE, null, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessUploadModel upload = JSON.parseObject(result, GuessUploadModel.class);
                dealData(upload, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessMineSettingsActivity.this, ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dismissLoaddingDialog();
            }
        }, photoPath);
    }

    /**
     * 处理请求数据
     *
     * @param upload
     * @param result
     */
    private void dealData(GuessUploadModel upload, String result) {
        if (upload.getErrorVo().getCode().equals("1000")) {
            /*赋值图片*/
            ToastUtils.showToast(this, "图片上传成功");
            Glide.with(this)
                    .load(upload.getData().getLogo())
                    .into(userHeadImage);
            //将图片保存信息保存在本地
            SPUtils.put(this, "headImage", upload.getData().getLogo());
        } else {
            getErrorMessage(result);
        }
    }


    /**
     * 采取单个修改策略，其他元素不修改时，设置为空
     *
     * @param nickName
     * @param gender
     * @param mobile
     * @param payPassword
     * @param code
     */
    private void updateUserInfo(final String nickName, final int gender, final String mobile, final String payPassword, final String code) {
        showLoaddingDialog("正在更新用户信息...");
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(nickName)) bundle.putString("nickName", nickName);
        if (gender != 0) bundle.putString("gender", gender + "");
        if (!TextUtils.isEmpty(mobile)) bundle.putString("mobile", mobile);
        if (!TextUtils.isEmpty(payPassword)){
            try {
                bundle.putString("payPassword", DESUtils.encryptDES(MD5Utils.hexdigest(payPassword), Common.DES_KEY));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(code)) bundle.putString("code", code);
        CckHttpClient.get(GuessApi.MINE_SETTINGS, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SimPleModel model = JSON.parseObject(result, SimPleModel.class);
                if (model.getErrorVo().getCode().equals("1000")) {
                    //昵称
                    if (!TextUtils.isEmpty(nickName)) {
                        ToastUtils.showToast(GuessMineSettingsActivity.this, "昵称已修改");
                        SPUtils.put(GuessMineSettingsActivity.this, "nickName", nickName);
                        userNicknameText.setText(nickName);
                        userNicknameText.setTextColor(getResources().getColor(R.color.actionbar_color));
                    }
                    //性别
                    if (gender != 0) {
                        ToastUtils.showToast(GuessMineSettingsActivity.this, "性别已修改");
                        SPUtils.put(GuessMineSettingsActivity.this, "gender", gender);
                        if (gender == 1) {
                            userGenderText.setText("男");
                        } else {
                            userGenderText.setText("女");
                        }
                        userGenderText.setTextColor(getResources().getColor(R.color.actionbar_color));
                    }
                    //手机
                    if (!TextUtils.isEmpty(mobile)) {
                        ToastUtils.showToast(GuessMineSettingsActivity.this, "绑定手机号码成功");
                        SPUtils.put(GuessMineSettingsActivity.this, "mobile", mobile);
                        userPhoneText.setText(mobile);
                        userPhoneText.setTextColor(getResources().getColor(R.color.actionbar_color));
                    }
                    //支付密码
                    if (!TextUtils.isEmpty(payPassword)) {
                        ToastUtils.showToast(GuessMineSettingsActivity.this, "支付密码设置成功");
                        SPUtils.put(GuessMineSettingsActivity.this, "password", 1);
                        userPaypassText.setText("已设置");
                        userPaypassText.setTextColor(getResources().getColor(R.color.actionbar_color));
                    }
                } else {
                    ToastUtils.showToast(GuessMineSettingsActivity.this, model.getErrorVo().getMsg());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessMineSettingsActivity.this, ex.getMessage());
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

    private void updatePayPassword(String paypass, String oldpass) {
        showLoaddingDialog("正在修改密码...");
        Bundle bundle = new Bundle();
        try {
            bundle.putString("payPassword", DESUtils.encryptDES(MD5Utils.hexdigest(paypass), Common.DES_KEY));
            bundle.putString("oldPayPassword", DESUtils.encryptDES(MD5Utils.hexdigest(oldpass), Common.DES_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        CckHttpClient.get(GuessApi.UPDATE_PASSWORD, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SimPleModel simPleModel = JSON.parseObject(result, SimPleModel.class);
                if (simPleModel.getErrorVo().getCode().equals("1000")) {
                    ToastUtils.showToast(GuessMineSettingsActivity.this, "密码修改成功");
                } else {
                    ToastUtils.showToast(GuessMineSettingsActivity.this, simPleModel.getErrorVo().getMsg());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessMineSettingsActivity.this, ex.getMessage());
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
            if (phoneUtils != null) {
                int time = (int) msg.obj;
                if (time > 0) {
                    phoneUtils.codeText().setText(time + "s");
                } else {
                    phoneUtils.codeText().setText("发送验证码");
                    phoneUtils.codeText().setClickable(true);
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                        task = null;
                    }
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

    private boolean isSuccess;

    /**
     * 发送验证码
     */
    private void sendCode() {
        if (!RegexUtil.checkMobile(phoneUtils.getPhoneNumber())) {
            ToastUtils.showToast(this, "手机格式不正确");
            return;
        }
        showLoaddingDialog("正在发送验证码...");
        Bundle bundle = new Bundle();
        bundle.putString("mobile", phoneUtils.getPhoneNumber());
        bundle.putString("type", 2 + "");
        isSuccess=false;
        phoneUtils.codeText().setClickable(false);
        CckHttpClient.get(GuessApi.SEND_CODE, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SimPleModel model = JSON.parseObject(result, SimPleModel.class);
                if (model.getErrorVo().getCode().equals("1000")) {
                    //发送成功
                    ToastUtils.showToast(GuessMineSettingsActivity.this, "验证码发送成功");
                    timeStep();//1分钟之后才可以再次发送验证码
                    isSuccess=true;
                } else {
                    //发生错误
                    ToastUtils.showToast(GuessMineSettingsActivity.this, model.getErrorVo().getMsg());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessMineSettingsActivity.this, ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dismissLoaddingDialog();
                if(!isSuccess){
                    phoneUtils.codeText().setClickable(true);
                }
                showSoftInputView();//弹出软键盘
            }
        });
    }


    /**
     * 退出登录
     */
    private void loginOut() {
        showLoaddingDialog("正在退出登录...");
        CckHttpClient.get(GuessApi.LOGIN_OUT, null, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SimPleModel simPleModel = JSON.parseObject(result, SimPleModel.class);
                if (simPleModel.getErrorVo().getCode().equals("1000")) {
                    SPUtils.put(GuessMineSettingsActivity.this, "userId", 0);//将用户id存储在本地
                    SPUtils.put(GuessMineSettingsActivity.this, "lId", "");//将登录信息存储在本地，表示已登录
                    SPUtils.put(GuessMineSettingsActivity.this, "headImage", "");
                    SPUtils.put(GuessMineSettingsActivity.this, "nickName", "");
                    SPUtils.put(GuessMineSettingsActivity.this, "gender", 0);
                    SPUtils.put(GuessMineSettingsActivity.this, "mobile", "");
                    SPUtils.put(GuessMineSettingsActivity.this, "password", "");
                    Intent intent = new Intent(GuessMineSettingsActivity.this, GuessLoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    GuessMineSettingsActivity.this.finish();
                } else {
                    ToastUtils.showToast(GuessMineSettingsActivity.this, simPleModel.getErrorVo().getMsg());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessMineSettingsActivity.this, ex.getMessage());
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
}
