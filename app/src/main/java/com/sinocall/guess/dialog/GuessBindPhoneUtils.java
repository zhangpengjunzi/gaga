package com.sinocall.guess.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinocall.guess.R;
import com.sinocall.guess.utils.ScreenUtils;
import com.sinocall.guess.view.MyEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 绑定手机号码
 * Created by Administrator on 2016/7/4.
 */

public class GuessBindPhoneUtils extends Dialog {


    @BindView(R.id.close_phone_dialog)
    ImageView closePhoneDialog;
    @BindView(R.id.phone_dialog_center_text)
    TextView phoneDialogCenterText;
    @BindView(R.id.dialog_done_btn)
    TextView dialogDoneBtn;
    @BindView(R.id.dialog_phone_number)
    MyEditText dialogPhoneNumber;
    @BindView(R.id.dialog_phone_code)
    MyEditText dialogPhoneCode;
    @BindView(R.id.dialog_send_code)
    TextView dialogSendCode;
    private WindowManager.LayoutParams wl;

    public GuessBindPhoneUtils(Context context, View.OnClickListener onClickListener) {
        super(context, R.style.super_dialog);
        //加载布局
        setContentView(R.layout.guess_dialog_bind_phone);
        ButterKnife.bind(this);
        dialogDoneBtn.setOnClickListener(onClickListener);
        dialogSendCode.setOnClickListener(onClickListener);
        Window window = getWindow();
        //设置dialog自动弹出键盘
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setGravity(Gravity.BOTTOM);
        //设置窗体动画
        window.setWindowAnimations(R.style.super_anim_dialog);
        wl = window.getAttributes();
        wl.width = ScreenUtils.getWindowWidth(context);
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        setCanceledOnTouchOutside(true);
    }

    /**
     * 获取发送验证码控件
     */
    public TextView codeText(){
        return dialogSendCode;
    }

    /**
     * 设置顶部标题
     * @param titleText
     */
    public void setTitleText(String titleText){
        phoneDialogCenterText.setText(titleText);
    }

    /**
     * 获取用户输入手机号码
     * @return
     */
    public String getPhoneNumber(){
        return dialogPhoneNumber.getText().toString();
    }

    /**
     * 获取用户输入验证码
     * @return
     */
    public String getCode(){
        return dialogPhoneCode.getText().toString();
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
    }


    @OnClick({R.id.close_phone_dialog, R.id.dialog_send_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_phone_dialog:
                dismiss();
                break;
            case R.id.dialog_send_code:
                //发送验证码
                break;
        }
    }
}
