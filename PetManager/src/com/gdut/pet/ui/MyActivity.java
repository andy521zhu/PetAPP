package com.gdut.pet.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.gdut.pet.common.info.AddedPetInfo;
import com.gdut.pet.common.network.GetUserAllAddedPetList;
import com.gdut.pet.common.network.GetUserAllPetPic;
import com.gdut.pet.common.tools.MyJson;
import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.common.view.MyDetailsListView;
import com.gdut.pet.common.view.adapter.AddedPetProfileItemAdapter;
import com.gdut.pet.common.view.adapter.MyActivityAllPetPhotoGridViewAdapter;
import com.gdut.pet.config.Configs;
import com.ui.mypet.R;

public class MyActivity extends Activity implements OnClickListener
{

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
				petPicAdapter = new MyActivityAllPetPhotoGridViewAdapter(
						mContext, petPiclist);
				gridview_all_pet_photo.setAdapter(petPicAdapter);
				break;
			case ADDED_PET:
				addedPetAdapter = new AddedPetProfileItemAdapter(mContext,
						addPetInfoList);
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
		new GetUserAllPetPic(Configs.GET_BBS_PATH, "testGetUserAllPetPic",
				new PersistentCookieStore(mContext),
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
		new GetUserAllAddedPetList(Configs.GET_USER_ALL_ADDED_PET_LIST_PATH,
				"testGetUserAllAddedPetList", new PersistentCookieStore(
						mContext), new GetUserAllAddedPetList.SuccessCallback()
				{

					@Override
					public void onSuccess(String result)
					{
						// TODO Auto-generated method stub
						if (result.equals("") || result == null)
						{
							L.i(TAG, "result == null");
							toastMgr.builder.display("û�г�����Ϣ", 0);
							return;
						}
						else
						{
							MyJson myJson = new MyJson(mContext, result);
							L.i(TAG, "myJson");
							addPetInfoList = myJson.getUserAllAddedPetList();
							if (addPetInfoList == null
									|| addPetInfoList.size() == 0)
							{
								toastMgr.builder.display("������Ϣ��ȡʧ��", 0);
								return;
							}
							Message msgMessage = new Message();
							msgMessage.what = ADDED_PET;
							updatePicsHandler.sendMessage(msgMessage);
							L.i(TAG, "sendMessage");

						}

					}
				}, new GetUserAllAddedPetList.FailCallback()
				{

					@Override
					public void onFail()
					{
						// TODO Auto-generated method stub

					}
				});
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
			SharedPreferences sp = getSharedPreferences(Configs.USERDATA_SP,
					MODE_PRIVATE);
			// �Ƿ��Ѿ���¼
			boolean isLogn = sp.getBoolean(Configs.IS_LOGIN, false);
			sp = null;
			if (isLogn)
			{
				// ��ʾ�û���Ϣ
				intent = new Intent();
				intent.setClass(MyActivity.this,
						ActivityAllMessageActivity.class);
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
			bundle.putStringArrayList("petpiclist",
					(ArrayList<String>) petPiclist);
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

			default:
				break;
			}
		}
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
