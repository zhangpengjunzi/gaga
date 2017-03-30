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
import com.sinocall.guess.model.GuessAwardReceiveModel;
import com.sinocall.guess.model.GuessHandicapDetailsModel;
import com.sinocall.guess.utils.ScreenUtils;
import com.zhy.android.percent.support.PercentLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 提示框
 * Created by Administrator on 2016/7/16.
 */

public class GuessReceiveDataDialog extends Dialog {


    @BindView(R.id.receive_title)
    TextView receiveTitle;
    @BindView(R.id.receive_mine_join_number)
    TextView receiveMineJoinNumber;
    @BindView(R.id.receive_mine_join_result)
    TextView receiveMineJoinResult;
    @BindView(R.id.receive_left_data)
    TextView receiveLeftData;
    @BindView(R.id.receive_left_person_number)
    TextView receiveLeftPersonNumber;
    @BindView(R.id.receive_left_number)
    TextView receiveLeftNumber;
    @BindView(R.id.receive_right_data)
    TextView receiveRightData;
    @BindView(R.id.receive_right_person_number)
    TextView receiveRightPersonNumber;
    @BindView(R.id.receive_right_number)
    TextView receiveRightNumber;
    @BindView(R.id.receive_btn)
    TextView receiveBtn;
    @BindView(R.id.receive_left_result)
    TextView receiveLeftResult;
    @BindView(R.id.receive_right_result)
    TextView receiveRightResult;
    @BindView(R.id.receive_mine_join_linear)
    PercentLinearLayout receiveMineJoinLinear;
    @BindView(R.id.receive_mine_commission)
    TextView receiveMineCommission;
    @BindView(R.id.receive_mine_commission_linear)
    PercentLinearLayout receiveMineCommissionLinear;

    private Context mContext;

    public GuessReceiveDataDialog(Context context) {
        super(context, R.style.super_dialog);
        //加载布局
        mContext = context;
        setContentView(R.layout.guess_dialog_receive_data);
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

    //我参与的与我发布的数据展示
    public void setMineData(GuessHandicapDetailsModel model){
        //盘口信息
        receiveTitle.setText(model.getData().getName());
        //佣金
        if (model.getData().getCommission() > 0) {//若我的佣金大于0，则显示
            receiveMineCommissionLinear.setVisibility(View.VISIBLE);
            receiveMineCommission.setText("我的佣金 "+model.getData().getCommission());
        } else {
            receiveMineCommissionLinear.setVisibility(View.GONE);
        }
        //根据购买的数量判断，如果大于0，则显示我买了多少
        if (model.getData().getBuyNum() > 0) {
            receiveMineJoinLinear.setVisibility(View.VISIBLE);
            //我下注多少
            receiveMineJoinNumber.setText("我的下注 " + model.getData().getBuyNum() + "； ");
            //我的盈亏
            receiveMineJoinResult.setText("总盈亏 " + model.getData().getReward());
        } else {
            receiveMineJoinLinear.setVisibility(View.GONE);
        }
        //左边还是右边获胜
        if (model.getData().getResult() == 1) {
            receiveLeftResult.setText("获胜");
            receiveLeftResult.setTextColor(mContext.getResources().getColor(R.color.slide_red));
            receiveRightResult.setText("失败");
            receiveRightResult.setTextColor(mContext.getResources().getColor(R.color.none_settings));
        } else {
            receiveLeftResult.setText("失败");
            receiveLeftResult.setTextColor(mContext.getResources().getColor(R.color.none_settings));
            receiveRightResult.setText("获胜");
            receiveRightResult.setTextColor(mContext.getResources().getColor(R.color.slide_red));
        }
        //左边其他数据
        receiveLeftData.setText(model.getData().getLeft());
        receiveLeftPersonNumber.setText(model.getData().getLeftPersonNum()+"人");
        receiveLeftNumber.setText("合计下注 " + model.getData().getLeftCoinNum());
        //右边其他数据
        receiveRightData.setText(model.getData().getRight());
        receiveRightPersonNumber.setText(model.getData().getRightPersonNum()+"人");
        receiveRightNumber.setText("合计下注 " + model.getData().getRightCoinNum());
    }

    //透传消息开奖结果数据
    public void setData(GuessAwardReceiveModel model) {
        //盘口信息
        receiveTitle.setText(model.getData().getName());
        if (model.getData().getCommission() > 0) {//若我的佣金大于0，则显示
            receiveMineCommissionLinear.setVisibility(View.VISIBLE);
            receiveMineCommission.setText("我的佣金 "+model.getData().getCommission());
        } else {
            receiveMineCommissionLinear.setVisibility(View.GONE);
        }
       /* if (model.getType() == 2) {
            //代表我参与的，但不是我发布的
            receiveMineCommissionLinear.setVisibility(View.GONE);
        } else if (model.getType() == 3) {
            //type为三代表是我发布的盘口
            if (model.getData().getCommission() > 0) {//若我的佣金大于0，则显示
                receiveMineCommissionLinear.setVisibility(View.VISIBLE);
                receiveMineCommission.setText("我的佣金 "+model.getData().getCommission());
            } else {
                receiveMineCommissionLinear.setVisibility(View.GONE);
            }
        }*/
        //根据购买的数量判断，如果大于0，则显示我买了多少
        if (model.getData().getBuyNum() > 0) {
            receiveMineJoinLinear.setVisibility(View.VISIBLE);
            //我下注多少
            receiveMineJoinNumber.setText("我的下注 " + model.getData().getBuyNum() + "； ");
            //我的盈亏
            receiveMineJoinResult.setText("总盈亏 " + model.getData().getReward());
        } else {
            receiveMineJoinLinear.setVisibility(View.GONE);
        }

        //左边还是右边获胜
        if (model.getData().getResult() == 1) {
            receiveLeftResult.setText("获胜");
            receiveLeftResult.setTextColor(mContext.getResources().getColor(R.color.slide_red));
            receiveRightResult.setText("失败");
            receiveRightResult.setTextColor(mContext.getResources().getColor(R.color.none_settings));
        } else {
            receiveLeftResult.setText("失败");
            receiveLeftResult.setTextColor(mContext.getResources().getColor(R.color.none_settings));
            receiveRightResult.setText("获胜");
            receiveRightResult.setTextColor(mContext.getResources().getColor(R.color.slide_red));
        }
        //左边其他数据
        receiveLeftData.setText(model.getData().getLeft());
        receiveLeftPersonNumber.setText(model.getData().getLeftPersonNum()+"人");
        receiveLeftNumber.setText("合计下注 " + model.getData().getLeftCoinNum());
        //右边其他数据
        receiveRightData.setText(model.getData().getRight());
        receiveRightPersonNumber.setText(model.getData().getRightPersonNum()+"人");
        receiveRightNumber.setText("合计下注 " + model.getData().getRightCoinNum());
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
    }


    @OnClick(R.id.receive_btn)
    public void onClick() {
        dismiss();
    }

}
