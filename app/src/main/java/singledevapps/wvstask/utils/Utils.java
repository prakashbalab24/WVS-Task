package singledevapps.wvstask.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.content.FileProvider;
import android.view.View;

import java.io.File;

import singledevapps.wvstask.R;

/**
 * Created by prakash on 5/9/17.
 */

public class Utils {
    private static int someColor[] = {R.color.color1,R.color.color2,R.color.color3,R.color.color4,R.color.color5};
    private static int i = 0;

    public static Bitmap takeScreenShot(View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        view.buildDrawingCache();

        if(view.getDrawingCache() == null) return null;
        Bitmap snapshot = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();

        return snapshot;
    }

    public static void shareImage(Context context,String fileName) {
        File imagePath = new File(context.getCacheDir(), "images");
        File newFile = new File(imagePath, fileName+".png");
        Uri contentUri = FileProvider.getUriForFile(context, "singledevapps.wvstask.fileprovider", newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, context.getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
//            shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name)+" - Awesome illusion game");
//            shareIntent.putExtra(Intent.EXTRA_TEXT, "Can you beat my score in "+context.getResources().getString(R.string.app_name)+"?"+" Download here: https://play.google.com/store/apps/details?id=illusion.color.colorgame");
            context.startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }
    }

    public static void shareNewsUrl(Context context,String url) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
//        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, url +"\n\n\n"+"Shared via Headlines app - https://play.google.com/store/apps/details?id=singledevapps.wvstask");

        context.startActivity(Intent.createChooser(share, "Share link!"));
    }
//
//    public static void makeVibrate(Context context){
//        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//        // Vibrate for 500 milliseconds
//        v.vibrate(50);
//    }

    public static int bckColor(){
        if (i>=5){
            i = 0;
        }
        int j = i;
        i++;
        return someColor[j];
    }
}
