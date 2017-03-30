package com.sinocall.guess.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.sinocall.guess.R;
import com.sinocall.guess.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/7/4.
 */

public class GuessQuestionUtils extends Dialog {

    /*标题*/
    @BindView(R.id.message_title)
    TextView messageTitle;

    /*取消按钮*/
    @BindView(R.id.btn_cancle)
    TextView btnCancle;

    /*确认按钮*/
    @BindView(R.id.btn_sure)
    TextView btnSure;

    private WindowManager.LayoutParams wl;

    public GuessQuestionUtils(Context context, View.OnClickListener onClickListener) {
        super(context, R.style.super_dialog);
        //加载布局
        setContentView(R.layout.guess_dialog_question);
        ButterKnife.bind(this);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        //设置窗体动画
        window.setWindowAnimations(R.style.super_anim_dialog);
        btnCancle.setOnClickListener(onClickListener);
        btnSure.setOnClickListener(onClickListener);
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

    /**
     * 设置标题
     */
    public void setTitle(String text){
        messageTitle.setText(text);
    }
    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
    }

}
