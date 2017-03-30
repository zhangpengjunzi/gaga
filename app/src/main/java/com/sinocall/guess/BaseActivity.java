package com.sinocall.guess;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.igexin.sdk.PushManager;
import com.sinocall.guess.dialog.GuessLoddingDialog;
import com.sinocall.guess.dialog.GuessReceiveDataDialog;
import com.sinocall.guess.http.CckHttpClient;
import com.sinocall.guess.model.GuessAwardReceiveModel;
import com.sinocall.guess.model.SimPleModel;
import com.sinocall.guess.service.GTPushIntentService;
import com.sinocall.guess.service.GTPushService;
import com.sinocall.guess.service.GuessInitInterfaceService;
import com.sinocall.guess.utils.ChannelUtil;
import com.sinocall.guess.utils.Common;
import com.sinocall.guess.utils.DeviceUtils;
import com.sinocall.guess.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.yalantis.ucrop.UCrop;

import org.xutils.common.Callback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.sinocall.guess.utils.Common.CREATE_PATH;
import static com.sinocall.guess.utils.Common.HEAD_PATH;
import static com.umeng.socialize.bean.SHARE_MEDIA.QQ;

/**
 * Created by Administrator on 2017/2/9.
 */

public class BaseActivity extends AppCompatActivity {

    //加载对话框
    private GuessLoddingDialog loddingDialog;

