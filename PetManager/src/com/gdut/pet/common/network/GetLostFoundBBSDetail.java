package com.gdut.pet.common.network;

import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.config.Configs;

/**
 * 连接网络 使用回调函数
 * 
 * @author andyzhu
 * 
 */
public class GetLostFoundBBSDetail
{

	private static final String TAG = "com.gdut.pet.common.network.GetLostFoundBBSDetail";

	public GetLostFoundBBSDetail(
			final String url, // final String action,
			final PersistentCookieStore cookieStore,
			final SuccessCallback successCallback,// 回调函数
			final FailCallback failCallback, String id, String isLostFound)
	{

		new NetConnection(url, HttpMethod.POST, cookieStore,
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
				}, Configs.ACTION, "testLostFoundBBS",
				//
				"isLostFound", isLostFound,// 2是lost 3是found
				//
				"id", id);
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
