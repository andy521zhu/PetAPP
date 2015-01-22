/**
 * ������
 */
package com.gdut.pet.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.domob.android.ads.AdEventListener;
import cn.domob.android.ads.AdManager.ErrorCode;
import cn.domob.android.ads.AdView;

import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.common.view.MyImageView;
import com.gdut.pet.common.view.MyImageView.OnViewClickListener;
import com.gdut.pet.config.Configs;
import com.gdut.pet.location.GPSLocation;
import com.igexin.sdk.PushManager;
import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends Activity
{
	private static final String TAG = "com.gdut.pet.ui.MainActivity";

	private MyImageView personal_center;
	private MyImageView pet_shop;
	private MyImageView lost_pet;
	private MyImageView find_pet;
	private Context mContext;

	// SDK���������Զ���Manifest�ļ��ж�ȡ�������������޸����б��������޸�AndroidManifest.xml�ļ�����Ӧ��meta-data��Ϣ��
	// �޸ķ�ʽ�μ�����SDK�ĵ�
	private String appkey = "";
	private String appsecret = "";
	private String appid = "";

	// �Ƿ��˳�����
	private static boolean isExit = false;
	// �˳�����Ķ�ʱ��
	private static Timer exitTimer = null;

	Thread t;

	private void addAd()
	{
		mAdContainer = (RelativeLayout) findViewById(R.id.adcontainer);
		// create a adView
		mAdView = new AdView(this, Configs.PUBLISHER_ID, Configs.INLINE_PPID);
		mAdView.setKeyword("lechong");
		mAdView.setUserGender("male");
		mAdView.setUserBirthdayStr("1990-05-15");
		mAdView.setUserPostcode("andyzhu");
		mAdView.setAdEventListener(new AdEventListener()
		{

			@Override
			public void onAdOverlayPresented(AdView adView)
			{
				L.i("DomobSDKDemo", "overlayPresented");
			}

			@Override
			public void onAdOverlayDismissed(AdView adView)
			{
				L.i("DomobSDKDemo", "Overrided be dismissed");
			}

			@Override
			public void onAdClicked(AdView arg0)
			{
				L.i("DomobSDKDemo", "onDomobAdClicked");
			}

			@Override
			public void onLeaveApplication(AdView arg0)
			{
				L.i("DomobSDKDemo", "onDomobLeaveApplication");
			}

			@Override
			public Context onAdRequiresCurrentContext()
			{
				return MainActivity.this;
			}

			@Override
			public void onAdFailed(AdView arg0, ErrorCode arg1)
			{
				L.i("DomobSDKDemo", "onDomobAdFailed");
			}

			@Override
			public void onEventAdReturned(AdView arg0)
			{
				L.i("DomobSDKDemo", "onDomobAdReturned");
			}
		});

		RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layout.addRule(RelativeLayout.CENTER_HORIZONTAL);
		mAdView.setLayoutParams(layout);
		mAdContainer.addView(mAdView);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_new);

		// ȡ��actionbar ������ȡ�����������汣��
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		// ��һ�ַ���
		// <activity android:theme="@android:style/Theme.Holo.NoActionBar">

		mContext = this;
		// findview and ע�����ʱ��
		findViews();

		String packageName = getApplicationContext().getPackageName();
		ApplicationInfo appinfo;
		try
		{
			appinfo = getPackageManager().getApplicationInfo(packageName,
					PackageManager.GET_META_DATA);
			if (appinfo.metaData != null)
			{
				appid = appinfo.metaData.getString("PUSH_APPID");
				appsecret = appinfo.metaData.getString("PUSH_APPSECRET");
				appkey = (appinfo.metaData.get("PUSH_APPKEY") != null) ? appinfo.metaData
						.get("PUSH_APPKEY").toString() : null;
			}
		}
		catch (NameNotFoundException e)
		{
			// TODO: handle exception
		}



		// ʵ�������͹�����
		PushManager.getInstance().initialize(this.getApplicationContext());

		// AD ���
		addAd();
	}

	void findViews()
	{
		personal_center = (MyImageView) findViewById(R.id.personal_center);// ��������
		pet_shop = (MyImageView) findViewById(R.id.pet_shop);
		lost_pet = (MyImageView) findViewById(R.id.lost_pet);
		find_pet = (MyImageView) findViewById(R.id.find_pet);

		personal_center.setOnClickIntent(new OnViewClickListener()
		{

			@Override
			public void onViewClick(MyImageView view)
			{
				// TODO Auto-generated method stub
				Intent it = new Intent(MainActivity.this,
						IndexActivityNew.class);
				startActivity(it);
			}
		});
		// �����̵�
		pet_shop.setOnClickIntent(new OnViewClickListener()
		{

			@Override
			public void onViewClick(MyImageView view)
			{
				// TODO Auto-generated method stub
				Intent it = new Intent(MainActivity.this, ActivityPetShop.class);
				startActivity(it);
			}
		});
		// ���ﶪʧ���ҵ�
		lost_pet.setOnClickIntent(new OnViewClickListener()
		{

			@Override
			public void onViewClick(MyImageView view)
			{
				// TODO Auto-generated method stub
				Intent it = new Intent(MainActivity.this,
						ActivityPetLostFound.class);
				toastMgr.builder.display("lost&found", 0);
				startActivity(it);
			}
		});

		find_pet.setOnClickIntent(new OnViewClickListener()
		{

			@Override
			public void onViewClick(MyImageView view)
			{
				// TODO Auto-generated method stub
				// Toast.makeText(mContext, "�Ѿ�������ϵͳ��,�������,��л����ʹ��!", 1).show();

				toastMgr.builder.display("�Ѿ�������ϵͳ��,�������,��л����ʹ��!", 0);

			}
		});
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (isExit == false)
			{
				isExit = true;
				if (exitTimer != null)
				{
					exitTimer.cancel(); // ��ԭ����Ӷ������Ƴ�
				}
				// ����ʵ��һ����ʱ��
				exitTimer = new Timer();
				TimerTask task = new TimerTask()
				{
					@Override
					public void run()
					{
						isExit = false;
					}
				};
				Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
				// ��ʱ���봥��task����
				exitTimer.schedule(task, 2000);
			}
			else
			{
				finish();
				System.exit(0);
			}
			return true;
		}

		return super.onKeyUp(keyCode, event);
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		GPSLocation.removeLocation();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	// d����
	private AdView mAdView;
	private RelativeLayout mAdContainer;

}
