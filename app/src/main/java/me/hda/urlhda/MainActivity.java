package me.hda.urlhda;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends Activity {
    private WebView WebViewHda;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);
        WebViewHda = (WebView) findViewById(R.id.webview);
        WebViewHda.loadUrl("file:///android_asset/index.html");
        WebViewHda.getSettings().setDomStorageEnabled(true);
        WebViewHda.getSettings().setJavaScriptEnabled(true);
        WebViewHda.getSettings().setAllowUniversalAccessFromFileURLs(true);

    }
}
