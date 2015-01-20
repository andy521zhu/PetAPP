package com.gdut.pet.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.common.view.MyImageView;
import com.ui.mypet.R;

public class ActivityPetLostFound extends Activity
{
	private static final String TAG = "com.gdut.pet.ui.ActivityPetLostFound";
	private Context mContext;

	private MyImageView petLost;// 宠物丢失
	private MyImageView petFound;// 宠物找到

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pet_lost_found);
		L.i(TAG, "oncreate");
		mContext = this;
		findViews();

	}

	private void findViews()
	{
		petLost = (MyImageView) findViewById(R.id.lostfound_lost);
		petFound = (MyImageView) findViewById(R.id.lostfound_found);

		petLost.setOnClickIntent(new MyImageView.OnViewClickListener()
		{

			@Override
			public void onViewClick(MyImageView view)
			{
				// TODO Auto-generated method stub
				L.i(TAG, "petLost click");
				toastMgr.builder.display("宠物丢失", 0);
				Intent intent = new Intent();
				intent.setClass(ActivityPetLostFound.this,
						ActivityPetLostBBS.class);
				startActivity(intent);
			}
		});

		petFound.setOnClickIntent(new MyImageView.OnViewClickListener()
		{

			@Override
			public void onViewClick(MyImageView view)
			{
				// TODO Auto-generated method stub
				L.i(TAG, "petFound click");
				toastMgr.builder.display("宠物找到", 0);
				Intent intent = new Intent();
				intent.setClass(ActivityPetLostFound.this,
						ActivityPetFoundBBS.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		L.i(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onStop()
	{
		// TODO Auto-generated method stub
		super.onStop();
	}

}
