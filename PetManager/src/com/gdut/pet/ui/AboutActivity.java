package com.gdut.pet.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.gdut.pet.common.utils.L;
import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

public class AboutActivity extends Activity
{

	private static final String TAG = "com.gdut.pet.ui.AboutActivity";
	private Context mContext;
	private Button aboutBack;
	private WebView aboutWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		L.d(TAG, "onCreate");
		mContext = this;
		initView();
	}

	private void initView()
	{
		aboutBack = (Button) findViewById(R.id.about_back_detail);
		aboutWebView = (WebView) findViewById(R.id.about_webview);
		aboutWebView.loadUrl("file:///android_asset/about.html");
		aboutBack.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				AboutActivity.this.finish();
			}
		});
	}

	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		L.d(TAG, "onResume");
		super.onStart();
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		L.d(TAG, "onResume");
		super.onResume();
		// ”—√À
		MobclickAgent.onResume(mContext);
	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		// ”—√À
		MobclickAgent.onPause(mContext);
	}

}
