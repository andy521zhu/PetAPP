package com.gdut.pet.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gdut.pet.common.utils.L;
import com.ui.mypet.R;

public class ActivityEditSign extends Activity
{

	private Context mContext;
	private static final String TAG = "com.gdut.pet.ui.ActivityEditSign";

	private Button btnBack;
	private TextView btnSure;
	private EditText editSignContent;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit_sign);
		mContext = this;
		L.i(TAG, "onCreate");
		findViews();
	}

	private void findViews()
	{
		// TODO Auto-generated method stub
		btnBack = (Button) findViewById(R.id.edit_sign_back);
		btnSure = (TextView) findViewById(R.id.edit_sign_confirm);
		editSignContent = (EditText) findViewById(R.id.edit_sign_content);

		btnBack.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		});
		btnSure.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				String gettedText = editSignContent.getText().toString();
				if (gettedText.equals(""))
				{
					gettedText = "";
				}
				Intent intent = new Intent();
				intent.putExtra("EDITSIGNCONTENT", gettedText);
				setResult(Activity.RESULT_OK, intent);
				// 并且这里联网 上传签名
				finish();

			}
		});
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
