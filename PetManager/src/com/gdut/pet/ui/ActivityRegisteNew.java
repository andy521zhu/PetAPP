//注册
package com.gdut.pet.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdut.pet.common.network.HttpMethod;
import com.gdut.pet.common.network.NetConnectionRegiste;
import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.config.Configs;
import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

//对于多个包的class记得注册为public
public class ActivityRegisteNew extends Activity implements
		View.OnClickListener
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_new);
		mContext = this;
		findViews();
	}

	// 提示对话框 还要改
	public void showDialog(Context context, final String username,
			final String password)
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
				Intent intent = new Intent(ActivityRegisteNew.this,
						LoginActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean(Configs.IS_FROM_REG, true);
				bundle.putString(Configs.USERNAME, username);
				bundle.putString(Configs.PASSWORD, password);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}
		});

		Log.i(TAG, "showDialog");
		builder.show();
		ActivityRegisteNew.this.finish();
	}

	@Override
	public void finish()
	{
		// System.out.println("finish Register Activity");
		L.i(TAG + "finish");
		super.finish();
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		L.i(TAG + "onResume");
		MobclickAgent.onResume(mContext);
	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		L.i(TAG + "onPause");
		super.onPause();
		MobclickAgent.onPause(mContext);
	}

	/**
	 * 寻找对象
	 */
	void findViews()
	{
		// 返回
		regBack = (Button) findViewById(R.id.reg_back);
		regBack.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				ActivityRegisteNew.this.finish();
				L.i(TAG, "click finish");
			}
		});
		// 暂时不注册
		textNotRegNow = (TextView) findViewById(R.id.text_not_reg_now);
		textNotRegNow.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				ActivityRegisteNew.this.finish();
				L.i(TAG, "not reg now finish");
			}
		});

		regLoginNameEmail = (EditText) findViewById(R.id.reg_loginName_email);
		regNickname = (EditText) findViewById(R.id.reg_nickname);
		regPassword = (EditText) findViewById(R.id.reg_password);
		regShowPwd = (ImageView) findViewById(R.id.reg_showpassword);
		// 显示隐藏密码
		regShowPwd.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				touchTime++;
				if (touchTime > 0)
				{
					regPassword
							.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					touchTime = 0;
				}
				else
				{
					regPassword
							.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}
			}
		});

		textUserAgreement = (TextView) findViewById(R.id.text_user_agreement);
		textUserAgreement.setOnClickListener(this);
		textPrivacyPolicy = (TextView) findViewById(R.id.text_privacy_policy);
		textPrivacyPolicy.setOnClickListener(this);
		// 同意协议的
		regCheckBox = (CheckBox) findViewById(R.id.reg_checkbox);
		regRightNowReg = (Button) findViewById(R.id.reg_rightnow_reg);
		regRightNowReg.setOnClickListener(this);

	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		if (v.equals(textUserAgreement))
		{
			// 用户协议
			toastMgr.builder.display("用户歇息", 0);
		}
		else if (v.equals(textPrivacyPolicy))
		{
			toastMgr.builder.display("隐私", 0);
		}
		else if (v.equals(regRightNowReg))
		{
			toastMgr.builder.display("注册", 0);
			regNow();
		}
	}

	void regNow()
	{
		gettedUserNameEmail = regLoginNameEmail.getText().toString().trim();
		gettedNickName = regNickname.getText().toString().trim();
		gettedPassword = regPassword.getText().toString().trim();
		if (gettedUserNameEmail.equals(""))
		{
			toastMgr.builder.display("邮箱不能空,请输入邮箱", 0);
			return;
		}
		if (gettedNickName.equals(""))
		{
			toastMgr.builder.display("昵称不能空,请输入昵称", 0);
			return;
		}
		if (gettedPassword.equals(""))
		{
			toastMgr.builder.display("密码不能空,请输入密码", 0);
			return;
		}
		// OK 注册的代码
		// 注册 联网 提交
		new NetConnectionRegiste(Configs.REGISTE_PATH, HttpMethod.POST,
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
							showDialog(ActivityRegisteNew.this,
									gettedUserNameEmail, gettedPassword);
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
				Configs.ACTION, "user_register",
				// 别名
				// Configs.USERNAME, LogInfo.NAME,// 昵称
				// 登录名 手机号码
				"name", gettedUserNameEmail,
				// 协议
				"protocol", "on",
				// 密码
				Configs.PASSWORD, gettedPassword);
	}

	private Context mContext;

	private static final String TAG = "com.gdut.pet.ui.RegisteActivity";
	private Button regBack;// 返回
	private TextView textNotRegNow;// 暂不注册
	private EditText regLoginNameEmail;// 用户名
	private EditText regNickname;// nickname
	private EditText regPassword;
	private TextView textUserAgreement;// 用户协议 新界面
	private TextView textPrivacyPolicy;// 隐私条款
	private CheckBox regCheckBox;
	private Button regRightNowReg;// 立即注册
	private ImageView regShowPwd;

	// 输入后 得到的用户名 nickname 密码
	private String gettedUserNameEmail;
	private String gettedNickName;
	private String gettedPassword;

	private int touchTime = 0;

}
