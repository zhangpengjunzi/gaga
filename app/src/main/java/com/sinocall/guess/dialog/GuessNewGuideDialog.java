package com.sinocall.guess.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
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
 * 提示框
 * Created by Administrator on 2016/7/16.
 */

public class GuessNewGuideDialog extends Dialog {
    @BindView(R.id.notice_btn)
    TextView noticeBtn;

    public GuessNewGuideDialog(Context context) {
        super(context, R.style.super_dialog);
        //加载布局
        setContentView(R.layout.guess_dialog_new_guide);
        ButterKnife.bind(this);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        //设置窗体动画
        window.setWindowAnimations(R.style.super_anim_dialog);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = ScreenUtils.getWindowWidth(context) / 4 * 3;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        setCanceledOnTouchOutside(true);
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick(R.id.notice_btn)
    public void onClick() {
        dismiss();
    }
}
