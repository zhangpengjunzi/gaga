package com.sinocall.guess.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinocall.guess.R;
import com.sinocall.guess.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 修改昵称
 * Created by Administrator on 2016/7/4.
 */

public class GuessResetNameUtils extends Dialog {


    @BindView(R.id.close_nick_dialog)
    ImageView closeNickDialog;
    @BindView(R.id.nick_done_btn)
    TextView nickDoneBtn;
    @BindView(R.id.reset_nickname_text)
    EditText resetNicknameText;
    private WindowManager.LayoutParams wl;

    public GuessResetNameUtils(Context context, View.OnClickListener onClickListener) {
        super(context, R.style.super_dialog);
        //加载布局
        setContentView(R.layout.guess_dialog_reset_nickname);
        ButterKnife.bind(this);
        nickDoneBtn.setOnClickListener(onClickListener);
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
     * 获取修改后的昵称
     * @return
     */
    public String getName(){
        return resetNicknameText.getText().toString();
    }

    /**
     * @param content 目前的昵称
     */
    public void showResetDiaolog(String content){
        resetNicknameText.setText(content);
        if(!TextUtils.isEmpty(content))resetNicknameText.setSelection(content.length());
        show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick(R.id.close_nick_dialog)
    public void onClick() {
        dismiss();
    }
}
