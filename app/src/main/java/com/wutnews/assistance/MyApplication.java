package com.wutnews.assistance;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Neo on 2015/4/8.
 */
public class MyApplication extends Application{
    private static Context context;

    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

    public static void setNetwork() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Network status");
        builder.setMessage("Is not available,go to set?");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        if (android.os.Build.VERSION.SDK_INT > 10) {
                            intent = new Intent(
                                    android.provider.Settings.ACTION_SETTINGS);
                        } else {
                            intent = new Intent(
                                    android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                        }

                        context.startActivity(intent);
                    }
                });
        builder.setNegativeButton("Not now",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create();
        builder.show();
    }

    /**
     * 联网前用的，确认手机是否联网
     *
     * @return
     * @author 林
     */
    public static boolean isNetworkAvailable() {

        ConnectivityManager connect = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == connect) {// connect==null TODO by lxh 在友盟上会报错
            return false;
        } else {// get all network info
            NetworkInfo[] info = connect.getAllNetworkInfo();
            if (null != info) {// info!=null by lxh
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * 判断是否为wifi
     *
     * @return
     */
    public static boolean isWifi() {// 判断是否为WIFI
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI
                && activeNetInfo.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 判断是否为移动网络
     *
     * @return
     */
    public static boolean isCellular() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE
                && activeNetInfo.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }
}
