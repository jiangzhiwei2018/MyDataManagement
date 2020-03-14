package com.example.mydatamanagement.Http;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    public static final MediaType JSON =MediaType.get("application/json;charset=utf-8");
    public  static OkHttpClient client = new OkHttpClient();
    public static String run(String url) throws IOException {
          Request request = new Request.Builder()
                .url(url)
                .build();
          try (Response response = client.newCall(request).execute()){
                return response.body().string();
         }
    }
    public static String postJson(String url,String json) throws IOException {
        RequestBody body = RequestBody.create(json,JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
