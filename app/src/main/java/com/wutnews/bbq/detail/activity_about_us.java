package com.wutnews.bbq.detail;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.wutnews.assistance.Cache_setting;
import com.wutnews.assistance.MyApplication;
import com.wutnews.bbq.R;

/**
 * Created by Neo on 2015/4/15.
 */
public class activity_about_us extends SherlockActivity {

    Cache_setting cache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        cache = new Cache_setting(MyApplication.getAppContext());
        setContentView(R.layout.activity_aboutus);
        cache.setAbout("BBQ\n" +
                "是一款移动端校园论坛APP。基于原有武汉理工大学论坛经纬BBS的移动端的需求开发，整合了BBS的闲聊特区、" +
                "失物招领、跳蚤市场等热门版块，并且根据移动端用户需求量身定制了匿名、周边、圈子版块。" +
                "旨在为校园用户提供一个综合校园信息发布与社交服务应用。\n" +
                "\n" +
                "\n•闲聊特区\n" +
                "整合BBS闲聊特区版块。传承“灌水”这一BBS传统文化。\n" +
                "\n•USecret\n" +
                "匿名版块。满足用户相较实名平台更加激烈的表达需求。\n" +
                "\n•理工周边\n" +
                "交流理工周边环境的版块。发现学校附近有意思的去处，挖掘校园生活圈的新鲜亮点。\n" +
                "\n•圈子\n" +
                "团体或个人发布活动的版块。便于学校广大社团组织和个人社交活动的发布与宣传。\n" +
                "\n•失物招领\n" +
                "整合BBS失物招领版块。为校园用户提供简洁高效的线上失物招领信息发布平台。\n" +
                "\n•跳蚤市场\n" +
                "整合BBS跳蚤买卖版块。为校园用户提供简洁而个性化的二手交易信息发布平台。\n" +
                "\n•记者帮你问\n" +
                "整合并改进BBS校园建言版块。依托新闻经纬网记者在线栏目，用户可以在这里将校园建言直接高效地反馈给值班记者，更深入挖掘并报道校园生活中存在的问题，共同改善校园环境。\n");
        String about = cache.getAbout();
        TextView t = (TextView)findViewById(R.id.about_us);
        t.setText(about);
    }
}
