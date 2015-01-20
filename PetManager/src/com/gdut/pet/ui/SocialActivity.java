package com.gdut.pet.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ui.mypet.R;

public class SocialActivity extends Activity
{

	private Button nearbybtn;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.social);
		findID();
	}

	private void findID()
	{
		nearbybtn = (Button) findViewById(R.id.showmap);
		nearbybtn.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SocialActivity.this, MapActivity.class);
				startActivity(intent);
			}
		});

	}
}