package com.example.crookfion.fc_assign7_webbrowser;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class ControlCentre {

    Activity theActivity;
    WebView webview;
    ImageButton loadPageButton;
    EditText urlText;

    ImageButton backButton;
    ImageButton homeButton;
    ImageButton fwdButton;

    String currentUrl="https://www.google.com";

    ListView listview;

    ControlCentre(Activity activity){
        theActivity = activity;
    }

    public void setupMainLayout(){

        theActivity.setContentView(R.layout.activity_main);
        Log.d("layout","layout loaded");

        loadPageButton = (ImageButton) theActivity.findViewById(R.id.loadPageBtn);

        urlText = (EditText) theActivity.findViewById(R.id.urlText);
        webview = (WebView) theActivity.findViewById(R.id.mainwebview);

        backButton = (ImageButton) theActivity.findViewById(R.id.backBtn);
        homeButton = (ImageButton) theActivity.findViewById(R.id.homeBtn);
        fwdButton = (ImageButton) theActivity.findViewById(R.id.fwdBtn);

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
                closeKeyboard();
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



        //repurpose temp to get listview to work
        backButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //loadHistory();
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



    public boolean backButtonPressed(){

        boolean goesBack=false;
        if (webview.canGoBack()) {
            webview.goBack();
            goesBack=true;
        }

        Log.d("geturl","gone back "+currentUrl);
        loadPageToBar();
        return goesBack;
    }


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

        //note this method cannot handle direct ip calls
        String newUrl;

        //if not a real url, do a google search of input
        if(url.startsWith("http://www.") || url.startsWith("https://www.")){
            newUrl=url;
        } else{
            newUrl="https://www.google.com/search?q="+url;
        }


        return newUrl;
//        if(url.startsWith("www.")||url.startsWith("http://") || url.startsWith("https://")){
//            newUrl=url;
//        } else{
//            newUrl="www."+url;
//        }
//
//        if(url.startsWith("http://") || url.startsWith("https://")){
//            return url;
//        } else{
//            //not sure how to know if page need http or https
//            newUrl = "https://"+url;
//            return newUrl;
//        }
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

        //this method works instead of onPageFinished, but doesn't give as nice of a urltext experience
        //and maybe causes the emulator to crash
//        @Override
//        public void onLoadResource(WebView view, String url){
//            Log.d("WebView", "onLoadResource  " + url);
//            currentUrl=url;
//            urlText.setText(currentUrl);
//            super.onLoadResource(view, url);
//        }
    }

    //prob don't need this, funtionality moved to onPageFinished
    public void loadPageToBar(){
        Log.d("existing url",urlText.getText().toString());
        //String currentPage = webview.getUrl();
        //urlText.setText(currentUrl);
        Log.d("current url",currentUrl);
    }

    private void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)theActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(theActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    private void openKeyboard() {
        InputMethodManager imm = (InputMethodManager) theActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null){
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }

    private void loadHistory(){
        theActivity.setContentView(R.layout.listview);
        listview = (ListView) theActivity.findViewById(R.id.listview);
        ArrayList<String> arraylist = new ArrayList<>();

        arraylist.add("this");
        arraylist.add("is");
        arraylist.add("a");
        arraylist.add("listview");
        arraylist.add("test");

        //ArrayAdapter arrayadapter = new ArrayAdapter (R.layout.single_list_item.arraylist);


    }
}





