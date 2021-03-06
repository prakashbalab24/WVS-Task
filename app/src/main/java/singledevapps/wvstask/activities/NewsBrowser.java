package singledevapps.wvstask.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import singledevapps.wvstask.R;

import static singledevapps.wvstask.utils.Utils.shareNewsUrl;

public class NewsBrowser extends AppCompatActivity {
    private WebView webView;
    private FloatingActionButton fabShareBtn;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_browser);
        webView = (WebView) findViewById(R.id.newsWv);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        fabShareBtn = (FloatingActionButton) findViewById(R.id.fabShare);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
                if (progress >=80) {
                    progressBar.setVisibility(View.GONE);

                } else {
                    progressBar.setVisibility(View.VISIBLE);

                }
            }
        });
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.getSettings().setJavaScriptEnabled(false);
        Log.i("Destroy","Called");
    }
}
