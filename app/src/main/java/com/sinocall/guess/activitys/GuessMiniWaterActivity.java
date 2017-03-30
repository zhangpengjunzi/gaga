package com.sinocall.guess.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.GuessApi;
import com.sinocall.guess.R;
import com.sinocall.guess.adapter.GuessMineWaterAdapter;
import com.sinocall.guess.http.CckHttpClient;
import com.sinocall.guess.model.GuessMineWaterModel;

import org.xutils.common.Callback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的流水
 * Created by Administrator on 2017/2/21.
 */

public class GuessMiniWaterActivity extends BaseActivity {

    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;

    private GuessMineWaterAdapter adapter;
    private LRecyclerViewAdapter lAdapter;
    private List<GuessMineWaterModel.DataBean.TakePartListBean> exList;
    //是否是第一次加载
    private boolean isFirst = true;
    //每页加载的数量
    private int count = 10;
    //第几页
    private int currentPage = 1;
    //头部view
    private View headView;
    //头部类型
    private TextView textType, textTypeTwo;
    //左边类型与数量
    private TextView leftText, leftNumber;
    //右边类型与数量
    private TextView rightText, rightNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_activity_mine_water);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initRecyclerview(recyclerView);
        adapter = new GuessMineWaterAdapter(this);
        lAdapter = new LRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(lAdapter);
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                isFirst = true;
                currentPage = 1;
                initDada(count, currentPage);
            }
        });
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //加载更多
                isFirst = false;
                initDada(count, currentPage);
            }
        });
        //头部view
        headView = LayoutInflater.from(this).inflate(R.layout.guess_head_view_release, (ViewGroup) findViewById(android.R.id.content), false);
        findHeadViewById(headView);
        recyclerView.setVisibility(View.GONE);
        initRequest();
    }

    private void findHeadViewById(View view) {
        textType = (TextView) view.findViewById(R.id.type_text);
        leftText = (TextView) view.findViewById(R.id.left_text);
        leftNumber = (TextView) view.findViewById(R.id.left_text_number);
        rightText = (TextView) view.findViewById(R.id.right_text);
        rightNumber = (TextView) view.findViewById(R.id.right_text_number);
        textTypeTwo = (TextView) view.findViewById(R.id.type_text_two);
    }

    /*初始加载数据*/
    private void initRequest(){
        showLoaddingDialog("正在加载...");
        isFirst=true;
        currentPage=1;
        initDada(count, currentPage);
    }

    @Override
    public void clickRequest() {
        super.clickRequest();
        initRequest();
    }

    private void initDada(final int count, final int currentPage) {
        Bundle bundle = new Bundle();
        bundle.putString("ps", count + "");
        bundle.putString("pn", currentPage + "");
        CckHttpClient.get(GuessApi.MINE_WATER, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessMineWaterModel waterModel = JSON.parseObject(result, GuessMineWaterModel.class);
                dealData(waterModel, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessMiniWaterActivity.this, ex.getMessage());
                setNoDataArea(true,"您的网络不太好，点击重新加载");
                recyclerView.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dismissLoaddingDialog();
                recyclerView.refreshComplete(count);
            }
        });
    }

    /**
     * 处理请求数据
     *
     * @param waterModel
     * @param result
     */
    private void dealData(GuessMineWaterModel waterModel, String result) {
        recyclerView.setVisibility(View.VISIBLE);
        if (waterModel.getErrorVo().getCode().equals("1000")) {
            if (count == waterModel.getData().getTakePartList().size()) {
                currentPage++;//可以加载更多
            } else {
                if(isFirst){
                    recyclerView.setLoadMoreEnabled(false);
                }
            }
            if (isFirst) {
                //首次加载或下拉刷新
                exList = waterModel.getData().getTakePartList();
                //添加头部
                if (lAdapter.getHeaderViewsCount() == 0) {
                    textType.setText("流水统计");
                    leftText.setText("今日");
                    rightText.setText("累计");
                    textTypeTwo.setText("流水明细");
                    leftNumber.setText(waterModel.getData().getTodayNum() + "");
                    rightNumber.setText(waterModel.getData().getTotalNum() + "");
                    lAdapter.addHeaderView(headView);
                }
                if (exList.size() > 0) {
                    adapter.setDataList(exList);
                }else{
                    setNoDataArea(false,"暂无流水数据哦");
                    recyclerView.setVisibility(View.GONE);
                }
            } else {
                //加载更多
                List<GuessMineWaterModel.DataBean.TakePartListBean> addList = waterModel.getData().getTakePartList();
                if (addList.size() > 0) {
                    exList.addAll(addList);
                    adapter.setDataList(exList);
                    if(addList.size()<count){
                        recyclerView.setNoMore(true);
                    }
                } else {
                    //加载完毕
                    recyclerView.setNoMore(true);
                }
            }
        } else {
            getErrorMessage(result);
        }
    }

    @OnClick(R.id.back_image)
    public void onClick() {
        this.finish();
    }
}
