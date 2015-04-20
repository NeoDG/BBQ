package com.wutnews.threads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.wutnews.assistance.BaseConnection;
import com.wutnews.assistance.MyApplication;
import com.wutnews.bbq.R;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neo on 2015/4/16.
 */
public class ReplyThreads implements Runnable {

    private String signature,detail,post_id;
    private Handler handler;
    String response = "";

    public ReplyThreads(Handler handler, String signature, String post_id, String detail){
        this.handler = handler;
        this.signature = signature;
        this.post_id = post_id;
        this.detail = detail;
    }

    @Override
    public void run() {
        Message msg = new Message();
        msg.what=1;//success;
        if(signature.isEmpty()){
            msg.what=-2;
            handler.sendMessage(msg);
            return;
        }
        List formParams = new ArrayList();
        formParams.add(new BasicNameValuePair("signature", signature));
        formParams.add(new BasicNameValuePair("post_id", post_id));
        formParams.add(new BasicNameValuePair("detail", detail));
        try {
            HttpEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
            response = BaseConnection.getInfo(MyApplication.getAppContext().getString(R.string.Reply_url),entity);
            Log.e("response",response);
            JSONObject jsonObject = new JSONObject(response);
            int i =jsonObject.getInt("status");
            Log.e("######test######",i+" ");
            switch(jsonObject.getInt("status")){
                case 300:
                    msg.what = 1;
                    break;
                case 311://空
                    msg.what = 0;
                    break;
                default:
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
        String s = this.response;
        return s;
    }
}
