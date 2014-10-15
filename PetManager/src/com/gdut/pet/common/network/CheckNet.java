package com.gdut.pet.common.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class CheckNet
{

	private static final String TAG = "check network";

	public static boolean checkNet(Context context)
	{
		try
		{
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager != null)
			{
				NetworkInfo info = connectivityManager.getActiveNetworkInfo();
				if (info != null && info.isConnected())
				{
					if (info.getState() == NetworkInfo.State.CONNECTED)
					{
						System.out.println("ÍøÂçÁ¬½Ó");
						return true;
					}
				}
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			return false;
		}
		return false;
	}

	public static boolean checkWifiState(Context context)
	{
		final ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final android.net.NetworkInfo wifi = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifi.isAvailable())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean checkGPRSState(Context context)
	{
		final ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo gprs = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		State state = gprs.getState();
		if (state.equals(State.CONNECTED))
		{
			System.out.println("connect");
		}
		if (gprs.isAvailable())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
