package com.philsutech.mobile.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Fragment {
	EditText username, password;
	Button login, register;
	
	public Login(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.login, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		username = (EditText) getActivity().findViewById(R.id.etUserName);
		password = (EditText) getActivity().findViewById(R.id.etPassword);
		login = (Button) getActivity().findViewById(R.id.btnLogin);
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		register = (Button) getActivity().findViewById(R.id.btnReg);
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), Register.class);
				startActivity(i);
				getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		});
	}

}
