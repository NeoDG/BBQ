package com.wutnews.bbq.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.wutnews.assistance.ListViewCompat;
import com.wutnews.assistance.SlideView;
import com.wutnews.assistance.SlideView.OnSlideListener;
import com.wutnews.bbq.R;

import java.util.ArrayList;
import java.util.List;

public class Me_detail_Activity extends SherlockActivity implements OnItemClickListener, OnClickListener,
        OnSlideListener {

    private static final String TAG = "MainActivity";

    private ListViewCompat mListView;

    private List<MessageItem> mMessageItems = new ArrayList<Me_detail_Activity.MessageItem>();

    private SlideView mLastSlideViewWithStatusOn;
    private SlideAdapter mSlideAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_pm_2activity);
        initView();
    }

    private void initView() {
        mListView = (ListViewCompat) findViewById(R.id.list);

        for (int i = 0; i < 10; i++) {
            MessageItem item = new MessageItem();
            if (i % 3 == 0) {
                item.name = "pm name";
                item.detail = "pm detail";
                item.time = "18:18";
            } else {
                item.name = "namename";
                item.detail = "detaildetail";
                item.time = "12月18日";
            }
            mMessageItems.add(item);
        }
        mSlideAdapter = new SlideAdapter();
        mListView.setAdapter(mSlideAdapter);
        mListView.setOnItemClickListener(this);
    }

    private class SlideAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        SlideAdapter() {
            super();
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mMessageItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mMessageItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            SlideView slideView = (SlideView) convertView;
            if (slideView == null) {
                View itemView = mInflater.inflate(R.layout.adapter_me_swipe, null);

                slideView = new SlideView(Me_detail_Activity.this);
                slideView.setContentView(itemView);

                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(Me_detail_Activity.this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
            }
            MessageItem item = mMessageItems.get(position);
            item.slideView = slideView;
            item.slideView.shrink();

            //holder.icon.setImageResource(item.iconRes);
            holder.name.setText(item.name);
            holder.detail.setText(item.detail);
            holder.time.setText(item.time);
            holder.deleteHolder.setOnClickListener(Me_detail_Activity.this);

            return slideView;
        }
    }

    public class MessageItem {
        public int iconRes;
        public String name;
        public String time;
        public String detail;
        public SlideView slideView;
    }

    private static class ViewHolder {
        public ImageView icon;
        public TextView name;
        public TextView time;
        public TextView detail;
        public ViewGroup deleteHolder;

        ViewHolder(View view) {
           // icon = (ImageView) view.findViewById(R.id.icon);
            name = (TextView) view.findViewById(R.id.pm_name);
            time = (TextView) view.findViewById(R.id.pm_time);
            detail = (TextView) view.findViewById(R.id.pm_detail);
            deleteHolder = (ViewGroup)view.findViewById(R.id.holder);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        Log.e(TAG, "onItemClick position=" + position);
    }

    @Override
    public void onSlide(View view, int status) {
        if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }

        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SlideView) view;
        }
    }

    public void replyMessage(View v){
        int position = mListView.getPositionForView(v);
        Log.d("me reply","yes"+position);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.holder) {
            Log.e(TAG, "#####onClick v=" + v);
            int position = mListView.getPositionForView(v);
            if (position != ListView.INVALID_POSITION) {
                mMessageItems.remove(position);
                mSlideAdapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.menu_refresh,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.refresh:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
