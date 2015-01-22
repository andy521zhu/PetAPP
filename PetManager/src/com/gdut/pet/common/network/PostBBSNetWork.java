package com.gdut.pet.common.network;

import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.config.Configs;

public class PostBBSNetWork
{

	public PostBBSNetWork()
	{

	}

	public PostBBSNetWork(final String url, final String action, final PersistentCookieStore cookieStore, final SuccessCallback successCallback,// 回调函数
			final FailCallback failCallback, String content, String type, String longitude, String latitude)
	{
		new NetConnection(url, HttpMethod.POST, cookieStore,
		//
				new NetConnection.SuccessCallback()
				{

					@Override
					public void onSuccess(String result)
					{
						// TODO Auto-generated method stub
						if (successCallback != null)
						{
							successCallback.onSuccess(result);
						}
					}
				},
				//
				new NetConnection.FailCallback()
				{

					@Override
					public void onFail()
					{
						// TODO Auto-generated method stub
						if (failCallback != null)
						{
							failCallback.onFail();
						}
					}
				},
				//
				Configs.ACTION, action,
				//
				"content", content,
				//
				"type", type,
				//
				"longitude",longitude,
				//
				"latitude", latitude
		//
		);
	}

	// 回调函数
	public static interface SuccessCallback
	{
		void onSuccess(String result);
	}

	public static interface FailCallback
	{
		void onFail();
	}

}
