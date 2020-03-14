package com.example.mydatamanagement.Fun.DataBase;

import com.example.mydatamanagement.Fun.Users;

import java.util.ArrayList;
import java.util.List;

public class DataSqlUtil {

    private final static String SERCH_URL="/DatasOperation/DataSerch";
    private final static String DELETE_URL="/DatasOperation/DataDelete";
    private final static String UPDATA_URL="/DatasOperation/DataUpdate";
    private final static String INSERT_URL="/DatasOperation/DataInsert";
    public DataSqlUtil() {

    }
    List<Users> getUsers(String url,int count){
        String realUrl=url+SERCH_URL;
        List<Users> list=new ArrayList<Users>(count);
     return list;
    }
}
