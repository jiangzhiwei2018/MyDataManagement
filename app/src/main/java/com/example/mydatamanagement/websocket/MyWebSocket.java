package com.example.mydatamanagement.websocket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class MyWebSocket extends WebSocketClient {
    private static final String TAG = "MyWebSocket";
    MyWebSocket myWebSocket=null;
    public MyWebSocket(URI serverUri) {
        super(serverUri);
    }
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d(TAG,"onOpen");
        myWebSocket=this;
    }
    @Override
    public void onMessage(String message) {
        Log.d(TAG,"onMessage");
    }
    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(TAG,"onClose");
    }
    @Override
    public void onError(Exception ex) {
        Log.d(TAG,"onError");
    }
}
