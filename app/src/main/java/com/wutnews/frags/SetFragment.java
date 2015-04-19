package com.wutnews.frags;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wutnews.assistance.Cache_setting;
import com.wutnews.assistance.MyApplication;
import com.wutnews.assistance.SwitchView;
import com.wutnews.bbq.R;
import com.wutnews.bbq.detail.activity_about_us;

public class SetFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_set, container, false);

	}
	Cache_setting cache;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);


		cache = new Cache_setting(MyApplication.getAppContext());
		Button about = (Button) getActivity().findViewById(R.id.btn_about_us);
		about.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getActivity().startActivity(new Intent(getActivity(),activity_about_us.class));
			}
		});

		SwitchView sWifi = (SwitchView) getActivity().findViewById(R.id.switch_wifi);
		sWifi.setSwitchStatus(cache.getWifi_update());
		sWifi.setOnSwitchChangeListener(new SwitchView.OnSwitchChangeListener() {
			@Override
			public void onSwitchChanged(boolean open) {
				cache.setWifi_update(open);
			}
		});
		SwitchView sNoti = (SwitchView) getActivity().findViewById(R.id.switch_noti);
		sNoti.setSwitchStatus(cache.getAuto_noti());
		sNoti.setOnSwitchChangeListener(new SwitchView.OnSwitchChangeListener() {
			@Override
			public void onSwitchChanged(boolean open) {
				cache.setAuto_noti(open);
			}
		});
	}
}
