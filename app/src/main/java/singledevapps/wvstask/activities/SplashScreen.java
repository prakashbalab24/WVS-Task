package singledevapps.wvstask.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import singledevapps.wvstask.MainActivity;
import singledevapps.wvstask.R;
import singledevapps.wvstask.helper.Apis;
import singledevapps.wvstask.helper.NetworkCalls;
import singledevapps.wvstask.helper.NetworkHelper;
import singledevapps.wvstask.helper.NewsSouce;
import singledevapps.wvstask.helper.SharedPrefHelper;

public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_LENGTH = 2000;
    private Handler handler;
    private Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        handler = new Handler();
        new LoadNews().execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(handler!= null && runnable != null) {
            handler.removeCallbacks(runnable);
            finish();
        }
    }

   private class LoadNews extends AsyncTask<Void,Void,Void>{

       @Override
       protected void onPostExecute(Void aVoid) {
           super.onPostExecute(aVoid);
       }

       @Override
        protected Void doInBackground(Void... params) {
            if (NetworkHelper.checkConnection(SplashScreen.this)) {
                for (String aNewsSourceList : NewsSouce.newsSourceList) {
                    NetworkCalls nc = new NetworkCalls();
                    String jsonStr = nc.getServerCall(Apis.getHitUrl(aNewsSourceList));
                    try {
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                            SharedPrefHelper.saveJsonOffline(SplashScreen.this, jsonStr, aNewsSourceList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            handler.postDelayed(runnable = new Runnable(){
                @Override
                public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }
}
