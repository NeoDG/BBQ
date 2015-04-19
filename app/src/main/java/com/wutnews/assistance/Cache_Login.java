package com.wutnews.assistance;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Neo on 2015/4/7.
 */
public class Cache_Login extends Cache_Base {
    private SharedPreferences preferences;
    private static String nickname= "nickname";
    private static String signature = "signature";
    private static String is_logged = "is logged";

    public Cache_Login(Context context){
        preferences = getInstance(context,"log").getSp();
    }

    public boolean getIs_logged() {
        boolean state = preferences.getBoolean(is_logged,false);
        return state;
    }

    public void setIs_logged(Boolean flag){
        SharedPreferences.Editor e = preferences.edit();
        e.putBoolean(is_logged,flag);
        Log.v("writing","aaaaaaaaaaaaaa"+flag);
        e.apply();
    }

    public String getNickname() {
        String name = preferences.getString(nickname,"");
        return name;
    }

    public void setNickname(String name){
        SharedPreferences.Editor e = preferences.edit();
        e.putString(nickname,name);
        e.apply();
    }

    public String getSignature() {
        String pwd = preferences.getString(signature,"");
        return pwd;
    }

    public void setSignature(String pwd){
        SharedPreferences.Editor e = preferences.edit();
        e.putString(signature,pwd);
        e.apply();
    }
}
