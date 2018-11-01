package com.example.crookfion.fc_assign7_webbrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ControlCentre control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        control = new ControlCentre(this);
        control.setupMainLayout();
    }

//    @Override
////    public void onSaveInstanceState(Bundle outState) {
////
////        super.onSaveInstanceState(outState);
////        String urlToSave=control.getUrl();
////
////        outState.putString("Url",urlToSave);
////
////        Log.d("onSaveInst",urlToSave);
////
////    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle bundle = new Bundle();
        ((WebView) findViewById(R.id.mainwebview)).saveState(bundle);
        outState.putBundle("webViewState", bundle);
        Log.d("onSaveInst","new save inst");
    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        //control.resetLayout();
//        String url = savedInstanceState.getString("Url");
//
//
//        control.setUrl(url);
//
//        Log.d("onRestInst", url);
//
//    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null)
            ((WebView) findViewById(R.id.mainwebview)).restoreState(savedInstanceState.getBundle("webViewState"));
        else {
//            webView = (WebView) findViewById(R.id.mainwebview);
//            webView.loadUrl("https://www.YAHOO.com/");
            control.setUrl("https://www.google.com");
        }
    }
    @Override
    public void onBackPressed() {
        boolean goesBack = control.backButtonPressed();
        if(!goesBack){
            super.onBackPressed();
        }
    }
}
