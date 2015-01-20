package com.gdut.pet.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gdut.pet.common.network.GetUserData;
import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.common.view.MyDetailsListView;
import com.gdut.pet.common.view.adapter.AddedPetProfileItemAdapter;
import com.gdut.pet.common.view.adapter.MyActivityAllPetPhotoGridViewAdapter;
import com.gdut.pet.config.Configs;
import com.ui.mypet.R;

public class ActivityMyActivityOtherUser extends Activity implements
		View.OnClickListener
{

	/**
	 * ������ʾGridView�е����ݵ�
	 */
	private final int HANDLE_GRIDVIEW = 0X1;
	/**
	 * ������ʾlistview�����ݵ�
	 */
	private final int HANDLE_LISTVIEW = 0X2;

	private Handler handlerAdapter = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
			case HANDLE_GRIDVIEW:

				break;
			case HANDLE_LISTVIEW:
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_other_user_profile_new);
		mContext = this;
		L.i(TAG, "onCreate");
		findViews();

		// ȥ����, �õ�����

		// otherUserLinearLayoutAllPhoto.setVisibility(View.VISIBLE);
		// otherUserAdapter = new MyActivityAllPetPhotoGridViewAdapter(mContext,
		// list);
		// otherUserGridAll.setAdapter(otherUserAdapter);
		//
		// addedPetAdapter = new AddedPetProfileItemAdapter(mContext, list);
		// otherUserAddedPetList.setAdapter(addedPetAdapter);

	}

	public void readData()
	{
		// ����Dialog
		final ProgressDialog pd = ProgressDialog.show(mContext, getResources()
				.getString(R.string.connecting),
				getResources().getString(R.string.connecting_to_server));
		PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);
		new GetUserData(Configs.GET_USET_DATA_PATH, "testGetUserData",
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
							if (status.equals("success"))
							{
								String username = jsonObject
										.getString("userName");
								String signature = jsonObject
										.getString("signature");
								String sex = jsonObject.getString("userSex");
								String qq = jsonObject.getString("QQ");
								String email = jsonObject.getString("email");
								String guhua = jsonObject
										.getString("tel_guding");
								String cellphone = jsonObject
										.getString("cellphone");
								String address = jsonObject
										.getString("address");
							}
							// ��¼���ɹ�
							else
							{
								// ShowToast.ShowToast1(mContext, "��ȡ������Ϣʧ��");
								toastMgr.builder.display("��ȡ������Ϣʧ��",
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
						// ShowToast.ShowToast1(mContext, "ʧ��");
						toastMgr.builder.display("ʧ��", Toast.LENGTH_SHORT);
						pd.dismiss();
					}
				});

	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		L.i(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onStop()
	{
		// TODO Auto-generated method stub
		L.i(TAG, "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		L.i(TAG, "onDestroy");
		super.onDestroy();
	}

	void findViews()
	{
		otherUserBack = (Button) findViewById(R.id.other_user_back);
		imageOtherUserMore = (ImageView) findViewById(R.id.image_onther_user_more);
		otherUserHeadPic = (ImageView) findViewById(R.id.other_user_headPic_myInfo);

		otherUserNameInfo = (TextView) findViewById(R.id.other_user_name_myInfo);
		otherUserNameMyplace = (TextView) findViewById(R.id.other_user_name_myplace);
		otherUserTextLiked = (TextView) findViewById(R.id.other_user_text_liked);
		otherUserTextFellow = (TextView) findViewById(R.id.other_user_text_follow);
		otherUserTextFans = (TextView) findViewById(R.id.other_user_text_fans);
		otherUserTextXinqing = (TextView) findViewById(R.id.other_user_text_xinqing);
		otherUserTextSign = (TextView) findViewById(R.id.other_user_text_sign);

		//
		otherUserLinearLayoutAllPhoto = (LinearLayout) findViewById(R.id.other_user_linearLayout_all_photo);
		otherUserGridAll = (GridView) findViewById(R.id.other_user_gridview_all_pet_photo);
		otherUserAddedPetList = (MyDetailsListView) findViewById(R.id.other_user_added_pet_list);
		// ���ü���
		otherUserBack.setOnClickListener(this);
		imageOtherUserMore.setOnClickListener(this);
		// �Ժ����һ�������ʾ��ͼͷ��
		otherUserHeadPic.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.other_user_headPic_myInfo:

			break;
		case R.id.image_onther_user_more:

			break;
		case R.id.other_user_back:
			ActivityMyActivityOtherUser.this.finish();
			break;
		default:
			break;
		}

	}

	private Context mContext;
	private static final String TAG = "com.gdut.pet.ui.ActivityMyActivityOtherUser";
	private Button otherUserBack;
	private ImageView imageOtherUserMore;
	private ImageView otherUserHeadPic;
	private TextView otherUserNameInfo;
	private TextView otherUserNameMyplace;
	private TextView otherUserTextLiked;
	private TextView otherUserTextFellow;
	private TextView otherUserTextFans;
	private TextView otherUserTextXinqing;
	private TextView otherUserTextSign;

	// ����ͼƬ�ͳ�������
	private LinearLayout otherUserLinearLayoutAllPhoto;
	private GridView otherUserGridAll;
	private MyDetailsListView otherUserAddedPetList;

	//
	private MyActivityAllPetPhotoGridViewAdapter otherUserAdapter;
	private AddedPetProfileItemAdapter addedPetAdapter;

}
