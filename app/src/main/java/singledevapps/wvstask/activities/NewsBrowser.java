package singledevapps.wvstask.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import singledevapps.wvstask.R;

public class NewsBrowser extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_browser);
        webView = (WebView) findViewById(R.id.newsWv);
        webView.setWebViewClient(new WebViewClient());
        webView.canGoBack();
        webView.getSettings().setJavaScriptEnabled(true);
        String url = getIntent().getStringExtra("url");
        Log.i("urlfromintent","url:"+url);
        if (url==null){
            showMsg();
            finish();
        } else {
            webView.loadUrl(url);
        }

    }

    private void showMsg(){
        Toast.makeText(NewsBrowser.this,"Oops! Something went wrong!!",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Destroy","Called");
    }
}
