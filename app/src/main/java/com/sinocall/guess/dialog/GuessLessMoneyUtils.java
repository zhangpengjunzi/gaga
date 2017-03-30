package com.sinocall.guess.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.sinocall.guess.R;
import com.sinocall.guess.activitys.GuessPayCenterActivity;
import com.sinocall.guess.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/7/4.
 */

public class GuessLessMoneyUtils extends Dialog {


    @BindView(R.id.pay_btn_cancle)
    TextView payBtnCancle;
    @BindView(R.id.pay_btn_sure)
    TextView payBtnSure;
    private WindowManager.LayoutParams wl;
    private Context mContext;
    public GuessLessMoneyUtils(Context context, View.OnClickListener onClickListener) {
        super(context, R.style.super_dialog);
        //加载布局
        setContentView(R.layout.guess_dialog_less_money);
        mContext=context;
        ButterKnife.bind(this);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        //设置窗体动画
        window.setWindowAnimations(R.style.super_anim_dialog);
        wl = window.getAttributes();
        wl.width = ScreenUtils.getWindowWidth(context) / 4 * 3;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        setCanceledOnTouchOutside(true);
    }


    /**
     * 设置宽度
     */
    public void setWidth(int width) {
        wl.width = width;
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.pay_btn_cancle, R.id.pay_btn_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_btn_cancle:
                dismiss();
                break;
            case R.id.pay_btn_sure:
                //跳转到充值中心
                dismiss();
                mContext.startActivity(new Intent(mContext, GuessPayCenterActivity.class));
                break;
        }
    }
}
