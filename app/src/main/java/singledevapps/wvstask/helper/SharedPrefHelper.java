package singledevapps.wvstask.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by prakash on 30/8/17.
 */

public class SharedPrefHelper {
    private static final String PREFS_NAME = "singledevapps-headlines";
    private static final String JSONOFFLINE = "key1";

    public static void saveJsonOffline(Context context, String jsonString,String source){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(JSONOFFLINE+"_"+source, jsonString);
        editor.apply();
    }

    public static String getOfflineJson(Context context,String source) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(JSONOFFLINE+"_"+source, "oops");
    }
}
