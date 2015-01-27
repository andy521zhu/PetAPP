package com.gdut.pet.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gdut.pet.common.info.AddedPetInfo;
import com.gdut.pet.common.network.GetUserAllPetPic;
import com.gdut.pet.common.network.GetUserData;
import com.gdut.pet.common.tools.MyJson;
import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.common.view.MyDetailsListView;
import com.gdut.pet.common.view.adapter.AddedPetProfileItemAdapter;
import com.gdut.pet.common.view.adapter.MyActivityAllPetPhotoGridViewAdapter;
import com.gdut.pet.config.Configs;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.ui.mypet.R;

public class MyActivity extends Activity implements OnClickListener
{

	private static final int GET_HEAD_IMAGE = 5;

	private ImageView headImageView;
	private TextView usernameTextView;
	/**
	 * 
	 */
	private Handler updatePicsHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
			case ALL_PET_PIC:
				L.i(TAG, "updatePicsHandler");
				petPicAdapter = new MyActivityAllPetPhotoGridViewAdapter(mContext, petPiclist);
				gridview_all_pet_photo.setAdapter(petPicAdapter);
				break;
			case ADDED_PET:
				addedPetAdapter = new AddedPetProfileItemAdapter(mContext, addPetInfoList);
				// �õ�������Ϣ
				petdataSP = getSharedPreferences("petdata", MODE_PRIVATE);
				// ��sp�����Ƿ���
				Set<String> petInfo = petdataSP.getStringSet("petName", null);
				// ����û����Ϣ �Ǿ�д��ȥ �������sp����ĸ��õ��Ĳ�һ�� ��Ϊÿ����ӳ��ﶼ��дһ��
				if (petInfo == null)
				{
					petInfo = new HashSet<String>();
					for (int i = 0; i < addPetInfoList.size(); i++)
					{
						String _petName = addPetInfoList.get(i).getPetname();
						petInfo.add(_petName);
					}
				}
				SharedPreferences.Editor petDataEditor = petdataSP.edit();
				petDataEditor.putStringSet("petName", petInfo);
				// �ύ����
				petDataEditor.commit();
				added_pet_listView.setAdapter(addedPetAdapter);
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.myactivity_new);

		L.d(TAG, "MyActivity oncreate");
		this.mContext = this;
		findViews();

		userdataSP = getSharedPreferences(Configs.USERDATA_SP, 0);

		petdataSP = getSharedPreferences("petdata", MODE_PRIVATE);
		/**
		 * �����½��, text_login_or_edit����ʾ�༭, ����, ����ʾ���ȵ�¼
		 */
		if (userdataSP.getBoolean(Configs.IS_LOGIN, false))
		{
			text_login_or_edit.setText("�༭");

		}
		else
		{
			text_login_or_edit.setText("���ȵ�¼!");
			text_login_or_edit.setTextColor(Color.rgb(255, 0, 0));
		}

		getUserAllPetPic();
		getAddPetList();

		// ����ͼƬ
		petPiclist = new ArrayList<String>();

		// ��ӵĳ���
		addPetInfoList = new ArrayList<AddedPetInfo>();

	}

	/**
	 * 
	 */
	void getUserAllPetPic()
	{

		L.i(TAG, "getUserAllPetPic");
		new GetUserAllPetPic(Configs.GET_BBS_PATH, "testGetUserAllPetPic", new PersistentCookieStore(mContext),
				new GetUserAllPetPic.SuccessCallback()
				{

					@Override
					public void onSuccess(String result)
					{
						// TODO Auto-generated method stub
						L.i(TAG, result);
						MyJson myJson = new MyJson(mContext, result);
						List<String> allPetPicList = myJson.getUserAllPetPics();
						if (allPetPicList != null)
						{
							L.i(TAG, "allpetpiclist != null");
						}
						if (allPetPicList == null)
						{
							toastMgr.builder.display("��ȡͼƬʧ��, �����������", 0);
							return;
						}
						else if (allPetPicList.get(0).equals("fail"))
						{
							toastMgr.builder.display("����������,������", 0);
							return;
						}
						petPiclist = allPetPicList;
						Message msg = new Message();
						msg.what = ALL_PET_PIC;
						updatePicsHandler.sendMessage(msg);
					}
				}, new GetUserAllPetPic.FailCallback()
				{

					@Override
					public void onFail()
					{
						// TODO Auto-generated method stub

					}
				});
	}

	/**
	 * �û��ĳ������, �õ�����б� ��ʾ����
	 */
	void getAddPetList()
	{
		// �õ�������Ϣ
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
								String petNumString = jsonObject.getString("petNum");
								int petNum = 0;
								try
								{
									petNum = Integer.parseInt(petNumString);
								}
								catch (NumberFormatException e)
								{
									// TODO: handle exception
									petNum = 0;
								}
								if (petNum == 0)
								{
									toastMgr.builder.display("û����ӳ���	, ����ӳ���", 0);
									return;
								}
								List<AddedPetInfo> listPet = new ArrayList<AddedPetInfo>();

								for (int i = 0; i < petNum; i++)
								{
									AddedPetInfo info = new AddedPetInfo();
									Map<String, String> map = new HashMap<String, String>();
									String petid = "pet" + i + "id";
									String petname = "pet" + i + "name";
									String petimage = "pet" + i + "pic";
									info.setPetid(jsonObject.getString(petid));
									info.setPetname(jsonObject.getString(petname));
									info.setPetsex("male");
									info.setPetImage(Configs.SERVER_IP_ADDRESS + jsonObject.getString(petimage));
									info.setPetage("1");
									info.setPetspeciese(" ");
									listPet.add(info);
								}

								// ���ǩ��
								String signature = jsonObject.getString("signature");
								textSign.setText(signature);
								String nameString = jsonObject.getString("userNickName");
								usernameTextView.setText(nameString);
								addPetInfoList = listPet;
								if (addPetInfoList == null || addPetInfoList.size() == 0)
								{
									toastMgr.builder.display("������Ϣ��ȡʧ��", 0);
									return;
								}
								Message msgMessage = new Message();
								msgMessage.what = ADDED_PET;
								updatePicsHandler.sendMessage(msgMessage);
								L.i(TAG, "sendMessage");
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

		// new GetUserAllAddedPetList(Configs.GET_USER_ALL_ADDED_PET_LIST_PATH, "testGetUserAllAddedPetList", new PersistentCookieStore(mContext),
		// new GetUserAllAddedPetList.SuccessCallback()
		// {
		//
		// @Override
		// public void onSuccess(String result)
		// {
		// // TODO Auto-generated method stub
		// if (result.equals("") || result == null)
		// {
		// L.i(TAG, "result == null");
		// toastMgr.builder.display("û�г�����Ϣ", 0);
		// return;
		// }
		// else
		// {
		// MyJson myJson = new MyJson(mContext, result);
		// L.i(TAG, "myJson");
		// addPetInfoList = myJson.getUserAllAddedPetList();
		// if (addPetInfoList == null || addPetInfoList.size() == 0)
		// {
		// toastMgr.builder.display("������Ϣ��ȡʧ��", 0);
		// return;
		// }
		// Message msgMessage = new Message();
		// msgMessage.what = ADDED_PET;
		// updatePicsHandler.sendMessage(msgMessage);
		// L.i(TAG, "sendMessage");
		//
		// }
		//
		// }
		// }, new GetUserAllAddedPetList.FailCallback()
		// {
		//
		// @Override
		// public void onFail()
		// {
		// // TODO Auto-generated method stub
		//
		// }
		// });
	}

	private void findViews()
	{
		ll_settings = (LinearLayout) findViewById(R.id.layout_setting);
		ll_settings.setOnClickListener(this);

		layout_add_pet = (LinearLayout) findViewById(R.id.layout_add_pet);
		layout_add_pet.setOnClickListener(this);
		// �༭������Ϣ
		layout_edit = (LinearLayout) findViewById(R.id.layout_edit);
		layout_edit.setOnClickListener(this);

		text_login_or_edit = (TextView) findViewById(R.id.text_login_or_edit);

		// �û�ͷ��
		headImageView = (ImageView) findViewById(R.id.other_user_headPic_myInfo);
		usernameTextView = (TextView) findViewById(R.id.other_user_name_myInfo);
		// ������Ƭ
		layout_all_pet_photo = (LinearLayout) findViewById(R.id.layout_all_pet_photo);
		layout_all_pet_photo.setOnClickListener(this);
		gridview_all_pet_photo = (GridView) findViewById(R.id.gridview_all_pet_photo);

		// ��ӳ���֮��
		layout_added_pet_profile = (LinearLayout) findViewById(R.id.layout_added_pet_profile);
		layout_added_pet_profile.setOnClickListener(this);
		added_pet_listView = (MyDetailsListView) findViewById(R.id.added_pet_list);

		// ���� ǩ��
		layoutMood = (LinearLayout) findViewById(R.id.layout_mood);
		layoutMood.setOnClickListener(this);
		layoutSign = (LinearLayout) findViewById(R.id.layout_sign);
		layoutSign.setOnClickListener(this);
		textMood = (TextView) findViewById(R.id.text_mood);
		textSign = (TextView) findViewById(R.id.text_sign);

	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId())
		{

		// ������������Ϣ���� �� �޸�
		// ����check��û�е�¼
		case R.id.layout_edit:
			SharedPreferences sp = getSharedPreferences(Configs.USERDATA_SP, MODE_PRIVATE);
			// �Ƿ��Ѿ���¼
			boolean isLogn = sp.getBoolean(Configs.IS_LOGIN, false);
			sp = null;
			if (isLogn)
			{
				// ��ʾ�û���Ϣ
				intent = new Intent();
				intent.setClass(MyActivity.this, ActivityAllMessageActivity.class);
				// startActivityForResult(intent, GET_HEAD_IMAGE);
				startActivity(intent);
			}
			else
			{
				intent = new Intent();
				intent.setClass(MyActivity.this, LoginActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean(Configs.IS_FROM_REG, false);
				intent.putExtras(bundle);
				startActivity(intent);
			}

			break;

		// ����������ý���
		case R.id.layout_setting:
			if (isUserLogined() != true)
			{
				toastMgr.builder.display("��δ��¼, ���ȵ�¼", 0);
				return;
			}
			intent = new Intent();
			intent.setClass(MyActivity.this, SettingsActivity.class);
			startActivity(intent);
			break;

		// ��ӳ���
		case R.id.layout_add_pet:
			if (isUserLogined() != true)
			{
				toastMgr.builder.display("��δ��¼, ���ȵ�¼����ӳ���", 0);
				return;
			}
			intent = new Intent();
			intent.setClass(MyActivity.this, ActivityAddPet.class);
			startActivity(intent);
			break;

		// ����ͼƬ
		case R.id.layout_all_pet_photo:
			// ����ͼƬ��� �ͽ�����һ������
			toastMgr.builder.display("������һ����", 0);
			intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putStringArrayList("petpiclist", (ArrayList<String>) petPiclist);
			intent.setClass(MyActivity.this, ActivityAllPetPic.class);
			intent.putExtras(bundle);
			startActivity(intent);
			break;

		case R.id.layout_mood:
			Intent it = new Intent();
			it.setClass(MyActivity.this, ActivityEditMood.class);
			startActivityForResult(it, START_EDIT_MOOD);
			break;
		case R.id.layout_sign:
			Intent it2 = new Intent();
			it2.setClass(MyActivity.this, ActivityEditSign.class);
			startActivityForResult(it2, START_EDIT_SIGN);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		if (resultCode == Activity.RESULT_OK)
		{
			switch (requestCode)
			{
			case START_EDIT_MOOD:
				textMood.setText(data.getStringExtra("EDITMOODCONTENT"));
				break;
			case START_EDIT_SIGN:

				textSign.setText(data.getStringExtra("EDITSIGNCONTENT"));
				break;
			case GET_HEAD_IMAGE:
				String headImageURL = data.getStringExtra("HEAD_IMAGE_URL");
				L.i(TAG, "onresult");
				showHeadImage(headImageURL);
				headImageURL = null;
				break;

			default:
				break;
			}
		}
	}

	// �Ӹ�����Ϣ�����õ�ͷ�� ��������ʾ
	private void showHeadImage(String petUrl)
	{
		if (petUrl.equals(""))
		{
			toastMgr.builder.display("�޷���ȡͷ��", 0);
			return;
		}
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
				headImageView.setImageBitmap(loadedImage);
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view)
			{
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		L.d(TAG, "MyActivity onStart");
		super.onStart();
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		L.d(TAG, "MyActivity onResume");
		if (userdataSP.getBoolean(Configs.IS_LOGIN, false))
		{
			text_login_or_edit.setText("�༭");

		}
		else
		{
			text_login_or_edit.setText("���ȵ�¼!");
			text_login_or_edit.setTextColor(Color.rgb(255, 0, 0));
		}
		SharedPreferences headImagesp = getSharedPreferences("userHeadImageUrl", Context.MODE_PRIVATE);
		String HeadImage = headImagesp.getString("headImage", null);
		if (HeadImage == null || HeadImage.equals(""))
		{

		}
		else
		{
			showHeadImage(HeadImage);
		}
		super.onResume();
	}

	@Override
	protected void onStop()
	{
		// TODO Auto-generated method stub
		L.d(TAG, "MyActivity onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		L.d(TAG, "MyActivity onDestroy");
		super.onDestroy();
	}

	/**
	 * �ж��û��Ƿ��Ѿ���¼��
	 * 
	 * @return true �Ѿ���¼, false ��δ��¼
	 */
	private boolean isUserLogined()
	{
		return userdataSP.getBoolean(Configs.IS_LOGIN, false);
	}

	private static final String TAG = "com.gdut.pet.ui.MyActivity";
	private Context mContext;
	private ImageView headPic_myInfo;
	private TextView name_myInfo;
	private LinearLayout ll_myInfo_head;// ͷ������

	/**
	 * ����ͼƬ
	 */
	private LinearLayout layout_all_pet_photo;// ����ͼƬ�Ĳ���
	private GridView gridview_all_pet_photo;// ��ʾ�û��ĵĳ������Ƭ
	private MyActivityAllPetPhotoGridViewAdapter petPicAdapter;// ��ʾ������Ƭ��������
	private List<String> petPiclist;// ����ͼƬ��list
	// �����ϵ����� �û���ӵĳ����б�
	private List<AddedPetInfo> addPetInfoList;

	/**
	 * ���֮��ĳ�����ʾ
	 */
	private LinearLayout layout_added_pet_profile;
	private MyDetailsListView added_pet_listView;
	private AddedPetProfileItemAdapter addedPetAdapter;
	private List<String> addedPetProfileList;

	private LinearLayout layout_add_pet;// ��ӳ���
	private LinearLayout ll_settings;// ����

	private LinearLayout layout_edit;// �༭������Ϣ
	private TextView text_login_or_edit;// ������������ʾ��¼��ʾ���Ǳ༭������Ϣ
	private SharedPreferences userdataSP;// sp��Ϣ
	private SharedPreferences petdataSP;// ��ӵĳ�����Ϣ

	/**
	 * ���� ǩ��
	 */
	private LinearLayout layoutMood;
	private LinearLayout layoutSign;
	private TextView textMood;
	private TextView textSign;
	// �û����г���ͼƬ
	private static final int ALL_PET_PIC = 1;
	// ��ʾ��ӵĳ�����б�
	private static final int ADDED_PET = 2;
	// �༭�û����������
	private static final int START_EDIT_MOOD = 0X1;
	// �༭�û���ǩ��
	private static final int START_EDIT_SIGN = 0X2;

}
