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
				// 得到宠物信息
				petdataSP = getSharedPreferences("petdata", MODE_PRIVATE);
				// 看sp里面是否有
				Set<String> petInfo = petdataSP.getStringSet("petName", null);
				// 里面没有信息 那就写进去 不会出现sp里面的跟得到的不一致 因为每次添加宠物都会写一次
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
				// 提交数据
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
		 * 如果登陆了, text_login_or_edit就显示编辑, 否则, 就显示请先登录
		 */
		if (userdataSP.getBoolean(Configs.IS_LOGIN, false))
		{
			text_login_or_edit.setText("编辑");

		}
		else
		{
			text_login_or_edit.setText("请先登录!");
			text_login_or_edit.setTextColor(Color.rgb(255, 0, 0));
		}

		getUserAllPetPic();
		getAddPetList();

		// 宠物图片
		petPiclist = new ArrayList<String>();

		// 添加的宠物
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
							toastMgr.builder.display("获取图片失败, 请检查你的网络", 0);
							return;
						}
						else if (allPetPicList.get(0).equals("fail"))
						{
							toastMgr.builder.display("服务器故障,请重试", 0);
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
	 * 用户的宠物个数, 得到这个列表 显示出来
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
							toastMgr.builder.display("没有宠物信息", 0);
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
								toastMgr.builder.display("宠物信息获取失败", 0);
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
		// 编辑个人信息
		layout_edit = (LinearLayout) findViewById(R.id.layout_edit);
		layout_edit.setOnClickListener(this);

		text_login_or_edit = (TextView) findViewById(R.id.text_login_or_edit);

		// 宠物照片
		layout_all_pet_photo = (LinearLayout) findViewById(R.id.layout_all_pet_photo);
		layout_all_pet_photo.setOnClickListener(this);
		gridview_all_pet_photo = (GridView) findViewById(R.id.gridview_all_pet_photo);

		// 添加宠物之后
		layout_added_pet_profile = (LinearLayout) findViewById(R.id.layout_added_pet_profile);
		layout_added_pet_profile.setOnClickListener(this);
		added_pet_listView = (MyDetailsListView) findViewById(R.id.added_pet_list);

		// 心情 签名
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

		// 点击进入个人信息设置 和 修改
		// 首先check有没有登录
		case R.id.layout_edit:
			SharedPreferences sp = getSharedPreferences(Configs.USERDATA_SP,
					MODE_PRIVATE);
			// 是否已经登录
			boolean isLogn = sp.getBoolean(Configs.IS_LOGIN, false);
			sp = null;
			if (isLogn)
			{
				// 显示用户信息
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

		// 点击进入设置界面
		case R.id.layout_setting:
			if (isUserLogined() != true)
			{
				toastMgr.builder.display("暂未登录, 请先登录", 0);
				return;
			}
			intent = new Intent();
			intent.setClass(MyActivity.this, SettingsActivity.class);
			startActivity(intent);
			break;

		// 添加宠物
		case R.id.layout_add_pet:
			if (isUserLogined() != true)
			{
				toastMgr.builder.display("暂未登录, 请先登录再添加宠物", 0);
				return;
			}
			intent = new Intent();
			intent.setClass(MyActivity.this, ActivityAddPet.class);
			startActivity(intent);
			break;

		// 宠物图片
		case R.id.layout_all_pet_photo:
			// 宠物图片点击 就进入另一个界面
			toastMgr.builder.display("进入另一界面", 0);
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
			text_login_or_edit.setText("编辑");

		}
		else
		{
			text_login_or_edit.setText("请先登录!");
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
	 * 判断用户是否已经登录了
	 * 
	 * @return true 已经登录, false 暂未登录
	 */
	private boolean isUserLogined()
	{
		return userdataSP.getBoolean(Configs.IS_LOGIN, false);
	}

	private static final String TAG = "com.gdut.pet.ui.MyActivity";
	private Context mContext;
	private ImageView headPic_myInfo;
	private TextView name_myInfo;
	private LinearLayout ll_myInfo_head;// 头部返回

	/**
	 * 宠物图片
	 */
	private LinearLayout layout_all_pet_photo;// 宠物图片的布局
	private GridView gridview_all_pet_photo;// 显示用户拍的宠物的照片
	private MyActivityAllPetPhotoGridViewAdapter petPicAdapter;// 显示宠物照片的适配器
	private List<String> petPiclist;// 宠物图片的list
	// 从网上的来的 用户添加的宠物列表
	private List<AddedPetInfo> addPetInfoList;

	/**
	 * 添加之后的宠物显示
	 */
	private LinearLayout layout_added_pet_profile;
	private MyDetailsListView added_pet_listView;
	private AddedPetProfileItemAdapter addedPetAdapter;
	private List<String> addedPetProfileList;

	private LinearLayout layout_add_pet;// 添加宠物
	private LinearLayout ll_settings;// 设置

	private LinearLayout layout_edit;// 编辑个人信息
	private TextView text_login_or_edit;// 这上面用来显示登录提示还是编辑个人信息
	private SharedPreferences userdataSP;// sp信息
	private SharedPreferences petdataSP;// 添加的宠物信息

	/**
	 * 心情 签名
	 */
	private LinearLayout layoutMood;
	private LinearLayout layoutSign;
	private TextView textMood;
	private TextView textSign;
	// 用户所有宠物图片
	private static final int ALL_PET_PIC = 1;
	// 显示添加的宠物的列表
	private static final int ADDED_PET = 2;
	// 编辑用户的心情你敢
	private static final int START_EDIT_MOOD = 0X1;
	// 编辑用户的签名
	private static final int START_EDIT_SIGN = 0X2;

}
