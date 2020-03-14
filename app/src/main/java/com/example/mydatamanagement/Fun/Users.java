package com.example.mydatamanagement.Fun;

import android.os.Parcel;
import android.os.Parcelable;

public class Users extends UsersHead implements Parcelable {
    private float temperature;
    private double co2Concentration;
    private float humidity;
    private String location;
    private String dataTime=null;
    private double O2Concentration;
    private float CH4_CON;
    private float Light;

    protected Users(Parcel in) {
        temperature = in.readFloat();
        co2Concentration = in.readDouble();
        humidity = in.readFloat();
        location = in.readString();
        dataTime = in.readString();
        O2Concentration = in.readDouble();
        CH4_CON = in.readFloat();
        Light = in.readFloat();
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    public float getCH4_CON() {
        return CH4_CON;
    }
    public void setCH4_CON(float CH4_CON) {
        this.CH4_CON = CH4_CON;
    }
    public float getLight() {
        return Light;
    }

    public void setLight(float light) {
        Light = light;
    }

    public Users() {
        // TODO Auto-generated constructor stub
        userName=null;
        userID=null;
        temperature=0;
        co2Concentration=0;
        humidity=0;
        location=null;
        O2Concentration=0;
    }

    public Users(float temperature, float humidity, String location, String dataTime, float CH4_CON, float light) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.location = location;
        this.dataTime = dataTime;
        this.CH4_CON = CH4_CON;
        Light = light;

    }
    public Users(String userID, String userName, String userPasswd) {
        super(userID,userName,userPasswd);
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public float getTemperature() {
        return temperature;
    }
    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
    public double getCo2Concentration() {
        return co2Concentration;
    }
    public void setCo2Concentration(double co2Concentration) {
        this.co2Concentration = co2Concentration;
    }
    public float getHumidity() {
        return humidity;
    }
    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getDataTime() {
        return dataTime;
    }
    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }
    public double getO2Concentration() {
        return O2Concentration;
    }
    public void setO2Concentration(double o2Concentration) {
        O2Concentration = o2Concentration;
    }
    @Override
    public String toString() {
        return "Users{" +
                "temperature=" + temperature +
                ", co2Concentration=" + co2Concentration +
                ", humidity=" + humidity +
                ", location='" + location + '\'' +
                ", dataTime='" + dataTime + '\'' +
                ", O2Concentration=" + O2Concentration +
                ", CH4_CON=" + CH4_CON +
                ", Light=" + Light +
                ", userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", creatDate='" + creatDate + '\'' +
                ", userPasswd='" + userPasswd + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(temperature);
        parcel.writeDouble(co2Concentration);
        parcel.writeFloat(humidity);
        parcel.writeString(location);
        parcel.writeString(dataTime);
        parcel.writeDouble(O2Concentration);
        parcel.writeFloat(CH4_CON);
        parcel.writeFloat(Light);
    }
}
