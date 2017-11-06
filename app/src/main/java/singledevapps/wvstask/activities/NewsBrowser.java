package singledevapps.wvstask.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import singledevapps.wvstask.R;

import static singledevapps.wvstask.utils.Utils.shareNewsUrl;

public class NewsBrowser extends AppCompatActivity {
    private WebView webView;
    private FloatingActionButton fabShareBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_browser);
        webView = (WebView) findViewById(R.id.newsWv);
        fabShareBtn = (FloatingActionButton) findViewById(R.id.fabShare);
        webView.setWebViewClient(new WebViewClient());
        webView.canGoBack();
        webView.getSettings().setJavaScriptEnabled(true);
        final String url = getIntent().getStringExtra("url");
        Log.i("urlfromintent","url:"+url);
        if (url==null){
            showMsg();
            finish();
        } else {
            webView.loadUrl(url);
        }

        fabShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareNewsUrl(NewsBrowser.this,url);

            }
        });

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
