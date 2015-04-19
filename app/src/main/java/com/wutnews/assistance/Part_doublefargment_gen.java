package com.wutnews.assistance;

import java.util.ArrayList;
import java.util.HashMap;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.wutnews.bbq.R;
import com.wutnews.bbq.detail.hot_detail_activity;
import com.wutnews.threads.ListThreads;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * ????listfragment R. layout.part_sub_list ??????pulltorefreshlistview R.id.
 * pull_to_refresh_listview ????view??? R.layout.part_1_3_adapter list??adapter
 * 
 * @author Neo
 * 
 */
public class Part_doublefargment_gen extends ListFragment {

	private int id;
	private ListThreads lt;

	public ListFragment genListFragment(int id) {
		Part_doublefargment_gen fragment = new Part_doublefargment_gen();
		fragment.id = id;
		return fragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private SimpleAdapter mSchedule;
	private ArrayList<HashMap<String, String>> mylist;
	private PullToRefreshListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.part_sub_list, container,
				false);

		listView = (PullToRefreshListView) v
				.findViewById(R.id.pull_to_refresh_listview);
		listView.setMode(Mode.BOTH);
		listView.setPullToRefreshOverScrollEnabled(false);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				lt = new ListThreads(handler, new Cache_Login(MyApplication.getAppContext()).getSignature(), id, 1);
				Thread thread = new Thread(lt);
				thread.start();
			}
		});
		
		mylist = new ArrayList<HashMap<String, String>>();
		Cache_List cache = new Cache_List(MyApplication.getAppContext());
		if(!cache.get(id).isEmpty()){
			try {
				JSONArray a = new JSONArray(cache.get(id));
					JsonToList j = new JsonToList(a);
					mylist.addAll(j.getlist());

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		switch (id) {
		case 2:// secret break
			mSchedule = new SimpleAdapter(getActivity(), mylist,
					R.layout.part_adapter_2secret, new String[] { 
							"preview", "time", "reply" }, new int[] {
							 R.id.preview, R.id.time,
							R.id.reply });
			break;

		case 7:// ask break
			;
		case 4:// circle break
			;
		case 1:
			;
		case 3:
			;
		case 5:
			;
		case 6:
			mSchedule = new SimpleAdapter(getActivity(), mylist,
					R.layout.part_adapter_util, new String[] { "title",
							"preview", "name", "time", "reply" }, new int[] {
							R.id.title, R.id.preview, R.id.name, R.id.time,
							R.id.reply });
			break;
		default:
			break;
		}
		listView.setAdapter(mSchedule);
		return v;
	}
	
	
	public void onListItemClick(ListView parent, View v, int position, long id) {
		HashMap<String, String> map = (HashMap<String, String>) parent
				.getItemAtPosition(position);
//		String title = map.get("title").toString();
//		String content = map.get("name").toString();
//		Toast.makeText(getActivity(), "You have selected " + position+ title + content,
//				Toast.LENGTH_SHORT).show();
		Intent i = new Intent().setClass(getActivity(), hot_detail_activity.class);
		Bundle bundle = new Bundle();
		bundle.putString("title",map.get("title").toString());
		bundle.putString("name",map.get("name").toString());
		bundle.putString("time_w",map.get("time_w").toString());
		bundle.putString("pid",map.get("pid").toString());
		bundle.putString("preview_w",map.get("preview_w").toString());
		i.putExtras(bundle);
		getActivity().startActivity(i);
	}

	private ArrayList<HashMap<String, String>> list;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					try {
						//JSONObject jsonObject = new JSONObject(lt.getResponse());
						//JSONArray data = jsonObject.getJSONArray("data");
						JSONArray data = new JSONArray(lt.getResponse());
						JsonToList jsonToList = new JsonToList(data);
						list = jsonToList.getlist();
						if (listView.isFooterShown())
							mylist.addAll(list);
						else
							mylist.addAll(0,list);
						mSchedule.notifyDataSetChanged();
						// Call onRefreshComplete when the list has been refreshed.
						listView.onRefreshComplete();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					break;
				case 0:
					Toast.makeText(getActivity(), "没有更多帖子了，发个帖子呗", Toast.LENGTH_SHORT).show();
				case -2:
					Toast.makeText(getActivity(), "还没登录呢", Toast.LENGTH_SHORT).show();
				default:
					break;
			}
		}
	};

//	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
//		@Override
//		protected String[] doInBackground(Void... params) {
//			// Simulates a background job.
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//			}
//			return new String[] { "d" };
//		}
//
//		@Override
//		protected void onPostExecute(String[] result) {
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("title", "title");
//			map.put("preview", "preview");
//			map.put("name", "name");
//			map.put("time", "time");
//			map.put("reply", "16");
//			map.put("preview_w", "显示前两句话~~~~~~~~~~~~");
//			map.put("time_w", "3分钟前");
//			map.put("pid","1");
//			if (listView.isFooterShown())
//				mylist.add(map);
//			else
//				mylist.add(0,map);
//			mSchedule.notifyDataSetChanged();
//			// Call onRefreshComplete when the list has been refreshed.
//			listView.onRefreshComplete();
//			super.onPostExecute(result);
//		}
//	}

}
