//注册
package com.gdut.pet.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gdut.pet.common.network.HttpMethod;
import com.gdut.pet.common.network.NetConnectionRegiste;
import com.gdut.pet.config.Configs;
import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

//对于多个包的class记得注册为public
public class RegisteActivity extends Activity
{

	EditText numMailEditText,// 号码 邮箱
			myNameEditText, // 用户名
			passWordEditText,// 第一次密码
			surePassWordEditText;// 第二次密码
	Button registerButton,// 注册按钮
			returnButton;// 返回按钮

	private Context mContext;

	private static final String TAG = "com.gdut.pet.ui.RegisteActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		numMailEditText = (EditText) findViewById(R.id.registerText);
		myNameEditText = (EditText) findViewById(R.id.myNameText);
		passWordEditText = (EditText) findViewById(R.id.passWordText);
		surePassWordEditText = (EditText) findViewById(R.id.surePassWordText);
		registerButton = (Button) findViewById(R.id.registerButton);
		returnButton = (Button) findViewById(R.id.returnButton);

		mContext = this;

		// 焦点改变提示
		focuseChangeListener();

		// 注册按钮实现
		registerButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				if (false

				// numMailEditText.getText().toString().isEmpty()
				// || !JudgeFormat.isMobileNO(numMailEditText.getText()
				// .toString())
				// && !JudgeFormat.isEmail(numMailEditText.getText()
				// .toString())
				)
				{
					Toast.makeText(RegisteActivity.this,
							R.string.invalid_phone_email_warnning,
							Toast.LENGTH_SHORT).show();
				}
				// else if (myNameEditText.getText().toString().isEmpty())
				// {
				// Toast.makeText(RegisteActivity.this,
				// R.string.please_input_username, Toast.LENGTH_SHORT)
				// .show();
				// }
				// else if (!JudgeFormat.isLogical(myNameEditText.getText()
				// .toString()))
				// {
				// Toast.makeText(RegisteActivity.this, "用户名含非法字符",
				// Toast.LENGTH_SHORT).show();
				// }
				// else if (passWordEditText.getText().toString().isEmpty()
				// || surePassWordEditText.getText().toString().isEmpty())
				// {
				// Toast.makeText(RegisteActivity.this,
				// R.string.please_input_password, Toast.LENGTH_SHORT)
				// .show();
				// }
				// else if (passWordEditText.getText().length() < 6)
				// {
				// Toast.makeText(RegisteActivity.this, "密码长度要大于5",
				// Toast.LENGTH_SHORT).show();
				// }
				// else if (!passWordEditText.getText().toString()
				// .equals(surePassWordEditText.getText().toString()))
				// {
				// Toast.makeText(RegisteActivity.this, "输入的密码不一置",
				// Toast.LENGTH_SHORT).show();
				// }
				else
				{

					LogInfo.NAME = myNameEditText.getText().toString();
					LogInfo.LOGNAME = numMailEditText.getText().toString();
					LogInfo.PASSWORD = passWordEditText.getText().toString();

					// 注册 联网 提交
					NetConnectionRegiste registeConnection = new NetConnectionRegiste(
							Configs.REGISTE_PATH, HttpMethod.POST,
							new NetConnectionRegiste.SuccessCallback()
							{

								@Override
								public void onSuccess(String result)
								{
									// TODO Auto-generated method stub
									// result是否成功的返回值
									// 如果注册成功 那就调用登录的函数
									if (result.equals("success"))
									{
										// 成功提示登录
										showDialog(RegisteActivity.this);
									}
									Log.i(TAG, result);
								}

							},
							//
							new NetConnectionRegiste.FailCallback()
							{

								@Override
								public void onFail()
								{
									// TODO Auto-generated method stub
									Log.i(TAG, "onfail");
								}
							},
							// ?action=pet_register&name=@qq.com&protocol=on&password=123
							// 别名 将这些信息传递过去
							Configs.ACTION, "pet_register",
							// 别名
							// Configs.USERNAME, LogInfo.NAME,// 昵称
							// 登录名 手机号码
							"name", LogInfo.LOGNAME,
							// 协议
							"protocol", "on",
							// 密码
							Configs.PASSWORD, LogInfo.PASSWORD);
				}
			}

		});

		//
		returnButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}

		});
	}

	// 提示对话框 还要改
	public void showDialog(Context context)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("╮(╯▽╰)╭");
		builder.setMessage("注册成功，点击前往登录");

		builder.setNeutralButton("立即登录", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				LogInfo.MAN_STOR = 1; // 登录为人
				Intent intent = new Intent(RegisteActivity.this,
						LoginActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean(Configs.IS_FROM_REG, true);
				bundle.putString(Configs.USERNAME, LogInfo.LOGNAME);
				bundle.putString(Configs.PASSWORD, LogInfo.PASSWORD);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}
		});

		Log.i(TAG, "showDialog");
		builder.show();
		RegisteActivity.this.finish();
	}

	// 焦点改变的响应
	public void focuseChangeListener()
	{
		//
		numMailEditText.setOnFocusChangeListener(new OnFocusChangeListener()
		{

			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				// TODO Auto-generated method stub
				if (!numMailEditText.isFocused())
				{
					if (numMailEditText.getText().toString().isEmpty()
							|| !JudgeFormat.isMobileNO(numMailEditText
									.getText().toString())
							&& !JudgeFormat.isEmail(numMailEditText.getText()
									.toString()))
					{
						Toast.makeText(RegisteActivity.this,
								R.string.invalid_phone_email_warnning,
								Toast.LENGTH_SHORT).show();
					}
				}
			}

		});
		myNameEditText.setOnFocusChangeListener(new OnFocusChangeListener()
		{

			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				// TODO Auto-generated method stub
				if (!myNameEditText.isFocused())
				{
					if (myNameEditText.getText().toString().isEmpty())
					{
						Toast.makeText(RegisteActivity.this,
								R.string.please_input_username,
								Toast.LENGTH_SHORT).show();
					}
					else if (!JudgeFormat.isLogical(myNameEditText.getText()
							.toString()))
					{
						Toast.makeText(RegisteActivity.this,
								R.string.username_should_be_num_letter,
								Toast.LENGTH_SHORT).show();
					}
				}
			}

		});

		passWordEditText.setOnFocusChangeListener(new OnFocusChangeListener()
		{

			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				// TODO Auto-generated method stub
				if (!passWordEditText.isFocused())
				{
					if (passWordEditText.getText().toString().isEmpty())
					{
						Toast.makeText(RegisteActivity.this,
								R.string.please_input_password,
								Toast.LENGTH_SHORT).show();
					}
					else if (passWordEditText.getText().length() < 6)
					{
						Toast.makeText(RegisteActivity.this,
								R.string.password_less_then_six,
								Toast.LENGTH_SHORT).show();
					}
					else if (!JudgeFormat.isPassword(passWordEditText.getText()
							.toString()))
					{
						Toast.makeText(RegisteActivity.this,
								R.string.pwd_should_num_letter,
								Toast.LENGTH_SHORT).show();
					}
				}
			}

		});

		surePassWordEditText
				.setOnFocusChangeListener(new OnFocusChangeListener()
				{

					@Override
					public void onFocusChange(View v, boolean hasFocus)
					{
						// TODO Auto-generated method stub
						if (!surePassWordEditText.isFocused())
						{
							if (surePassWordEditText.getText().toString()
									.isEmpty())
							{
								Toast.makeText(RegisteActivity.this,
										R.string.please_input_sure_pwd,
										Toast.LENGTH_SHORT).show();
							}
							if (!passWordEditText
									.getText()
									.toString()
									.equals(surePassWordEditText.getText()
											.toString()))
							{
								Toast.makeText(RegisteActivity.this,
										R.string.different_pwd,
										Toast.LENGTH_SHORT).show();
							}
						}
					}

				});

	}

	@Override
	public void finish()
	{
		// System.out.println("finish Register Activity");
		Log.i(TAG + "finish()", "finish");
		super.finish();
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
