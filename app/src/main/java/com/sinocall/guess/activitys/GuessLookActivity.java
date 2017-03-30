package com.sinocall.guess.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.GuessApi;
import com.sinocall.guess.R;
import com.sinocall.guess.http.CckHttpClient;
import com.sinocall.guess.model.SimPleModel;
import com.sinocall.guess.utils.ToastUtils;
import com.sinocall.guess.view.OvalImageView;
import com.sinocall.guess.view.RoundProgressBar;

import org.xutils.common.Callback;

import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sinocall.guess.utils.Common.CREATE_PATH;

/**
 * Created by Administrator on 2017/3/2.
 */

public class GuessLookActivity extends BaseActivity {

    @BindView(R.id.guess_image)
    OvalImageView guessImage;
    @BindView(R.id.guess_describe)
    TextView guessDescribe;
    @BindView(R.id.guess_left_text)
    TextView guessLeftText;
    @BindView(R.id.roundProgressBar)
    RoundProgressBar roundProgressBar;
    @BindView(R.id.guess_right_text)
    TextView guessRightText;
    @BindView(R.id.award_number)
    TextView awardNumber;

    //描述
    private String describe;
    //选项
    private String selectText;
    //开奖数目
    private String award;
    //标签
    private String lable;

    String selects[];
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_activity_look);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void rightClick() {
        super.rightClick();
        //新建盘口
        uploadCreateInfo();
    }

    private void initView() {
        initTitle("预览", "发布");
        //图片
        Bitmap bitmap= null;
        try {
            bitmap = BitmapFactory.decodeStream(openFileInput(CREATE_PATH));
            guessImage.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //描述
        describe = getIntent().getStringExtra("describe");
        guessDescribe.setText(describe);
        //选项
        selectText = getIntent().getStringExtra("selectText");
        selects= selectText.split("/");
        guessLeftText.setText(selects[0]);
        guessRightText.setText(selects[1]);
        //开盘数目
        award=getIntent().getStringExtra("awardNumber");
        awardNumber.setText(award);
        //标签
        lable=getIntent().getStringExtra("lable");
    }

    private void uploadCreateInfo(){
        showLoaddingDialog("正在发布...");
        Bundle bundle=new Bundle();
        bundle.putString("left",selects[0]);
        bundle.putString("right",selects[1]);
        bundle.putString("description",describe);
        bundle.putString("tag",lable);
        bundle.putString("coinSwitch",award);
        CckHttpClient.post(GuessApi.CREATE_NEW, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SimPleModel simPleModel= JSON.parseObject(result,SimPleModel.class);
                if(simPleModel.getErrorVo().getCode().equals("1000")){
                    ToastUtils.showToast(GuessLookActivity.this,"发布成功");
                    Intent release=new Intent(GuessLookActivity.this,GuessMineJoinActivity.class);
                    release.putExtra("page_position", 1);
                    startActivity(release);
                    if(GuessSendInfoActivity.activity!=null){
                        if(!GuessSendInfoActivity.activity.isFinishing()){
                            GuessSendInfoActivity.activity.finish();
                        }
                    }
                    GuessLookActivity.this.finish();
                }else{
                    ToastUtils.showToast(GuessLookActivity.this,simPleModel.getErrorVo().getMsg());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessLookActivity.this,ex.getMessage());
                ToastUtils.showToast(GuessLookActivity.this,ex.getLocalizedMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dismissLoaddingDialog();
            }
        },getFilesDir()+"/"+ CREATE_PATH);
    }
}
