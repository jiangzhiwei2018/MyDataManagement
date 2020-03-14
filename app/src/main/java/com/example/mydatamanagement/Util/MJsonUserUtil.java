package com.example.mydatamanagement.Util;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.mydatamanagement.Fun.Users;
import java.util.ArrayList;

public class MJsonUserUtil {

    private static final String TAG = "MJsonUserUtil";
   public static ArrayList<Users> mUsers_JsonStringToUserArray(String jsString){
       JSONObject jsObj=JSON.parseObject(jsString);
        if (jsObj.getBoolean("result")){
            JSONArray  jsonArray=jsObj.getJSONArray("DataObj");
            Log.d(TAG,jsString);
            int size=jsObj.getInteger("Realcount");
            ArrayList<Users> usersArrayList=new ArrayList<Users>(size);
          for (int i=0;i<size;i++){
                Users mUser=new Users();
                JSONObject mObj=jsonArray.getJSONObject(i);
                mUser.setTemperature(mObj.getFloatValue("Temp"));
                mUser.setLocation(mObj.getString("Loca"));
                mUser.setDataTime(mObj.getString("DataTime"));
                mUser.setTemperature(mObj.getFloatValue("Temp"));
                mUser.setHumidity(mObj.getFloatValue("Humi"));
                mUser.setLight(mObj.getInteger("Light"));
                mUser.setUserID(mObj.getString("Id"));
                usersArrayList.add(mUser);
            }
            return usersArrayList;
        }
        return null;
    }
}
