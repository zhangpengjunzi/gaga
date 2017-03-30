package com.sinocall.guess.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.R;
import com.sinocall.guess.dialog.GuessSelectUtils;
import com.sinocall.guess.utils.Common;
import com.sinocall.guess.utils.SystemUtils;
import com.sinocall.guess.utils.ToastUtils;
import com.zhy.android.percent.support.PercentFrameLayout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sinocall.guess.utils.Common.CREATE_PATH;

/**
 * 发布竞猜内容
 * Created by Administrator on 2017/2/16.
 */

public class GuessSendInfoActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.send_info_linear)
    LinearLayout sendInfoLinear;
    @BindView(R.id.guess_select)
    PercentFrameLayout guessSelect;
    @BindView(R.id.guess_content)
    PercentFrameLayout guessContent;
    @BindView(R.id.guess_award)
    PercentFrameLayout guessAward;
    @BindView(R.id.create_image)
    ImageView createImage;
    @BindView(R.id.select_text)
    TextView selectText;
    @BindView(R.id.content_text)
    TextView contentText;
    @BindView(R.id.award_text)
    TextView awardText;
    @BindView(R.id.create_describe)
    EditText createDescribe;
    @BindView(R.id.edit_length)
    TextView editLength;
    @BindView(R.id.publish_help_btn)
    ImageView publishHelpBtn;

    public static GuessSendInfoActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_activity_send_info);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化页面
     */
    private void initView() {
        initTitle("发布内容", "预览");
        activity = this;
        cropType=1;//设置裁剪类型
        //点击页面其他地方，隐藏软键盘
        sendInfoLinear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideSoftInputView();
                return false;
            }
        });

        //加载裁剪好的图片
        try {
            inputStream=openFileInput(CREATE_PATH);
            bitmap = BitmapFactory.decodeStream(inputStream);
            createImage.setImageBitmap(bitmap);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //为edittext设置监听
        createDescribe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                editLength.setText((100 - editable.toString().length()) + "");
            }
        });
    }

    @Override
    public void rightClick() {
        super.rightClick();
        //预览
        if (createDescribe.getText().toString().length() < 20) {
            ToastUtils.showToast(this, "描述文字不能少于20个");
            return;
        }
        if (TextUtils.isEmpty(select_text)) {
            ToastUtils.showToast(this, "竞猜选项不能为空");
            return;
        }
        if (TextUtils.isEmpty(content_text)) {
            ToastUtils.showToast(this, "标签选择不能为空");
            return;
        }
        if (TextUtils.isEmpty(award_text)) {
            ToastUtils.showToast(this, "开奖条件选择不能为空");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("describe", createDescribe.getText().toString());//描述
        bundle.putString("selectText", select_text);//竞猜选项
        bundle.putString("lable", content_text);//标签
        bundle.putString("awardNumber", award_text);//选择开奖条件
        SystemUtils.StartByParams(this, GuessLookActivity.class, bundle);
    }

    private final int SELECT = 1;//选择内容
    private final int LABLE = 2;//选择标签
    private final int AWARD = 3;//选择开奖条件
    private GuessSelectUtils selectUtils;
    private boolean isLocal=true;//是否是进本页面生命周期，false进父类生命周期
    private Bitmap bitmap;//用来显示图片
    private FileInputStream inputStream;//读取图片流

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.guess_select, R.id.guess_content, R.id.guess_award,R.id.publish_help_btn,R.id.create_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guess_select:
                //设置精彩选项
                isLocal=true;
                Intent select=new Intent(this, GuessSettingContentActivity.class);
                if(!TextUtils.isEmpty(select_text)){
                    select.putExtra("select_text",select_text);
                }
                startActivityForResult(select, SELECT);
                break;
            case R.id.guess_content:
                //设置内容标签
                isLocal=true;
                Intent content=new Intent(this, GuessSelectLableActivity.class);
                if(!TextUtils.isEmpty(content_text)){
                    content.putExtra("content_text",content_text);
                }
                startActivityForResult(content, LABLE);
                break;
            case R.id.guess_award:
                isLocal=true;
                Intent award=new Intent(this, GuessSettingAwardActivity.class);
                if(!TextUtils.isEmpty(award_text)){
                    award.putExtra("award_text",award_text);
                }
                startActivityForResult(award, AWARD);
                break;
            case R.id.publish_help_btn:
                //跳转到发布帮助h5页面
                Bundle bundle=new Bundle();
                bundle.putString("name","发布帮助");
                if(Common.initModel!=null)bundle.putString("url", Common.initModel.getData().getPublishHelp());
                SystemUtils.StartByParams(this,GuessWebViewActivity.class,bundle);
                break;
            case R.id.create_image:
                //点击可重新选择图片
                if(selectUtils==null){
                    selectUtils=new GuessSelectUtils(this,this);
                }
                selectUtils.show();
                isLocal=false;
                break;
            case R.id.select_one:
                //拍照
                selectUtils.dismiss();
                selectPhoto(this);
                break;
            case R.id.select_two:
                //图库选择
                selectUtils.dismiss();
                pickFromGallery(this);
                break;
        }
    }

    @Override
    public void cropAfter() {
        super.cropAfter();
        //加载裁剪好的图片
        try {
            inputStream=openFileInput(CREATE_PATH);
            bitmap = BitmapFactory.decodeStream(inputStream);
            createImage.setImageBitmap(bitmap);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //竞猜选项
    private String select_text;
    //标签内容
    private String content_text;
    //开奖额度
    private String award_text;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null&&isLocal) {
            switch (requestCode) {
                case SELECT:
                    //竞猜选项
                    select_text = data.getStringExtra("first") + "/" + data.getStringExtra("second");
                    selectText.setText(select_text);
                    break;
                case LABLE:
                    //标签
                    content_text = data.getStringExtra("lable");
                    contentText.setText(content_text);
                    break;
                case AWARD:
                    //开奖条件
                    award_text = data.getStringExtra("number");
                    awardText.setText("满" + award_text + "即开奖");
                    break;
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

}
