package com.example.crookfion.fc_assign7_webbrowser;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebBackForwardList;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ControlCentre {

    Activity theActivity;
    WebView webview;
    ImageButton loadPageButton;
    EditText urlText;

    ImageButton backButton;
    ImageButton homeButton;
    ImageButton fwdButton;
    ImageButton loadHistBtn;
    ImageButton backLayBtn;
    ImageButton emptyHistBtn;
    boolean hasEmptiedHist=false;
    ImageButton menuButton;
    int selectedHist ;


    String homeUrl = "https://www.google.com";
    String currentUrl="https://www.google.com";

    ListView listview;
    ArrayList<Data> arraylist = new ArrayList<>();
    private ListViewCustomAdapter listAdapter;

//    WebBackForwardList webviewHistList;
    Bundle bundle;

    ControlCentre(Activity activity){
        theActivity = activity;
    }

    public void setupMainLayout(){

        theActivity.setContentView(R.layout.activity_main);
        Log.d("layout","layout loaded");
        Log.d("geturl","currentUrl1: "+currentUrl);

        if(hasEmptiedHist){
            webview.clearHistory();
            hasEmptiedHist=false;
        }

        Log.d("geturl","currentUrl2: "+currentUrl);
        menuButton = (ImageButton) theActivity.findViewById(R.id.menuBtn);
        loadPageButton = (ImageButton) theActivity.findViewById(R.id.loadPageBtn);

        urlText = (EditText) theActivity.findViewById(R.id.urlText);
        webview = (WebView) theActivity.findViewById(R.id.mainwebview);

        //restores bundle after layout change
        if(bundle!=null){
            webview.restoreState(bundle);
        }
        Log.d("geturl","currentUrl3: "+currentUrl);
        backButton = (ImageButton) theActivity.findViewById(R.id.backBtn);
        homeButton = (ImageButton) theActivity.findViewById(R.id.homeBtn);
        fwdButton = (ImageButton) theActivity.findViewById(R.id.fwdBtn);

        //these two line allow javascript access in webview
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Log.d("pull","pulled");
        Log.d("geturl","currentUrl4: "+currentUrl);
        //opens links within webview
        webview.setWebViewClient(new MyWebViewClient());
        currentUrl = webview.getUrl();

//        if(currentUrl.matches(homeUrl)) {
//            webview.loadUrl(checkForHttp(homeUrl));
//        } //else{
//        if(currentUrl==null) {
//            webview.loadUrl(checkForHttp(homeUrl));
//        }
        //}
        Log.d("geturl","currentUrl5: "+currentUrl);
        //open history layout
        menuButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //save the webview state before changing layout
                bundle = new Bundle();
//                webviewHistList=webview.copyBackForwardList();
//                bundle.put("history",webviewHistList);
                webview.saveState(bundle);
                loadHistory();

            }
        }));

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
//                loadPageToBar();
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
//                loadPageToBar();
            }
        }));

        homeButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("click","go home");
                webview.loadUrl("https://www.google.com");
                //Log.d("current url",currentUrl);
                Log.d("geturl","gone home "+currentUrl);
//                loadPageToBar();

            }
        }));

        fwdButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("click","go forward");
                if (webview.canGoForward()) {
                    webview.goForward();
                    Log.d("geturl","gone forward "+currentUrl);
//                    loadPageToBar();
                }
                //Log.d("current url",currentUrl);
            }
        }));

        //Log.d("current url",currentUrl);
        //these two lines cause a crash
        if(webview.getUrl() !=null) {
            Log.d("geturl","loading page "+currentUrl);

//            loadPageToBar();
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
//        loadPageToBar();
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
        if(url.startsWith("http://") || url.startsWith("https://")){
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

            //load title and url into data arryalist with every page visited
            Data pageData = new Data(view.getTitle(),url);
            arraylist.add(pageData);
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
//    public void loadPageToBar(){
//        Log.d("existing url",urlText.getText().toString());
//        //String currentPage = webview.getUrl();
//        //urlText.setText(currentUrl);
//        Log.d("current url",currentUrl);
//    }

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
        loadHistBtn = (ImageButton)theActivity.findViewById(R.id.loadHistBtn);
        backLayBtn = (ImageButton) theActivity.findViewById(R.id.backLayBtn);
        emptyHistBtn = (ImageButton) theActivity.findViewById(R.id.emptyHistBtn);



//        ArrayAdapter arrayadapter = new ArrayAdapter (R.layout.single_list_item.arraylist);
        listAdapter = new ListViewCustomAdapter(theActivity,arraylist);
        listview.setAdapter(listAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                //unhappy with this line
                //Toast.makeText(theActivity, i, Toast.LENGTH_LONG).show();
                Log.d("site",arraylist.get(i).getTitle());
                TextView historyUrl = (TextView)theActivity.findViewById(R.id.historyUrlText);
                historyUrl.setText(arraylist.get(i).getTitle());
                selectedHist=i;
            }

//            @Override
//            public void OnItemClick(AdapterView<?> parent, View view, int position, long id){
//                Toast.makeText(theActivity, position, Toast.LENGTH_LONG).show();
//            }
        });

        loadHistBtn.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){

                currentUrl = arraylist.get(selectedHist).getUrl();
                setupMainLayout();

            }
        }));

        emptyHistBtn.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){

                arraylist.clear();
                hasEmptiedHist=true;
                loadHistory();
            }
        }));

        backLayBtn.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //currentUrl = arraylist.get(selectedHist).getUrl();
                setupMainLayout();

            }
        }));


    }
}





