package com.gdut.pet.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdut.pet.config.Configs;
import com.peo.man.AllMessage;
import com.ui.mypet.R;

public class MyActivity extends Activity implements OnClickListener
{

	private static final String TAG = "com.gdut.pet.ui.MyActivity";
	private ImageView headPic_myInfo;
	private TextView name_myInfo;
	private LinearLayout ll_myInfo_head;

	private LinearLayout ll_petShop;
	private LinearLayout ll_lostPet;
	private LinearLayout ll_settings;

	private Button showMap;

	private SharedPreferences userdataSP;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myactivity);

		userdataSP = getSharedPreferences(Configs.USERDATA_SP, 0);

		ll_myInfo_head = (LinearLayout) findViewById(R.id.myInfo_head);
		ll_myInfo_head.setOnClickListener(this);
		ll_petShop = (LinearLayout) findViewById(R.id.ll_petShop);
		ll_lostPet = (LinearLayout) findViewById(R.id.ll_lostPet);
		ll_settings = (LinearLayout) findViewById(R.id.ll_settings);

		name_myInfo = (TextView) findViewById(R.id.name_myInfo);
		if (userdataSP.getBoolean(Configs.IS_LOGIN, false))
		{
			name_myInfo.setText("用户已经登录");
		}

		// 显示地图的按钮
		showMap = (Button) findViewById(R.id.showMap);
		showMap.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

			}
		});
		ll_petShop.setOnClickListener(this);
		ll_lostPet.setOnClickListener(this);
		ll_settings.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId())
		{

		// 点击进入个人信息设置 和 修改
		// 首先check有没有登录
		case R.id.myInfo_head:
			SharedPreferences sp = getSharedPreferences(Configs.USERDATA_SP,
					MODE_PRIVATE);
			// 是否已经登录
			boolean isLogn = sp.getBoolean(Configs.IS_LOGIN, false);
			sp = null;
			if (isLogn)
			{
				// 显示用户信息
				intent = new Intent();
				intent.setClass(MyActivity.this, AllMessage.class);
				startActivity(intent);
			}
			else
			{
				intent = new Intent();
				intent.setClass(MyActivity.this, LoginActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean(Configs.IS_FROM_REG, false);
				intent.putExtras(bundle);
				startActivity(intent);
			}

			break;
		// 点击进入宠物商店界面
		case R.id.ll_petShop:
			intent = new Intent();
			intent.setClass(MyActivity.this, AllMessage.class);
			startActivity(intent);
			break;
		// 点击进入宠物丢失界面
		case R.id.ll_lostPet:
			intent = new Intent();
			intent.setClass(MyActivity.this, AllMessage.class);
			startActivity(intent);
			break;
		// 点击进入设置界面
		case R.id.ll_settings:
			intent = new Intent();
			intent.setClass(MyActivity.this, AllMessage.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
