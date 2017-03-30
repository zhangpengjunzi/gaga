package com.sinocall.guess.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinocall.guess.R;
import com.sinocall.guess.activitys.GuessWebViewActivity;
import com.sinocall.guess.utils.Common;
import com.sinocall.guess.utils.ScreenUtils;
import com.sinocall.guess.utils.SystemUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 购买服务
 * Created by Administrator on 2016/7/4.
 */

public class GuessBuyServiceUtils extends Dialog {

    @BindView(R.id.close_buyservice_dialog)
    ImageView closeBuyserviceDialog;
    @BindView(R.id.buyservice_center_text)
    TextView buyserviceCenterText;
    @BindView(R.id.buyservice_help_btn)
    ImageView buyserviceHelpBtn;
    @BindView(R.id.buyservice_title)
    TextView buyserviceTitle;
    @BindView(R.id.buyservice_grid)
    GridView buyserviceGrid;
    @BindView(R.id.buy_btn)
    TextView buyBtn;
    private WindowManager.LayoutParams wl;

    private Activity mContext;

    public GuessBuyServiceUtils(Activity context, View.OnClickListener onClickListener) {
        super(context, R.style.super_dialog);
        //加载布局
        mContext=context;
        setContentView(R.layout.guess_dialog_buy_service);
        ButterKnife.bind(this);
        buyBtn.setOnClickListener(onClickListener);
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
     * 设置中心标题
     *
     * @param centerText
     */
    public void setCenterText(String centerText) {
        buyserviceCenterText.setText(centerText);
    }

    /**
     * 设置购买标题
     *
     * @param title
     */
    public void setTitle(String title) {
        buyserviceTitle.setText(title);
    }

    public GridView getGriView() {
        return buyserviceGrid;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.close_buyservice_dialog,R.id.buyservice_help_btn})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.close_buyservice_dialog:
                dismiss();
                break;
            case R.id.buyservice_help_btn:
                //进入购物服务H5页面
               if(Common.initModel!=null){
                   Bundle bundle=new Bundle();
                   bundle.putString("name","购买说明");
                   bundle.putString("url", Common.initModel.getData().getBuyPrivilegeHelp());
                   SystemUtils.StartByParams(mContext, GuessWebViewActivity.class,bundle);
               }
                break;
        }
    }



}
