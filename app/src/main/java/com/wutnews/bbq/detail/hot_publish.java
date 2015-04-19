package com.wutnews.bbq.detail;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.wutnews.assistance.Cache_Login;
import com.wutnews.assistance.MyApplication;
import com.wutnews.bbq.R;
import com.wutnews.threads.PublishThreads;

/**
 * Created by Neo on 2015/4/13.
 */
public class hot_publish extends SherlockActivity {

    EditText e , r;
    int sec_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_publish_hot);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        e = (EditText) findViewById(R.id.publish_title);
        r = (EditText) findViewById(R.id.publish_detail);
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        sec_id = bundle.getInt("sec_id");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.menu_publish_submit,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.publish_submit:
                if(!(e.getText().toString().equals("")||r.getText().toString().equals(""))){
                    showRequestDialog();
                    pt = new PublishThreads(handler,new Cache_Login(MyApplication.getAppContext()).getSignature(),sec_id+"",
                            e.getText().toString(),r.getText().toString());
                    Thread thread = new Thread(pt);
                    thread.start();
                }
                else{
                    Toast.makeText(this,"多写点什么呗",Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    dismissDialog();
                    onBackPressed();
                    Toast.makeText(MyApplication.getAppContext(),"发布成功",Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    dismissDialog();
                    Toast.makeText(MyApplication.getAppContext(),"什么鬼",Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    dismissDialog();
                    Toast.makeText(MyApplication.getAppContext(),"发布成功",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private PublishThreads pt;
    private Dialog mDialog = null;

    public void dismissDialog() {
        try {
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showRequestDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
        mDialog = new ProgressDialog(this);
        mDialog.setTitle("正在发布");
        mDialog.show();
    }

}
