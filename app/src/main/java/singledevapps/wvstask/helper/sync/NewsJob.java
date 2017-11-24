package singledevapps.wvstask.helper.sync;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import singledevapps.wvstask.MainActivity;
import singledevapps.wvstask.R;
import singledevapps.wvstask.helper.Apis;
import singledevapps.wvstask.model.News;
import singledevapps.wvstask.model.data.local.DatabaseHandler;
import singledevapps.wvstask.model.data.remote.NetworkCalls;
import singledevapps.wvstask.model.data.remote.NewsSouce;


public class NewsJob extends Job {

    public static final String TAG = "newsJob";
    private Context context;
    private List<News> newsList;

    public NewsJob(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    protected Result onRunJob(Params params) {

     // showNotification();

        for (String aNewsSourceList : NewsSouce.newsSourceList) {
            NetworkCalls nc = new NetworkCalls();
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
                        new DatabaseHandler(context).clearOldNews(aNewsSourceList);
                    }
                    new DatabaseHandler(context).addNewsintoTable(newsList);

                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
        return Result.SUCCESS;
    }

    private void showNotification() {
        PendingIntent pi = PendingIntent.getActivity(getContext(), 0,
                new Intent(getContext(), MainActivity.class), 0);
        Notification notification = new NotificationCompat.Builder(getContext())
                .setContentTitle("News Headlines")
                .setContentText("News Updated")
                .setAutoCancel(true)
                .setContentIntent(pi)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setShowWhen(true)
                .setColor(Color.RED)
                .setLocalOnly(true)
                .build();

        NotificationManagerCompat.from(getContext())
                .notify(new Random().nextInt(), notification);
    }

    public static void schedulePeriodic() {
        new JobRequest.Builder(NewsJob.TAG)
                .setPeriodic(TimeUnit.HOURS.toMillis(1), TimeUnit.MINUTES.toMillis(10))
                .setUpdateCurrent(true)
                .setPersisted(true)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .build()
                .schedule();
    }
}
