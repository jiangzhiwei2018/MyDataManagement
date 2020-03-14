package com.example.mydatamanagement.Http;

import android.util.Log;

import com.example.mydatamanagement.Fun.Users;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class OkHttpClientUtil {
    private static final String TAG = "OkHttpClientUtil";
    //json传输方式
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    //获取okHttpClient对象
    private final static OkHttpClient client =OkHttpClientObject.getInstance();
    private String result;
    /**
     * get形式,同步执行
     */
    public static String get(String url) throws IOException {
        //创建请求
       final Request request = new Request.Builder()
                .url(url)
                .build();
        //同步执行请求，将响应结果存放到response中
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            //处理response的响应消息
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
    /**
     * post形式
     */
    public String post(String url, String message) throws IOException {
        //请求体传输json格式的数据
        RequestBody requestBody = RequestBody.create(message,JSON);
        //创建请求
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "*****")
                .addHeader("Accept", "*****")
                .post(requestBody)
                .build();
        //同步请求
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            return response.body().string();
        }else {
            throw new IOException("Unexpected code " + response);
        }
    }
    /**
     * post形式提交表单
     */
    public static String postForm(String url,Users user) throws IOException {
        //创建请求
        FormBody formBody = new FormBody.Builder()
                .add("userId", user.getUserID())
                .add("userName",user.getUserName())
                .add("passWd",user.getUserPasswd())
                .build();
        final Request request = new Request.Builder()
                .url(url)//请求的url
                .post(formBody)
                .build();
        //同步请求
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()){
            return response.body().string();
        }else {
            throw new IOException("Unexpected code:"+response);
        }
    }

    /**
     * post形式提交表单
     */
    public static String postForm(String url) throws IOException {
        //创建请求
        FormBody formBody = new FormBody.Builder()

                .build();
        final Request request = new Request.Builder()
                .url(url)//请求的url
                .post(formBody)
                .build();
        //同步请求
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()){
            return response.body().string();
        }else {
            throw new IOException("Unexpected code:"+response);
        }
    }

    /**
     * 异步发起请求
     */
    public void pool(String url) {
        //创建请求
        Request request = new Request.Builder()
                .url(url)
                .build();
        //异步请求
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"请求失败");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG,response.body().string());
            }
        });
    }
}
