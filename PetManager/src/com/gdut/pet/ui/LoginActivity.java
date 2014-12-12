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
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gdut.pet.common.network.HttpMethod;
import com.gdut.pet.common.network.NetConnectionLogin;
import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.config.Configs;
import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

public class LoginActivity extends Activity implements OnClickListener
{

	private static final String TAG = "com.gdut.pet.ui.LoginActivity";

	EditText loginNameEditText;

	// 定义变量
	private EditText loginPasswordEditText;
	private CheckBox rememberPwdCheckBox, autoLoginCheckBox;
	private Button loginButton, registeButton;
	private TextView forgetPwpTextView;

	private Button pet_login_user_clear;// 删除按钮 图标
	private Button pet_login_password_clear;

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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login_main);
		// 得到存储用户数据的SP
		userdataSP = getSharedPreferences("userdata", MODE_PRIVATE);
		mContext = this;

		// 得到ID
		FindObjects();

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

		// 注册监听事件
		RegisteListeners();
		// 检查网络设置
		CheckNetworkState();

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle.getBoolean(Configs.IS_FROM_REG))
		{
			// 从注册是跳过来的
			loginNameEditText.setText(bundle.getString(Configs.USERNAME));
			loginPasswordEditText.setText(bundle.getString(Configs.PASSWORD));

		}
		else
		{
			// 不是从注册中跳过来的
		}
	}

	// 找到ID
	private void FindObjects()
	{
		// 用户名
		loginNameEditText = (EditText) findViewById(R.id.loginName);
		loginNameEditText.addTextChangedListener(myTextWatcherU);
		// 密码
		loginPasswordEditText = (EditText) findViewById(R.id.loginPassWord);
		loginPasswordEditText.addTextChangedListener(myTextWatcherP);
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

	/**
	 * 添加文本内容改变监听器
	 */
	private TextWatcher myTextWatcherU = new TextWatcher()
	{

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count)
		{
			// TODO Auto-generated method stub
			// toastMgr.builder.display(s, 0);
			if (s.length() != 0)
			{
				pet_login_user_clear = (Button) findViewById(R.id.pet_login_user_clear);
				pet_login_user_clear.setVisibility(View.VISIBLE);
				pet_login_user_clear
						.setOnClickListener(new View.OnClickListener()
						{

							@Override
							public void onClick(View v)
							{
								// TODO Auto-generated method stub
								loginNameEditText.setText("");
							}
						});
			}

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s)
		{
			// TODO Auto-generated method stub

		}
	};

	// 监听 密码 更改密码是否可见
	private TextWatcher myTextWatcherP = new TextWatcher()
	{

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after)
		{
			// TODO Auto-generated method stub
			if (s.length() != 0)
			{
				pet_login_password_clear = (Button) findViewById(R.id.pet_login_password_clear);
				pet_login_password_clear.setVisibility(View.VISIBLE);
				pet_login_password_clear
						.setOnClickListener(new View.OnClickListener()
						{

							@Override
							public void onClick(View v)
							{
								// TODO Auto-generated method stub
								loginPasswordEditText
										.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
							}
						});
			}
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s)
		{
			// TODO Auto-generated method stub

		}

	};

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
							Log.i(TAG + "记住密码",
									getString(R.string.remember_pwd));
							toastMgr.builder.display("记住密码", 0);
						}
						else
						{
							editor.putBoolean(Configs.REMEMBER_PASSWORD, false);
							Log.i(TAG, getString(R.string.cancle_remember_pwd));
							toastMgr.builder.display("不记住密码", 0);
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
		builder.setPositiveButton(R.string.positive_button_save,
				new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub
						// 点击确定 就是要去系统设置里面的设置网络
						startActivity(new Intent(
								Settings.ACTION_WIRELESS_SETTINGS));
					}
				});
		builder.setNegativeButton(R.string.negative_button_cancle,
				new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub
						dialog.cancel();
						LoginActivity.this.finish();
					}
				});
		builder.create();
		builder.show();
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
				toastMgr.builder.display("用户名密码不能为空", 0);
				return;
			}
			// 进度Dialog
			final ProgressDialog pd = ProgressDialog.show(LoginActivity.this,
					getResources().getString(R.string.connecting),
					getResources().getString(R.string.connecting_to_server));
			NetConnectionLogin connection = new NetConnectionLogin(mContext,

			Configs.LOGIN_PATH, HttpMethod.POST,
			// 成功回调函数, result就是传回来的值
					new NetConnectionLogin.SuccessCallback()
					{

						@Override
						public void onSuccess(String result)
						{
							// TODO Auto-generated method stub

							pd.dismiss();
							String result1 = "";
							try
							{
								JSONObject object = new JSONObject(result);
								result1 = object.getString("status");
							}
							catch (JSONException e)
							{
								// TODO Auto-generated catch block
								L.i(TAG, "json error");
								e.printStackTrace();
							}

							// 判断是否登录成功
							if (result1.equals("success"))
							{
								Toast.makeText(mContext,
										R.string.login_success,
										Toast.LENGTH_LONG).show();
								// 保存用户名密码
								Editor userdataEditor = userdataSP.edit();
								if (rememberPwdCheckBox.isChecked())
								{
									userdataEditor.putString(Configs.USERNAME,
											username);
									userdataEditor.putString(Configs.PASSWORD,
											password);
									userdataEditor.putBoolean(
											Configs.REMEMBER_PASSWORD, true);
									userdataEditor.putBoolean(Configs.IS_LOGIN,
											true);
									toastMgr.builder.display(
											"点击登录, 并且保存了用户名密码", 0);
								}
								else
								{
									userdataEditor.putBoolean(
											Configs.REMEMBER_PASSWORD, false);
								}
								if (autoLoginCheckBox.isChecked())
								{
									userdataEditor.putBoolean(
											Configs.AUTOLOGIN, true);

								}
								else
								{
									userdataEditor.putBoolean(
											Configs.AUTOLOGIN, false);
								}
								userdataEditor.commit();
								LoginActivity.this.finish();
							}
							else
							{
								Toast.makeText(mContext, R.string.login_fail,
										Toast.LENGTH_LONG).show();
								Editor userdataEditor = userdataSP.edit();
								userdataEditor.putBoolean(Configs.IS_LOGIN,
										false);
							}

						}

					},
					// 失败的回调函数实现
					new NetConnectionLogin.FailCallback()
					{

						@Override
						public void onFail()
						{
							// TODO Auto-generated method stub
							Toast.makeText(mContext, R.string.login_fail,
									Toast.LENGTH_LONG).show();
							Editor userdataEditor = userdataSP.edit();
							userdataEditor.putBoolean(Configs.IS_LOGIN, false);
							pd.dismiss();
						}

					},
					// Configs.USERNAME, username, Configs.PASSWORD, password
					// LoginType=userlogin&login=on&username=12345&password=123
					"LoginType", "userlogin",
					//
					"login", "on",
					// Configs.ACTION, "Login",
					//
					"username", username, "password", password);
			connection = null;
			// 取消之前的方法
			// Thread loginThread = new Thread(new LoginThread());
			// loginThread.start();

			break;
		case R.id.registeBtn:
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, ActivityRegisteNew.class);
			startActivity(intent);
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

	private void setCheckBoxSP()
	{

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
