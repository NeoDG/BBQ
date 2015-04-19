package com.wutnews.frags;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wutnews.assistance.Cache_Login;
import com.wutnews.threads.LoginThreads;
import com.wutnews.assistance.MyApplication;
import com.wutnews.bbq.R;

import android.os.Handler;
import org.json.JSONException;
import org.json.JSONObject;

public class MeFragment_Login extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_login, container, false);
	}
	Cache_Login cache;
	String usrName;
	String usrPwd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		cache = new Cache_Login(MyApplication.getAppContext());
		ImageButton ib = (ImageButton) getActivity().findViewById(R.id.btn_start);
		ib.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startLogin(view);
			}
		});
	}

	public void startLogin(View v){
		EditText iptName = (EditText) getActivity().findViewById(R.id.input_name);
		EditText iptPwd = (EditText) getActivity().findViewById(R.id.input_pwd);
		usrName = iptName.getText().toString();
		usrPwd = iptPwd.getText().toString();

		if(usrName.length()<=0||usrPwd.length()<=0){
			Toast.makeText(getActivity(),"学号或密码不能为空",Toast.LENGTH_SHORT).show();
		} else {
			showRequestDialog();
			lt = new LoginThreads(handler,usrName,usrPwd);
			Thread threads = new Thread(lt);
			threads.start();
		}
		/**
		 * sync codes
		 */
		//cache.setIs_logged(true);
		Log.v("login log ->", "#" + cache.getIs_logged() + "#" + cache.getNickname());
	}

	public void geneView(){
		android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		MeFragment f = new MeFragment();
		ft.remove(this);
		ft.add(R.id.content, f);
		ft.addToBackStack("me");
		ft.commit();
	}
	private LoginThreads lt;
	private Handler handler = new Handler(){
		JSONObject jsonObject;
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case 1:
					try {
						jsonObject = new JSONObject(lt.getResponse());
						JSONObject data = jsonObject.getJSONObject("data");
						cache.setNickname(data.getString("nickname"));
						cache.setSignature(data.getString("signature"));
						cache.setIs_logged(true);
						Toast.makeText(getActivity(),"登陆成功", Toast.LENGTH_SHORT).show();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					dismissDialog();
					geneView();
					break;
				case -1:
					Toast.makeText(getActivity(),"用户名或密码错",Toast.LENGTH_SHORT).show();
					dismissDialog();
					break;
				case -2:
					Toast.makeText(getActivity(),"用户名或密码错",Toast.LENGTH_SHORT).show();
					dismissDialog();
					break;
				default:
					Toast.makeText(getActivity(),"什么鬼",Toast.LENGTH_SHORT).show();
					dismissDialog();
			}
		}
	};

	private Dialog mDialog = null;

	public void dismissDialog() {
		try {
			if (mDialog != null) {
				mDialog.dismiss();
				mDialog = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showRequestDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
		mDialog = new ProgressDialog(getActivity());
		mDialog.setTitle("查询ing...");
		mDialog.show();
	}


}
