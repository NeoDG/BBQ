package com.wutnews.threads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.wutnews.assistance.BaseConnection;
import com.wutnews.assistance.Cache_List;
import com.wutnews.assistance.MyApplication;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neo on 2015/4/16.
 */
public class ReplyListThreads implements Runnable {

    private String signature;
    private String pid;
    private Handler handler;
    String response = "",response2="";

    public ReplyListThreads(Handler handler, String signature, String pid){
        this.handler = handler;
        this.signature = signature;
        this.pid=pid;
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
        try {
            HttpEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
            response = BaseConnection.getInfo("http://bbq.wutnews.com/post/"+pid,entity);
            Log.e("response",response);
            JSONObject jsonObject = new JSONObject(response);
            response2 = jsonObject.getJSONObject("data").getJSONArray("reply").toString();
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

    public String getResponse(){
        String s ;
        s = this.response2;
        return s;
    }
}
