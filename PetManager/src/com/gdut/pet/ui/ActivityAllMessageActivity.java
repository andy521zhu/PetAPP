// ��ʾ�û���ϸ��Ϣ�����ṩ����
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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

public class ActivityAllMessageActivity extends Activity
{
	private static final int GET_HEAD_IMAGE = 5;
	EditText editName, editSay, editCallPhone, editPhone, editEmail, editQQ, editAddress;
	ImageView editImg;
	TextView myNameView, birthD, birthM, birthY, asd, dsa;// ����
	Button editUserInfoButton;
	RadioGroup sex;
	private String headImageURL = "";

	private Context mContext;
	private static int flag = 1;
	private static final String TAG = " com.gdut.pet.ui.AllMessageActivity";
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// ����
	private static final int PHOTO_REQUEST_GALLERY = 2;// �������ѡ��
	private static final int PHOTO_REQUEST_CUT = 3;// ���
	private Drawable drawable;

	File tempFile;

	// ����UI
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
		editAddress = (EditText) findViewById(R.id.editAddress);
		editImg = (ImageView) findViewById(R.id.myPic);
		sex = (RadioGroup) findViewById(R.id.mySexGroup);

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
					editAddress.setEnabled(true);
					flag = 2;
					editUserInfoButton.setText("���");
				}
				else
				{
					flag = 1;
					editUserInfoButton.setText("�༭");
					// �õ����ݲ����ϴ����� �ϴ��û���Ϣ
					getAndUpdate();
				}

			}

		});

		// �ϴ�ͷ��
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
		editAddress.setEnabled(false);
		editAddress.setFocusable(false);
	}

	// ��ȡ����
	public void readData()
	{
		// ����Dialog
		final ProgressDialog pd = ProgressDialog.show(mContext, getResources().getString(R.string.connecting),
				getResources().getString(R.string.connecting_to_server));
		PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);
		new GetUserData(Configs.GET_USET_DATA_PATH, "user", cookieStore,
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
								// ��ȡ�û�ͷ��
								String headImage = jsonObject.getString("headImage");
								headImageURL = Configs.SERVER_IP_ADDRESS + headImage;
								showGettedHeadImage(headImageURL);
								editName.setText(username);
								editSay.setText(signature);
								editEmail.setText(email);
								editCallPhone.setText(guhua);
								editPhone.setText(cellphone);
								editAddress.setText(address);
							}
							// ��¼���ɹ�
							else if (status.equals("2"))
							{
								toastMgr.builder.display("2δ��¼.��ȡ������Ϣʧ��", Toast.LENGTH_SHORT);
							}
							else
							{
								// ShowToast.ShowToast1(mContext, "��ȡ������Ϣʧ��");
								toastMgr.builder.display("��ȡ������Ϣʧ��", Toast.LENGTH_SHORT);

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
						// ShowToast.ShowToast1(mContext, "ʧ��");
						toastMgr.builder.display("ʧ��", Toast.LENGTH_SHORT);
						pd.dismiss();
					}
				});

		// �����Ӧ

	}

	// ��ʾ�û�ͷ��ĺ���
	private void showGettedHeadImage(String petUrl)
	{
		if (petUrl.equals(""))
		{
			toastMgr.builder.display("�޷���ȡͷ��", 0);
			return;
		}

		SharedPreferences userHeadImageUrl = getSharedPreferences("userHeadImageUrl", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = userHeadImageUrl.edit();
		editor.putString("headImage", headImageURL);
		editor.commit();
		// ����ͼƬ
		ImageLoader.getInstance().loadImage(petUrl, new ImageLoadingListener()
		{

			@Override
			public void onLoadingStarted(String imageUri, View view)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
			{
				// TODO Auto-generated method stub
				editImg.setImageBitmap(loadedImage);
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view)
			{
				// TODO Auto-generated method stub

			}
		});

	}

	public boolean checkChanged()
	{
		boolean tag = false; // falseΪ�޸��ģ�trueΪ�и���
		if (editName.getText().toString().isEmpty())
		{
			Toast.makeText(this, "�û�����Ϊ�գ��������и���", Toast.LENGTH_SHORT).show();
		}
		else if (!editName.getText().toString().equals(LogInfo.NAME))
		{
			LogInfo.NAME = editName.getText().toString();
			tag = true;
		}
		// �Ա�
		if (sex.getCheckedRadioButtonId() == R.id.manButton && LogInfo.SEX == 1 || sex.getCheckedRadioButtonId() == R.id.womanButton
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

		if (!editAddress.getText().toString().equals(LogInfo.ADDRESS))
		{
			LogInfo.ADDRESS = editAddress.getText().toString();
			tag = true;
		}

		return tag;
	}

	// �õ��û���д������ �����ϴ�������
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
		// // �ϴ�����
		// Toast.makeText(this, "���ݸ��ģ��ϴ�����", Toast.LENGTH_SHORT).show();
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
		// ȡ�òü����ͼƬ
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

	// ���߳� ����ͼƬ �����ϴ�����������
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
	 * ���� ����ͼƬ
	 * 
	 * @param uri
	 * @param size
	 */
	private void startPhotoZoom(Uri uri, int size)
	{
		// TODO Auto-generated method stub
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// cropΪtrue�������ڿ�����intent��������ʾ��view���Լ���
		intent.putExtra("crop", "true");

		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY �Ǽ���ͼƬ�Ŀ��
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	@Override
	public void finish()
	{
		// TODO Auto-generated method stub
		L.i(TAG, "finish");
		Intent data = new Intent();

		data.putExtra("HEAD_IMAGE_URL", headImageURL);
		setResult(Activity.RESULT_OK, data);
		// setResult(Activity.RESULT_OK, data);
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
	 * ���ͷ��ѡ��ͼƬ ����ͼƬ �ϴ�
	 */
	private void showPickPicDialog()
	{
		// TODO Auto-generated method stub
		final CharSequence[] charSequences =
		{ "����", "���ֻ����ѡ��" };
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle("ͼƬ").setIcon(null).setItems(charSequences, new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// TODO Auto-generated method stub
				tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
				L.i(TAG, which + "�����");
				// ����
				if (which == 0)
				{
					dialog.dismiss();
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					intent.putExtra("camerasensortype", 2);// ����ǰ������ͷ
					intent.putExtra("autofocus", true);// �Զ��Խ�
					intent.putExtra("fullScreen", false);// ȫ��
					intent.putExtra("showActionIcons", false);
					// ָ������������պ���Ƭ�Ĵ���·��
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
					startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
				}
				// �����ѡȡ
				else if (which == 1)
				{
					dialog.dismiss();
					Intent intent = new Intent(Intent.ACTION_PICK, null);
					intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
					startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
				}
			}
		}).show();

	}

	// ʹ��ϵͳ��ǰ���ڼ��Ե�����Ϊ��Ƭ������
	private String getPhotoFileName()
	{
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

}
