package com.example.crookfion.fc_assign7_webbrowser;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {

    private String title = "";
    private String url = "";

    public Data (String t, String u){
        this.title=t;
        this.url=u;
    }

    public String getTitle(){
       return this.title;
    }

    public String getUrl(){
        return this.url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
