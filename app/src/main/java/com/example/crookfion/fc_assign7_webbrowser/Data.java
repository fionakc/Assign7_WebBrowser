/**
 * SWEN502 - Assignment 7 - Web Browser
 * Fiona Crook
 * 300442873
 */

package com.example.crookfion.fc_assign7_webbrowser;

//holds the data for the listview
//holds two strings, the webpage Title, and webpage Url
public class Data  {

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
