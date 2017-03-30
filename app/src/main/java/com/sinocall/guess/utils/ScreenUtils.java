package com.sinocall.guess.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenUtils {
	public static int getWindowWidth(Context context) {
		int mScreenWidth=0;
		if(context!=null){
			WindowManager wm = (WindowManager) (context
					.getSystemService(Context.WINDOW_SERVICE));
			DisplayMetrics dm = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(dm);
			mScreenWidth = dm.widthPixels;
		}
		return mScreenWidth;
	}

	public static int getWindowHeigh(Context context) {
		int mScreenHeigh=0;
		if(context!=null){
			WindowManager wm = (WindowManager) (context
					.getSystemService(Context.WINDOW_SERVICE));
			DisplayMetrics dm = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(dm);
			mScreenHeigh = dm.heightPixels;
		}
		return mScreenHeigh;
	}

}
