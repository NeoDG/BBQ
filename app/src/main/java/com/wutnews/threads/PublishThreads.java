package com.wutnews.threads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.wutnews.assistance.BaseConnection;

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
public class PublishThreads implements Runnable {
    private String signature,title,detail,sec_id;
    private Handler handler;
    String response = "";

    /**
     *
     * @param handler handler
     * @param signature sign certificate
     * @param sec_id part serials
     * @param title title
     * @param detail detail
     */
    public PublishThreads(Handler handler, String signature, String sec_id, String title, String detail){
        this.handler = handler;
        this.signature=signature;
        this.sec_id=sec_id;
        this.title=title;
        this.detail=detail;
    }

    @Override
    public void run() {
        Message msg = new Message();
        msg.what=1;//success;
        if(signature.isEmpty()){
            msg.what = -2;
            handler.sendMessage(msg);
            return;
        }
        List formParams = new ArrayList();
        formParams.add(new BasicNameValuePair("signature", signature));
        formParams.add(new BasicNameValuePair("title",title));
        formParams.add(new BasicNameValuePair("detail",detail));
        formParams.add(new BasicNameValuePair("sec_id", sec_id));

        try {
            HttpEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
            response = BaseConnection.getInfo("http://bbq.wutnews.com/post/add",entity);
            Log.d("response", response);
            JSONObject jsonObject = new JSONObject(response);
            switch(jsonObject.getInt("status")){
                case 200:
                    msg.what = 1;
                    break;
                case 211://用户不对
                    msg.what = 0;
                    break;
                case 222://添加失败
                    msg.what = -1;
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
