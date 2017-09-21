package singledevapps.wvstask.model.data.remote;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

import singledevapps.wvstask.R;
import singledevapps.wvstask.fragments.ResponseListner;
import singledevapps.wvstask.helper.Apis;
import singledevapps.wvstask.helper.NetworkHelper;
import singledevapps.wvstask.model.News;
import singledevapps.wvstask.model.data.local.DatabaseHandler;

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
        pDialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (NetworkHelper.checkConnection(context.getApplicationContext())) {
            NetworkCalls nc = new NetworkCalls();
            String jsonStr = nc.getServerCall(Apis.getHitUrl(source));
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    JSONArray jsonArray;
                        jsonObject = new JSONObject(jsonStr);
                        jsonArray = jsonObject.getJSONArray("articles");
                        Log.i("response","jsonArray:"+jsonArray.toString());
                        for (int i=0; i<jsonArray.length(); i++) {
                            JSONObject jsonFromSource = jsonArray.getJSONObject(i);
                            String title = jsonFromSource.getString("title");
                            String urlToImage = jsonFromSource.getString("urlToImage");
                            String description = jsonFromSource.getString("description");
                            String sourceUrl = jsonFromSource.getString("url");
                            model = new News(source,title,urlToImage,description,sourceUrl);
                            newsList.add(model);
                        }
                        if(newsList.size()>0){
                            new DatabaseHandler(context).deleteAll();
                        }
                        new DatabaseHandler(context).addNewsintoTable(newsList);

                    }
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
        else {
            List<News> temp = new DatabaseHandler(context).getOfflineNews(source);
            Log.i("tempFromDb","tempList:"+temp);
            newsList.addAll(temp);
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
