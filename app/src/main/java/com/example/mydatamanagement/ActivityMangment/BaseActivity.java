package com.example.mydatamanagement.ActivityMangment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class BaseActivity extends AppCompatActivity {

    private WeakReference<Activity> weakReference = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将activity添加到列表中
        if (weakReference==null) {
            weakReference =  new WeakReference<Activity>(this);
        }
        ActivityManager.getInstance().addActivity(weakReference.get());

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //将activity从列表中移除
        ActivityManager.getInstance().removeActivity(weakReference.get());
    }




}