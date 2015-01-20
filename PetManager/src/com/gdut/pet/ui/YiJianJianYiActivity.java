package com.gdut.pet.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

public class YiJianJianYiActivity extends Activity
{
	private static final String TAG = "com.gdut.pet.ui.YiJianJianYiActivity";
	private Context mContext;
	private Button backbtn;
	private Button commitAdvice;
	private EditText ideasAndAdvices;
	private EditText contactWays;
	private String ideasAndAdvicesString;
	private String contactWaysString;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_yijianjianyi);
		L.d(TAG, "onCreate");
		mContext = this;
		initView();
	}

	private void initView()
	{
		// TODO Auto-generated method stub
		backbtn = (Button) findViewById(R.id.yijianjianyi_back);
		backbtn.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				YiJianJianYiActivity.this.finish();
			}
		});
		ideasAndAdvices = (EditText) findViewById(R.id.yijianfankui_Edittext1);
		contactWays = (EditText) findViewById(R.id.yijianfankui_Edittext2);
		commitAdvice = (Button) findViewById(R.id.yijianfankui_button);
		commitAdvice.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				ideasAndAdvicesString = ideasAndAdvices.getText().toString();
				contactWaysString = contactWays.getText().toString();
				// 下面就是联网反馈
				toastMgr.builder.display("反馈已经成功提交服务器,谢谢反馈!", 0);
			}
		});
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		L.d(TAG, "onResume");
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

	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		L.d(TAG, "onStart");
		super.onStart();
	}

}
