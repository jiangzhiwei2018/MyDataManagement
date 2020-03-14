package com.example.mydatamanagement.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class LoginActivity extends BaseActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;
    private static final String TAG = "LoginActivity";
    private   LoadingDialog loadingDialog ;
    private final MyHandler myHandler = new MyHandler(this);
    private boolean isLoadingDialog=false;
    ImageButton imbuttonLogin,imbuttonShuaxn,imbuttonZzhuce;
    EditText passWordEdit=null,userNameEdit=null;
    String password="";
    String name="";
    boolean match = false;
    View vi;
  //  private Context logincontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intId();
        setImageButtonOclickListener();
        passwordInit();
        loadingDialog=new LoadingDialog(this,"正在登录...",R.mipmap.ic_dialog_loading);
        //logincontext=this;
    }
    private void intId(){
        imbuttonLogin=(ImageButton)findViewById(R.id.denglu);
        imbuttonShuaxn=(ImageButton)findViewById(R.id.chongzhi);
        imbuttonZzhuce=(ImageButton)findViewById(R.id.zhuce);
        passWordEdit=(EditText)findViewById(R.id.password);
        userNameEdit=(EditText)findViewById(R.id.yonghuming);
        rememberPass= (CheckBox) findViewById(R.id.remember_pass);
    }
    private void setImageButtonOclickListener(){
        imbuttonLogin.setOnClickListener(loginListener);
        imbuttonShuaxn.setOnClickListener(chonzhiListener);
        imbuttonZzhuce.setOnClickListener(zhuceListener);
    }
    View.OnClickListener loginListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            name = userNameEdit.getText().toString().trim();
             password = passWordEdit.getText().toString().trim();
            Users mUser=new Users();
            mUser.setUserID(name);
            mUser.setUserPasswd(password);
            mUser.setUserName("0");
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                showloginDialog();
               try {
                   flashDialog(5);
                     getResult(mUser);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            }
        }
    };
   private void getResult(Users user) throws  IOException{
       final Users u=user;
       Runnable runnable= new Runnable() {
           @Override
           public void run() {
               try {
                   JSONObject jsObj=  JSON.parseObject(OkHttpClientUtil.postForm(HttpInformations.LOGIN_URL,u));
                   handlerLoginResult(new ResponeseBody(jsObj.getBoolean("result"),jsObj.getString("reason")));
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       };
      new Thread(runnable).start();
    }
    View.OnClickListener chonzhiListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            passWordEdit.setText("");
            userNameEdit.setText("");
        }
    };
    View.OnClickListener zhuceListener =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent();
            intent.setClass(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
    };
    private void passwordInit(){
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember=preferences.getBoolean("remember_password",false);
        if (isRemember){//将密码和账号设置到文本框中
            String account=preferences.getString("account","");
            String password=preferences.getString("password","");
            userNameEdit.setText(account);
            passWordEdit.setText(password);
            rememberPass.setChecked(true);
        }
    }
    private void showloginDialog(){
        loadingDialog.show();
        isLoadingDialog=true;
    }
    private void dismissloginDialog(){
        loadingDialog.dismiss();
        isLoadingDialog=false;
    }
    public void showIosDialog(View v){
        IosPopupWindow iosPopupWindow = new IosPopupWindow(this);
        iosPopupWindow.showAtScreenBottom(v);
    }
    private class MyHandler extends Handler{
        private final WeakReference<LoginActivity> mActivity;
        MyHandler(LoginActivity activity){
            mActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            LoginActivity activity = mActivity.get();
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
                            Toast.makeText(LoginActivity.this, "登陆超时，稍后重试", Toast.LENGTH_SHORT).show();
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
            editor = preferences.edit();
            if (rememberPass.isChecked()) {
                editor.putString("account", name);
                editor.putString("password", password);
                editor.putBoolean("remember_password", true);
            }else editor.clear();
            Toast.makeText(LoginActivity.this,"登录成功", Toast.LENGTH_SHORT).show();
            editor.apply();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();//销毁此Activity
        }else Toast.makeText(LoginActivity.this,"用户名或密码错误", Toast.LENGTH_SHORT).show();
    }
    private void handlerLoginResult(ResponeseBody responeseBody){
        Message message=Message.obtain();
        message.what=1;
        message.obj=responeseBody;
        myHandler.sendMessage(message);
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
    private void handlerFlashDialog(){
        Message message=Message.obtain();
        message.what=2;
        // message.obj=msg;
        myHandler.sendMessage(message);
    }
}
