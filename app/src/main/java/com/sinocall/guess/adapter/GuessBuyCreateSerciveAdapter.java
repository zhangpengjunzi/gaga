package com.sinocall.guess.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinocall.guess.R;
import com.sinocall.guess.base.BaseListAdapter;
import com.sinocall.guess.model.GuessCreateModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 购买新建盘口次数
 * Created by Administrator on 2017/2/15.
 */

public class GuessBuyCreateSerciveAdapter extends BaseListAdapter<GuessCreateModel.DataBean.ListBean> {

    private Context context;

    private LayoutInflater mInflater;

    private int clickStatus=0;
    private String isHave="";
    //设置选中项
    public void setSelection(int position){
        clickStatus=position;
    }
    //获取选中项
    public int getPostion(){
        return clickStatus;
    }

    public void setHave(String have) {
        isHave = have;
    }

    public GuessBuyCreateSerciveAdapter(Context context, List<GuessCreateModel.DataBean.ListBean> list) {
        super(context, list);
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.guess_list_item_buy_service, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GuessCreateModel.DataBean.ListBean buyType = getList().get(position);
        //购买数量
        if(!TextUtils.isEmpty(isHave)){
            viewHolder.buyNumber.setText(buyType.getNum()+isHave);
        }else{
            viewHolder.buyNumber.setText(buyType.getNum()+"");
        }
        //购买产品
        viewHolder.buyProduct.setText(setFontColorSize("盘缠:"+buyType.getPayCoin()));
        //日期
        viewHolder.buyDate.setText("有效期"+buyType.getExpireDay()+"天");
        //选中改变背景
        if(clickStatus==position){
            viewHolder.buyLinear.setBackgroundResource(R.drawable.guess_buy_service_select);
        }else{
            viewHolder.buyLinear.setBackgroundResource(R.drawable.guess_buy_service_unselect);
        }
        return convertView;
    }

    private SpannableStringBuilder setFontColorSize(String label) {
        int fstart=label.indexOf("盘缠:")+3;
        SpannableStringBuilder style=new SpannableStringBuilder(label);
        style.setSpan(new ForegroundColorSpan(Color.RED),fstart,label.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }


    static class ViewHolder {
        @BindView(R.id.buy_number)
        TextView buyNumber;
        @BindView(R.id.buy_product)
        TextView buyProduct;
        @BindView(R.id.buy_date)
        TextView buyDate;
        @BindView(R.id.buy_linear)
        LinearLayout buyLinear;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
