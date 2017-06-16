package singledevapps.wvstask.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

import singledevapps.wvstask.fragments.ResponseListner;
import singledevapps.wvstask.model.News;

/**
 * Created by prakash on 16/6/17.
 */

public class AsyncTaskHelper extends AsyncTask<Void,Void,Void> {
    private ResponseListner listner;
    private News model;
    private List<News> newsList;
    private String source;
    private ProgressDialog pDialog;
    private Context context;

    public AsyncTaskHelper(ResponseListner listner, List<News> newsList, String source, Context context){
        this.listner = listner;
        this.newsList = newsList;
        this.source = source;
        this.context = context;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        NetworkCalls nc = new NetworkCalls();
        String jsonStr = nc.getServerCall(Apis.getHitUrl(source));
        JSONObject jsonObject;
        JSONArray jsonArray;
        try {
            jsonObject = new JSONObject(jsonStr);
            jsonArray = jsonObject.getJSONArray("articles");
            Log.i("response","jsonArray:"+jsonArray.toString());
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject wallpaper = jsonArray.getJSONObject(i);
                String title = wallpaper.getString("title");
                String urlToImage = wallpaper.getString("urlToImage");
                String description = wallpaper.getString("description");
                model = new News(title,urlToImage,description);
                newsList.add(model);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listner.onAsyncTaskComplete();
        if (pDialog!=null){
            pDialog.dismiss();
        }
    }
}
