package com.gdut.pet.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.ui.mypet.R;

public class BBSDetailActivity extends Activity
{

	private static final String TAG = "com.gdut.pet.ui.BBSDetailActivity";
	private ActionBar mActionBar;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bbs_detail);

		mActionBar = getActionBar();
		mContext = this;
		mActionBar.setTitle("ÏêÏ¸");
	}
}
