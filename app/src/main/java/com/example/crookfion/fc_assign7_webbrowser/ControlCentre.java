/**
 * SWEN502 - Assignment 7 - Web Browser
 * Fiona Crook
 * 300442873
 */

package com.example.crookfion.fc_assign7_webbrowser;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;


public class ControlCentre {

    private Activity theActivity;

    private WebView webview;

    private EditText urlText;
    private ImageButton loadPageButton;
    private ImageButton backButton;
    private ImageButton homeButton;
    private ImageButton fwdButton;
    private ImageButton loadHistBtn;
    private ImageButton backLayBtn;
    private ImageButton emptyHistBtn;
    private ImageButton menuButton;

    private boolean hasEmptiedHist=false;
    private int selectedHist ;
    private boolean inHistLayout;
    private boolean loadHistPage=false;

    private String currentUrl="https://www.google.com";

    private ListView listview;
    private ArrayList<Data> arraylist = new ArrayList<>();
    private ListViewCustomAdapter listAdapter;

    private Bundle bundle;

    //ControlCentre constructor
    ControlCentre(Activity activity){

        theActivity = activity;
    }


    //initialises the Main layout
    public void setupMainLayout(){
        inHistLayout=false;
        theActivity.setContentView(R.layout.activity_main);

        //call the layout components
        menuButton = (ImageButton) theActivity.findViewById(R.id.menuBtn);
        loadPageButton = (ImageButton) theActivity.findViewById(R.id.loadPageBtn);
        urlText = (EditText) theActivity.findViewById(R.id.urlText);
        webview = (WebView) theActivity.findViewById(R.id.mainwebview);
        backButton = (ImageButton) theActivity.findViewById(R.id.backBtn);
        homeButton = (ImageButton) theActivity.findViewById(R.id.homeBtn);
        fwdButton = (ImageButton) theActivity.findViewById(R.id.fwdBtn);

        //restores bundle after layout change
        if(bundle!=null){
            webview.restoreState(bundle);
        }

        //if have emptied page history arraylist, clear list from webview also
        //this is currently not working correctly
        if(hasEmptiedHist){
            webview.clearHistory();
            hasEmptiedHist=false;

        }

        //these two line allow javascript access in webview
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //opens links within webview
        webview.setWebViewClient(new MyWebViewClient());
        currentUrl = webview.getUrl();

        //load selected page from history view
        if(loadHistPage){
            currentUrl = arraylist.get(selectedHist).getUrl();
            webview.loadUrl(currentUrl);
            loadHistPage=false;
        }

        //button listener to open History layout
        menuButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //save the webview state before changing layout
                bundle = new Bundle();
                webview.saveState(bundle);
                loadHistory();

            }
        }));

        //button listener to load selected page
        loadPageButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //close the soft keyboard
                closeKeyboard();

                //get url from urlText field, load into webview after checking for http
                String url = urlText.getText().toString();
                webview.loadUrl(checkForHttp(url));
            }
        }));


        //button listener to go back a page if available
        backButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (webview.canGoBack()) {
                    webview.goBack();
                }
            }
        }));

        //button listener to load the home page (hardcoded)
        homeButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){

                webview.loadUrl("https://www.google.com");
            }
        }));

        //button listener to go forward a page if available
        fwdButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (webview.canGoForward()) {
                    webview.goForward();
                }
            }
        }));

    } //end setupMainLayout


    //back button method for physical back button
    //if can go back, does so and posts a boolean back to MainActivity.onBackPressed()
    public boolean backButtonPressed(){

        boolean goesBack=false;
        if (webview.canGoBack()) {
            webview.goBack();
            goesBack=true;
        }

        return goesBack;
    }

    //sets the webview to the passed in string
    public void setUrl(String url){
        webview.loadUrl(checkForHttp(url));
    }


    //takes a string from the urlText field, checks for http:// or https:// on the front
    //if not detected, returns a google search of the string
    //note this method means the browser cannot handle direct ip calls
    private String checkForHttp(String url){

        String newUrl;

        //if not a real url, do a google search of input
        if(url.startsWith("http://") || url.startsWith("https://")){
            newUrl=url;
        } else{
            newUrl="https://www.google.com/search?q="+url;
        }

        return newUrl;
    }

    //override some inbuilt methods of WebViewClient
    private class MyWebViewClient extends WebViewClient {

        //keep all pages loading within the webview
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            currentUrl=url;

            return super.shouldOverrideUrlLoading(view, url);
        }

        //when page starts loading, move url to the urlText bar
        @Override
        public void onPageStarted(WebView view, String url, Bitmap bitmap) {

            currentUrl=url;
            urlText.setText(currentUrl);

            super.onPageStarted(view, url, bitmap);
        }

        //when page has finished loading, move url to urlText bar
        //and save page Title and url to page history arraylist
        @Override
        public void onPageFinished(WebView view, String url) {

            currentUrl=url;
            urlText.setText(currentUrl);

            //down here because need page to finish loading before can grab title
            Data pageData = new Data(view.getTitle(),url);

            arraylist.add(pageData);
            super.onPageFinished(view, url);
        }

    } //end MyWebViewClient


    //closes the soft keyboard onscreen
    //code adapted from a stackoverflow post
    private void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)theActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(theActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    //initialises the History layout
    public void loadHistory(){
        inHistLayout=true;

        theActivity.setContentView(R.layout.listview);

        //call the layout components
        listview = (ListView) theActivity.findViewById(R.id.listview);
        loadHistBtn = (ImageButton)theActivity.findViewById(R.id.loadHistBtn);
        backLayBtn = (ImageButton) theActivity.findViewById(R.id.backLayBtn);
        emptyHistBtn = (ImageButton) theActivity.findViewById(R.id.emptyHistBtn);


        //list view code adapted from Karsten's slides
        listAdapter = new ListViewCustomAdapter(theActivity,arraylist);
        listview.setAdapter(listAdapter);

        //click listener for data objects in listview
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                TextView historyUrl = (TextView)theActivity.findViewById(R.id.historyUrlText);
                historyUrl.setText(arraylist.get(i).getTitle());
                selectedHist=i;
            }

//            @Override
//            public void OnItemClick(AdapterView<?> parent, View view, int position, long id){
//                Toast.makeText(theActivity, position, Toast.LENGTH_LONG).show();
//            }
        });

        //button listener to load selected page
        loadHistBtn.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                loadHistPage=true;
                setupMainLayout();

            }
        }));

        //button listener to empty out the page history list
        emptyHistBtn.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){

                arraylist.clear();
                hasEmptiedHist=true;
                Log.d("hasEmptiedHist",Boolean.toString(hasEmptiedHist));
                loadHistory();
            }
        }));

        //button listener to go back to Main layout
        backLayBtn.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){

                setupMainLayout();

            }
        }));


    } //end loadHistory


    //used to tell MainActivity if History layout is in focus
    public boolean inHistoryView(){
        return inHistLayout;
    }

    //repopulates the page history arraylist, called from MainActivity.onRestoreState
    public void reloadPageHistory(){

            WebBackForwardList webviewHistList = webview.copyBackForwardList();
            int listSize = webviewHistList.getSize();
            for(int i = 0; i < listSize; i++)
            {
                WebHistoryItem webHistoryItem = webviewHistList.getItemAtIndex(i);
                String title = webHistoryItem.getTitle();
                String url = webHistoryItem.getUrl();
                Data pageData = new Data(title,url);
                arraylist.add(pageData);

            }
    }

}





