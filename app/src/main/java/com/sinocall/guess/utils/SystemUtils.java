package com.sinocall.guess.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Administrator on 2017/2/21.
 */

public class SystemUtils {
    public static void StartByParams(Activity activity, Class<?> cla, Bundle bundle){
        Intent intent=new Intent(activity,cla);
        if(bundle!=null)intent.putExtras(bundle);
        activity.startActivity(intent);
    }


}
