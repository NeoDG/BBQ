package com.wutnews.frags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wutnews.assistance.Cache_List;
import com.wutnews.assistance.Cache_Login;
import com.wutnews.assistance.JsonToList;
import com.wutnews.assistance.MyApplication;
import com.wutnews.bbq.R;
import com.wutnews.bbq.detail.hot_detail_activity;
import com.wutnews.threads.ListThreads;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HotFragment extends ListFragment {
    private SimpleAdapter mSchedule;
    private ArrayList<HashMap<String, String>> mylist;
    private PullToRefreshListView listView;
    private ListThreads lt;
    private Dialog mDialog = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater
                .inflate(R.layout.fragment_hot, container, false);
        listView = (PullToRefreshListView) v
                .findViewById(R.id.pull_to_refresh_listview);
        listView.setMode(Mode.BOTH);
        listView.setPullToRefreshOverScrollEnabled(false);
        listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                lt = new ListThreads(handler, new Cache_Login(MyApplication.getAppContext()).getSignature(), 1, 1);
                Thread thread = new Thread(lt);
                thread.start();
            }
        });
        listView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载更多");
        listView.getLoadingLayoutProxy(true, true).setRefreshingLabel("加载ing");
        listView.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");
        listView.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新");

        // 生成动态数组，并且转载数据
        mylist = new ArrayList<HashMap<String, String>>();
        Cache_List cache = new Cache_List(MyApplication.getAppContext());
        if(!cache.get(1).isEmpty()){
            try {
                JSONArray a = new JSONArray(cache.get(1));
                    JsonToList j = new JsonToList(a);
                    mylist.addAll(j.getlist());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // 生成适配器，数组===》ListItem
        mSchedule = new SimpleAdapter(getActivity(), mylist,
                R.layout.hot_adapter, new String[]{"part", "title",
                "preview", "name", "time", "reply"}, new int[]{
                R.id.belongspart, R.id.title, R.id.preview, R.id.name,
                R.id.time, R.id.reply});
        // 添加并且显示
        listView.setAdapter(mSchedule);
        return v;
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        HashMap<String, Object> map = (HashMap<String, Object>) parent
                .getItemAtPosition(position);
//        String title = map.get("title").toString();
//        String content = map.get("name").toString();
//        Toast.makeText(getActivity(), "You have selected " + position + title + content,
//                Toast.LENGTH_SHORT).show();
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

    /**
     * load more function
     * include refresh and load
     */
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
                        Log.v("hotfragment:data",data.toString());
                        list = jsonToList.getlist();
                        Log.v("hotfragment:list",list.toString());
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
//
//    private class GetDataTask extends AsyncTask<Void, Void, String[]> {
//        @Override
//        protected String[] doInBackground(Void... params) {
//            // Simulates a background job.
//            try {
//                showRequestDialog();
//                lt = new ListThreads(handler, new Cache_Login(MyApplication.getAppContext()).getSignature(), 1, 1);
//                Thread thread = new Thread(lt);
//                thread.start();
//            } catch (Exception e) {
//                Log.e("err", "wrong");
//            }
//            return new String[]{"d"};
//        }
//
//        @Override
//        protected void onPostExecute(String[] result) {
//            if (listView.isFooterShown())
//                mylist.addAll(list);
//            else
//                mylist.addAll(0,list);
//            mSchedule.notifyDataSetChanged();
//            // Call onRefreshComplete when the list has been refreshed.
//            listView.onRefreshComplete();
//            super.onPostExecute(result);
//        }
//    }
}
