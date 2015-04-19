package com.wutnews.assistance;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 缓存的基类，使用单例模式复用SharedPreference对象； 使用参照LibCache.java
 * 
 * @author future
 * 
 */
public class Cache_Base {

	private static SharedPreferences sp;
	private static Cache_Base bc;
	private static final String defaultName = "com.bbq_preference";// context.getPackageName()返回的永远是manifest里面的包名；
	
	/**
	 * 使用默认的SharedPreference名字，为com.bus_preference
	 * 
	 * @param context
	 * @return 单例模式的SharedPreference对象
	 */
	public static Cache_Base getInstance(Context context) {
		if (sp == null | bc == null) {
			synchronized (Cache_Base.class) {// 这是一种很高端的单例模式写法Orz
				if (sp == null | bc == null)
					bc = new Cache_Base(context, defaultName);
			}
		}
		return bc;
	}
	/**
	 * 使用自定义的SharedPreference名字
	 * @param context
	 * @param SPName
	 * @return 单例模式的SharedPreference对象
	 */
	public static Cache_Base getInstance(Context context, String SPName) {
		if (sp == null | bc == null) {
			synchronized (Cache_Base.class) {// 这是一种很高端的单例模式写法Orz
				if (sp == null | bc == null)
					bc = new Cache_Base(context, SPName);
			}
		}
		return bc;
	}

	private Cache_Base(Context context, String packageName) {
		sp = context.getSharedPreferences(packageName, Context.MODE_PRIVATE);
	}
	public SharedPreferences getSp() {
		return sp;
	}
	public Cache_Base() {
	}

}
