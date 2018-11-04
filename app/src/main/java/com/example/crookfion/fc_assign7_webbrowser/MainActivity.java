/**
 * SWEN502 - Assignment 7 - Web Browser
 * Fiona Crook
 * 300442873
 */

package com.example.crookfion.fc_assign7_webbrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;


public class MainActivity extends AppCompatActivity {

    ControlCentre control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hides top ActionBar
        this.getSupportActionBar().hide();

        //creates and sets up the main browser layout
        control = new ControlCentre(this);
        control.setupMainLayout();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //check if in History layout
        boolean inHistLayout=control.inHistoryView();

        //if in History Layout, load Main layout
        if(inHistLayout) {
            control.setupMainLayout();
        }

        //code adapted from a stackoverflow post
        //save webview state as a bundle, save into a bundle
        Bundle bundle = new Bundle();
        ((WebView) findViewById(R.id.mainwebview)).saveState(bundle);
        outState.putBundle("webViewState", bundle);

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //code adapted from a stackoverflow post
        //if there is something in the bundle to restore
        if (savedInstanceState != null){

            //restore the webview bundle
            ((WebView) findViewById(R.id.mainwebview)).restoreState(savedInstanceState.getBundle("webViewState"));
            control.reloadPageHistory();
    } else {

            //otherwise, load google search page
            control.setUrl("https://www.google.com");
        }
    }


    @Override
    public void onBackPressed() {
        boolean goesBack = control.backButtonPressed();
        boolean inHistLayout=control.inHistoryView();
        if(inHistLayout){
            control.setupMainLayout();

        } else if(!goesBack){
            super.onBackPressed();
        }
    }
}
