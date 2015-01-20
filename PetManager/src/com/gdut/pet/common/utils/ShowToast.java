package com.gdut.pet.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ShowToast
{

	private String msg = null;
	private Context mContext;
	private static Toast toast;

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
		if (toast == null)
		{
			toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		}
		toast.setText(text);
		toast.show();
	}

	public static void ShowToastCenter(Context context, String string)
	{
		Toast toast = Toast.makeText(context, string, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
	}
}
