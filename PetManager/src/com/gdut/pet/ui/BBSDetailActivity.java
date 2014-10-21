package com.gdut.pet.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ui.mypet.R;

public class BBSDetailActivity extends Activity
{

	private static final String TAG = "com.gdut.pet.ui.BBSDetailActivity";
	private ActionBar mActionBar;
	private Context mContext;

	private EditText bbs_replay_TV;
	private Button backButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bbs_detail);

		bbs_replay_TV = (EditText) findViewById(R.id.bbs_reply_detail);
		bbs_replay_TV.setFocusable(false);
		bbs_replay_TV.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(BBSDetailActivity.this, BBSReplyActivity.class);
				startActivity(intent);
			}
		});

		backButton = (Button) findViewById(R.id.bbs_back_detail);
		backButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		});

	}
}
