package com.example.crookfion.fc_assign7_webbrowser;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class ControlCentre {

    Activity theActivity;
    WebView webview;
    Button loadPageButton;
    EditText urlText;

    ControlCentre(Activity activity){
        theActivity = activity;
    }

    public void setupMainLayout(){
        theActivity.setContentView(R.layout.activity_main);
        Log.d("layout","layout loaded");

        loadPageButton = (Button) theActivity.findViewById(R.id.loadPageBtn);
        urlText = (EditText) theActivity.findViewById(R.id.urlText);
        webview = (WebView) theActivity.findViewById(R.id.mainwebview);
        Log.d("pull","pulled");
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
    }

    public String getUrl(){
        urlText=(EditText) theActivity.findViewById(R.id.urlText);
        String url = urlText.getText().toString();
        return url;
    }

    public void setUrl(String url){
        webview.loadUrl(url);
    }

    public void resetLayout(){
        theActivity.setContentView(R.layout.activity_main);
        Log.d("layout","layout reloaded");

    }

}
