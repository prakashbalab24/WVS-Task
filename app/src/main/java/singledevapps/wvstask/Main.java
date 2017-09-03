package singledevapps.wvstask;

import android.app.Application;

import com.evernote.android.job.JobManager;

import singledevapps.wvstask.sync.NewsJobCreator;

/**
 * Created by prakash on 3/9/17.
 */

public class Main extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new NewsJobCreator(this));
        //    JobManager.instance().getConfig().setAllowSmallerIntervalsForMarshmallow(true); // Don't use this in production
    }
}
