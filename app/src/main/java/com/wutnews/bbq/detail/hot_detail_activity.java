package com.wutnews.bbq.detail;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.internal.nineoldandroids.animation.ObjectAnimator;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.wutnews.assistance.Cache_Login;
import com.wutnews.assistance.JsonToList;
import com.wutnews.assistance.JsonToReplyList;
import com.wutnews.assistance.MyApplication;
import com.wutnews.bbq.R;
import com.wutnews.threads.ListThreads;
import com.wutnews.threads.PublishThreads;
import com.wutnews.threads.ReplyListThreads;
import com.wutnews.threads.ReplyThreads;

import org.json.JSONArray;
import org.json.JSONException;

public class hot_detail_activity extends ListActivity{
	private SimpleAdapter mSchedule;
	private ArrayList<HashMap<String, String>> mylist;
	private PullToRefreshListView listView;
	private EditText rep;
	private String pid;
	private ReplyListThreads rlt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_detail);

		rlt = new ReplyListThreads(handler, new Cache_Login(MyApplication.getAppContext()).getSignature(), pid);
		Thread thread = new Thread(rlt);
		thread.start();

		rep = (EditText) findViewById(R.id.edit_reply);
		listView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
		listView.setMode(Mode.PULL_FROM_END);
		listView.setPullToRefreshOverScrollEnabled(false);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				rlt = new ReplyListThreads(handler, new Cache_Login(MyApplication.getAppContext()).getSignature(), pid);
				Thread thread = new Thread(rlt);
				thread.start();
			}
		});

		final Bundle bundle = this.getIntent().getExtras();

		mylist = new ArrayList<HashMap<String, String>>();
//		for (int i = 0; i < 0; i++) {
//			HashMap<String, String> map = new HashMap<String, String>();
//			map.put("name", "小红");
//			map.put("time", "2014-7-16 12:53");
//			map.put("detail", "lalala");
//			mylist.add(map);
//		}

		mSchedule = new SimpleAdapter(this, mylist,
				R.layout.hot_detail_list, new String[] {"name", "time", "detail" }, new int[] {
						R.id.rp_name, R.id.rp_time, R.id.rp_detail});
		View head = LayoutInflater.from(this).inflate(R.layout.hot_detail_header, null);

		TextView title = (TextView) head.findViewById(R.id.head_line);
		TextView name = (TextView) head.findViewById(R.id.lz_name);
		TextView time = (TextView) head.findViewById(R.id.lz_time);
		TextView detail = (TextView) head.findViewById(R.id.detail_artical);

		title.setText(bundle.getString("title"));
		name.setText(bundle.getString("name"));
		time.setText(bundle.getString("time_w"));
		detail.setText(bundle.getString("preview_w"));

		pid = bundle.getString("pid");

		listView.getRefreshableView().addHeaderView(head);
		listView.setAdapter(mSchedule);	
		getOverflowMenu();

		ImageButton reply = (ImageButton) findViewById(R.id.btn_reply);
		reply.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showRequestDialog();
				pt = new ReplyThreads(handlerReply,new Cache_Login(MyApplication.getAppContext()).getSignature(),bundle.getString("pid"),rep.getText().toString());
				Thread thread = new Thread(pt);
				thread.start();
			}
		});
	}


	private ArrayList<HashMap<String, String>> list;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					try {
						JSONArray data = new JSONArray(rlt.getResponse());
						JsonToReplyList jsonToList = new JsonToReplyList(data);
						list = jsonToList.getlist();
						if(list.size()>=mylist.size()){
							if (listView.isFooterShown())
								mylist.addAll(list.subList(mylist.size(),list.size()));
							else
								mylist.addAll(0,list.subList(mylist.size(),list.size()));
						}
						mSchedule.notifyDataSetChanged();
						// Call onRefreshComplete when the list has been refreshed.
						listView.onRefreshComplete();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					break;
				case 0:
					Toast.makeText(MyApplication.getAppContext(), "没有更多帖子了，发个帖子呗", Toast.LENGTH_SHORT).show();
				case -2:
					Toast.makeText(MyApplication.getAppContext(), "还没登陆呢", Toast.LENGTH_SHORT).show();
				default:
					break;
			}
		}
	};

	Handler handlerReply = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case 1:
					dismissDialog();
					Toast.makeText(MyApplication.getAppContext(),"发布成功",Toast.LENGTH_SHORT).show();
					//rep.clearFocus();
					InputMethodManager inputMethodManager = (InputMethodManager) MyApplication.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(hot_detail_activity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
					rep.setText("");
					break;
				case 0:
					dismissDialog();
					Toast.makeText(MyApplication.getAppContext(),"什么鬼",Toast.LENGTH_SHORT).show();
					break;
				case -2:
					dismissDialog();
					Toast.makeText(MyApplication.getAppContext(),"还没登陆呢",Toast.LENGTH_SHORT).show();
					break;
				default:
					dismissDialog();
					Toast.makeText(MyApplication.getAppContext(),"发布失败",Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 0, 0, "收藏").setIcon(android.R.drawable.ic_menu_add);
        menu.add(0, 1, 1, "只看楼主").setIcon(android.R.drawable.ic_menu_rotate);
        menu.add(0, 2, 2, "举报").setIcon(android.R.drawable.ic_menu_day);
		return super.onCreateOptionsMenu(menu);
	}
	
//	public void onListItemClick(ListView parent, View v, int position, long id) {
//		HashMap<String, Object> map = (HashMap<String, Object>) parent
//				.getItemAtPosition(position);
//		String title = map.get("rp_name").toString();
//		String content = map.get("rp_time").toString();
//		Toast.makeText(this, "You have selected " + position+ title + content,
//				Toast.LENGTH_SHORT).show();
//	}
	public void replyOther(View v){
		int position = listView.getRefreshableView().getPositionForView(v);
		HashMap<String,String> a = (HashMap<String,String>) listView.getRefreshableView().getAdapter().getItem(position);
		String c = a.get("name").toString();
		Log.v("b", position + "");
		rep.setText("reply to"+ c+":" );
		rep.requestFocus();
	}

	private void getOverflowMenu() {
	      try {
	         ViewConfiguration config = ViewConfiguration.get(this);
	         Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	         if(menuKeyField != null) {
	             menuKeyField.setAccessible(true);
	             menuKeyField.setBoolean(config, false);
	         }
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	 }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			super.onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private ReplyThreads pt;
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
