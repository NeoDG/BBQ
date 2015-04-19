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
public class LoginThreads implements Runnable {

    private String username,userpwd;
    private Handler handler;
    String response = "";

    public LoginThreads(Handler handler, String username, String userpwd){
        this.handler = handler;
        this.username=username;
        this.userpwd = userpwd;
    }

    @Override
    public void run() {
        Message msg = new Message();
        msg.what=1;//success;

        List formParams = new ArrayList();
        formParams.add(new BasicNameValuePair("username", username));
        formParams.add(new BasicNameValuePair("password", userpwd));

        try {
            HttpEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
            response = BaseConnection.getInfo("http://bbq.wutnews.com/user/login",entity);
            Log.d("response",response);
            JSONObject jsonObject = new JSONObject(response);
            switch(jsonObject.getInt("status")){
                case 100:
                    msg.what = 1;
                    break;
                case 101:
                    msg.what = 0;
                    break;
                case 102:
                    msg.what = -1;
                    break;
                case 103:
                    msg.what = -2;
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
