package com.gdut.pet.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.gdut.pet.common.tools.BitmapByteConverter;
import com.gdut.pet.common.utils.BitmapSerializable;
import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

public class ImageShowerActivity extends Activity
{
	private static final String TAG = "com.gdut.pet.ui.ImageShowerActivity";
	private Context mContext;
	private BitmapSerializable bitmapSerializable;
	private Bitmap bitmapToDisplay;
	private ImageView bigImage;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_big_image_show);

		mContext = this;

		initView();

		Bundle bundle = getIntent().getExtras();
		bitmapSerializable = (BitmapSerializable) bundle
				.getSerializable("bitmap");
		bitmapToDisplay = BitmapByteConverter.getBitmap(bitmapSerializable
				.getBitmap());
		bigImage.setImageBitmap(bitmapToDisplay);

		final ImageLoadingDialog dialog = new ImageLoadingDialog(this);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		// 两秒后关闭后dialog
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				dialog.dismiss();
			}
		}, 1000 * 2);
	}

	private void initView()
	{
		bigImage = (ImageView) findViewById(R.id.big_image_show);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO Auto-generated method stub
		finish();
		return true;
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(mContext);
	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(mContext);
	}

}
