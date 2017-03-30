package com.sinocall.guess.http;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;

import com.sinocall.guess.GuessApplication;
import com.sinocall.guess.utils.DeviceUtils;
import com.sinocall.guess.utils.MD5Utils;
import com.sinocall.guess.utils.SPUtils;
import com.sinocall.guess.utils.ToastUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sinocall.guess.utils.DeviceUtils.os;

/**
 * Created by Administrator on 2016/6/22.
 */

public class CckHttpClient {

    /*POST请求方法*/
    public static void post(String requestUrl, Bundle params, Callback.CommonCallback<String> callback,String path) {
        RequestParams requestParams = getParams(params, requestUrl,path);
        x.http().post(requestParams, callback);
    }

    /*GET请求方法*/
    public static void get(String requestUrl, Bundle params, Callback.CommonCallback<String> callback) {
        RequestParams requestParams = getParams(params, requestUrl);
        x.http().get(requestParams,callback);
    }

    /*请求错误*/
    public static void error(Context context,String info){
        if(!TextUtils.isEmpty(info)){
            if(info.toLowerCase().contains("connecttimeoutexception") || info.toLowerCase().contains("sockettimeoutexception")||info.contains("after 10000ms")){
                ToastUtils.showToast(context, "网络请求超时！请检查您的网络设置");
            }else if(info.contains("HttpHostConnectException")||info.contains("No address associated")||info.contains("Not Found")){
                ToastUtils.showToast(context, "连接服务器出错！请检查您的网络设置");
            }else if(info.contains("Server Error")){
                ToastUtils.showToast(context, "服务器出错！请稍后重试");
            }else{
                ToastUtils.showToast(context, "请检查您的网络设置");
            }
        }else{
            ToastUtils.showToast(context, "请检查您的网络设置");
        }
    }

