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
        webview.setWebViewClient(new WebViewClient());

        loadPageButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("click","has clicked");
                String url = urlText.getText().toString();
                Log.d("url",url);
                webview.loadUrl(url);

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

                }
            }
        }));

        homeButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("click","go home");
                webview.loadUrl("https://www.google.com");

            }
        }));

        fwdButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("click","go forward");
                if (webview.canGoForward()) {
                    webview.goForward();

                }
            }
        }));

    } //end setup

    public String getUrl(){
        urlText=(EditText) theActivity.findViewById(R.id.urlText);
        String url = urlText.getText().toString();
        return url;
    }

    public void setUrl(String url){
        webview.loadUrl(url);
    }

    //maybe not use this method anymore
    public void resetLayout(){
        theActivity.setContentView(R.layout.activity_main);
        Log.d("layout","layout reloaded");

    }

}
