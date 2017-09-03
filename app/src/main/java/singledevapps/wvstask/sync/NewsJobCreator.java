package singledevapps.wvstask.sync;

import android.content.Context;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * @author Rajesh Pattanaik
 */

public class NewsJobCreator implements JobCreator {
    private Context context;

    public NewsJobCreator(Context context){
        this.context = context;
    }

    @Override
    public Job create(String tag) {
        switch (tag) {
            case NewsJob.TAG:
                return new NewsJob(context);
            default:
                return null;
        }
    }
}
