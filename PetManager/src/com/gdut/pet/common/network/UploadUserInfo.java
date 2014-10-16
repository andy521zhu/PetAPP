package com.gdut.pet.common.network;

import com.gdut.pet.common.tools.PersistentCookieStore;

/**
 * �������� ʹ�ûص�����
 * 
 * @author andyzhu
 * 
 */
public class UploadUserInfo
{

	private static final String TAG = "com.gdut.pet.common.network.UploadUserInfo";

	public UploadUserInfo(
			final String url, // final String action,
			final PersistentCookieStore cookieStore,
			final SuccessCallback successCallback,// �ص�����
			final FailCallback failCallback, boolean isRefresh, String id)
	{

		if (isRefresh)
		{
			// ������ˢ�¹���

		}
		else
		{
			// �״ν���ȥȡ������

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
							// ���ûص�����
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
				"poststype", "1",
				//
				"firstid", "1",
				//
				"lastid", "2");
	}

	// �ص�����
	public static interface SuccessCallback
	{
		void onSuccess(String result);
	}

	public static interface FailCallback
	{
		void onFail();
	}
}
