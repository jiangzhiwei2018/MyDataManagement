package com.example.mydatamanagement;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mydatamanagement.ActivityMangment.BaseActivity;
import com.example.mydatamanagement.Fun.Users;
import com.example.mydatamanagement.Http.OkHttpClientUtil;
import com.example.mydatamanagement.ListUtit.MyUserAdapter;
import com.example.mydatamanagement.Util.MJsonUserUtil;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class SerchActivity  extends BaseActivity {
    private List<Users> usersList = new ArrayList<>();
    private ListView listView;
    private static final String TAG = "SerchActivity";
    private final static int FLASH_LISTVIEW=0;
    private final MyHandler myHandler = new MyHandler(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initID();
        showDatas(getIntent().getStringExtra("url"));
    }
    private void initID(){
        listView = (ListView)findViewById(R.id.list_view);
    }
    private void showDatas(final String url){
            new  Thread(new Runnable() {

                @Override
                public void run() {
                    String string = null;
                    try {
                        string = OkHttpClientUtil.get(url);
                        usersList=MJsonUserUtil.mUsers_JsonStringToUserArray(string);
                        handlerMessage(FLASH_LISTVIEW,"");
                        Looper.prepare();
                       Toast.makeText(SerchActivity.this,"找到"+usersList.size()+"条数据",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
    private class MyHandler extends Handler {
        private final WeakReference<SerchActivity> mActivity;
        MyHandler(SerchActivity activity){
            mActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            SerchActivity activity = mActivity.get();
            if (activity!= null){
                switch (msg.what){
                    case FLASH_LISTVIEW:
                        MyUserAdapter myAdapter = new MyUserAdapter(SerchActivity.this,R.layout.linkmain_item,usersList);
                        listView.setAdapter(myAdapter);
                        break;
                }
            }
        }
    }
    private void handlerMessage(int what,Object msg){
        Message message=Message.obtain();
        message.what=what;
        message.obj=msg;
        myHandler.sendMessage(message);
    }
}
