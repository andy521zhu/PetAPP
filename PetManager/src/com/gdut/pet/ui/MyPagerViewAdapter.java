package com.gdut.pet.ui;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class MyPagerViewAdapter extends PagerAdapter
{

	private ArrayList<View> pageViews;

	public MyPagerViewAdapter()
	{

	}

	public MyPagerViewAdapter(ArrayList<View> _pageViews)
	{
		this.pageViews = _pageViews;
	}

	@Override
	public void destroyItem(View container, int position, Object object)
	{
		// TODO Auto-generated method stub
		((ViewPager) container).removeView(pageViews.get(position));

		// 不能调用 否则就会出错 说没有重写destroyItem这个方法
		// super.destroyItem(container, position, object);
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return pageViews.size();
	}

	@Override
	public int getItemPosition(final Object object)
	{
		return super.getItemPosition(object);
	}

	@Override
	public Object instantiateItem(final View arg0, final int arg1)
	{
		((ViewPager) arg0).addView(pageViews.get(arg1));
		return pageViews.get(arg1);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

}
