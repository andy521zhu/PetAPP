//显示用户详细信息，并提供更改
package com.gdut.pet.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gdut.pet.common.network.GetUserData;
import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.config.Configs;
import com.ui.mypet.R;

public class AllMessageActivity extends Activity
{

	EditText editName, editSay, editCallPhone, editPhone, editEmail, editQQ,
			editAddress;
	ImageView editImg;
	TextView myNameView, birthD, birthM, birthY, asd, dsa;// 生日
	Button editUserInfoButton;
	RadioGroup sex;
	MyDateClickListener md;

	private Context mContext;
	private static int flag = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_message);

		mContext = this;

		finsdIDs();

		md = new MyDateClickListener();
		readData();

	}

	private void finsdIDs()
	{
		// TODO Auto-generated method stub
		myNameView = (TextView) findViewById(R.id.myNameView);

		editName = (EditText) findViewById(R.id.editName);

		editSay = (EditText) findViewById(R.id.editSay);
		editCallPhone = (EditText) findViewById(R.id.editCallPhone);
		editPhone = (EditText) findViewById(R.id.editPhone);
		editEmail = (EditText) findViewById(R.id.editEmail);
		editQQ = (EditText) findViewById(R.id.editQQ);
		editAddress = (EditText) findViewById(R.id.editAddress);
		editImg = (ImageView) findViewById(R.id.myPic);
		sex = (RadioGroup) findViewById(R.id.mySexGroup);
		birthY = (TextView) findViewById(R.id.myBirthYView);
		birthM = (TextView) findViewById(R.id.myBirthMView);
		birthD = (TextView) findViewById(R.id.myBirthDView);
		asd = (TextView) findViewById(R.id.asd);
		dsa = (TextView) findViewById(R.id.dsa);

		editUserInfoButton = (Button) findViewById(R.id.editUserInfoButton);
		editUserInfoButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (flag == 1)
				{
					myNameView.setEnabled(true);
					editName.setEnabled(true);
					editSay.setEnabled(true);
					editCallPhone.setEnabled(true);
					editPhone.setEnabled(true);
					editEmail.setEnabled(true);
					editQQ.setEnabled(true);
					editAddress.setEnabled(true);
					flag = 2;
					editUserInfoButton.setText("完成");
				}
				else
				{
					flag = 1;
					editUserInfoButton.setText("编辑");
					// 得到数据并且上传数据 上传用户信息
					getAndUpdate();
				}

			}

		});

		myNameView.setEnabled(false);
		editName.setEnabled(false);
		editSay.setEnabled(false);
		editCallPhone.setEnabled(false);
		editPhone.setEnabled(false);
		editEmail.setEnabled(false);
		editQQ.setEnabled(false);
		editAddress.setEnabled(false);
	}

	public void readData()
	{
		PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);
		GetUserData getUserData = new GetUserData(Configs.GET_USET_DATA_PATH,
				"user", cookieStore,
				//
				new GetUserData.SuccessCallback()
				{

					@Override
					public void onSuccess(String result)
					{
						// TODO Auto-generated method stub
						try
						{
							JSONObject jsonObject = new JSONObject(result);
							jsonObject.getString("userId");
							jsonObject.getString("petNum");
							jsonObject.getString("userName");

							editName.setText(jsonObject
									.getString("userNickName"));
						}
						catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				},
				//
				new GetUserData.FailCallback()
				{

					@Override
					public void onFail()
					{
						// TODO Auto-generated method stub

					}
				});

		myNameView.setText(LogInfo.NAME);
		if (LogInfo.SEX == 1)
		{ // 女
			sex.check(R.id.womanButton);
		}
		else
			sex.check(R.id.manButton);
		editSay.setText(LogInfo.SAY);
		editCallPhone.setText(LogInfo.CALLPHONE);
		editPhone.setText(LogInfo.PHONE);
		editEmail.setText(LogInfo.E_MAIL);
		editQQ.setText(LogInfo.QQ);
		editAddress.setText(LogInfo.ADDRESS);
		editImg.setImageURI(LogInfo.IMAGE_URI);
		birthY.setText(LogInfo.BIRTH_Y + "");
		birthM.setText(LogInfo.BIRTH_M + "");
		birthD.setText(LogInfo.BIRTH_D + "");

		// 点击响应
		birthY.setOnClickListener(md);
		birthM.setOnClickListener(md);
		birthD.setOnClickListener(md);
		asd.setOnClickListener(md);
		dsa.setOnClickListener(md);
	}

	public boolean checkChanged()
	{
		boolean tag = false; // false为无更改，true为有更改
		if (editName.getText().toString().isEmpty())
		{
			Toast.makeText(this, "用户名称为空，将不进行更改", Toast.LENGTH_SHORT).show();
		}
		else if (!editName.getText().toString().equals(LogInfo.NAME))
		{
			LogInfo.NAME = editName.getText().toString();
			tag = true;
		}
		// 性别
		if (sex.getCheckedRadioButtonId() == R.id.manButton && LogInfo.SEX == 1
				|| sex.getCheckedRadioButtonId() == R.id.womanButton
				&& LogInfo.SEX == 0)
		{
			LogInfo.SEX ^= 1;
			tag = true;
		}
		if (!editSay.getText().toString().equals(LogInfo.SAY))
		{
			LogInfo.SAY = editSay.getText().toString();
			tag = true;
		}
		if (!editCallPhone.getText().toString().equals(LogInfo.CALLPHONE))
		{
			LogInfo.CALLPHONE = editCallPhone.getText().toString();
			tag = true;
		}
		if (!editPhone.getText().toString().equals(LogInfo.PHONE))
		{
			LogInfo.PHONE = editPhone.getText().toString();
			tag = true;
		}
		if (!editEmail.getText().toString().equals(LogInfo.E_MAIL))
		{
			LogInfo.E_MAIL = editEmail.getText().toString();
			tag = true;
		}
		if (!editQQ.getText().toString().equals(LogInfo.QQ))
		{
			LogInfo.QQ = editQQ.getText().toString();
			tag = true;
		}
		if (!editAddress.getText().toString().equals(LogInfo.ADDRESS))
		{
			LogInfo.ADDRESS = editAddress.getText().toString();
			tag = true;
		}

		// 日期
		if (Integer.parseInt(birthY.getText().toString()) != LogInfo.BIRTH_Y
				|| Integer.parseInt(birthM.getText().toString()) != LogInfo.BIRTH_M
				|| Integer.parseInt(birthD.getText().toString()) != LogInfo.BIRTH_D)
		{
			LogInfo.BIRTH_Y = (int) Integer.parseInt(birthY.getText()
					.toString());
			LogInfo.BIRTH_M = (int) Integer.parseInt(birthM.getText()
					.toString());
			LogInfo.BIRTH_D = (int) Integer.parseInt(birthD.getText()
					.toString());
			tag = true;
		}
		return tag;
	}

	public class MyDateClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			new DatePickerDialog(AllMessageActivity.this,
					new OnDateSetListener()
					{

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth)
						{
							// TODO Auto-generated method stub
							birthY.setText(year + "");
							birthM.setText(monthOfYear + "");
							birthD.setText(dayOfMonth + "");
							System.out.println("setdate");
						}

					}, LogInfo.BIRTH_Y, LogInfo.BIRTH_M, LogInfo.BIRTH_D)
					.show();
		}

	}

	// 得到用户填写的数据 并且上传服务器
	private void getAndUpdate()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		if (checkChanged())
		{
			// 上传数据
			Toast.makeText(this, "数据更改，上传数据", Toast.LENGTH_SHORT).show();
		}
		super.onDestroy();
	}

	@Override
	public void finish()
	{
		// TODO Auto-generated method stub
		super.finish();
	}
}
