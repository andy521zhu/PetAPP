package com.peo.ceneral;

import java.util.ArrayList;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdut.pet.common.utils.ScreenInfo;
import com.ui.mypet.R;

public class IndexActivity extends ActivityGroup
{
	private final static String TAG = "IndexActivity.java";
	private TextView petTextView;
	private TextView friendsTextView;
	private TextView socialTextView;
	private TextView myTextView;
	private ImageView youbiao;
	private ViewPager viewPager;

	float predee = 0.0f;
	int current = 0;
	private int w = 0;

	private ArrayList<View> pageViews;

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.index_activity);

		mContext = this;

		petTextView = (TextView) findViewById(R.id.pet_index);
		friendsTextView = (TextView) findViewById(R.id.friends_index);
		socialTextView = (TextView) findViewById(R.id.social_index);
		myTextView = (TextView) findViewById(R.id.my_index);

		viewPager = (ViewPager) findViewById(R.id.pager);
		youbiao = (ImageView) findViewById(R.id.youbiao);

		TextView btns[] =
		{ petTextView, friendsTextView, socialTextView, myTextView };

		initView();
		viewPager.setAdapter(new MyPagerViewAdapter(pageViews));

		final ScreenInfo screenInfo = new ScreenInfo(IndexActivity.this);
		w = screenInfo.getWidth() / 4;
		final RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) youbiao
				.getLayoutParams();
		lp.width = w;
		youbiao.setLayoutParams(lp);
		// 设置第一次显示在左边
		final TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 0);
		animation.setDuration(1);
		animation.setFillAfter(true);
		youbiao.startAnimation(animation);

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{
			// 此方法是页面跳转完后得到调用，arg0是你当前选中的页面的Position（位置编号）
			@Override
			public void onPageSelected(int arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
				// TODO Auto-generated method stub
				// arg0 :当前页面，及你点击滑动的页面
				// arg1:当前页面偏移的百分比
				// arg2:当前页面偏移的像素位置
				if (arg1 != 0)
				{
					final TranslateAnimation animation = new TranslateAnimation(
							predee * w + current, arg1 * w + arg0 * w, 0, 0);
					animation.setDuration(200);
					animation.setFillAfter(true);
					youbiao.startAnimation(animation);
					predee = arg1;
					current = arg0 * w;
				}
			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
				// TODO Auto-generated method stub
				if (arg0 == 0)
				{
					// arg0==0的时辰默示什么都没做。
					Log.i(TAG, "----->>>>onPageScrollStateChanged == 0");
				}
				else if (arg0 == 1)
				{
					// arg0 ==1的时辰默示正在滑动
					Log.i(TAG, "----->>>>onPageScrollStateChanged == 1");
				}
				else if (arg0 == 2)
				{
					// arg0==2的时辰默示滑动完毕了
					Log.i(TAG, "----->>>>onPageScrollStateChanged == 2");
				}
			}
		});
		for (int i = 0; i < 4; i++)
		{
			final int a = i;
			btns[a].setOnClickListener(new View.OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{
					// TODO Auto-generated method stub
					viewPager.setCurrentItem(a);
				}
			});
		}
		btns[0].performClick();

	}

	@SuppressWarnings("deprecation")
	private void initView()
	{
		pageViews = new ArrayList<View>();
		final View view00 = getLocalActivityManager().startActivity(
				"Activity00",
				new Intent(this, com.peo.ceneral.PetActivity.class))
				.getDecorView();
		final View view01 = getLocalActivityManager().startActivity(
				"Activity01",
				new Intent(this, com.peo.ceneral.MyFriendsActivity.class))
				.getDecorView();
		final View view02 = getLocalActivityManager().startActivity(
				"Activity02",
				new Intent(this, com.peo.ceneral.SocialActivity.class))
				.getDecorView();
		final View view03 = getLocalActivityManager().startActivity(
				"Activity03",
				new Intent(this, com.peo.ceneral.MyActivity.class))
				.getDecorView();
		pageViews.add(view00);
		pageViews.add(view01);
		pageViews.add(view02);
		pageViews.add(view03);

	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
