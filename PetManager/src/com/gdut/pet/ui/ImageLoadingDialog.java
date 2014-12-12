package com.gdut.pet.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.ui.mypet.R;

/**
 * 
 */
public class ImageLoadingDialog extends Dialog
{

	public ImageLoadingDialog(Context context)
	{
		super(context, R.style.ImageloadingDialogStyle);
		// setOwnerActivity((Activity) context);// …Ë÷√dialog»´∆¡œ‘ æ
	}

	private ImageLoadingDialog(Context context, int theme)
	{
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog_big_imageloading);
	}

}
