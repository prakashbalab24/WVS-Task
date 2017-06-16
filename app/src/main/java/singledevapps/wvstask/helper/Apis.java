package singledevapps.wvstask.helper;

import android.util.Log;

/**
 * Created by prakash on 6/6/17.
 */

public class Apis {
    private static String BASE_URL = "https://newsapi.org/v1/articles?apiKey=c4b530e1fe0f401daa7a236257a865c0&source=";


    public static String getHitUrl(String source) {
        Log.i("hiturl",BASE_URL+source);
        return BASE_URL+source;

    }
}
