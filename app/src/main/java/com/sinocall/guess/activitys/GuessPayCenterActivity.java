package com.sinocall.guess.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.GuessApi;
import com.sinocall.guess.R;
import com.sinocall.guess.adapter.GuessBuyTypeAdapter;
import com.sinocall.guess.adapter.GuessPayTypeAdapter;
import com.sinocall.guess.http.CckHttpClient;
import com.sinocall.guess.model.AliPayResult;
import com.sinocall.guess.model.GuessPayCenterModel;
import com.sinocall.guess.model.GuessPayModel;
import com.sinocall.guess.model.SimPleModel;
import com.sinocall.guess.service.GuessInitInterfaceService;
import com.sinocall.guess.utils.Common;
import com.sinocall.guess.utils.SPUtils;
import com.sinocall.guess.utils.ToastUtils;
import com.sinocall.guess.view.LinearListView;
import com.sinocall.guess.view.ProgressWebview;
import com.sinocall.guess.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.xutils.common.Callback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 充值中心
 * Created by Administrator on 2017/2/13.
 */

public class GuessPayCenterActivity extends BaseActivity {

    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.pay_center_details)
    TextView payCenterDetails;
    @BindView(R.id.surplus_number)
    TextView surplusNumber;
    @BindView(R.id.can_exchange)
    TextView canExchange;
    @BindView(R.id.pay_type_list)
    LinearListView payTypeList;
    @BindView(R.id.pay_btn)
    LinearLayout payBtn;
    @BindView(R.id.buy_type_list)
    LinearListView buyTypeList;
    @BindView(R.id.pay_money)
    TextView payMoney;
    @BindView(R.id.pay_scroll)
    NestedScrollView payScroll;
    @BindView(R.id.pay_notice)
    TextView payNotice;
    @BindView(R.id.pay_bottom_linear)
    PercentLinearLayout payBottomLinear;
    @BindView(R.id.pay_center_linear)
    PercentRelativeLayout payCenterLinear;
    @BindView(R.id.common_text)
    TextView commonText;
    @BindView(R.id.common_linear)
    LinearLayout commonLinear;
    @BindView(R.id.webView)
    ProgressWebview webView;

    //选择购买的金豆价格
    private double price;
    //实际支付的金豆价格
    private double payPrice;
    //赠送的金豆数量
    private int giftCoin;
    //支付类型
    private String payType;
    //支付回调结果
    private static final int SDK_PAY_FLAG = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_activity_pay_center);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    /*处于无网络状态时点击重新加载*/

    @Override
    public void clickRequest() {
        super.clickRequest();
        initData();
    }

    private void initView() {
        //选择购买方式
        buyTypeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取位置
                int position = Integer.parseInt(((TextView) view.findViewById(R.id.position_text)).getText().toString());
                if (buyTypeAdapter.getList().get(position).getSelect() == 1) {
                    return;//当前被选中
                }
                //没有被选中，则将原来被选中的置0，选择被点击的位置
                for (int i = 0; i < buyTypeAdapter.getList().size(); i++) {
                    if (buyTypeAdapter.getList().get(i).getSelect() == 1) {
                        buyTypeAdapter.getList().get(i).setSelect(0);
                        break;
                    }
                }
                buyTypeAdapter.getList().get(position).setSelect(1);
                price = buyTypeAdapter.getList().get(position).getPrice();
                payPrice = buyTypeAdapter.getList().get(position).getPayPrice();
                giftCoin = buyTypeAdapter.getList().get(position).getGiftCoinNum();
                buyTypeList.setAdapter(buyTypeAdapter);
                //改变实际支付金额
                payMoney.setText("￥" + payPrice + "元");
                if (!TextUtils.isEmpty(buyTypeAdapter.getList().get(position).getRemark())) {
                    payNotice.setText(" (" + buyTypeAdapter.getList().get(position).getRemark() + ")");
                }
            }
        });
        /*选择付款方式*/
        payTypeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取位置
                int position = Integer.parseInt(((TextView) view.findViewById(R.id.position_text)).getText().toString());
                if (payTypeAdapter.getList().get(position).getPaySelect() == 1) {
                    return;//当前被选中
                }
                //没有被选中，则将原来被选中的置0，选择被点击的位置
                for (int i = 0; i < payTypeAdapter.getList().size(); i++) {
                    if (payTypeAdapter.getList().get(i).getPaySelect() == 1) {
                        payTypeAdapter.getList().get(i).setPaySelect(0);
                        break;
                    }
                }
                payTypeAdapter.getList().get(position).setPaySelect(1);
                payType = payTypeAdapter.getList().get(position).getPayType();
                payTypeList.setAdapter(payTypeAdapter);
            }
        });
    }


    /**
     * 加载数据
     */
    private void initData() {
        showLoaddingDialog("正在加载...");
        payScroll.setVisibility(View.GONE);
        payBottomLinear.setVisibility(View.GONE);
        CckHttpClient.get(GuessApi.PAY_CENTER, null, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessPayCenterModel payCenterModel = JSON.parseObject(result, GuessPayCenterModel.class);
                dealData(result, payCenterModel);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessPayCenterActivity.this, ex.getMessage());
                setNoDataArea(true, "您的网络不太好，点击重新加载");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dismissLoaddingDialog();
            }
        });
    }

    //购买类型适配器
    private GuessBuyTypeAdapter buyTypeAdapter;
    //支付类型适配器
    private GuessPayTypeAdapter payTypeAdapter;

    /**
     * 处理数据
     *
     * @param result
     * @param payCenterModel
     */
    private void dealData(String result, GuessPayCenterModel payCenterModel) {
        if (payCenterModel.getErrorVo().getCode().equals("1000")) {
            if (payCenterModel.getData().getJump() == 1) {
                //进入h5支付页面
                payScroll.setVisibility(View.GONE);
                payBottomLinear.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                String url = null;
                if (Common.initModel != null)
                    url = Common.initModel.getData().getChargeH5() + "?userId=" + SPUtils.get(GuessPayCenterActivity.this, "userId", 0) + "&lId=" + SPUtils.get(GuessPayCenterActivity.this, "lId", "");
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                if (url != null) {
                    webView.loadUrl(url);
                } else {
                    ToastUtils.showToast(this, "请检查您的网络设置");
                    startService(new Intent(this, GuessInitInterfaceService.class));
                }
            } else {
                //进入原生支付页面
                payScroll.setVisibility(View.VISIBLE);
                payBottomLinear.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
            /*余额*/
                surplusNumber.setText(payCenterModel.getData().getRemain() + "");
            /*可兑换数量*/
                canExchange.setText(payCenterModel.getData().getExchangeCoin() + "");
            /*购买类型*/
                List<GuessPayCenterModel.DataBean.ChargeItemsBean> buyTypes = payCenterModel.getData().getChargeItems();
                if (buyTypes != null) {
                    if (buyTypes.size() > 0) {
                        buyTypes.get(0).setSelect(1);//设置默认支付第一个
                        buyTypeAdapter = new GuessBuyTypeAdapter(this, buyTypes);
                        buyTypeList.setAdapter(buyTypeAdapter);
                        price = buyTypes.get(0).getPrice();
                        payPrice = buyTypes.get(0).getPayPrice();
                        giftCoin = buyTypes.get(0).getGiftCoinNum();
                        //显示实际支付金额
                        payMoney.setText("￥" + payPrice + "元");
                        if (!TextUtils.isEmpty(buyTypes.get(0).getRemark())) {
                            payNotice.setText(" (" + buyTypes.get(0).getRemark() + ")");
                        }
                    }
                }
            /*支付类型*/
                List<GuessPayCenterModel.DataBean.PayTypesBean> payTypes = payCenterModel.getData().getPayTypes();
                if (payTypes != null) {
                    if (payTypes.size() > 0) {
                        //默认支付宝支付
                        for (int i = 0; i < payTypes.size(); i++) {
                            if (payTypes.get(i).getPayType().equals("100")) {//100代表支付宝支付
                                payTypes.get(i).setPaySelect(1);
                                payType = "100";
                                break;
                            }
                        }
                        payTypeAdapter = new GuessPayTypeAdapter(this, payTypes);
                        payTypeList.setAdapter(payTypeAdapter);
                    }
                }
            }
        } else {
            getErrorMessage(result);
        }
    }

    @OnClick({R.id.back_image, R.id.pay_center_details, R.id.pay_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_image:
                this.finish();
                break;
            case R.id.pay_center_details:
                //充值明细
                startActivity(new Intent(this, GuessPayDetailsActivity.class));
                break;
            case R.id.pay_btn:
                pay();
                break;
        }
    }

    private void pay() {
        showLoaddingDialog("正在提交订单...");
        Bundle bundle = new Bundle();
        bundle.putString("payType", payType);
        bundle.putString("price", price + "");
        bundle.putString("payPrice", payPrice + "");
        bundle.putString("giftCoin", giftCoin + "");
        CckHttpClient.get(GuessApi.PAY, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessPayModel model = JSON.parseObject(result, GuessPayModel.class);
                dealGuessPay(result, model);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessPayCenterActivity.this, ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dismissLoaddingDialog();
            }
        });
    }

    private String outTradeNo;//订单号，支付成功后回调使用

    /**
     * 支付宝支付
     *
     * @param result
     * @param model
     */
    private void dealGuessPay(String result, final GuessPayModel model) {
        if (model.getErrorVo().getCode().equals("1000")) {
            if (payType.equals("100")) {//支付宝支付
                outTradeNo = model.getData().getOutTradeNo();
/*                String orderInfo = getOrderInfo(model.getData().getAliPayData());
                 *//*订单信息拼接*//*
                final String orderInfos = orderInfo + "&sign=\"" + model.getData().getAliPayData().getSign() + "\"&" + getSignType();*/
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        //构造payTask对象
                        PayTask aliPay = new PayTask(GuessPayCenterActivity.this);
                        // 调用支付接口，获取支付结果
                        String result = aliPay.pay(model.getData().getSendStr(), true);
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        aLiHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            } else {//微信支付
                //注册
                final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, model.getData().getWxData().getAppid(), false);
                msgApi.registerApp(model.getData().getWxData().getAppid());
                WXPayEntryActivity.APP_ID = model.getData().getWxData().getAppid();
                //调起支付
                PayReq req = new PayReq();
                req.appId = model.getData().getWxData().getAppid();
                req.partnerId = model.getData().getWxData().getPartnerid();
                req.packageValue = model.getData().getWxData().getPackageValue();
                req.prepayId = model.getData().getWxData().getPrepayid();
                req.nonceStr = model.getData().getWxData().getNoncestr();
                req.timeStamp = model.getData().getWxData().getTimestamp();
                req.sign = model.getData().getWxData().getSign();
                msgApi.sendReq(req);
            }
        } else {
            getErrorMessage(result);
        }
    }


    //处理支付结果
    private Handler aLiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    AliPayResult payResult = new AliPayResult((String) msg.obj);
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        paySuccess();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtils.showToast(GuessPayCenterActivity.this, "支付结果确认中");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ToastUtils.showToast(GuessPayCenterActivity.this, "支付失败");
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /*支付成功后回调*/
    private void paySuccess() {
        if (!TextUtils.isEmpty(outTradeNo)) {
            Bundle bundle = new Bundle();
            bundle.putString("outTradeNo", outTradeNo);
            CckHttpClient.get(GuessApi.PAY_SUCCESS, bundle, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    SimPleModel simPleModel = JSON.parseObject(result, SimPleModel.class);
                    if (simPleModel.getErrorVo().getCode().equals("1000")) {
                        ToastUtils.showToast(GuessPayCenterActivity.this, "充值成功");
                        surplusNumber.setText(simPleModel.getData().getRemain() + "");
                    } else if (simPleModel.getErrorVo().getCode().equals("1001")) {
                        ToastUtils.showToast(GuessPayCenterActivity.this, "充值成功后余额5分钟内会更新");
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }
    }
}
