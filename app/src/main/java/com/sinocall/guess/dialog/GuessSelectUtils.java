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
import butterknife.OnClick;


/**
 * 用于选择两种
 * Created by Administrator on 2016/7/4.
 */

public class GuessSelectUtils extends Dialog {


    @BindView(R.id.select_one)
    TextView selectOne;
    @BindView(R.id.select_two)
    TextView selectTwo;
    @BindView(R.id.select_cancle)
    TextView selectCancle;

    private WindowManager.LayoutParams wl;

    public GuessSelectUtils(Context context, View.OnClickListener onClickListener) {
        super(context, R.style.super_dialog);
        //加载布局
        setContentView(R.layout.guess_dialog_select);
        ButterKnife.bind(this);
        selectOne.setOnClickListener(onClickListener);
        selectTwo.setOnClickListener(onClickListener);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        //设置窗体动画
        window.setWindowAnimations(R.style.super_anim_dialog);
        wl = window.getAttributes();
        wl.width = ScreenUtils.getWindowWidth(context);
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        setCanceledOnTouchOutside(true);
    }

    /**
     * 设置两种选择内容
     * @param one
     * @param two
     */
    public void setSelectContent(String one,String two){
        selectOne.setText(one);
        selectTwo.setText(two);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick(R.id.select_cancle)
    public void onClick() {
        dismiss();
    }
}
