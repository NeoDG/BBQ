package com.wutnews.bbq;




import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.wutnews.assistance.Part_doublefargment_gen;
import com.wutnews.bbq.detail.hot_detail_activity;
import com.wutnews.bbq.detail.hot_publish;
import com.wutnews.frags.HotFragment;
/**
 *
 */
public class Part_1ChatActivity extends SherlockFragmentActivity{
	
	private static FragmentManager fragmentManager;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.part_sub_fragmentutil);
		fragmentManager = this.getSupportFragmentManager();
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.add(R.id.content, new Part_doublefargment_gen().genListFragment(1), "part-chat");
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_publish,menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
			case android.R.id.home:
				super.onBackPressed();
				return true;
			case R.id.publish:
				Intent intent = new Intent(this,hot_publish.class);
				Bundle bundle = new Bundle();
				bundle.putInt("sec_id",1);
				intent.putExtras(bundle);
				startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

}
