package com.wutnews.assistance;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

/**
 * Created by Neo on 2015/4/17.
 */
public class JsonToList {
    private JSONArray jsonArray;
    public JsonToList(JSONArray jsonArray){
        this.jsonArray = jsonArray;
    }

    public ArrayList<HashMap<String,String>> getlist(){
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        int n = jsonArray.length();
        for(int i = 0; i< n; i++){
            HashMap<String,String> map = new HashMap<String,String>();
            try {
                Log.v("jtl:get", jsonArray.get(i).toString());
                if(jsonArray.get(i).toString() != "null"){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    map.put("part", getPart(Integer.parseInt(jsonObject.getString("sec_id"))));
                    map.put("title", jsonObject.getString("title"));
                    String a = jsonObject.getString("detail");
                    if (a.length()>30)
                        map.put("preview", jsonObject.getString("detail").substring(0, 30));
                    else
                        map.put("preview", jsonObject.getString("detail"));
                    map.put("preview_w", jsonObject.getString("detail"));
                    map.put("name", jsonObject.getJSONObject("user").getString("nickname"));
                    map.put("time", jsonObject.getString("pub_time").substring(11, 16));
                    map.put("time_w",jsonObject.getString("pub_time"));
                    map.put("reply", jsonObject.getString("reply"));
                    map.put("pid", jsonObject.getString("pid"));
                    list.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return list;
    }

    public String getPart(int part){
        switch (part){
            case 1:
                return "闲聊特区";
            case 2:
                return "Usecret";
            case 3:
                return "理工周边";
            case 4:
                return  "圈子";
            case 5:
                return "失物招领";
            case 6:
                return "跳蚤市场";
            case 7:
                return "记者帮你问";
            default:
                return "闲聊特区";
        }
    }
}
