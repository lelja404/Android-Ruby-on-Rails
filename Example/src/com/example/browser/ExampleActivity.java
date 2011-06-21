package com.example.browser;

import android.app.Activity;
import android.webkit.*;
import android.os.Bundle;
import com.example.browser.R;

public class ExampleActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        WebView webview = (WebView) this.findViewById(R.id.webview);

        
        WebSettings websettings = webview.getSettings();

        
        websettings.setJavaScriptEnabled(true);

        
        websettings.setBuiltInZoomControls(true);
        webview.loadUrl("http://10.0.2.2:3000");
        
    }
}