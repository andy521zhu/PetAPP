package com.gdut.pet.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.gdut.pet.common.network.GetBBS;
import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.config.Configs;
import com.ui.mypet.R;

public class SocialActivity extends Activity
{
	private static final String TAG = "com.gdut.pet.ui.SocialActivity";
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.social);
		mContext = this;
		PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);
		new GetBBS(Configs.GET_BBS_PATH, cookieStore,
				new GetBBS.SuccessCallback()
				{

					@Override
					public void onSuccess(String result)
					{
						// TODO Auto-generated method stub

					}
				},
				//
				new GetBBS.FailCallback()
				{

					@Override
					public void onFail()
					{
						// TODO Auto-generated method stub

					}
				}, false, "0");

	}
}
