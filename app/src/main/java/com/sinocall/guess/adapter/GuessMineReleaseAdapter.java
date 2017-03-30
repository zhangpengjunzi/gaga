package com.sinocall.guess.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sinocall.guess.R;
import com.sinocall.guess.base.ListBaseAdapter;
import com.sinocall.guess.base.SuperViewHolder;
import com.sinocall.guess.model.GuessMineReleaseModel;
import com.sinocall.guess.utils.RelativeDateFormat;

/**
 * 我发布的
 * Created by Administrator on 2017/2/20.
 */

public class GuessMineReleaseAdapter extends ListBaseAdapter<GuessMineReleaseModel.DataBean.CreateListBean> {

    private ShareRelease shareRelease;

    public GuessMineReleaseAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.guess_list_item_mine_release_do;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        final GuessMineReleaseModel.DataBean.CreateListBean historyTopicBean=mDataList.get(position);
        //投注图片
        ImageView imageView=holder.getView(R.id.release_image);
        Glide.with(mContext)
        .load(historyTopicBean.getPicUrl())
        .into(imageView);
        //盘口名称
        TextView name=holder.getView(R.id.release_name);
        name.setText(historyTopicBean.getName());
        //下注时间
        TextView time=holder.getView(R.id.release_time);
        if(!TextUtils.isEmpty(historyTopicBean.getCreateTime()))time.setText(RelativeDateFormat.getTimeRange(historyTopicBean.getCreateTime()));
        //盘口状态
        TextView state=holder.getView(R.id.release_state);
        TextView share_btn=holder.getView(R.id.share_btn);//分享按钮
        TextView cancle_text=holder.getView(R.id.cancle_text);//若已开奖，则显示
        if(historyTopicBean.getStatus()==0){
            state.setText("投注:"+historyTopicBean.getProgress());
            share_btn.setVisibility(View.VISIBLE);
            cancle_text.setVisibility(View.GONE);
            share_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(shareRelease!=null){
                        shareRelease.shareRelease(position);
                    }
                }
            });
        } else{
            state.setText(setFontColorSize("人数:"+historyTopicBean.getRnum()+"  佣金:+"+historyTopicBean.getCommission()));
            share_btn.setVisibility(View.GONE);
            cancle_text.setVisibility(View.VISIBLE);
        }
    }
    //将佣金：后面的文字红色高亮
    private SpannableStringBuilder setFontColorSize(String label) {
        int fstart=label.indexOf("佣金:")+3;
        SpannableStringBuilder style=new SpannableStringBuilder(label);
        style.setSpan(new ForegroundColorSpan(Color.RED),fstart,label.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }

    public void setShareRelease(ShareRelease shareRelease){
        this.shareRelease=shareRelease;
    }

    public interface ShareRelease{
        void shareRelease(int postion);
    }

}
