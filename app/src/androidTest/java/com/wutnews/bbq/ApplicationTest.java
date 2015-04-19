package com.wutnews.bbq;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.wutnews.assistance.Cache_Login;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
        Cache_Login c = new Cache_Login(getContext());
        boolean a = c.getIs_logged();
        Log.v("a",a+"");
        c.setIs_logged(true);
        boolean b = c.getIs_logged();
        Log.v("a",a+"");

    }
}