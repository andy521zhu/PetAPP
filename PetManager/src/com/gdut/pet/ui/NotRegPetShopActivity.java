package com.gdut.pet.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

public class NotRegPetShopActivity extends Activity
{

	private static final String TAG = "com.gdut.pet.ui.NotRegPetShopActivity";
	private Button regPetShopbtn;
	private Context mContext;
	private Button back;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_not_reg_pet_store);
		mContext = this;
		regPetShopbtn = (Button) findViewById(R.id.btn_reg_pet_shop);
		regPetShopbtn.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(NotRegPetShopActivity.this,
						RegPetShopActivity.class);
				startActivity(intent);
			}
		});

		back = (Button) findViewById(R.id.not_reg_back);
		back.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		});

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
