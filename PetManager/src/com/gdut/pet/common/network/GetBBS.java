package com.gdut.pet.common.network;

import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.config.Configs;

/**
 * 连接网络 使用回调函数
 * 
 * @author andyzhu
 * 
 */
public class GetBBS
{

	private static final String TAG = "com.gdut.pet.common.network.GetBBS";

	public GetBBS(
			final String url, // final String action,
			final PersistentCookieStore cookieStore,
			final SuccessCallback successCallback,// 回调函数
			final FailCallback failCallback, boolean isRefresh, String id)
	{

		if (isRefresh)
		{
			// 从下拉刷新过来

		}
		else
		{
			// 首次进入去取得数据

		}

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
				}, Configs.ACTION, "testGetBBS",
				//
				"poststype", "1",
				//
				"firstid", "0",
				//
				"lastid", id);
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
