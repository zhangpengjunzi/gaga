package com.sinocall.guess.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jungly.gridpasswordview.GridPasswordView;
import com.sinocall.guess.R;
import com.sinocall.guess.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 输入支付密码买商品
 * Created by Administrator on 2016/7/4.
 */

public class GuessBuyShoppingUtils extends Dialog {


    @BindView(R.id.close_shopping_dialog)
    ImageView closeShoppingDialog;
  /*  @BindView(R.id.forget_password_btn)
    TextView forgetPasswordBtn;*/
    @BindView(R.id.shopping_pass)
    GridPasswordView shoppingPass;
    private WindowManager.LayoutParams wl;

    public GuessBuyShoppingUtils(Context context, View.OnClickListener onClickListener) {
        super(context, R.style.super_dialog);
        //加载布局
        setContentView(R.layout.guess_dialog_buy_shopping);
        ButterKnife.bind(this);
        Window window = getWindow();
        /*forgetPasswordBtn.setOnClickListener(onClickListener);*/
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

    public GridPasswordView getPassView(){
        return shoppingPass;
    }

    /**
     * 获取密码输入框控件
     * @return
     */
    private GridPasswordView getView(){
        return shoppingPass;
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
        shoppingPass.clearPassword();
    }


    @OnClick(R.id.close_shopping_dialog)
    public void onClick() {
        dismiss();
    }
}
