package com.gdut.pet.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.ui.mypet.R;

public class PetActivity extends Activity implements OnClickListener
{

	private static final String TAG = "com.gdut.pet.ui.PetActivity";

	private LinearLayout ll_petInfo_head;
	private LinearLayout ll_petInfo;
	private LinearLayout ll_petPics;
	private LinearLayout ll_petSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pet);
		// find ids
		ll_petInfo_head = (LinearLayout) findViewById(R.id.ll_petInfo_head);
		ll_petInfo = (LinearLayout) findViewById(R.id.ll_petInfo);
		ll_petPics = (LinearLayout) findViewById(R.id.ll_petPics);
		ll_petSearch = (LinearLayout) findViewById(R.id.ll_petSearch);

		// set click listener
		ll_petInfo_head.setOnClickListener(this);
		ll_petInfo.setOnClickListener(this);
		ll_petPics.setOnClickListener(this);
		ll_petSearch.setOnClickListener(this);
	}

	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.ll_petInfo_head:

			break;
		case R.id.ll_petInfo:

			break;
		case R.id.ll_petPics:

			break;
		case R.id.ll_petSearch:

			break;

		default:
			break;
		}
	}

	public void invisibleOnScreen()
	{
		Log.d(TAG, "invisibleOnScreen");

	}

	public void goneOnScreen()
	{
		Log.d(TAG, "goneOnScreen");

	}

}
