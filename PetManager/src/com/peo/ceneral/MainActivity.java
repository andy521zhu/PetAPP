/**
 * 主界面
 */
package com.peo.ceneral;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.peo.location.CELLLocation;
import com.peo.location.GPSLocation;
import com.peo.man.PersonalCentral;
import com.peo.stor.StorCentral;
import com.peo.straypet.StrayPetList;
import com.peo.view.MyImageView;
import com.peo.view.MyImageView.OnViewClickListener;
import com.ui.mypet.R;

public class MainActivity extends Activity
{

	private MyImageView personal_center;
	private MyImageView pet_shop;
	private MyImageView lost_pet;
	private MyImageView find_pet;
	private Context mContext;

	GPSLocation getlocation;
	Thread t;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_new);

		mContext = this;

		personal_center = (MyImageView) findViewById(R.id.personal_center);// 个人中心
		pet_shop = (MyImageView) findViewById(R.id.pet_shop);
		lost_pet = (MyImageView) findViewById(R.id.lost_pet);
		find_pet = (MyImageView) findViewById(R.id.find_pet);

		CELLLocation.getLocationByCell(this);
		GPSLocation.getLocal(this);

		personal_center.setOnClickIntent(new OnViewClickListener()
		{

			@Override
			public void onViewClick(MyImageView view)
			{
				// TODO Auto-generated method stub
				Intent it = new Intent(MainActivity.this, PersonalCentral.class);
				startActivity(it);
			}
		});

		pet_shop.setOnClickIntent(new OnViewClickListener()
		{

			@Override
			public void onViewClick(MyImageView view)
			{
				// TODO Auto-generated method stub
				Intent it = new Intent(MainActivity.this, StorCentral.class);
				startActivity(it);
			}
		});

		lost_pet.setOnClickIntent(new OnViewClickListener()
		{

			@Override
			public void onViewClick(MyImageView view)
			{
				// TODO Auto-generated method stub
				Intent it = new Intent(MainActivity.this, StrayPetList.class);
				startActivity(it);
			}
		});

		find_pet.setOnClickIntent(new OnViewClickListener()
		{

			@Override
			public void onViewClick(MyImageView view)
			{
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "功能尚未实现", 1).show();
			}
		});
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		GPSLocation.removeLocation();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
