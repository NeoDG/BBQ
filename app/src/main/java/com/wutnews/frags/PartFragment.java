package com.wutnews.frags;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.wutnews.bbq.Part_7AskActivity;
import com.wutnews.bbq.Part_1ChatActivity;
import com.wutnews.bbq.Part_4CircleActivity;
import com.wutnews.bbq.Part_5LostfoundActivity;
import com.wutnews.bbq.Part_6SaleActivity;
import com.wutnews.bbq.Part_2SecretActivity;
import com.wutnews.bbq.Part_3SuroundActivity;
import com.wutnews.bbq.R;

public class PartFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_part, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Button chatBtn = (Button) getActivity().findViewById(R.id.btn_chat);
		chatBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), Part_1ChatActivity.class);
				startActivity(intent);
			}
		});
		
		Button secretBtn = (Button) getActivity().findViewById(R.id.btn_secret);
		secretBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), Part_2SecretActivity.class);
				startActivity(intent);
			}
		});
	
		Button suroundBtn = (Button) getActivity().findViewById(R.id.btn_suround);
		suroundBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), Part_3SuroundActivity.class);
				startActivity(intent);
			}
		});
		
		Button circleBtn = (Button) getActivity().findViewById(R.id.btn_circle);
		circleBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), Part_4CircleActivity.class);
				startActivity(intent);
			}
		});
		
		Button lfBtn = (Button) getActivity().findViewById(R.id.btn_lostfound);
		lfBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), Part_5LostfoundActivity.class);
				startActivity(intent);
			}
		});
		
		Button saleBtn = (Button) getActivity().findViewById(R.id.btn_sale);
		saleBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), Part_6SaleActivity.class);
				startActivity(intent);
			}
		});
		
		Button askBtn = (Button) getActivity().findViewById(R.id.btn_ask);
		askBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), Part_7AskActivity.class);
				startActivity(intent);
			}
		});
	}
}
