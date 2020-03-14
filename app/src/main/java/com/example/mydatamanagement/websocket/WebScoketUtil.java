package com.example.mydatamanagement.websocket;

import android.util.Log;

import java.util.concurrent.TimeUnit;

public class WebScoketUtil {

    /**
     * 建立连接
     * @param mWebSocket
     * @return
     */
    public static boolean connectWebSocketServer(MyWebSocket mWebSocket){
        try {
          return  mWebSocket.connectBlocking(2,TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            Log.d("CONNECT","连接失败");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 断开连接
     * @param mWebSocket
     */
    public static void closeConnect(MyWebSocket mWebSocket){
        try {
            if (null!=mWebSocket)
                mWebSocket.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mWebSocket=null;
        }
    }

}
