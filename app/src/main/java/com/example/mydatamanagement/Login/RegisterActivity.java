package com.example.mydatamanagement.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.mydatamanagement.ActivityMangment.BaseActivity;
import com.example.mydatamanagement.Fun.Users;
import com.example.mydatamanagement.Http.HttpInformations;
import com.example.mydatamanagement.Http.OkHttpClientUtil;
import com.example.mydatamanagement.MainActivity;
import com.example.mydatamanagement.R;

import java.io.IOException;
import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by littlecurl 2018/6/24
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private String realCode;
    EditText mEtRegisteractivityUsername;
    EditText mEtRegisteractivityPassword1;
    EditText mEtRegisteractivityPassword2;
    EditText mEtRegisteractivityPhonecodes;
    ImageView mIvRegisteractivityShowcode;
    String username;
    String password ;
    String phoneCode ;
    boolean match = false;
    private boolean isLoadingDialog=false;
    private   LoadingDialog loadingDialog ;
    private final MyHandler myHandler = new MyHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initID();
        //将验证码用图片的形式显示出来
       mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
       realCode = Code.getInstance().getCode().toLowerCase();

    }
    /**
     * 注册页面能点击的就三个地方
     * top处返回箭头、刷新验证码图片、注册按钮
     */
    @OnClick({
            R.id.iv_registeractivity_back,
            R.id.iv_registeractivity_showCode,
            R.id.bt_registeractivity_register
    })
    private void initID(){
        mIvRegisteractivityShowcode=(ImageView)findViewById(R.id.iv_registeractivity_showCode);
        mEtRegisteractivityUsername =(EditText)findViewById(R.id.et_registeractivity_username);
        mEtRegisteractivityPassword1=(EditText)findViewById(R.id.et_registeractivity_password1);
        mEtRegisteractivityPassword2=(EditText)findViewById(R.id.et_registeractivity_password2);
        mEtRegisteractivityPhonecodes=(EditText)findViewById(R.id.et_registeractivity_phoneCodes);
        loadingDialog=new LoadingDialog(this,"正在注册...",R.mipmap.ic_dialog_loading);
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_registeractivity_back: //返回登录页面
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.iv_registeractivity_showCode:    //改变随机验证码的生成
                mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.bt_registeractivity_register:    //注册按钮
                //获取用户输入的用户名、密码、验证码
                username = mEtRegisteractivityUsername.getText().toString().trim();
                password = mEtRegisteractivityPassword2.getText().toString().trim();
                phoneCode = mEtRegisteractivityPhonecodes.getText().toString().toLowerCase();
                //注册验证
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(phoneCode) ) {
                    if (phoneCode.equals(realCode)) {
                        //将用户名和密码加入到数据库中
                        showloginDialog();
                        flashDialog(5);
                        Users mUser=new Users(username,username,password);
                        getResult(mUser);
                    } else {
                        Toast.makeText(this, "验证码错误,注册失败", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private Runnable flashDialogThread= new Runnable() {
        @Override
        public void run() {
            handlerFlashDialog();
        }
    };
    private void  flashDialog(int timout){
        new Handler().postDelayed(flashDialogThread,timout*1000);
    }
    private class MyHandler extends Handler {
        private final WeakReference<RegisterActivity> mActivity;
        MyHandler(RegisterActivity activity){
            mActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            RegisterActivity activity = mActivity.get();
            if (activity!= null){
                switch (msg.what){
                    case 1:
                        try{
                           doResult((ResponeseBody)msg.obj);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        if (isLoadingDialog) {
                            dismissloginDialog();
                            Toast.makeText(RegisterActivity.this, "连接超时，稍后重试", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        break;
                }
            }
        }
    }
    private void doResult(ResponeseBody responeseBody) {
        boolean result=responeseBody.getResult();
        if (result) match=true;
        else match=false;
        if (isLoadingDialog) dismissloginDialog();
        if (match) {
            Intent intent2 = new Intent(this, LoginActivity.class);
            startActivity(intent2);
            finish();
            Toast.makeText(this,  "验证通过，注册成功",Toast.LENGTH_SHORT).show();
            finish();//销毁此Activity
        }else
            Toast.makeText(this,responeseBody.getReason(),Toast.LENGTH_SHORT).show();
    }
    private void getResult(Users user) {
        final Users u=user;
        Runnable runnable= new Runnable() {
            @Override
            public void run() {
                try {
                  JSONObject jsObj=  JSON.parseObject(OkHttpClientUtil.postForm(HttpInformations.REGIST_URL,u));
                    handlerRegisterResult(new ResponeseBody(jsObj.getBoolean("result"),jsObj.getString("reason")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }
    private void handlerRegisterResult(ResponeseBody msg){
        Message message=Message.obtain();
        message.what=1;
        message.obj=msg;
        myHandler.sendMessage(message);
    }
    private void handlerFlashDialog(){
        Message message=Message.obtain();
        message.what=2;
       // message.obj=msg;
        myHandler.sendMessage(message);
    }
    private void showloginDialog(){
        loadingDialog.show();
        isLoadingDialog=true;
    }
    private void dismissloginDialog(){
        loadingDialog.dismiss();
        isLoadingDialog=false;
    }

}
