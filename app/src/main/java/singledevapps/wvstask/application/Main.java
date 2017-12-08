package singledevapps.wvstask.application;

import android.app.Application;

import com.evernote.android.job.JobManager;
import com.squareup.leakcanary.LeakCanary;

import singledevapps.wvstask.R;
import singledevapps.wvstask.helper.sync.NewsJobCreator;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by prakash on 3/9/17.
 */

public class Main extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new NewsJobCreator(this));
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Nunito-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        //    JobManager.instance().getConfig().setAllowSmallerIntervalsForMarshmallow(true); // Don't use this in production
    }
}
