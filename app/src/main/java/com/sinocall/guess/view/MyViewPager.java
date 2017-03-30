package com.sinocall.guess.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by wjb on 2016/5/23.
 */
public class MyViewPager extends ViewPager {

    private int mLastX = 0;
    private int mLastY = 0;

    private boolean scrollable = true;

    /**
     * 上一次x坐标
     */
    //private float beforeX;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int dealtX = 0;
        int dealtY = 0;

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //beforeX = x;
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if(x<50){
                    //当处于侧滑状态时，可以左滑关闭侧滑
                    getParent().requestDisallowInterceptTouchEvent(false);
                    break;
                }

                if(!scrollable){
                    getParent().requestDisallowInterceptTouchEvent(false);
                    break;
                }

                int deltaX = x - mLastX;
                if( getCurrentItem()==0 && deltaX >0 ){
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                int deltaY=y-mLastY;
                Log.i("zp",mLastX+" "+mLastY+"  "+deltaY);
                if(deltaY >0&&deltaX<10||deltaX>-10&&deltaY >0){
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

           /*    if(!isCanScroll){
                   float motionValue = ev.getX() - beforeX;
                  if(direction==1){
                      if (motionValue > 0) {//禁止左滑
                          return true;
                      }
                  }else if(direction==2){
                      if (motionValue < 0) {//禁止右滑
                          return true;
                      }
                  }
                   beforeX = ev.getX();//手指移动时，再把当前的坐标作为下一次的‘上次坐标’，解决上述问题
               }*/

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }

    public void setScrollable(boolean state){
        scrollable = state;
    }

}
