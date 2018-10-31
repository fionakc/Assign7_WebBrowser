package com.example.crookfion.fc_assign7_webbrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("layout","layout loaded");

        Button button = (Button) findViewById(R.id.button);
        final EditText edittext = (EditText) findViewById(R.id.editText);
        final WebView webview = (WebView) findViewById(R.id.mainwebview);
        Log.d("pull","pulled");
        webview.setWebViewClient(new WebViewClient());

        button.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("click","has clicked");
                String url = edittext.getText().toString();
                Log.d("url",url);
                webview.loadUrl(url);

//                WebView myWebView = (WebView) findViewById(R.id.mainwebview);
//                myWebView.loadUrl("http://www.example.com");
            }
        }));
    }
}
