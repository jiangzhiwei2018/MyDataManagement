package com.example.mydatamanagement.Fun;

import android.os.Parcel;
import android.os.Parcelable;

public class UsersHead implements Parcelable {
    protected String userID;
    protected String userName;
    protected String creatDate;
    protected String userPasswd;
    public UsersHead() {
        // TODO Auto-generated constructor stub
        creatDate=null;
        userID=null;
        userName=null;
        userPasswd=null;
    }

    public UsersHead(String userID, String userName, String userPasswd) {
        this.userID = userID;
        this.userName = userName;
        this.userPasswd = userPasswd;
    }

    protected UsersHead(Parcel in) {
        userID = in.readString();
        userName = in.readString();
        creatDate = in.readString();
        userPasswd = in.readString();
    }

    public static final Creator<UsersHead> CREATOR = new Creator<UsersHead>() {
        @Override
        public UsersHead createFromParcel(Parcel in) {
            return new UsersHead(in);
        }

        @Override
        public UsersHead[] newArray(int size) {
            return new UsersHead[size];
        }
    };

    @Override
    public String toString() {
        return "UsersHead{" +
                "userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", creatDate='" + creatDate + '\'' +
                ", userPasswd='" + userPasswd + '\'' +
                '}';
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCreatDate(String creatDate) {
        this.creatDate = creatDate;
    }

    public void setUserPasswd(String userPasswd) {
        this.userPasswd = userPasswd;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getCreatDate() {
        return creatDate;
    }

    public String getUserPasswd() {
        return userPasswd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userID);
        parcel.writeString(userName);
        parcel.writeString(creatDate);
        parcel.writeString(userPasswd);
    }
}
