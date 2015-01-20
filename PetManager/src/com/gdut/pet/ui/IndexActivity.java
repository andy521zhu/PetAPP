package com.gdut.pet.ui;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdut.pet.common.utils.ScreenInfo;
import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

public class IndexActivity extends ActivityGroup
{
	private final static String TAG = "com.gdut.pet.ui.IndexActivity";
	private TextView petTextView;
	private TextView friendsTextView;
	private TextView socialTextView;
	private TextView myTextView;
	private ImageView youbiao;
	private ViewPager viewPager;
	private LocalActivityManager mActivityManager;

	// private ActivityManager mActivityManager;

	private ActionBar actionBar;

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
		setContentView(R.layout.index_activity);

		mActivityManager = getLocalActivityManager();
		// ȡ������actionbar
		actionBar = getActionBar();
		// ȡ��Сͼ�����ʾ
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("�ҵĳ���");
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
		// ���õ�һ����ʾ�����
		final TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 0);
		animation.setDuration(1);
		animation.setFillAfter(true);
		youbiao.startAnimation(animation);

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{
			// �˷�����ҳ����ת���õ����ã�arg0���㵱ǰѡ�е�ҳ���Position��λ�ñ�ţ�
			@Override
			public void onPageSelected(int arg0)
			{
				// TODO Auto-generated method stub
				// actionbar����ʾ�����ݲ�һ��
				select(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
				// TODO Auto-generated method stub
				// arg0 :��ǰҳ�棬������������ҳ��
				// arg1:��ǰҳ��ƫ�Ƶİٷֱ�
				// arg2:��ǰҳ��ƫ�Ƶ�����λ��
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
					// arg0==0��ʱ��Ĭʾʲô��û����
					Log.i(TAG, "----->>>>onPageScrollStateChanged == 0");
				}
				else if (arg0 == 1)
				{
					// arg0 ==1��ʱ��Ĭʾ���ڻ���
					Log.i(TAG, "----->>>>onPageScrollStateChanged == 1");
				}
				else if (arg0 == 2)
				{
					// arg0==2��ʱ��Ĭʾ���������
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
		// ������ȿ�ʼ��ʾ
		btns[0].performClick();

	}

	@SuppressWarnings("deprecation")
	private void initView()
	{
		pageViews = new ArrayList<View>();
		final View view00 = mActivityManager.startActivity("Activity00",
				new Intent(this, com.gdut.pet.ui.PetActivity.class))
				.getDecorView();
		final View view01 = mActivityManager.startActivity("Activity01",
				new Intent(this, com.gdut.pet.ui.MyFriendsActivity.class))
				.getDecorView();
		final View view02 = mActivityManager.startActivity("Activity02",
				new Intent(this, com.gdut.pet.ui.SocialActivity.class))
				.getDecorView();
		final View view03 = mActivityManager.startActivity("Activity03",
				new Intent(this, com.gdut.pet.ui.MyActivity.class))
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
		MobclickAgent.onResume(mContext);
		mActivityManager.dispatchResume();
		if (viewPager != null)
		{
			int num = viewPager.getCurrentItem();
			switch (num)
			{
			case 0:
				Activity activity0 = mActivityManager.getActivity("Activity00");
				if (activity0 != null && activity0 instanceof PetActivity)
				{
					((PetActivity) activity0).invisibleOnScreen();
				}
				break;
			case 1:
				Activity activity1 = mActivityManager.getActivity("Activity01");
				if (activity1 != null && activity1 instanceof PetActivity)
				{
					((MyFriendsActivity) activity1).invisibleOnScreen();
				}
				break;
			case 2:
				break;
			case 3:
				break;

			default:
				break;
			}
		}
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO Auto-generated method stub

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}

	private void select(final int i)
	{
		switch (i)
		{
		case 0:
			actionBar.setTitle("�ҵĳ���");
			break;
		case 1:
			actionBar.setTitle("�ҵ�����");
			break;
		case 2:
			actionBar.setTitle("����");
			break;
		case 3:
			actionBar.setTitle("�ҵ���Ϣ");
			break;
		default:
			break;
		}
	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(mContext);
	}

}
