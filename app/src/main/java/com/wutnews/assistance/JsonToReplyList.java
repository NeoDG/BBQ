package com.wutnews.assistance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Neo on 2015/4/17.
 */
public class JsonToReplyList {
    private JSONArray jsonArray;
    public JsonToReplyList(JSONArray jsonArray){
        this.jsonArray = jsonArray;
    }

    public ArrayList<HashMap<String,String>> getlist(){
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        int n = jsonArray.length();
        for(int i = 0; i< n; i++){
            HashMap<String,String> map = new HashMap<String,String>();
            try {
                if(jsonArray.get(i).toString() != "null"){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    map.put("detail", jsonObject.getString("detail"));
                    map.put("name", jsonObject.getJSONObject("user").getString("nickname"));
                    map.put("time",jsonObject.getString("pub_time"));
                    map.put("pid", jsonObject.getString("post_id"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.add(map);
        }
        return list;
    }
}
