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

import com.jungly.gridpasswordview.GridPasswordView;
import com.sinocall.guess.R;
import com.sinocall.guess.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 设置支付密码和修改支付密码
 * Created by Administrator on 2016/7/4.
 */

public class GuessPayPassUtils extends Dialog {


    @BindView(R.id.close_paypass_dialog)
    ImageView closePaypassDialog;
    @BindView(R.id.paypass_dialog_center_text)
    TextView paypassDialogCenterText;
    @BindView(R.id.paypass_done_btn)
    TextView paypassDoneBtn;
    @BindView(R.id.pay_text_top)
    TextView payTextTop;
    @BindView(R.id.grid_one)
    GridPasswordView gridOne;
    @BindView(R.id.pay_text_bottom)
    TextView payTextBottom;
    @BindView(R.id.grid_two)
    GridPasswordView gridTwo;
    private WindowManager.LayoutParams wl;

    public GuessPayPassUtils(Context context, View.OnClickListener onClickListener) {
        super(context, R.style.super_dialog);
        //加载布局
        setContentView(R.layout.guess_dialog_pay_pass);
        ButterKnife.bind(this);
        Window window = getWindow();
        paypassDoneBtn.setOnClickListener(onClickListener);
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

    public void setType(int has){
        if(has==0){
            paypassDialogCenterText.setText("设置支付密码");
            payTextTop.setVisibility(View.GONE);
            payTextBottom.setText("再次输入密码");
        }else{
            paypassDialogCenterText.setText("修改支付密码");
            payTextTop.setVisibility(View.VISIBLE);
            payTextTop.setText("原支付密码");
            payTextBottom.setText("输入新密码");
        }
    }

    /**
     * 获取第一个密码框的密码
     * @return
     */
    public String getPassOne(){
        return gridOne.getPassWord();
    }

    /**
     * 获取第二个密码框的密码
     * @return
     */
    public String getPassTwo(){
        return gridTwo.getPassWord();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
        gridOne.clearPassword();
        gridTwo.clearPassword();
    }


    @OnClick(R.id.close_paypass_dialog)
    public void onClick() {
        dismiss();
    }
}
