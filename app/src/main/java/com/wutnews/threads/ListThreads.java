package com.wutnews.threads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.wutnews.assistance.BaseConnection;
import com.wutnews.assistance.Cache_List;
import com.wutnews.assistance.MyApplication;
import com.wutnews.bbq.R;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Neo on 2015/4/16.
 */
public class ListThreads implements Runnable {

    private String signature;
    private int sec_id,page;
    private Handler handler;
    String response = "",response2="";

    public ListThreads(Handler handler, String signature, int sec_id, int page){
        this.handler = handler;
        this.signature = signature;
        this.sec_id = sec_id;
        this.page = page;
    }

    @Override
    public void run() {
        Message msg = new Message();
        msg.what=1;//success;

//        if(signature.isEmpty()){
//            msg.what = -2;
//            handler.sendMessage(msg);
//            return;
//        }
        List formParams = new ArrayList();
        formParams.add(new BasicNameValuePair("signature", signature));
        formParams.add(new BasicNameValuePair("page", page+""));
        try {
            HttpEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
            response = BaseConnection.getInfo(MyApplication.getAppContext().getString(R.string.ListAll_url)+sec_id,entity);
            Log.e("response",response);
            JSONObject jsonObject = new JSONObject(response);
            response2 = jsonObject.getJSONArray("data").toString();
            int i =jsonObject.getInt("status");
            Log.e("######test######",i+"w");
            switch(jsonObject.getInt("status")){
                case 200:
                    msg.what = 1;
                    break;
                case 211://空
                    msg.what = 0;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IO","wrong");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("json","wrong");
        }
        handler.sendMessage(msg);
    }

    Cache_List cache = new Cache_List(MyApplication.getAppContext());
    public String getResponse(){
        String s ;
        JSONArray a = new JSONArray();
        if(cache.get(sec_id).isEmpty()){
            s = this.response2;
            cache.set(sec_id,s);
        }
        else{
            try {
                JSONArray store = new JSONArray(cache.get(sec_id));
                JSONArray now = new JSONArray(response2);
                for(int i = 0; i<now.length(); i++){
                    int flag = 0;
                    String b = now.getJSONObject(i).getString("pid");
                    for(int j =0; j<store.length(); j++){
                        if (b.equals(store.getJSONObject(j).getString("pid"))){
                            flag = 1;
                        }
                    }
                    if(0==flag) {
                        a.put(i, now.getJSONObject(i));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            s=a.toString();
        }
        return s;
    }
}