    //activity头部actionbar
    private ImageView backImage;
    private TextView centerText;
    private TextView rightText;
    //友盟对象
    public UMShareAPI umShareAPI;
    //广播对象
    private AwardReceive awardReceive;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         /*强制竖屏*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //动态获取用户手机信息权限
        initPhoneImei();
        /*个推服务初始化*/
        PushManager.getInstance().initialize(this.getApplicationContext(), GTPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), GTPushIntentService.class);
        umShareAPI=UMShareAPI.get(this);
        //注册广播,监听开奖信息
        IntentFilter intentFilter=new IntentFilter();
        awardReceive=new AwardReceive();
        intentFilter.addAction("com.sinocall.guess.awardreceive");
        registerReceiver(awardReceive,intentFilter);
        //裁剪图片缓存地址
        mDestinationUri=Uri.fromFile(new File(getCacheDir(), "SampleCropImage.jpeg"));
    }

    private GuessReceiveDataDialog dialog;

    public class AwardReceive extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent!=null){
                String data=intent.getStringExtra("receiveData");
                GuessAwardReceiveModel receiveModel=JSON.parseObject(data,GuessAwardReceiveModel.class);
                //展示开奖数据
                if(dialog==null){
                    dialog=new GuessReceiveDataDialog(BaseActivity.this);
                }
                dialog.setData(receiveModel);
                dialog.show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册
        unregisterReceiver(awardReceive);
        umShareAPI.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);//友盟统计
        //若初始化model为空，则请求初始化接口
        if(Common.initModel==null)startService(new Intent(this, GuessInitInterfaceService.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);//友盟统计
    }

    public void initRecyclerview(LRecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader); //设置下拉刷新Progress的样式
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        //设置头部加载颜色
        recyclerView.setHeaderViewColor(R.color.actionbar_color,R.color.login_btn ,android.R.color.white);
        //设置底部加载颜色
        recyclerView.setFooterViewColor(R.color.actionbar_color, R.color.login_btn ,android.R.color.white);
        //设置底部加载文字提示
        recyclerView.setFooterViewHint("拼命加载中","已经全部加载完了","网络不给力啊，点击再试一次吧");
    }


    /*隐藏软键盘*/
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //弹出软键盘
    public void showSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /*显示加载对话框*/
    public  void showLoaddingDialog(String content){
        if(loddingDialog==null){
            loddingDialog =new GuessLoddingDialog(this);
        }
        loddingDialog.setText(content);
        loddingDialog.show();
    }

    /*隐藏加载对话框*/
    public  void dismissLoaddingDialog(){
        if(loddingDialog!=null&&loddingDialog.isShowing()){
            loddingDialog.dismiss();
            loddingDialog=null;
        }
    }

    /*显示错误信息*/
    public  void  getErrorMessage(String result){
        SimPleModel simPleModel= JSON.parseObject(result,SimPleModel.class);
        ToastUtils.showToast(this,simPleModel.getErrorVo().getMsg());
    }

    /**
     * 初始化页面toolbar信息
     * @param center
     * @param right
     */
    public void initTitle(String center,String right){
        backImage= (ImageView) findViewById(R.id.back_image);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        centerText= (TextView) findViewById(R.id.title_text);
        centerText.setText(center);
        if(!TextUtils.isEmpty(right)){
            rightText= (TextView) findViewById(R.id.right_text);
            rightText.setText(right);
            rightText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rightClick();
                }
            });
        }
    }
    //子类重写该方法设置点击事件
    public void rightClick(){}

    //无数据或网络公共布局
    private LinearLayout common_linear;
    private TextView common_text;

    /**
     * 设置无网络或无数据时的页面状态
     * @param isClick 无网路时可以点击重新加载
     * @param text 无数据时的理由
     */
    public void setNoDataArea(boolean isClick,String text){
        if(common_linear==null)common_linear= (LinearLayout) findViewById(R.id.common_linear);
        if(common_text==null)common_text= (TextView) findViewById(R.id.common_text);
        common_linear.setVisibility(View.VISIBLE);
        if(isClick){
            common_text.setText(text);
            common_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击重新加载
                    clickRequest();
                }
            });
        }else{
            //无数据理由
            if(!TextUtils.isEmpty(text)){}common_text.setText(text);
        }
    }
    //隐藏无数据状态页面
    public void setVisibleGone(){
        if(common_linear!=null)common_linear.setVisibility(View.GONE);
    }
    

    //子类重写该方法处理无网络状态下的重新请求
    public void clickRequest(){
        common_linear.setVisibility(View.GONE);
    }



    private static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    private static final int GALLERY_REQUEST_CODE = 0;    // 相册选图标记
    private static final int CAMERA_REQUEST_CODE = 1;    // 相机拍照标记
    //裁剪后图像文件
    private Uri mDestinationUri;
    private Uri photoUri;
    private String photoPath;//相片临时文件
    public static  int cropType=1;//裁剪类型  1为创建新盘口，2为设置个人头像

    @RequiresApi(api = Build.VERSION_CODES.M)
    public  void selectPhoto(Activity activity) {
        //相机权限需要15以上的系统版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(activity,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    "需要相机使用权限，是否打开？",
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION,activity);
        } else {
            Intent phoneIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photoPath = Environment.getExternalStorageDirectory() + File.separator +System.currentTimeMillis()+ ".png";
            File file=new File(photoPath);
            if (phoneIntent.resolveActivity(getPackageManager()) != null) {
                //兼容Android 7.0,防止调用相机闪退问题
                if(android.os.Build.VERSION.SDK_INT<=Build.VERSION_CODES.M){
                    photoUri=Uri.fromFile(file);
                }else{
                    //7.0获取url方法
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                    photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
                }
                phoneIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                activity.startActivityForResult(phoneIntent, CAMERA_REQUEST_CODE);
            }else{
                ToastUtils.showToast(this,"相机无法使用");
            }
        }
    }


    /**
     * 选择本地图片
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public  void pickFromGallery(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    "是否允许使用本地图库？",
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION,activity);
        } else {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
            // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
            pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            activity.startActivityForResult(pickIntent, GALLERY_REQUEST_CODE);
        }
    }

    /**
     * 请求权限
     * <p>
     * 如果权限被拒绝过，则提示用户需要权限
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected  void requestPermission(final String permission, String rationale, final int requestCode,final Activity activity) {
        if (activity.shouldShowRequestPermissionRationale(permission)) {
            showAlertDialog("权限需求", rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.requestPermissions(new String[]{permission}, requestCode);
                        }
                    }, "确定", null, "取消",activity);
        } else {
            activity.requestPermissions(new String[]{permission}, requestCode);
        }
    }

    private  AlertDialog mAlertDialog;

    /**
     * 显示指定标题和信息的对话框
     *
     * @param title                         - 标题
     * @param message                       - 信息
     * @param onPositiveButtonClickListener - 肯定按钮监听
     * @param positiveText                  - 肯定按钮信息
     * @param onNegativeButtonClickListener - 否定按钮监听
     * @param negativeText                  - 否定按钮信息
     */
    protected  void showAlertDialog(@Nullable String title, @Nullable String message,
                                          @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                          @NonNull String positiveText,
                                          @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                          @NonNull String negativeText,
                                          Activity activity ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        mAlertDialog = builder.show();
    }


    /**
     * 保存文件
     *
     * @param bm
     * @param name
     */
    public  void saveFile(Bitmap bm, Context context, String name)  {
        try {//将裁剪后的文件保存到/data/data/pack name/file下
            FileOutputStream outputStream=context.openFileOutput(name,Context.MODE_PRIVATE);
            bm.compress(Bitmap.CompressFormat.PNG, 100 , outputStream);
            outputStream.close();
        }catch (IOException e){
            ToastUtils.showToast(context,"找不到文件");
        }
    }

    /**
     * 删除拍照临时文件
     */
    public  void deleteTempPhotoFile(String path) {
        if (path != null) {
            File tempFile = new File(path);
            if (tempFile.exists() && tempFile.isFile()) {
                tempFile.delete();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    //相机回调返回
                    if(photoUri!=null)startCropActivity(photoUri);
                    break;
                case GALLERY_REQUEST_CODE:
                    //图库返回
                    if (data != null) startCropActivity(data.getData());
                    break;
                case UCrop.REQUEST_CROP:
                    //裁剪成功后回调
                    if (data != null) handleCropResult(data);
                    break;
                case UCrop.RESULT_ERROR:
                    //裁剪失败
                    if (data != null) {
                        handleCropError(data);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     */
    private void startCropActivity(Uri uri) {
        if(cropType==1){
            UCrop.of(uri, mDestinationUri)
                    .withAspectRatio(4, 3)
                    .withMaxResultSize(600,450)
                    .start(this);
        }else if(cropType==2){
            UCrop.of(uri, mDestinationUri)
                    .withAspectRatio(1, 1)
                    .withMaxResultSize(300,300)
                    .start(this);
        }

    }


    /**
     * 处理剪切失败的返回值
     *
     * @param result
     */
    private void handleCropError(Intent result) {
        deleteTempPhotoFile(photoPath);
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            ToastUtils.showToast(this, cropError.getMessage());
        } else {
            ToastUtils.showToast(this, "无法裁剪图片");
        }
    }




    /**
     * 处理剪切成功的返回值
     *
     * @param result
     */
    private void handleCropResult(Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (null != resultUri) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                /*将裁剪后的图片保存在本地*/
                if(cropType==1){
                    saveFile(bitmap,this,CREATE_PATH);
                }else if(cropType==2){
                    saveFile(bitmap,this,HEAD_PATH);
                }
                //跳转到新建页面
                cropAfter();
                deleteTempPhotoFile(photoPath);//删除相机临时文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showToast(this, "无法裁剪图片");
        }
    }

    /**
     * 子类重写此方法执行裁剪成功后的逻辑
     */
    public void cropAfter(){}
    private ShareAction shareAction;
    //打开分享面板
    public void openShareBoard(ShareBoardlistener shareBoardlistener){
        shareAction = new ShareAction(this)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA, QQ, SHARE_MEDIA.QZONE)
                .setShareboardclickCallback(shareBoardlistener);
        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);//分享面板在中间出现
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);// 圆角背景
        config.setShareboardBackgroundColor(Color.WHITE);//分享面板背景颜色
        config.setCancelButtonText("取消分享");//取消按钮
        config.setTitleVisibility(false);// 隐藏title
        shareAction.open(config);//打开分享面板
    }
    //分享
    public void share(String imageUrl, String url, String title, String description, SHARE_MEDIA plat){
        //图片
        UMImage umImage;
        if(!TextUtils.isEmpty(imageUrl)){
            umImage=new UMImage(this,imageUrl);
        }else{
            umImage=new UMImage(this,R.mipmap.logo_share);
        }
        //分享链接‘
        UMWeb umWeb=new UMWeb(url);
        umWeb.setTitle(title);//标题
        umWeb.setDescription(description);//描述
        umWeb.setThumb(umImage);//新浪微博缩略图
        if(plat==SHARE_MEDIA.SINA){
            new ShareAction(this).setPlatform(plat).setCallback(umShareListener)
                    .withMedia(umImage)
                    .withText(description+url)
                    .share();
        }else{
            new ShareAction(this).setPlatform(plat).setCallback(umShareListener)
                    .withMedia(umWeb)
                    .share();
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showToast(BaseActivity.this,"分享成功");
            //分享成功之后回调接口
            shareSuccess();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
        }
    };

    /**
     * 屏幕横竖屏切换时避免出现window leak的问题
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        shareAction.close();
    }

    private void shareSuccess(){
        CckHttpClient.get(GuessApi.SHARE_SUCCESS, null, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {}

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private static final int REQUEST_CODE_ASK_READ_PHONE_STATE = 120;

    /**
     * 获取用户手机读取权限
     */
    private void initPhoneImei() {
        if (Build.VERSION.SDK_INT >= 23) {
            //Android 6.0系统需要动态获取权限
            int checkReadPhoneStatePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);
            if (checkReadPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE_ASK_READ_PHONE_STATE);
            } else {
                DeviceUtils.did = ChannelUtil.getImei(this) + "_" + Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
                DeviceUtils.deviceId = ChannelUtil.getImei(this);
            }
        } else {
            DeviceUtils.did = ChannelUtil.getImei(this) + "_" + Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            DeviceUtils.deviceId = ChannelUtil.getImei(this);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    DeviceUtils.did = ChannelUtil.getImei(this) + "_" + Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
                    DeviceUtils.deviceId = ChannelUtil.getImei(this);
                } else {
                    MobclickAgent.onKillProcess(this);//保存统计数据
                    System.exit(0);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //不根据用户设置的系统字体大小而改变
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }

}
