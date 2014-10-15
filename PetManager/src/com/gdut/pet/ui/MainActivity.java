/**
 * 主界面
 */
package com.gdut.pet.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.Toast;

import com.gdut.pet.common.view.MyImageView;
import com.gdut.pet.common.view.MyImageView.OnViewClickListener;
import com.gdut.pet.location.GPSLocation;
import com.peo.stor.StorCentral;
import com.peo.straypet.StrayPetList;
import com.ui.mypet.R;

public class MainActivity extends Activity
{
	private static final String TAG = "com.gdut.pet.ui.MainActivity";

	private MyImageView personal_center;
	private MyImageView pet_shop;
	private MyImageView lost_pet;
	private MyImageView find_pet;
	private Context mContext;

	// 是否退出程序
	private static boolean isExit = false;
	// 退出程序的定时器
	private static Timer exitTimer = null;

	GPSLocation getlocation;

	Thread t;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_new);

		// 取消actionbar 主界面取消，其他界面保留
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		// 另一种方法
		// <activity android:theme="@android:style/Theme.Holo.NoActionBar">

		mContext = this;

		personal_center = (MyImageView) findViewById(R.id.personal_center);// 个人中心
		pet_shop = (MyImageView) findViewById(R.id.pet_shop);
		lost_pet = (MyImageView) findViewById(R.id.lost_pet);
		find_pet = (MyImageView) findViewById(R.id.find_pet);

		personal_center.setOnClickIntent(new OnViewClickListener()
		{

			@Override
			public void onViewClick(MyImageView view)
			{
				// TODO Auto-generated method stub
				Intent it = new Intent(MainActivity.this, IndexActivity.class);
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
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (isExit == false)
			{
				isExit = true;
				if (exitTimer != null)
				{
					exitTimer.cancel(); // 将原任务从队列中移除
				}
				// 重新实例一个定时器
				exitTimer = new Timer();
				TimerTask task = new TimerTask()
				{
					@Override
					public void run()
					{
						isExit = false;
					}
				};
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				// 延时两秒触发task任务
				exitTimer.schedule(task, 2000);
			}
			else
			{
				finish();
				System.exit(0);
			}
			return true;
		}

		return super.onKeyUp(keyCode, event);
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
