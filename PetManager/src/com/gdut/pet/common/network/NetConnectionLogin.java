package com.gdut.pet.common.network;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.config.Configs;

/**
 * �������� ʹ�ûص�����
 * 
 * @author andyzhu
 * 
 */
public class NetConnectionLogin
{

	private static final String TAG = "com.gdut.pet.common.network.NetConnectionLogin";

	public NetConnectionLogin(final Context context, final String url,
			final HttpMethod method, final SuccessCallback successCallback,
			final FailCallback failCallback, final String... kvs // ����ɱ�����������������������ֵ��
	)
	{
		// �������첽�߳� �ܺ���
		new AsyncTask<Void, Void, String>()
		{

			@Override
			protected String doInBackground(Void... arg0)
			{
				HttpPost request = new HttpPost(url);
				HttpResponse response = null;
				StringBuffer paramsStr = new StringBuffer();
				String result = null;
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				DefaultHttpClient client = getHttpClient();
				for (int i = 0; i < kvs.length; i += 2)
				{
					// �õ������б�
					paramsStr.append(kvs[i]).append("=").append(kvs[i + 1])
							.append("&");
					params.add(new BasicNameValuePair(kvs[i], kvs[i + 1]));
				}
				try
				{

					switch (method)
					{
					case POST:
						request.setEntity(new UrlEncodedFormEntity(params,
								HTTP.UTF_8));
						// get httpclient���� ��DefaultHttpClient Ĭ�ϵ�

						PersistentCookieStore myCookieStore1 = new PersistentCookieStore(
								context);
						if (myCookieStore1.isPrefCookieOk())
						{
							Log.i(TAG, "sp������cookie");
							client.setCookieStore(myCookieStore1);
						}
						else
						{
							Log.i(TAG, "sp����û��cookie");
						}

						response = client.execute(request);

						break;
					default:

						break;
					}

					System.out.println("Request url:" + url);
					System.out.println("Request data:" + paramsStr);

					if (response.getStatusLine().getStatusCode() == 200)
					{
						result = EntityUtils.toString(response.getEntity());

						PersistentCookieStore myCookieStore = new PersistentCookieStore(
								context);
						List<Cookie> cookies = client.getCookieStore()
								.getCookies();
						for (Cookie cookie : cookies)
						{
							// ����cookie
							myCookieStore.addCookie(cookie);
						}
					}
					else if (response.getStatusLine().getStatusCode() == 404)
					{
						// �Ҳ�����ҳ
					}

					System.out.println("Result:" + result);
					if (result == null)
					{
						return null;
					}
					return result.toString();

				}
				catch (MalformedURLException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}

				return null;
			}

			/**
			 * ��ִ����doInBackground֮��ͻ�ִ�����onPostExecute����
			 * �������Ĳ�������doInBackground�ķ���ֵ result
			 */
			@Override
			protected void onPostExecute(String result)
			{

				if (result != null)
				{
					if (successCallback != null)
					{
						// ����ص��������Ǹ���֪ͨ������,�������,ִ�еĽ��
						// ������⼸�ɻص������ĳ���
						successCallback.onSuccess(result);
					}
				}
				else
				{
					if (failCallback != null)
					{
						failCallback.onFail();
					}
				}

				super.onPostExecute(result);
			}
		}.execute();

	}

	// ��ʼ��HttpClient�������ó�ʱ
	private DefaultHttpClient getHttpClient()
	{
		// TODO Auto-generated method stub
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, Configs.timeout);
		HttpConnectionParams.setSoTimeout(httpParams, Configs.SO_TIMEOUT);
		DefaultHttpClient client = new DefaultHttpClient(httpParams);

		// �ж��Ƿ񱣴��� cookie ����� ������cookie ���û�оͲ�����cookie

		return client;
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
