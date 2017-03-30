package com.sinocall.guess.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.sinocall.guess.GuessApi;
import com.sinocall.guess.R;
import com.sinocall.guess.adapter.GuessReportAdapter;
import com.sinocall.guess.http.CckHttpClient;
import com.sinocall.guess.model.GuessReportModel;
import com.sinocall.guess.model.SimPleModel;
import com.sinocall.guess.utils.ScreenUtils;
import com.sinocall.guess.utils.ToastUtils;

import org.xutils.common.Callback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 举报
 * Created by Administrator on 2016/7/4.
 */

public class GuessReportUtils extends Dialog {


    @BindView(R.id.report_cancle)
    TextView reportCancle;
    @BindView(R.id.list_report)
    ListView listReport;

    private WindowManager.LayoutParams wl;

    private GuessReportAdapter reportAdapter;
    private GuessLoddingDialog loddingDialog;
    private int id;//被举报盘口id
    private Context mContext;

    public GuessReportUtils(Context context) {
        super(context, R.style.super_dialog);
        mContext=context;
        //加载布局
        setContentView(R.layout.guess_dialog_report);
        ButterKnife.bind(this);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        //设置窗体动画
        window.setWindowAnimations(R.style.super_anim_dialog);
        wl = window.getAttributes();
        wl.width = ScreenUtils.getWindowWidth(context);
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        setCanceledOnTouchOutside(true);
        listReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GuessReportModel.DataBean.ListBean reportModel= (GuessReportModel.DataBean.ListBean) adapterView.getItemAtPosition(i);
                dismiss();
                report(reportModel.getReason());
            }
        });
    }

    /**
     * 设置举报内容
     * @param listBeen 举报列表数据
     * @param id  举报的盘口id
     */
    public  void setData(List<GuessReportModel.DataBean.ListBean> listBeen,int id){
        reportAdapter=new GuessReportAdapter(mContext,listBeen);
        listReport.setAdapter(reportAdapter);
        this.id=id;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
    }


    @OnClick(R.id.report_cancle)
    public void onClick() {
        dismiss();
    }

    /**
     *举报
     * @param name 举报是由
     */
    private void report(String name){
        if(loddingDialog==null){
            loddingDialog=new GuessLoddingDialog(mContext);
        }
        loddingDialog.setText("正在上传举报信息...");
        loddingDialog.show();
        Bundle bundle=new Bundle();
        bundle.putString("topicId",id+"");
        bundle.putString("reason",name);
        CckHttpClient.get(GuessApi.REPORT, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SimPleModel simPleModel= JSON.parseObject(result,SimPleModel.class);
                if(simPleModel.getErrorVo().getCode().equals("1000")){
                    ToastUtils.showToast(mContext,"举报成功");
                }else{
                    ToastUtils.showToast(mContext,simPleModel.getErrorVo().getMsg());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showToast(mContext,"举报失败，请检查您的网络设置");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                loddingDialog.dismiss();
            }
        });
    }
}
