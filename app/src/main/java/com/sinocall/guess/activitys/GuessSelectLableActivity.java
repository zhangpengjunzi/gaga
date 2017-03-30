package com.sinocall.guess.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.GuessApi;
import com.sinocall.guess.R;
import com.sinocall.guess.http.CckHttpClient;
import com.sinocall.guess.model.GuessSelectLableModel;
import com.sinocall.guess.utils.ToastUtils;
import com.sinocall.guess.view.MyScrollView;
import com.sinocall.guess.view.tagview.FlowLayout;
import com.sinocall.guess.view.tagview.TagAdapter;
import com.sinocall.guess.view.tagview.TagFlowLayout;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/17.
 */

public class GuessSelectLableActivity extends BaseActivity {

    @BindView(R.id.select_lable)
    TagFlowLayout selectLable;
    @BindView(R.id.lable_scroll)
    MyScrollView lableScroll;

    private String select_lable;//当前选中的标签
    private String top_lable;//上一个页面返回的标签
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_activity_select_lable);
        ButterKnife.bind(this);
        initTitle("内容标签", "完成");
        initData();
    }

    @Override
    public void clickRequest() {
        super.clickRequest();
        initData();//点击屏幕重新请求
    }

    @Override
    public void rightClick() {
        super.rightClick();
        if(TextUtils.isEmpty(select_lable)){
            ToastUtils.showToast(this,"请选择题目标签");
            return;
        }
        //将选中的标签返回到上个页面
        Intent intent = new Intent();
        intent.putExtra("lable", select_lable);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initData() {
        showLoaddingDialog("正在加载...");
        lableScroll.setVisibility(View.GONE);
        CckHttpClient.get(GuessApi.LABLE_LIST, null, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessSelectLableModel lableModel = JSON.parseObject(result, GuessSelectLableModel.class);
                dealData(lableModel, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessSelectLableActivity.this, ex.getMessage());
                lableScroll.setVisibility(View.GONE);
                setNoDataArea(true,"您的网络不太好，点击重新加载");
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

    /**
     * 处理请求数据
     *
     * @param lableModel
     * @param result
     */
    private void dealData(GuessSelectLableModel lableModel, String result) {
        if (lableModel.getErrorVo().getCode().equals("1000")) {
            lableScroll.setVisibility(View.VISIBLE);
            //将list放到标签控件中
            List<GuessSelectLableModel.DataBean.TagListBean> listBean = lableModel.getData().getTagList();
            if (listBean != null && listBean.size() > 0) {
                //添加标签
                final List<String> strList = new ArrayList<>();
                for (GuessSelectLableModel.DataBean.TagListBean item : listBean) {
                    strList.add(item.getName());
                }
                top_lable=getIntent().getStringExtra("content_text");//不为空就赋值
                selectLable.setAdapter(new TagAdapter<String>(strList.toArray(new String[0])) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        TextView tv = (TextView) LayoutInflater.from(GuessSelectLableActivity.this).inflate(R.layout.guess_tag_view_lable, selectLable, false);
                        tv.setText(s);
                        return tv;
                    }

                    @Override
                    public boolean setSelected(int position, String s) {
                        if(!TextUtils.isEmpty(top_lable)){
                            if(top_lable.equals(s)){
                                select_lable=s;
                                return true;
                            }
                        }
                        return super.setSelected(position, s);
                    }
                });


                //标签点击事件
                selectLable.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        select_lable = strList.get(position);
                        return true;
                    }
                });
            }else{
                setNoDataArea(false,"暂无任何标签");
                lableScroll.setVisibility(View.GONE);
            }
        } else {
            getErrorMessage(result);
        }
    }

}
