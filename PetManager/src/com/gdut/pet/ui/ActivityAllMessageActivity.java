// 显示用户详细信息，并提供更改
package com.gdut.pet.ui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
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
import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.config.Configs;
import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

public class ActivityAllMessageActivity extends Activity
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
	private static final String TAG = " com.gdut.pet.ui.AllMessageActivity";
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	private Drawable drawable;

	File tempFile;

	// 更新UI
	Handler setUserPicHandler = new Handler()
	{

		@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
		@SuppressLint("NewApi")
		@Override
		public void handleMessage(Message msg)
		{
			// TODO Auto-generated method stub
			switch (msg.what)
			{
			case 1:
				editImg.setBackground(drawable);
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

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
					// editName.setFocusable(true);
					editName.setFocusableInTouchMode(true);
					// editName.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
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

		editImg.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				showPickPicDialog();
			}

		});

		myNameView.setEnabled(false);
		myNameView.setFocusable(false);
		editName.setEnabled(false);
		editName.setFocusable(false);
		editSay.setEnabled(false);
		editSay.setFocusable(false);
		editCallPhone.setEnabled(false);
		editCallPhone.setFocusable(false);
		editPhone.setEnabled(false);
		editPhone.setFocusable(false);
		editEmail.setEnabled(false);
		editEmail.setFocusable(false);
		editQQ.setEnabled(false);
		editQQ.setFocusable(false);
		editAddress.setEnabled(false);
		editAddress.setFocusable(false);
	}

	public void readData()
	{
		// 进度Dialog
		final ProgressDialog pd = ProgressDialog.show(mContext, getResources()
				.getString(R.string.connecting),
				getResources().getString(R.string.connecting_to_server));
		PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);
		new GetUserData(Configs.GET_USET_DATA_PATH, "user",
				cookieStore,
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
							String status = jsonObject.getString("status");
							if (status.equals("1"))
							{
								String username = jsonObject.getString("userNickName");// userName
								String signature = " ";// jsonObject.getString("signature");
								String sex = jsonObject.getString("userSex");
								String qq = " ";// jsonObject.getString("QQ");
								String email = " ";// jsonObject.getString("email");
								String guhua = " ";// jsonObject.getString("tel_guding");
								String cellphone = " ";// jsonObject.getString("cellphone");//
								String address = " ";// jsonObject.getString("address");//
								editName.setText(username);
								editSay.setText(signature);
								editQQ.setText(qq);
								editEmail.setText(email);
								editCallPhone.setText(guhua);
								editPhone.setText(cellphone);
								editAddress.setText(address);
							}
							// 登录不成功
							else
							{
								// ShowToast.ShowToast1(mContext, "获取个人信息失败");
								toastMgr.builder.display("获取个人信息失败",
										Toast.LENGTH_SHORT);

							}

						}
						catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						pd.dismiss();

					}
				},
				//
				new GetUserData.FailCallback()
				{

					@Override
					public void onFail()
					{
						// TODO Auto-generated method stub
						// ShowToast.ShowToast1(mContext, "失败");
						toastMgr.builder.display("失败", Toast.LENGTH_SHORT);
						pd.dismiss();
					}
				});

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
			new DatePickerDialog(ActivityAllMessageActivity.this,
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
		// if (checkChanged())
		// {
		// // 上传数据
		// Toast.makeText(this, "数据更改，上传数据", Toast.LENGTH_SHORT).show();
		// }
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		switch (requestCode)
		{
		case PHOTO_REQUEST_TAKEPHOTO:
			startPhotoZoom(Uri.fromFile(tempFile), 150);
			break;
		case PHOTO_REQUEST_GALLERY:
			if (data != null)
			{
				startPhotoZoom(data.getData(), 150);
			}
			break;
		// 取得裁剪后的图片
		case PHOTO_REQUEST_CUT:
			if (data != null)
			{
				setPicToView(data);
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 新线程 设置图片 并且上传到服务器上
	private void setPicToView(Intent data)
	{
		// TODO Auto-generated method stub
		Bundle extras = data.getExtras();
		if (extras != null)
		{
			final Bitmap photo = extras.getParcelable("data");
			drawable = new BitmapDrawable(photo);
			new Thread()
			{

				@Override
				public void run()
				{
					// TODO Auto-generated method stub
					Message msg = new Message();
					msg.what = 1;
					setUserPicHandler.sendMessage(msg);
				}

			}.start();
		}
	}

	/**
	 * 剪切 缩放图片
	 * 
	 * @param uri
	 * @param size
	 */
	private void startPhotoZoom(Uri uri, int size)
	{
		// TODO Auto-generated method stub
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	@Override
	public void finish()
	{
		// TODO Auto-generated method stub
		super.finish();
	}

	@Override
	protected void onResume()
	{
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

	/**
	 * 点击头像，选择图片 剪切图片 上传
	 */
	private void showPickPicDialog()
	{
		// TODO Auto-generated method stub
		final CharSequence[] charSequences =
		{ "拍摄", "从手机相册选择" };
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle("图片").setIcon(null)
				.setItems(charSequences, new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub
						tempFile = new File(Environment
								.getExternalStorageDirectory(),
								getPhotoFileName());
				L.i(TAG, which + "被点击");
				// 拍照
						if (which == 0)
						{
							dialog.dismiss();
							Intent intent = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);

					intent.putExtra("camerasensortype", 2);// 调用前置摄像头
					intent.putExtra("autofocus", true);// 自动对焦
					intent.putExtra("fullScreen", false);// 全屏
							intent.putExtra("showActionIcons", false);
					// 指定调用相机拍照后照片的储存路径
							intent.putExtra(MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(tempFile));
							startActivityForResult(intent,
									PHOTO_REQUEST_TAKEPHOTO);
						}
				// 从相册选取
						else if (which == 1)
						{
							dialog.dismiss();
							Intent intent = new Intent(Intent.ACTION_PICK, null);
							intent.setDataAndType(
									MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
									"image/*");
							startActivityForResult(intent,
									PHOTO_REQUEST_GALLERY);
						}
					}
				}).show();

	}

	// 使用系统当前日期加以调整作为照片的名称
	private String getPhotoFileName()
	{
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

}
