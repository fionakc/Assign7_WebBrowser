package com.example.crookfion.fc_assign7_webbrowser;

public class Data {

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

}
