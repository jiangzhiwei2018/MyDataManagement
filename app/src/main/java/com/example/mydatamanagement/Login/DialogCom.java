package com.example.mydatamanagement.Login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.mydatamanagement.R;

public class DialogCom {
   public static   LoadingDialog loadingDialog;
    public static void show(@NonNull Context context, String msg){
        loadingDialog = new LoadingDialog(context,msg,R.mipmap.ic_dialog_loading);
    }
}
