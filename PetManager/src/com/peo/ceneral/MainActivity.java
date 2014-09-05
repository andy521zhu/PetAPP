/**
 * 主界面
 */
package com.peo.ceneral;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.peo.location.CELLLocation;
import com.peo.location.GPSLocation;
import com.peo.man.PersonalCentral;
import com.peo.stor.StorCentral;
import com.peo.straypet.StrayPetList;
import com.ui.mypet.R;

public class MainActivity extends Activity
{

	private Button personal_center;
	private Button pet_shop;
	private Button lost_pet;
	private Button find_pet;
	GPSLocation getlocation;
	Thread t;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_new);

		personal_center = (Button) findViewById(R.id.personal_center);// 个人中心
		pet_shop = (Button) findViewById(R.id.pet_shop);
		lost_pet = (Button) findViewById(R.id.lost_pet);
		find_pet = (Button) findViewById(R.id.find_pet);

		CELLLocation.getLocationByCell(this);
		GPSLocation.getLocal(this);

		personal_center.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent it = new Intent(MainActivity.this, PersonalCentral.class);
				startActivity(it);
			}

		});

		pet_shop.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent it = new Intent(MainActivity.this, StorCentral.class);
				startActivity(it);
			}

		});

		lost_pet.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent it = new Intent(MainActivity.this, StrayPetList.class);
				startActivity(it);
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
