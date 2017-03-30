package com.sinocall.guess.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.sinocall.guess.R;


/**
 * 加载dialog
 * Created by Administrator on 2016/7/16.
 */

public class GuessLoddingDialog extends Dialog {
    private TextView textView;
    public GuessLoddingDialog(Context context) {
        super(context, R.style.loddingdialog);
        //加载布局
        setContentView(R.layout.guess_dialog_loadding);
        textView= (TextView) findViewById(R.id.tips);
        Window window=getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams wl=window.getAttributes();
        wl.width= ViewGroup.LayoutParams.WRAP_CONTENT;
        wl.height= ViewGroup.LayoutParams.WRAP_CONTENT;
        setCanceledOnTouchOutside(false);
         /*设置按返回键无法取消dialog*/
        setOnKeyListener(keylistener);
    }

    OnKeyListener keylistener = new OnKeyListener(){
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    } ;

    /*设置字体内容*/
    public void setText(String text){
        textView.setText(text);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
    }

}
