package com.gdut.pet.common.utils;

import android.content.Context;
import android.widget.Toast;

public class ShowToast
{

	private String msg = null;
	private Context mContext;

	public ShowToast()
	{
	}

	public ShowToast(Context context, String msg)
	{
		this.msg = msg;
		this.mContext = context;
	}

	public static void ShowToast1(Context context, String text)
	{
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}
}
