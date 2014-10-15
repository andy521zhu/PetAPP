/**
 * 登录页面， 
 */
package com.gdut.pet.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gdut.pet.common.utils.ShowToast;
import com.gdut.pet.config.Configs;
import com.ui.mypet.R;

public class LoginActivity1 extends Activity implements OnClickListener
{

	private static final String TAG = "com.gdut.pet.ui.LoginActivity1";

	// 定义变量
	private EditText loginNameEditText, loginPasswordEditText;
	private CheckBox rememberPwdCheckBox, autoLoginCheckBox;
	private Button loginButton, registeButton;
	private TextView forgetPwpTextView;

	private Context mContext;

	//
	private String username;
	private String password;
	private boolean isLogin;
	private boolean rememberPwd;

	// 相应结果
	private String responseMsg;

	private SharedPreferences userdataSP;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_main);
		// 得到存储用户数据的SP
		userdataSP = getSharedPreferences("userdata", MODE_PRIVATE);
		mContext = this;

		//
		if (userdataSP.getBoolean(Configs.REMEMBER_PASSWORD, false))
		{
			rememberPwdCheckBox.setChecked(true);
		}
		else
		{
			rememberPwdCheckBox.setChecked(false);
		}

		if (userdataSP.getBoolean(Configs.AUTOLOGIN, false))
		{
			autoLoginCheckBox.setChecked(true);
		}
		else
		{
			autoLoginCheckBox.setChecked(false);
		}

		// 得到ID
		FindObjects();
		// 注册监听事件
		RegisteListeners();
		// 检查网络设置
		CheckNetworkState();
	}

	// 找到ID
	private void FindObjects()
	{
		// 用户名
		loginNameEditText = (EditText) findViewById(R.id.loginName);
		// 密码
		loginPasswordEditText = (EditText) findViewById(R.id.loginPassWord);
		// 记住密码
		rememberPwdCheckBox = (CheckBox) findViewById(R.id.rememberPassword);
		// 自动登录
		autoLoginCheckBox = (CheckBox) findViewById(R.id.autoLogin);
		// 登录按钮
		loginButton = (Button) findViewById(R.id.loginInBtn);
		// 注册按钮
		registeButton = (Button) findViewById(R.id.registeBtn);
		// 忘记密码
		forgetPwpTextView = (TextView) findViewById(R.id.forgetPass);
	}

	//
	private void RegisteListeners()
	{
		// 登录响应事件
		loginButton.setOnClickListener(this);

		// 注册相应事件
		registeButton.setOnClickListener(this);

		// 侦听 忘记密码 点击 响应
		forgetPwpTextView.setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				// TODO Auto-generated method stub
				if (event.getAction() == KeyEvent.ACTION_DOWN)
				{
					forgetPwpTextView.setTextColor(Color.rgb(192, 192, 192));
				}
				else
					forgetPwpTextView.setTextColor(Color.RED);
				return true;
			}

		});

		// 记住密码
		rememberPwdCheckBox
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
				{

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked)
					{
						Editor editor = userdataSP.edit();
						// TODO Auto-generated method stub
						if (isChecked == true)
						{
							editor.putBoolean(Configs.REMEMBER_PASSWORD, true);
							Log.i(TAG, "记住密码选中");
						}
						else
						{
							editor.putBoolean(Configs.REMEMBER_PASSWORD, false);
							Log.i(TAG, "记住密码取消");
						}
						editor.commit();

					}
				});

		autoLoginCheckBox
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
				{

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked)
					{
						// TODO Auto-generated method stub
						Editor editor = userdataSP.edit();
						// TODO Auto-generated method stub
						if (isChecked == true)
						{
							editor.putBoolean(Configs.AUTOLOGIN, true);
							Log.i(TAG, "记住密码选中");
						}
						else
						{
							editor.putBoolean(Configs.AUTOLOGIN, false);
							Log.i(TAG, "记住密码取消");
						}
						editor.commit();
					}
				});

	}

	// 检查网络状态
	public void CheckNetworkState()
	{
		boolean flag = false;
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		// 如果3G wifi 2G都是连接状态 否则 提示网络设置
		if (mobile == State.CONNECTED || wifi == State.CONNECTING)
		{
			return;
		}
		if (wifi == State.CONNECTED || wifi == State.CONNECTED)
		{
			return;
		}
		// 当前没有网络 要显示提示
		ShowNetConfigTips();
	}

	// 网络没打开 对话框显示提示
	private void ShowNetConfigTips()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle("没有可用网络");
		builder.setMessage("当前网络不可用,是否设置网络?");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// TODO Auto-generated method stub
				// 点击确定 就是要去系统设置里面的设置网络
				startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// TODO Auto-generated method stub
				dialog.cancel();
				LoginActivity1.this.finish();
			}
		});
		builder.create();
		builder.show();
	}

	// 登录函数
	public boolean login(String name, String passWord)
	{

		return true;
	}

	// 读取登录数据函数
	public void getManData()
	{
		if (rememberPwdCheckBox.isChecked())
		{
			// Toast.makeText(LoginMan.this, "记住密码", Toast.LENGTH_SHORT).show();
		}
		if (autoLoginCheckBox.isChecked())
		{
			// Toast.makeText(LoginMan.this, "自动登录", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.loginInBtn:
			// 点击登录 获得用户名密码
			username = loginNameEditText.getText().toString().trim();
			password = loginPasswordEditText.getText().toString().trim();
			if (username.equals("") || username == null || password.equals("")
					|| password == null)
			{
				ShowToast.ShowToast1(mContext, "用户名密码不能为空");
				break;
			}
			Thread loginThread = new Thread(new LoginThread());
			loginThread.start();

			break;
		case R.id.registeBtn:

			break;

		default:
			break;
		}
	}

	/**
	 * 登录服务器
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean loginServer(String username, String password)
	{
		boolean loginValidate = false;

		String urlString = "http://10.21.63.145:8080/PetMgr/LoginPage.jsp";
		HttpPost request = new HttpPost(urlString);
		// 如果传递参数多的话，可以丢传递的参数进行封装
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 添加用户名和密码
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		try
		{
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpClient client = getHttpClient();
			// 执行请求返回相应
			HttpResponse reponse = client.execute(request);

			// 判断是否请求成功
			if (reponse.getStatusLine().getStatusCode() == 200)
			{
				loginValidate = true;
				responseMsg = EntityUtils.toString(reponse.getEntity());
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
		return loginValidate;
	}

	// 初始化HttpClient，并设置超时
	private HttpClient getHttpClient()
	{
		// TODO Auto-generated method stub
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, Configs.timeout);
		HttpConnectionParams.setSoTimeout(httpParams, Configs.SO_TIMEOUT);
		HttpClient client = new DefaultHttpClient(httpParams);
		return client;
	}

	// Handler
	Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 0:

				break;
			case 1:

				break;
			case 2:
				Toast.makeText(getApplicationContext(), "URL验证失败",
						Toast.LENGTH_SHORT).show();
				break;

			}

		}
	};

	class LoginThread implements Runnable
	{

		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			boolean isSuccess = loginServer(username, password);
			Message msg = handler.obtainMessage();
			if (isSuccess)
			{
				if (responseMsg.equals("succese"))
				{
					msg.what = 0;
					handler.sendMessage(msg);
				}
				else
				{
					msg.what = 1;
					handler.sendMessage(msg);
				}
			}
			else
			{
				msg.what = 2;
				handler.sendMessage(msg);
			}
		}

	}

}
