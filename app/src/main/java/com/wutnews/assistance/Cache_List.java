package com.wutnews.assistance;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Neo on 2015/4/7.
 */
public class Cache_List extends Cache_Base {
    private SharedPreferences preferences;
    public Cache_List(Context context){
        preferences = getInstance(context,"log").getSp();
    }

    public String get(int sec){
        String a = preferences.getString(getPart(sec),"");
        return a;
    }
    public void set(int sec,String v){
        SharedPreferences.Editor e = preferences.edit();
        e.putString(getPart(sec),v);
        e.apply();
    }

    public String getPart(int part){
        switch (part){
            case 1:
                return "chat";
            case 2:
                return "usecret";
            case 3:
                return "suround";
            case 4:
                return  "circle";
            case 5:
                return "lost";
            case 6:
                return "sale";
            case 7:
                return "ask";
            default:
                return "hot";
        }
    }

}
