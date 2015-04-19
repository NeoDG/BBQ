package com.wutnews.assistance;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Neo on 2015/4/7.
 */
public class Cache_setting extends Cache_Base {
    private SharedPreferences preferences;
    private static String wifi_update = "wifi_update";
    private static String auto_noti = "auto_noti";
    private static String about = "about_us";

    public Cache_setting(Context context){
        preferences = getInstance(context,"log").getSp();
    }

    public String getAbout(){
        String a = preferences.getString(about," ");
        return a;
    }

    public void setAbout(String b){
        SharedPreferences.Editor e = preferences.edit();
        e.putString(about, b);
        e.commit();
    }

    public boolean getWifi_update() {
        boolean state = preferences.getBoolean(wifi_update,true);
        return state;
    }

    public void setWifi_update(Boolean flag){
        SharedPreferences.Editor e = preferences.edit();
        e.putBoolean(wifi_update, flag);
        e.commit();
    }

    public boolean getAuto_noti() {
        boolean state = preferences.getBoolean(auto_noti,true);
        return state;
    }

    public void setAuto_noti(Boolean flag){
        SharedPreferences.Editor e = preferences.edit();
        e.putBoolean(auto_noti, flag);
        e.commit();
    }

}
