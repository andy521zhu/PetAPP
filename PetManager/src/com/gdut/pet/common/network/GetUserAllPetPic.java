package com.gdut.pet.common.network;

import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.config.Configs;

/**
 * 连接网络 使用回调函数
 * 
 * @author andyzhu
 * 
 */
public class GetUserAllPetPic
{

	private static final String TAG = "com.gdut.pet.common.network.GetUserAllPetPic";

	/**
	 * 
	 * @param url
	 *            请求地址
	 * @param action
	 *            action
	 * @param cookieStore
	 *            cookie
	 * @param successCallback
	 *            成功回调
	 * @param failCallback
	 *            失败回调
	 */
	public GetUserAllPetPic(final String url, final String action,
			final PersistentCookieStore cookieStore,
			final SuccessCallback successCallback,// 回调函数
			final FailCallback failCallback)
	{
		new NetConnection(url, HttpMethod.POST, cookieStore,
				new NetConnection.SuccessCallback()
				{

					@Override
					public void onSuccess(String result)
					{
						// TODO Auto-generated method stub
						// 这里应该解析一下json 判断时候成功, 成功就是success 否则就是fail
						if (successCallback != null)
						{
							// 调用回调函数
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
				Configs.ACTION, action);
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
