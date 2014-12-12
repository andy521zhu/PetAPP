package com.gdut.pet.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gdut.pet.common.utils.L;
import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

public class BBSReplyActivity extends Activity
{

	private static final String TAG = "com.gdut.pet.ui.BBSReplyActivity";
	private Context mContext;

	private Button backButton;
	private TextView confirmButton;
	private EditText replyEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_mood);
		mContext = this;
		L.i(TAG, "onCreate");
		backButton = (Button) findViewById(R.id.post_pet_back);
		backButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				L.i(TAG, "finish()---back click");
				finish();
			}
		});

		confirmButton = (TextView) findViewById(R.id.post_pet_confirm);
		confirmButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				// 确认评论 的一些逻辑
				L.i(TAG, "confirmButton click");
			}
		});

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
