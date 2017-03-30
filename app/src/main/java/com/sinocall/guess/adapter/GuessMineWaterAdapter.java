package com.sinocall.guess.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.sinocall.guess.R;
import com.sinocall.guess.base.ListBaseAdapter;
import com.sinocall.guess.base.SuperViewHolder;
import com.sinocall.guess.model.GuessMineWaterModel;

/**
 * 我的流水
 * Created by Administrator on 2017/2/20.
 */

public class GuessMineWaterAdapter extends ListBaseAdapter<GuessMineWaterModel.DataBean.TakePartListBean> {


    public GuessMineWaterAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.guess_list_item_pay_details;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        final GuessMineWaterModel.DataBean.TakePartListBean exchangeListBean=mDataList.get(position);
        if(!TextUtils.isEmpty(exchangeListBean.getCreateTime())){
            //分割日期
            String [] dates=exchangeListBean.getCreateTime().split(" ");
            //日期
            TextView date=holder.getView(R.id.convert_details_date);
            date.setText(dates[0]);
            //时间
            TextView time=holder.getView(R.id.convert_details_time);
            time.setText(dates[1]);
        }
        //花费
        TextView price=holder.getView(R.id.convert_details_price);
        price.setText(exchangeListBean.getNum()+"");
        //花费事项
        TextView reason=holder.getView(R.id.convert_details_reason);
        reason.setText(exchangeListBean.getRemark());
        //底部线
        View bottom_line=holder.getView(R.id.convert_details_bottom_line);
        if(position==getDataList().size()-1){
            bottom_line.setVisibility(View.GONE);
        }else{
            bottom_line.setVisibility(View.VISIBLE);
        }
    }



}
