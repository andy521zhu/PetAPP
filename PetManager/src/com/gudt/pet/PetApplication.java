package com.gudt.pet;

import android.app.Application;

import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.analytics.AnalyticsConfig;

public class PetApplication extends Application
{

	private static final String TAG = "application";

	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
		L.i(TAG, "oncreate");

		// 创建默认的imageLoader配置参数
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration
				.createDefault(getApplicationContext());
		L.i(TAG, "ImageLoaderConfiguration");
		// initialise imageLoader whit configuration
		ImageLoader.getInstance().init(configuration);
		L.i(TAG, "ImageLoader.getInstance().init(configuration);");
		toastMgr.builder.init(getApplicationContext());

		// umeng
		AnalyticsConfig.setAppkey("5450da23fd98c5a66d031ea2");
	}

}
