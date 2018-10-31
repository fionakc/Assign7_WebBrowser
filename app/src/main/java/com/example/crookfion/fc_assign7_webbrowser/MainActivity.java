package com.example.crookfion.fc_assign7_webbrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        control = new ControlCentre(this);
        control.setupMainLayout();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        String urlToSave=control.getUrl();

        outState.putString("Url",urlToSave);

        Log.d("onSaveInst",urlToSave);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //control.resetLayout();
        String url = savedInstanceState.getString("Url");
        control.setUrl(url);

        Log.d("onRestInst", url);

    }
}
