package singledevapps.wvstask.helper;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by prakash on 30/8/17.
 */

public class NetworkHelper {
    public static boolean checkConnection(Context context){
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            return cm.getActiveNetworkInfo() != null;
    }
}
