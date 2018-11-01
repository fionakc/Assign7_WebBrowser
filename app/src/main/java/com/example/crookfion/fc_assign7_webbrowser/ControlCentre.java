package com.example.crookfion.fc_assign7_webbrowser;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class ControlCentre {

    Activity theActivity;
    WebView webview;
    Button loadPageButton;
    EditText urlText;

    Button backButton;
    Button homeButton;
    Button fwdButton;

    String currentUrl="https://www.google.com";


    ControlCentre(Activity activity){
        theActivity = activity;
    }

    public void setupMainLayout(){
        theActivity.setContentView(R.layout.activity_main);
        Log.d("layout","layout loaded");

        loadPageButton = (Button) theActivity.findViewById(R.id.loadPageBtn);

        urlText = (EditText) theActivity.findViewById(R.id.urlText);
        webview = (WebView) theActivity.findViewById(R.id.mainwebview);

        backButton = (Button) theActivity.findViewById(R.id.backBtn);
        homeButton = (Button) theActivity.findViewById(R.id.homeBtn);
        fwdButton = (Button) theActivity.findViewById(R.id.fwdBtn);

        //these two line allow javascript access in webview
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Log.d("pull","pulled");

        //opens links within webview
        webview.setWebViewClient(new MyWebViewClient());
        //currentUrl = webview.getUrl();

        loadPageButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("click","has clicked");
                String url = urlText.getText().toString();
//                Log.d("geturl","before load "+webview.getUrl());
                Log.d("geturl","before load "+currentUrl);
                webview.loadUrl(checkForHttp(url));
                Log.d("geturl","after load "+currentUrl);
                loadPageToBar();
//                String currentPage = webview.getUrl();
//                urlText.setText(currentPage);
//                Log.d("current url",currentPage);
//                WebView myWebView = (WebView) findViewById(R.id.mainwebview);
//                myWebView.loadUrl("http://www.example.com");
            }
        }));

        backButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("click","go back");
                if (webview.canGoBack()) {
                    webview.goBack();
//                    Log.d("geturl","gone back "+webview.getUrl());
//                    loadPageToBar();
                }
                //Log.d("current url",currentUrl);
                Log.d("geturl","gone back "+currentUrl);
                loadPageToBar();
            }
        }));

        homeButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("click","go home");
                webview.loadUrl("https://www.google.com");
                //Log.d("current url",currentUrl);
                Log.d("geturl","gone home "+currentUrl);
                loadPageToBar();

            }
        }));

        fwdButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("click","go forward");
                if (webview.canGoForward()) {
                    webview.goForward();
                    Log.d("geturl","gone forward "+currentUrl);
                    loadPageToBar();
                }
                //Log.d("current url",currentUrl);
            }
        }));

        //Log.d("current url",currentUrl);
        //these two lines cause a crash
        if(webview.getUrl() !=null) {
            Log.d("geturl","loading page "+currentUrl);

            loadPageToBar();
//            String currentPage = webview.getUrl();
//            Log.d("current url", currentPage);
        }
    } //end setup

    public String getUrl(){
        urlText=(EditText) theActivity.findViewById(R.id.urlText);
        String url = urlText.getText().toString();
        return url;
    }

    public void setUrl(String url){
        webview.loadUrl(checkForHttp(url));
    }

    //maybe not use this method anymore
    public void resetLayout(){
        theActivity.setContentView(R.layout.activity_main);
        Log.d("layout","layout reloaded");

    }

    public String checkForHttp(String url){

        String newUrl;
        if(url.startsWith("www.")||url.startsWith("http://") || url.startsWith("https://")){
            newUrl=url;
        } else{
            newUrl="www."+url;
        }

        if(url.startsWith("http://") || url.startsWith("https://")){
            return url;
        } else{
            //not sure how to know if page need http or https
            newUrl = "https://"+url;
            return newUrl;
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            currentUrl=url;
            //return true;

            //have to use this, else doesn't work, can't use above
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d("WebView", "your current url when webpage loading.. finish" + url);
            currentUrl=url;
            urlText.setText(currentUrl);
            super.onPageFinished(view, url);
        }
    }

    //prob don't need this, funtionality moved to onPageFinished
    public void loadPageToBar(){
        Log.d("existing url",urlText.getText().toString());
        //String currentPage = webview.getUrl();
        //urlText.setText(currentUrl);
        Log.d("current url",currentUrl);
    }
}





