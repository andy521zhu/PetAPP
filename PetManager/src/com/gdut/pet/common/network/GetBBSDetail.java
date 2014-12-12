package com.gdut.pet.common.network;

import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.config.Configs;

/**
 * 连接网络 使用回调函数
 * 
 * @author andyzhu
 * 
 */
public class GetBBSDetail
{

	private static final String TAG = "com.gdut.pet.common.network.GetBBSDetail";

	public GetBBSDetail(
			final String url, // final String action,
			final PersistentCookieStore cookieStore,
			final SuccessCallback successCallback,// 回调函数
			final FailCallback failCallback, String id)
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
							// 调用回调函数[{"content":"宠物不见了，5555","id":"1","title":"宠物丢失啦！","imgNum":"0"},{"status":"success"}]
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
				}, Configs.ACTION, "testGetBBSDetail",
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
