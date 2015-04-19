package com.wutnews.bbq;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.wutnews.assistance.Part_doublefargment_gen;
import com.wutnews.assistance.TabPagerAdapter;
import com.wutnews.bbq.detail.hot_publish;

public class Part_4CircleActivity extends SherlockFragmentActivity
		/*implements ActionBar.TabListener, OnPageChangeListener*/ {
	/**
	 * 顶部Tab的title
	 */
	private String[] mTabTitles;

	/**
	 * ViewPager对象的引用
	 */
	private ViewPager mViewPager;

	/**
	 * 装载Fragment的容器，我们的每一个界面都是一个Fragment
	 */
	private List<Fragment> mFragmentList;

	/**
	 * ActionBar对象的引用
	 */
	private ActionBar mActionBar;
	private static FragmentManager fragmentManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.part_sub_fragmentutil);

		fragmentManager = this.getSupportFragmentManager();

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.add(R.id.content, new Part_doublefargment_gen().genListFragment(4), "part-chat");
		ft.commit();
//		// 从资源文件在获取Tab的title
//		mTabTitles = new String[] { "团体", "个人" };
//		mFragmentList = new ArrayList<Fragment>();
//
//		mViewPager = (ViewPager) findViewById(R.id.viewpager);
//		// 设置Adapter
//		PagerAdapter pagerAdapter = new TabPagerAdapter(
//				getSupportFragmentManager(), mFragmentList);
//		mViewPager.setAdapter(pagerAdapter);
//		// 设置监听
//		mViewPager.setOnPageChangeListener(this);
//
//		// 获取Action实例我们使用getSupportActionBar()方法
//		mActionBar = getSupportActionBar();
//		mActionBar.setDisplayHomeAsUpEnabled(true);
//		mActionBar.setHomeButtonEnabled(true);
//		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//		// 为ActionBar添加Tab并设置TabListener
//		for (int i = 0; i < mTabTitles.length; i++) {
//			ActionBar.Tab tab = mActionBar.newTab();
//			tab.setText(mTabTitles[i]);
//			tab.setTabListener(this);
//			mActionBar.addTab(tab, i);
//		}
//
//		// 将Fragment加入到List中，并将Tab的title传递给Fragment
//		for (int i = 0; i < mTabTitles.length; i++) {
//			Fragment fragment = new Part_doublefargment_gen().genListFragment(4);
//			Bundle args = new Bundle();
//			args.putString("arg", mTabTitles[i]);
//			fragment.setArguments(args);
//
//			mFragmentList.add(fragment);
//			pagerAdapter.notifyDataSetChanged();
//		}
//
	}
//
//	@Override
//	public void onTabSelected(Tab tab, FragmentTransaction ft) {
//		// 点击ActionBar Tab的时候切换不同的Fragment界面
//		mViewPager.setCurrentItem(tab.getPosition());
//	}
//
//	@Override
//	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
//
//	}
//
//	@Override
//	public void onTabReselected(Tab tab, FragmentTransaction ft) {
//
//	}
//
//	@Override
//	public void onPageScrollStateChanged(int arg0) {
//
//	}
//
//	@Override
//	public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//	}
//
//	@Override
//	public void onPageSelected(int arg0) {
//		// 滑动ViewPager的时候设置相对应的ActionBar Tab被选中
//		mActionBar.setSelectedNavigationItem(arg0);
//	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_publish, menu);
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
				bundle.putInt("sec_id",4);
				intent.putExtras(bundle);
				startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
