package com.example.crookfion.fc_assign7_webbrowser;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {

    String currentUrl;

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        currentUrl=url;
        return true;
    }
}
