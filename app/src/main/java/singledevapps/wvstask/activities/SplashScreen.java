package singledevapps.wvstask.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import singledevapps.wvstask.MainActivity;
import singledevapps.wvstask.R;
import singledevapps.wvstask.helper.Apis;
import singledevapps.wvstask.model.News;
import singledevapps.wvstask.model.data.local.DatabaseHandler;
import singledevapps.wvstask.model.data.remote.NetworkCalls;
import singledevapps.wvstask.helper.NetworkHelper;
import singledevapps.wvstask.model.data.remote.NewsSouce;
import singledevapps.wvstask.helper.SharedPrefHelper;

public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_LENGTH = 1000;
    private static final int NETSLOW = 15000;
    private Handler handler,handler2;
    private Runnable runnable,runnable2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        handler = new Handler();
        handler2 = new Handler();
        handler2.postDelayed(runnable2 = new Runnable(){
            @Override
            public void run() {
                Toast.makeText(SplashScreen.this,"Slow Internet, Please Wait!!",Toast.LENGTH_LONG).show();
            }
        }, NETSLOW);

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
       private List<News> newsList;


//       @Override
//        protected Void doInBackground(Void... params) {
//            if (NetworkHelper.checkConnection(SplashScreen.this)) {
//                for (String aNewsSourceList : NewsSouce.newsSourceList) {
//                    NetworkCalls nc = new NetworkCalls();
//                    String jsonStr = nc.getServerCall(Apis.getHitUrl(aNewsSourceList));
//                    try {
//                        JSONObject jsonObject = new JSONObject(jsonStr);
//                        if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
//                            SharedPrefHelper.saveJsonOffline(SplashScreen.this, jsonStr, aNewsSourceList);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//
//                    }
//                }
//            }
//            return null;
//        }
@Override
protected Void doInBackground(Void... params) {
    if (NetworkHelper.checkConnection(SplashScreen.this)) {
        NetworkCalls nc = new NetworkCalls();
        for (String aNewsSourceList : NewsSouce.newsSourceList) {
            String jsonStr = nc.getServerCall(Apis.getHitUrl(aNewsSourceList));
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    JSONArray jsonArray;
                    jsonObject = new JSONObject(jsonStr);
                    jsonArray = jsonObject.getJSONArray("articles");
                    Log.i("response", "jsonArray:" + jsonArray.toString());
                    newsList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonFromSource = jsonArray.getJSONObject(i);
                        String title = jsonFromSource.getString("title");
                        String urlToImage = jsonFromSource.getString("urlToImage");
                        String description = jsonFromSource.getString("description");
                        String sourceUrl = jsonFromSource.getString("url");
                        News model = new News(aNewsSourceList, title, urlToImage, description, sourceUrl);
                        newsList.add(model);
                    }
                    if (newsList.size() > 0) {
                        //new DatabaseHandler(context).deleteAll();
                        new DatabaseHandler(SplashScreen.this).clearOldNews(aNewsSourceList);
                    }
                    new DatabaseHandler(SplashScreen.this).addNewsintoTable(newsList);

                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }
//    else {
//        List<News> temp = new DatabaseHandler(context).getOfflineNews(source);
//        Log.i("tempFromDb","tempList:"+temp);
//        newsList.addAll(temp);
//    }

    return null;
}

       @Override
       protected void onPostExecute(Void aVoid) {
           super.onPostExecute(aVoid);
           handler2.removeCallbacks(runnable2);
            Log.i("response","Post exe Called");
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
