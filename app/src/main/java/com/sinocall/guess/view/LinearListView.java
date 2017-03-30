package com.sinocall.guess.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;


/*
* 用来解决scrollview嵌套listview的问题
* */
public class LinearListView extends LinearLayout {
	
	public BaseAdapter adapter;
	public OnClickListener mOnClickListener = null;

	public LinearListView(Context context) {
		super(context);
	}
	
	public LinearListView(Context context, AttributeSet attrs) {
	        super(context, attrs);
	}
	
	private void bindLinearLayout(){
		int count = adapter.getCount();
		this.removeAllViews();
		for(int i=0;i<count;i++){
			View v = adapter.getDropDownView(i, null, null);
			v.setOnClickListener(this.mOnClickListener);
			this.addView(v, i);
		}
	}



	public void setAdapter(BaseAdapter adapter){
		this.adapter = adapter;
		bindLinearLayout();
	}
	
	public void setOnClickListener(OnClickListener mOnClickListener){
		this.mOnClickListener = mOnClickListener;
	}


}
