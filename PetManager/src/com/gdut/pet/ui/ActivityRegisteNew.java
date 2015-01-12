//ע��
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

//���ڶ������class�ǵ�ע��Ϊpublic
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

	// ��ʾ�Ի��� ��Ҫ��
	public void showDialog(Context context, final String username,
			final String password)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("�r(�s���t)�q");
		builder.setMessage("ע��ɹ������ǰ����¼");

		builder.setNeutralButton("������¼", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				LogInfo.MAN_STOR = 1; // ��¼Ϊ��
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
	 * Ѱ�Ҷ���
	 */
	void findViews()
	{
		// ����
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
		// ��ʱ��ע��
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
		// ��ʾ��������
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
		// ͬ��Э���
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
			// �û�Э��
			toastMgr.builder.display("�û�ЪϢ", 0);
		}
		else if (v.equals(textPrivacyPolicy))
		{
			toastMgr.builder.display("��˽", 0);
		}
		else if (v.equals(regRightNowReg))
		{
			toastMgr.builder.display("ע��", 0);
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
			toastMgr.builder.display("���䲻�ܿ�,����������", 0);
			return;
		}
		if (gettedNickName.equals(""))
		{
			toastMgr.builder.display("�ǳƲ��ܿ�,�������ǳ�", 0);
			return;
		}
		if (gettedPassword.equals(""))
		{
			toastMgr.builder.display("���벻�ܿ�,����������", 0);
			return;
		}
		// OK ע��Ĵ���
		// ע�� ���� �ύ
		new NetConnectionRegiste(Configs.REGISTE_PATH, HttpMethod.POST,
				new NetConnectionRegiste.SuccessCallback()
				{

					@Override
					public void onSuccess(String result)
					{
						// TODO Auto-generated method stub
						// result�Ƿ�ɹ��ķ���ֵ
						// ���ע��ɹ� �Ǿ͵��õ�¼�ĺ���
						if (result.equals("success"))
						{
							// �ɹ���ʾ��¼
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
				// ���� ����Щ��Ϣ���ݹ�ȥ
				Configs.ACTION, "user_register",
				// ����
				// Configs.USERNAME, LogInfo.NAME,// �ǳ�
				// ��¼�� �ֻ�����
				"name", gettedUserNameEmail,
				// Э��
				"protocol", "on",
				// ����
				Configs.PASSWORD, gettedPassword);
	}

	private Context mContext;

	private static final String TAG = "com.gdut.pet.ui.RegisteActivity";
	private Button regBack;// ����
	private TextView textNotRegNow;// �ݲ�ע��
	private EditText regLoginNameEmail;// �û���
	private EditText regNickname;// nickname
	private EditText regPassword;
	private TextView textUserAgreement;// �û�Э�� �½���
	private TextView textPrivacyPolicy;// ��˽����
	private CheckBox regCheckBox;
	private Button regRightNowReg;// ����ע��
	private ImageView regShowPwd;

	// ����� �õ����û��� nickname ����
	private String gettedUserNameEmail;
	private String gettedNickName;
	private String gettedPassword;

	private int touchTime = 0;

}
