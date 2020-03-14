package com.example.mydatamanagement.Http;

import okhttp3.MediaType;

public class HttpInformations {
    public  static final String URL_STRING="";
    public  static final String IP_ADDRESS="47.101.158.218";
    public  static final MediaType JSON =MediaType.get("application/json;charset=utf-8");
    public  static final String LOGIN_URL="http://"     +IP_ADDRESS+"/MyProject3/UsersOperation/UserLogin";
    public  static final String REGIST_URL="http://"    +IP_ADDRESS+"/MyProject3/UsersOperation/UserRegist";
    public  static final String DATA_SERCH_URL="http://"+IP_ADDRESS+"/MyProject3/DatasOperation/DataSerch";
}
