package com.gdut.pet.common.network;

import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.config.Configs;

/**
 * �������� ʹ�ûص�����
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
	 *            �����ַ
	 * @param action
	 *            action
	 * @param cookieStore
	 *            cookie
	 * @param successCallback
	 *            �ɹ��ص�
	 * @param failCallback
	 *            ʧ�ܻص�
	 */
	public GetUserAllPetPic(final String url, final String action,
			final PersistentCookieStore cookieStore,
			final SuccessCallback successCallback,// �ص�����
			final FailCallback failCallback)
	{
		new NetConnection(url, HttpMethod.POST, cookieStore,
				new NetConnection.SuccessCallback()
				{

					@Override
					public void onSuccess(String result)
					{
						// TODO Auto-generated method stub
						// ����Ӧ�ý���һ��json �ж�ʱ��ɹ�, �ɹ�����success �������fail
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
				Configs.ACTION, action);
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
