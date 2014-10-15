package com.gdut.pet.common.network;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

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
public class NetConnection
{

	private static final String TAG = "com.gdut.pet.common.network.NetConnection";

	public NetConnection(final String url, final HttpMethod method,
			final PersistentCookieStore cookieStore,
			final SuccessCallback successCallback,
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
				// get httpclient���� ��DefaultHttpClient Ĭ�ϵ�
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
						client.setCookieStore(cookieStore);
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
						Log.i(TAG, "ע��,��������,200����,");
					}

					System.out.println("Result:" + result);
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
