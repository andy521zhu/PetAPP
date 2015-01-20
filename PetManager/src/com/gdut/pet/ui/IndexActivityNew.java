package com.gdut.pet.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import com.ui.mypet.R;

@SuppressWarnings("deprecation")
public class IndexActivityNew extends TabActivity
{

	private TabHost mTabHost;

	private ImageView image_discover;
	private ImageView image_fellow;
	private ImageView image_me;

	private static boolean isDiscoverSelected = true;
	private static boolean isFellowSelected = false;
	private static boolean isMeSelected = false;

	/** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);

		this.initTab();
		// 初始化控件
		this.findViews();

		// 初始化监听
		this.setListener();
		// /

	}

	// 初始化tabhost
	private void initTab()
	{
		// TODO Auto-generated method stub
		mTabHost = getTabHost();
		// 发现
		mTabHost.addTab(mTabHost.newTabSpec("discover")
				.setIndicator("discover")
				.setContent(new Intent(this, AllPetListActivity.class)));
		// 拍照
		mTabHost.addTab(mTabHost.newTabSpec("camera").setIndicator("camera")
				.setContent(new Intent(this, BBSGuanzhuActivity.class)));
		// 我的
		mTabHost.addTab(mTabHost.newTabSpec("my").setIndicator("my")
				.setContent(new Intent(this, MyActivity.class)));
	}

	public void closeActivity()
	{
		Toast.makeText(this, "quit", Toast.LENGTH_SHORT).show();
		this.finish();
	}

	/**
	 * 初始化控件
	 */
	public void findViews()
	{

		image_discover = (ImageView) findViewById(R.id.image_discover_foot);
		image_fellow = (ImageView) findViewById(R.id.image_camera_foot);
		image_me = (ImageView) findViewById(R.id.image_me_foot);
	}

	/**
	 * 初始化监听
	 */
	public void setListener()
	{
		image_discover.setOnClickListener(new clickListener_discover());
		image_fellow.setOnClickListener(new clickListener_camera());
		image_me.setOnClickListener(new clickListener_me());
	}

	private class clickListener_discover implements View.OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			mTabHost.setCurrentTabByTag("discover");
			image_discover.setImageDrawable(getResources().getDrawable(
					R.drawable.tabhost_all_dark_press));
			image_fellow.setImageDrawable(getResources().getDrawable(
					R.drawable.tabhost_follow_bnormal));
			image_me.setImageDrawable(getResources().getDrawable(
					R.drawable.tabhost_my_normal));
		}

	}

	private class clickListener_camera implements View.OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			mTabHost.setCurrentTabByTag("camera");
			image_discover.setImageDrawable(getResources().getDrawable(
					R.drawable.tabhost_all_dark_normal));
			image_fellow.setImageDrawable(getResources().getDrawable(
					R.drawable.tabhost_follow_perss));
			image_me.setImageDrawable(getResources().getDrawable(
					R.drawable.tabhost_my_normal));
		}

	}

	private class clickListener_me implements View.OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			mTabHost.setCurrentTabByTag("my");
			image_discover.setImageDrawable(getResources().getDrawable(
					R.drawable.tabhost_all_dark_normal));
			image_fellow.setImageDrawable(getResources().getDrawable(
					R.drawable.tabhost_follow_bnormal));
			image_me.setImageDrawable(getResources().getDrawable(
					R.drawable.tabhost_my_press));
		}

	}

}