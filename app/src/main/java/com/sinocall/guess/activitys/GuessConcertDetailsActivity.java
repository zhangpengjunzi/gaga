package com.sinocall.guess.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.GuessApi;
import com.sinocall.guess.R;
import com.sinocall.guess.adapter.GuessConcertDetailsAdapter;
import com.sinocall.guess.http.CckHttpClient;
import com.sinocall.guess.model.GuessConcertDetailsModel;

import org.xutils.common.Callback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 兑换明细
 * Created by Administrator on 2017/2/20.
 */

public class GuessConcertDetailsActivity extends BaseActivity {

    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;

    private GuessConcertDetailsAdapter adapter;
    private LRecyclerViewAdapter lAdapter;
    private List<GuessConcertDetailsModel.DataBean.ListBean> exList;
    //是否是第一次加载
    private boolean isFirst = true;
    //每页加载的数量
    private int count = 10;
    //第几页
    private int currentPage = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_activity_convert_details);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void clickRequest() {
        super.clickRequest();
        showLoaddingDialog("正在加载...");
        currentPage=1;
        isFirst=true;
        initDada(count, currentPage);
    }

    private void initView() {
        initRecyclerview(recyclerView);
        adapter = new GuessConcertDetailsAdapter(this);
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
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuessConcertDetailsActivity.this.finish();
            }
        });
        showLoaddingDialog("正在加载...");
        initDada(count, currentPage);
    }

    private void initDada(final int count, final int currentPage) {
        Bundle bundle = new Bundle();
        bundle.putString("ps", count + "");
        bundle.putString("pn", currentPage + "");
        CckHttpClient.get(GuessApi.CONVERT_DETAILS, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessConcertDetailsModel detailsModel = JSON.parseObject(result, GuessConcertDetailsModel.class);
                dealData(detailsModel, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessConcertDetailsActivity.this, ex.getMessage());
                recyclerView.setVisibility(View.GONE);
                setNoDataArea(true,"您的网路不太好，点击重新加载");
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
     * @param detailsModel
     * @param result
     */
    private void dealData(GuessConcertDetailsModel detailsModel, String result) {
        if (detailsModel.getErrorVo().getCode().equals("1000")) {
            recyclerView.setVisibility(View.VISIBLE);
            //是否有下一页
            if (count == detailsModel.getData().getList().size()) {
                currentPage++;
            } else {
                if(isFirst){
                    recyclerView.setLoadMoreEnabled(false);
                }
            }
            if (isFirst) {
                exList = detailsModel.getData().getList();
                if (exList.size() > 0) {
                    adapter.setDataList(exList);
                }else{
                    setNoDataArea(false,"还没有兑换过任何东西哦");
                    recyclerView.setVisibility(View.GONE);
                }
            } else {
                List<GuessConcertDetailsModel.DataBean.ListBean> addList = detailsModel.getData().getList();
                if (addList.size() > 0) {
                    exList.addAll(addList);
                    adapter.setDataList(exList);
                    if (addList.size() < count) {
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

}
