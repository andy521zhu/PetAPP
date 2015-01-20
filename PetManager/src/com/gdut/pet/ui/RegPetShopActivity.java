package com.gdut.pet.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

public class RegPetShopActivity extends Activity
{

	private static final String TAG = "com.gdut.pet.ui.RegPetShopActivity";
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_stor);
		mContext = this;
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(mContext);
	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(mContext);
	}

}
