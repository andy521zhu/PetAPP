package com.gdut.pet.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.config.Configs;
import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

public class SettingsActivity extends Activity implements OnClickListener
{
	private Context mContext;
	private static final String TAG = "com.gdut.pet.ui.SettingsActivity";

	private LinearLayout settingCleanCache;
	private LinearLayout settingTongZhi;
	private LinearLayout settingYiJianFanKui;
	private LinearLayout settingCheckNewVersion;
	private LinearLayout settingAbout;
	private CheckBox settingTongzhiCheckBox;
	private LinearLayout settingDianZan;
	private Button back;
	private Button btnSettingQuit;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		mContext = this;
		initView();
	}

	private void initView()
	{
		settingCleanCache = (LinearLayout) findViewById(R.id.setting_clean_cache);
		settingCleanCache.setOnClickListener(this);
		settingTongZhi = (LinearLayout) findViewById(R.id.setting_tongzhi);
		settingTongZhi.setOnClickListener(this);
		settingYiJianFanKui = (LinearLayout) findViewById(R.id.setting_yijianfankui);
		settingYiJianFanKui.setOnClickListener(this);
		settingCheckNewVersion = (LinearLayout) findViewById(R.id.setting_checkNewVersioin);
		settingCheckNewVersion.setOnClickListener(this);
		settingAbout = (LinearLayout) findViewById(R.id.setting_about);
		settingAbout.setOnClickListener(this);
		settingTongzhiCheckBox = (CheckBox) findViewById(R.id.setting_tongzhi_checkbox);
		settingTongzhiCheckBox.setOnClickListener(this);
		back = (Button) findViewById(R.id.settings_back_detail);
		back.setOnClickListener(this);
		settingDianZan = (LinearLayout) findViewById(R.id.setting_dianzan);
		settingDianZan.setOnClickListener(this);
		btnSettingQuit = (Button) findViewById(R.id.setting_quit_btn);
		btnSettingQuit.setOnClickListener(this);

	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.setting_clean_cache:

			break;
		case R.id.setting_tongzhi:

			break;
		case R.id.setting_yijianfankui:
			Intent intent1 = new Intent();
			intent1.setClass(SettingsActivity.this, YiJianJianYiActivity.class);
			startActivity(intent1);
			break;
		case R.id.setting_checkNewVersioin:
			checkVersion();
			break;
		case R.id.setting_about:
			Intent intent = new Intent();
			intent.setClass(SettingsActivity.this, AboutActivity.class);
			startActivity(intent);
			break;
		case R.id.setting_tongzhi_checkbox:

			break;
		case R.id.settings_back_detail:
			SettingsActivity.this.finish();
			break;
		case R.id.setting_dianzan:
			dianZan();
			break;
		case R.id.setting_quit_btn:
			SharedPreferences sp = getSharedPreferences("userdata",
					MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putBoolean(Configs.IS_LOGIN, false);
			toastMgr.builder.display("您已经退出了登录", 0);
			editor.commit();
			finish();
			break;
		default:
			break;
		}
	}

	private void dianZan()
	{
		// TODO Auto-generated method stub
		toastMgr.builder.display("谢谢您的支持,我们已经收到`(*∩_∩*)′", 0);
	}

	/**
	 * 检查新版本
	 */
	private void checkVersion()
	{
		// TODO Auto-generated method stub
		toastMgr.builder.display("已经是最新版本了!", 0);
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
