package com.example.mydatamanagement.Http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkHttpClientObject {

    private static Integer connectTimeout_time = 10;
    private static Integer writeTimeout_time = 10;
    private static Integer readTimeout_time = 10;
    public static OkHttpClient instance;


    private OkHttpClientObject() {}
    public static OkHttpClient getInstance() {
        if (instance == null) {
            synchronized (OkHttpClientObject.class) {
                if (instance == null) {
                    //配置了网络请求的超时时间
                    instance = new OkHttpClient().newBuilder()
                            .connectTimeout(connectTimeout_time, TimeUnit.SECONDS)
                            .readTimeout(readTimeout_time, TimeUnit.SECONDS)
                            .writeTimeout(writeTimeout_time, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return instance;
    }
}
