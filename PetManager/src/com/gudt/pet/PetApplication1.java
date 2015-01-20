package com.gudt.pet;

import android.app.Application;

import com.gdut.pet.common.utils.L;
import com.umeng.analytics.AnalyticsConfig;

public class PetApplication1 extends Application
{

	private static final String TAG = "application";

	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
		L.i(TAG, "oncreate");

		// umeng
		AnalyticsConfig.setAppkey("5450da23fd98c5a66d031ea2");
	}

}
