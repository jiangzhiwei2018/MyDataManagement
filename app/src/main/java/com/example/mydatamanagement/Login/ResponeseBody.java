package com.example.mydatamanagement.Login;

import android.os.Parcel;
import android.os.Parcelable;

public class ResponeseBody implements Parcelable {
    private boolean result;
    private String reason;
    public ResponeseBody(){
    }
    public ResponeseBody(boolean result, String reason) {
        this.result = result;
        this.reason = reason;
    }
    protected ResponeseBody(Parcel in) {
        result = in.readByte() != 0;
        reason = in.readString();
    }
    @Override
    public String toString() {
        return "ResponeseBody{" +
                "result=" + result +
                ", reason='" + reason + '\'' +
                '}';
    }
    public boolean getResult() {
        return result;
    }
    public void setResult(boolean result) {
        this.result = result;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public static final Creator<ResponeseBody> CREATOR = new Creator<ResponeseBody>() {
        @Override
        public ResponeseBody createFromParcel(Parcel in) {
            return new ResponeseBody(in);
        }
        @Override
        public ResponeseBody[] newArray(int size) {
            return new ResponeseBody[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (result ? 1 : 0));
        parcel.writeString(reason);
    }

}
