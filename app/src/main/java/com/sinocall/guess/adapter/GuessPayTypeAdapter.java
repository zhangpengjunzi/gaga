package com.sinocall.guess.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinocall.guess.R;
import com.sinocall.guess.base.BaseListAdapter;
import com.sinocall.guess.model.GuessPayCenterModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/15.
 */

public class GuessPayTypeAdapter extends BaseListAdapter<GuessPayCenterModel.DataBean.PayTypesBean> {

    private Context context;

    private LayoutInflater mInflater;

    public GuessPayTypeAdapter(Context context, List<GuessPayCenterModel.DataBean.PayTypesBean> list) {
        super(context, list);
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.guess_list_item_pay_buy, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        GuessPayCenterModel.DataBean.PayTypesBean payType=getList().get(position);
        viewHolder.buyName.setText(payType.getPayDec());//支付名称
        if(payType.getPaySelect()==0){
            //未选中
            viewHolder.selectImage.setImageResource(R.mipmap.unchoose_rechargecenter);
        }else{
            //选中
            viewHolder.selectImage.setImageResource(R.mipmap.choose_rechargecenter);
        }
        if(position==getList().size()-1){
            viewHolder.bottomLine.setVisibility(View.GONE);
        }else{
            viewHolder.bottomLine.setVisibility(View.VISIBLE);
        }
        if(payType.getPayType().equals("100")){
            viewHolder.payImage.setImageResource(R.mipmap.alipay_rechargecenter);
        }else{
            viewHolder.payImage.setImageResource(R.mipmap.wechat_rechargecenter);
        }
        //将位置赋值给text,做点击事件时用
        viewHolder.positionText.setText(position+"");
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.buy_name)
        TextView buyName;
        @BindView(R.id.pay_image)
        ImageView payImage;
        @BindView(R.id.select_image)
        ImageView selectImage;
        @BindView(R.id.pay_bottom_line)
        View bottomLine;
        @BindView(R.id.position_text)
        TextView positionText;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
