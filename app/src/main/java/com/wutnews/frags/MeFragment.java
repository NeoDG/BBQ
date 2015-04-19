package com.wutnews.frags;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wutnews.assistance.Cache_Login;
import com.wutnews.assistance.MyApplication;
import com.wutnews.bbq.R;
import com.wutnews.bbq.detail.Me_detail_Activity;

public class MeFragment extends Fragment {

    TextView nick;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nick = (TextView) getActivity().findViewById(R.id.nickname);
        nick.setText(new Cache_Login(MyApplication.getAppContext()).getNickname());
        Button btn_pm = (Button) getActivity().findViewById(R.id.btn_pm);
        btn_pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Me_detail_Activity.class);
                startActivity(intent);
            }
        });
        Button btn_favor = (Button) getActivity().findViewById(R.id.btn_favored);
        btn_favor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Me_detail_Activity.class);
                startActivity(intent);
            }
        });
        Button btn_submitted = (Button) getActivity().findViewById(R.id.btn_submitted);
        btn_submitted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Me_detail_Activity.class);
                startActivity(intent);
            }
        });
        Button btn_replyed = (Button) getActivity().findViewById(R.id.btn_replyed);
        btn_replyed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Me_detail_Activity.class);
                startActivity(intent);
            }
        });
    }
}
