package com.example.mydatamanagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.biansemao.widget.ThermometerView;
import com.example.mydatamanagement.ActivityMangment.BaseActivity;
import com.example.mydatamanagement.Http.HttpInformations;
import com.example.mydatamanagement.Login.IosPopupWindow;
import com.example.mydatamanagement.Util.InputDialog;
import com.example.mydatamanagement.websocket.MyWebSocket;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import am.widget.circleprogressbar.CircleProgressBar;



public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{
    URI uri =null;
    MyWebSocket client;
    TextView dataText=null;
    private static final  String IP_ADDRESS="ws://"+HttpInformations.IP_ADDRESS+"/MyProject3/websocket";
    private static final String TAG = "MainActivity";
    private boolean isConnected=false;
    private final MyHandler myHandler = new MyHandler(this);
    private MenuItem connectMenu=null,shareMenuItem=null;
    private static final int CONNECT_SUCCEFFUL=0;
    private static final int CONNECT_FAIL=1,WEBSCOKET_MESG=2;
    Button button=null;
    View shareMenu=null;
    private ThermometerView thermometerTv;
    CircleProgressBar cpbDemo_Hum=null,cpbDemo_light=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        uri = URI.create(IP_ADDRESS);
        shareMenu=View.inflate(this, R.layout.activity_main, null);//将一个布局文件转换成一个view对象
        initID();
        initCircleProgressBar();
    }
    private void initID(){
         cpbDemo_Hum = (CircleProgressBar) findViewById(R.id.humCr);
         cpbDemo_light=(CircleProgressBar) findViewById(R.id.lightCr);
         dataText=(TextView)findViewById(R.id.data_text);
         thermometerTv = findViewById(R.id.mTemp);
    }
    private void initCircleProgressBar(){
        cpbDemo_Hum.setProgress(0);
        cpbDemo_Hum.setProgressSize(20);
        cpbDemo_Hum.setProgressMode(CircleProgressBar.ProgressMode.PROGRESS);
       cpbDemo_Hum.setShowProgressValue(true);

        cpbDemo_light.setProgress(0);
        cpbDemo_light.setProgressSize(20);
        cpbDemo_light.setProgressMode(CircleProgressBar.ProgressMode.PROGRESS);
        cpbDemo_light.setShowProgressValue(true);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        connectMenu=menu.findItem(R.id.connectingwebscoket);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
                break;
            case R.id.connectingwebscoket:
                if (!isConnected)
                    webSocketTest(uri);
                else closeConnect(client);
                break;
            case R.id.history_data:
                historyDataActionItem();
                break;
        }
        return true;
    }
    private float getRandomValue(){
        float value = new Random().nextFloat() * 7 + 10;
        Toast.makeText(MainActivity.this, "current value: " + value,Toast.LENGTH_SHORT).show();

        return value;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_camera:break;

            case R.id.nav_gallery:break;

            case R.id.nav_slideshow:break;

            case R.id.nav_manage:break;

            case R.id.nav_share:
                showIosDialog(shareMenu);
                break;
            case R.id.nav_send:break;
            default: break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void webSocketTest (URI uri) {
        client = new MyWebSocket(uri) {
            @Override
            public void onMessage(String message) {
               final String msg=message;
            // handlerMessage(WEBSCOKET_MESG,message);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       dataText.setText(getTextStringFromValue(
                               JSON.parseObject(msg)
                                       .getJSONObject("DataObj")
                       ));
                    }
                });
            }
        };
        if (client==null) return;
      Runnable runnable= new Runnable() {
            @Override
            public void run() {
                try {
                    if (client.connectBlocking(3,TimeUnit.SECONDS)) {
                        handlerMessage(CONNECT_SUCCEFFUL,null);
                    }
                    else {
                        handlerMessage(CONNECT_FAIL,null);
                    }
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
      Thread mTread=new Thread(runnable);
      mTread.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeConnect(client);
        finish();
    }
    private void closeConnect(MyWebSocket mWebSocket){
        try {
            if (null!=mWebSocket)
                mWebSocket.close();
        }catch (Exception e){
            isConnected=false;
            e.printStackTrace();
        }finally {
            mWebSocket=null;
        }
        isConnected=false;
        connectMenu.setIcon(R.drawable.kehuduanlianjie);
        Toast.makeText(MainActivity.this,"连接关闭",Toast.LENGTH_SHORT).show();
    }
    private String getTextStringFromValue(JSONObject jsonObject){
        float temp=jsonObject.getFloatValue("Temp");
        float humi=jsonObject.getFloatValue("Humi");
        float ch4=jsonObject.getFloatValue("CH4");
        int light=jsonObject.getInteger("Light");
        thermometerTv.setValueAndStartAnim(temp);
        cpbDemo_Hum.setProgress((int)humi);
        cpbDemo_light.setProgress(light);
        return "温度:"+temp+"℃\n湿度:"+humi+"%\n光照强度:"+light+"x\n甲烷指数:"+(ch4<30 ?"正常":"超标");
    }
    private class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;
        MyHandler(MainActivity activity){
            mActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity!= null){
                switch (msg.what){
                    case CONNECT_SUCCEFFUL:
                        connectMenu.setIcon(R.drawable.lianjiechenggong);
                        Toast.makeText(MainActivity.this,"连接成功",Toast.LENGTH_SHORT).show();
                        isConnected=true;
                        break;
                    case CONNECT_FAIL:
                        if (isConnected) {
                            isConnected=false;
                        }
                        Toast.makeText(MainActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                        connectMenu.setIcon(R.drawable.kehuduanlianjie);
                        break;
                    case WEBSCOKET_MESG:
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
    private void showIosDialog(View v){
        IosPopupWindow iosPopupWindow = new IosPopupWindow(this);
        iosPopupWindow.showAtScreenBottom(v);
    }
    private void setTempValueUI(float newValue){
        thermometerTv.setValueAndStartAnim(newValue);
    }

    private void historyDataActionItem(){
        InputDialog dialog=new InputDialog(MainActivity.this, new InputDialog.OnEditInputFinishedListener() {
            @Override
            public void editInputFinished(String id, String count) {
                Intent intent = new Intent();
                intent.putExtra("url",HttpInformations.DATA_SERCH_URL+"?product_id="+id+"&Cont="+count);
                intent.setClass(MainActivity.this,SerchActivity.class);
                startActivity(intent);
            }
        });
        dialog.setView(new EditText(MainActivity.this));//若对话框无法弹出输入法，加上这句话
        dialog.show();
    }

    @Override
    protected void onStop() {
       closeConnect(client);
        super.onStop();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
