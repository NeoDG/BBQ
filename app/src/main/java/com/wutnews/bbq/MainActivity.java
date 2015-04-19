package com.wutnews.bbq;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.wutnews.assistance.Cache_Login;
import com.wutnews.assistance.MyApplication;
import com.wutnews.frags.HotFragment;
import com.wutnews.frags.MeFragment;
import com.wutnews.frags.MeFragment_Login;
import com.wutnews.frags.PartFragment;
import com.wutnews.frags.SetFragment;

/**
 *
 * @author Neo
 *
 *
 */
public class MainActivity extends SherlockFragmentActivity {
	private static FragmentManager fragmentManager;
	private RadioGroup radioGroup;
	TextView text;
	public Cache_Login c;
	//@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		c = new Cache_Login(MyApplication.getAppContext());
		setContentView(R.layout.main_activity);

		fragmentManager = this.getSupportFragmentManager();
		radioGroup = (RadioGroup) findViewById(R.id.main_tab_group);
		//this.getActionBar().show();
		
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		getSupportActionBar().setCustomView(R.layout.main_actionbar_style);
		text = (TextView) findViewById(R.id.fragtitle);
		inithot();
		dealClickEvent();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//super.onSaveInstanceState(outState);
	}

	public void inithot() {
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.add(R.id.content, new HotFragment(), "hot");
		ft.addToBackStack("hot");
		ft.commit();
		text.setText("热帖");
		text.setTextColor(getResources().getColor(R.color.white));

	}

	/**
	 */

	public static void popStackExceptOne() {
		for (int i = 0, count = fragmentManager.getBackStackEntryCount() - 1; i < count; i++) {
			fragmentManager.popBackStack();
		}
	}

	/**
	 */

	public void dealClickEvent() {

		findViewById(R.id.main_tab_hot).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (fragmentManager.findFragmentByTag("hot") != null
								&& fragmentManager.findFragmentByTag("hot")
								.isVisible()) {
							return;
						}
						text.setText("热帖");
						popStackExceptOne();
					}
				});
		findViewById(R.id.main_tab_part).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						popStackExceptOne();
						FragmentTransaction ft = fragmentManager
								.beginTransaction();
						ft.hide(fragmentManager.findFragmentByTag("hot"));
						PartFragment sf = new PartFragment();
						ft.add(R.id.content, sf, "part");
						ft.addToBackStack("part");
						ft.commit();
						text.setText("版块");
					}
				});
		findViewById(R.id.main_tab_me).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						popStackExceptOne();
						FragmentTransaction ft = fragmentManager
								.beginTransaction();
						ft.hide(fragmentManager.findFragmentByTag("hot"));
						if (!c.getIs_logged()) {
							Log.v("    log?   ", "#" + c.getIs_logged() + "#" +c.getNickname()+"#");
							MeFragment_Login sf = new MeFragment_Login();
							ft.add(R.id.content, sf, "me");
							ft.addToBackStack("login");
						} else {
							MeFragment sf = new MeFragment();
							ft.add(R.id.content, sf, "me");
							ft.addToBackStack("me");
						}
						ft.commit();
						text.setText("我");
					}
				});
		findViewById(R.id.main_tab_set).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						popStackExceptOne();
						FragmentTransaction ft = fragmentManager
								.beginTransaction();
						ft.hide(fragmentManager.findFragmentByTag("hot"));
						SetFragment sf = new SetFragment();
						ft.add(R.id.content, sf, "set");
						ft.addToBackStack("set");
						ft.commit();
						text.setText("设置");
					}
				});
	}

	@Override
	public void onBackPressed() {
		if (fragmentManager.findFragmentByTag("hot") != null
				&& fragmentManager.findFragmentByTag("hot").isVisible())
			// MainActivity.this.finish();
			exitBy2Click();
		else {
			super.onBackPressed();
			radioGroup.check(R.id.main_tab_hot);
		}
	}

	/**
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; //
			Toast.makeText(this, ">_<", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; //
				}
			}, 1000); //

		} else {
			finish();
			System.exit(0);
		}
	}
	
	public void showsub(){
		Intent intent = new Intent();
		intent.setClass(this, Part_1ChatActivity.class);
		startActivity(intent);
	}
	
	 // @Override 
//	  public boolean onCreateOptionsMenu(Menu menu) { 
//		  // Inflate the menu; this adds items to the action bar if it is present.
//	  getMenuInflater().inflate(R.menu.main, menu); return true; }
	  
	  
	 
}