    //获取参数
    private static RequestParams getParams(Bundle bundle, String url) {
        RequestParams params = new RequestParams(url);
        //最大数据缓存事件
        params.setCacheMaxAge(1000);
        //请求超时时间
        params.setConnectTimeout(15000);
        params.setCharset("UTF-8");
        //请求头
        params.addHeader("did", DeviceUtils.did);
        params.addHeader("deviceId", DeviceUtils.deviceId);
        params.addHeader("mac", DeviceUtils.mac);
        params.addHeader("apn", DeviceUtils.apn);
        params.addHeader("lat", "" + DeviceUtils.lat);
        params.addHeader("lon", "" + DeviceUtils.lon);
        params.addHeader("net", DeviceUtils.net);
        params.addHeader("ua", DeviceUtils.ua);
        params.addHeader("os", os + "");
        params.addHeader("osv", DeviceUtils.osv);
        params.addHeader("ver", DeviceUtils.ver);
        params.addHeader("r", DeviceUtils.r);
        params.addHeader("channelid", "1");
        if ((Integer) SPUtils.get(GuessApplication.getInstance(), "userId", 0) != 0) {
            params.addHeader("userId", "" + SPUtils.get(GuessApplication.getInstance(), "userId", 0));
        } else {
            params.addHeader("userId", 0 + "");
        }
        if (!SPUtils.get(GuessApplication.getInstance(), "lId", "").equals("")) {
            params.addHeader("lId", SPUtils.get(GuessApplication.getInstance(), "lId", "").toString());
        } else {
            params.addHeader("lId", "");
        }
        params.addHeader("brand", DeviceUtils.brand);
        if ((Integer) SPUtils.get(GuessApplication.getInstance(), "platform", 0) != 0) {
            params.addHeader("platform", "" + SPUtils.get(GuessApplication.getInstance(), "platform", 0));
        } else {
            params.addHeader("platform", "" + 0);
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("v", DeviceUtils.v);
        long currentTime = System.currentTimeMillis();
        bundle.putString("t", "" + currentTime);
        bundle.putString("s", getMD5(bundle));

        for (String key : bundle.keySet()) {
            params.addBodyParameter(key, bundle.getString(key));
        }
        return params;
    }


    private static RequestParams getParams(Bundle bundle, String url,String path) {

        RequestParams params = new RequestParams(url);

//        List<KeyValue> list=new ArrayList<>();
//        list.add(new KeyValue("file",new File(path)));
//        for (String key : bundle.keySet()) {
//            list.add(new KeyValue(key,bundle.getString(key)));
//        }
//        list.add(new KeyValue("s",getMD5(bundle)));
//        MultipartBody boty=new MultipartBody(list,"UTF-8");
//        params.setRequestBody(boty);

        /*StringBuilder sbUrl=new StringBuilder(url+"?");
        for (String key : bundle.keySet()) {
            sbUrl.append(key+"="+bundle.getString(key)+"&");
        }
        sbUrl.append("s="+getMD5(bundle));
        RequestParams params = new RequestParams(sbUrl.toString());*/
        //最大数据缓存事件
        params.setCacheMaxAge(1000);
    /*    params.setMultipart(true);
        //上传的文件
        params.addBodyParameter("file", new File(path));*/
        //请求超时时间
        params.setConnectTimeout(15000);
        //请求头
        params.addHeader("did", DeviceUtils.did);
        params.addHeader("deviceId", DeviceUtils.deviceId);
        params.addHeader("mac", DeviceUtils.mac);
        params.addHeader("apn", DeviceUtils.apn);
        params.addHeader("lat", "" + DeviceUtils.lat);
        params.addHeader("lon", "" + DeviceUtils.lon);
        params.addHeader("net", DeviceUtils.net);
        params.addHeader("ua", DeviceUtils.ua);
        params.addHeader("os", os + "");
        params.addHeader("osv", DeviceUtils.osv);
        params.addHeader("ver", DeviceUtils.ver);
        params.addHeader("r", DeviceUtils.r);
        params.addHeader("channelid", "1");
        if ((Integer) SPUtils.get(GuessApplication.getInstance(), "userId", 0) != 0) {
            params.addHeader("userId", "" + SPUtils.get(GuessApplication.getInstance(), "userId", 0));
        } else {
            params.addHeader("userId", 0 + "");
        }
        if (!SPUtils.get(GuessApplication.getInstance(), "lId", "").equals("")) {
            params.addHeader("lId", SPUtils.get(GuessApplication.getInstance(), "lId", "").toString());
        } else {
            params.addHeader("lId", "");
        }
        params.addHeader("brand", DeviceUtils.brand);
        if ((Integer) SPUtils.get(GuessApplication.getInstance(), "platform", 0) != 0) {
            params.addHeader("platform", "" + SPUtils.get(GuessApplication.getInstance(), "platform", 0));
        } else {
            params.addHeader("platform", "" + 0);
        }

        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("v", DeviceUtils.v);
        long currentTime = System.currentTimeMillis();
        bundle.putString("t", "" + currentTime);

        for (String key : bundle.keySet()) {
            params.addQueryStringParameter(key,bundle.getString(key));
        }
        params.addQueryStringParameter("s",getMD5(bundle));
        params.setMultipart(true);
        params.addBodyParameter(
                "file",
                new File(path),
                null); // 如果文件没有扩展名, 最好设置contentType参数.

        return params;
    }


    private static String getMD5(Bundle params) {
        if (params == null || params.size() == 0) {
            return null;
        }
        List<String> keys = new ArrayList<>();
        for (String key : params.keySet()) {
            keys.add(key);
        }
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        int size = 0;
        for (String key : keys) {
            if (!key.equals("s")) {
                sb.append(key).append("=").append(params.get(key)).append('&');
                size++;
            }
        }
        sb.append("xxxwwwa8");
        sb.append("=");
        sb.append(200 + size);
        return MD5Utils.hexdigest(sb.toString());
    }

    public static void updateApp(Context context, String url) {
        //下载文件
        String newFilename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.msdrx/";
        File dir = new File(newFilename);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        newFilename = newFilename + "updateMSDRX.apk";

        File file = new File(newFilename);
        //如果目标文件已经存在，则删除。产生覆盖旧文件的效果
        if (file.exists()) {
            file.delete();
        }
        try {
            // 构造URL
            URL downloadUrl = new URL(url);
            // 打开连接
            URLConnection con = downloadUrl.openConnection();
            //获得文件的长度
            int contentLength = con.getContentLength();
            System.out.println("长度 :" + contentLength);
            // 输入流
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            int downlaodlen = 0;
            //是否允许读写sd卡
            /*File sd=Environment.getExternalStorageDirectory();
            boolean write=sd.canWrite();*/
            // 输出的文件流
            OutputStream os = new FileOutputStream(newFilename);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
                os.flush();
                downlaodlen += len;
                System.out.println("下载长度 :" + downlaodlen);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();

            String command = "chmod 777 " + " " + newFilename;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + newFilename), "application/vnd.android.package-archive");
            context.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //启动安装
    }
}
