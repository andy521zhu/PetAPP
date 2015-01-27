package com.gdut.pet.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.gdut.pet.common.network.GetBBS;
import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.common.view.adapter.BBSGridListAdapter;
import com.gdut.pet.config.Configs;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.ui.mypet.R;

public class AllPetListActivity extends Activity
{

	private Context mContext;
	private static final String TAG = "com.gdut.pet.ui.AllPetListActivity";

	private PullToRefreshGridView mPullToRefreshGridView;// ����ˢ��
	private LinearLayout mNoNetWorkLayout;// ��ʾ�����������ӵ�
	private GridView mGridView;// ����ˢ�µ�gridview
	private BBSGridListAdapter gridListAdapter;
	private List<Map<String, String>> mGridList;
	private LinearLayout layoutPostPetImage;
	private int refreshID = 0;// ����ˢ��ʱ�򴫽�ȥ��id
	private int loadingID = 0;// ��������ʱ���id
	private int firstLoadID = 0;// �տ�ʼ���������id
	int _refreshid = 0;

	private Handler handleGridViewDisplay = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 1:
				gridListAdapter = new BBSGridListAdapter(mContext, mGridList);
				// ����������
				mGridView.setAdapter(gridListAdapter);
				gridListAdapter.notifyDataSetChanged();
				mPullToRefreshGridView.onRefreshComplete();
				break;
			case 2:
				gridListAdapter.notifyDataSetChanged();
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
		setContentView(R.layout.activity_list_pet_all_pic_grid);
		// ���ý���
		mContext = this;
		findViews();

		mGridList = new ArrayList<Map<String, String>>();

		// �ж������Ƿ�����, û�����Ӿ���ʾ,����û������

		mGridView = mPullToRefreshGridView.getRefreshableView();
		mPullToRefreshGridView.setMode(Mode.BOTH);

		// ������������
		mPullToRefreshGridView.setOnRefreshListener(new OnRefreshListener2<GridView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView)
			{
				// TODO Auto-generated method stub
				L.i(TAG, "onPullDownToRefresh");
				// ShowToast.ShowToastCenter(mContext, "����");
				toastMgr.builder.display("����", 1);
				getBBSFromRefresh(false, "0");

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView)
			{
				// TODO Auto-generated method stub
				L.i(TAG, "onPullUpToRefresh");
				// ShowToast.ShowToastCenter(mContext, "����");
				toastMgr.builder.display("����", 1);
				mPullToRefreshGridView.onRefreshComplete();
			}
		});
		// item ���
		/*
		 * mPullToRefreshGridView .setOnItemClickListener(new AdapterView.OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // TODO Auto-generated method stub
		 * toastMgr.builder.display("the " + position + "clicked", 1);
		 * 
		 * Bundle bundle = new Bundle(); bundle.putString("id", "1"); bundle.putString("bbsTitle", "hello"); bundle.putString("username", "andy");
		 * Intent intent = new Intent(); intent.setClass(AllPetListActivity.this, BBSDetailActivityNew.class); intent.putExtras(bundle);
		 * startActivity(intent); } });
		 */

		// �õ���������ͼƬ(�����б�)
		L.i(TAG, "getbbslist");
		getBBSList(false, "0");

	}

	/**
	 * �״ν���������� �������
	 * 
	 * @param isRefresh
	 * @param id
	 */
	private void getBBSList(boolean isRefresh, String id)
	{
		// TODO Auto-generated method stub

		// ����Dialog
		final ProgressDialog pd = ProgressDialog.show(mContext, getResources().getString(R.string.connecting),
				getResources().getString(R.string.connecting_to_server));
		new GetBBS(Configs.GET_BBS_PATH, new PersistentCookieStore(mContext), new GetBBS.SuccessCallback()
		{

			@Override
			public void onSuccess(String result)
			{
				// TODO Auto-generated method stub
				if (result != null)
				{
					try
					{
						JSONArray jsonArray = new JSONArray(result);
						JSONObject statusObject = jsonArray.getJSONObject(0);
						String status = statusObject.getString("status");
						if (status.equals("1"))
						{

						}
						else if (status.equals("2"))
						{
							toastMgr.builder.display("��ȡ����ʧ��", 0);
							return;
						}
						else
						{
							toastMgr.builder.display("��ȡ����ʧ��", 0);
							return;
						}
						for (int i = 1; i < jsonArray.length(); i++)
						{
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							Map<String, String> map = new HashMap<String, String>();
							// id
							map.put("id", (String) jsonObject.get("bbsID"));
							String __refreshid = (String) jsonObject.get("bbsID");
							// _refreshid = Integer.parseInt(__refreshid);
							// ����
							// map.put("title", "");
							// (String) jsonObject.get("title"));
							// ��������
							// map.put("description", (String) jsonObject.get("content"));
							// ����ʱ��
							// map.put("time", (String) jsonObject.get("time"));
							// �û���
							// map.put("username", (String) jsonObject.get("username"));
							// �ظ���
							// map.put("replynum", (String) jsonObject.get("replynum"));
							// �Ƿ��ע
							// map.put("isGuanzhu", (String) jsonObject.get("isGuanzhu"));
							// ������Ŀ
							// map.put("likenum", (String) jsonObject.get("likenum"));
							// �û�ͷ��URL
							String userImage = Configs.SERVER_IP_ADDRESS + ((String) jsonObject.get("userImage"));
							L.i(TAG + "userimage", userImage);
							map.put("userImage", userImage);
							map.put("postType", jsonObject.getString("postType"));
							// ����ͼƬURL
							String petImage = Configs.SERVER_IP_ADDRESS + (String) jsonObject.get("petImage");
							L.i(TAG + "petimage", petImage);
							map.put("petImage", petImage);
							// �ӵ�list����
							mGridList.add(0, map);
							// if (refreshID > _refreshid)
							// {
							// refreshID = refreshID;
							// }
							// else
							// refreshID = _refreshid;// �õ�����һ��id

						}
					}
					catch (Exception e)
					{
						// TODO: handle exception
						L.i(TAG, "����json�쳣 ��λ������");
						L.i(TAG, e.toString());
						pd.dismiss();
					}

					Message msg = new Message();
					msg.what = 1;
					handleGridViewDisplay.sendMessage(msg);
					pd.dismiss();

				}
				else
				{
					toastMgr.builder.display("��ȡ��Ϣʧ��", 0);
					pd.dismiss();
				}
				toastMgr.builder.display("��ȡ��Ϣ�ɹ�", 0);
			}
		},
		//
				new GetBBS.FailCallback()
				{

					@Override
					public void onFail()
					{
						// TODO Auto-generated method stub
						toastMgr.builder.display("��ȡ��Ϣʧ��", 0);
						pd.dismiss();
					}
				}, isRefresh, id);
	}

	/**
	 * ����ˢ�µ�ʱ�����
	 * 
	 * @param isRefresh
	 * @param id
	 */
	private void getBBSFromRefresh(boolean isRefresh, String id)
	{
		new GetBBS(Configs.GET_BBS_PATH, new PersistentCookieStore(mContext), new GetBBS.SuccessCallback()
		{

			@Override
			public void onSuccess(String result)
			{
				// TODO Auto-generated method stub
				if (result != null)
				{
					try
					{
						JSONArray jsonArray = new JSONArray(result);
						for (int i = 0; i < jsonArray.length(); i++)
						{
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							Map<String, String> map = new HashMap<String, String>();
							// id
							map.put("id", (String) jsonObject.get("id"));
							String __refreshid = (String) jsonObject.get("id");
							_refreshid = Integer.parseInt(__refreshid);
							// ����
							map.put("title", (String) jsonObject.get("title"));
							// ��������
							map.put("description", (String) jsonObject.get("content"));
							// ����ʱ��
							map.put("time", (String) jsonObject.get("time"));
							// �û���
							map.put("username", (String) jsonObject.get("username"));
							// �ظ���
							map.put("replynum", (String) jsonObject.get("replynum"));
							// �Ƿ��ע
							map.put("isGuanzhu", (String) jsonObject.get("isGuanzhu"));
							// ������Ŀ
							map.put("likenum", (String) jsonObject.get("likenum"));
							// �û�ͷ��URL
							map.put("userImage", (String) jsonObject.get("userImage"));
							// ����ͼƬURL
							map.put("petImage", (String) jsonObject.get("petImage"));
							// �ӵ�list����
							mGridList.add(0, map);
							if (refreshID > _refreshid)
							{
								refreshID = refreshID;
							}
							else
								refreshID = _refreshid;// �õ�����һ��id

						}
					}
					catch (Exception e)
					{
						// TODO: handle exception
						L.i(TAG, "����json�쳣 ��λ������");
						L.i(TAG, e.toString());
					}

					Message msg = new Message();
					msg.what = 2;
					handleGridViewDisplay.sendMessage(msg);

				}
				else
				{
					toastMgr.builder.display("��ȡ��Ϣʧ��", 0);
				}
				toastMgr.builder.display("��ȡ��Ϣ�ɹ�", 0);
				mPullToRefreshGridView.onRefreshComplete();
			}
		},
		//
				new GetBBS.FailCallback()
				{

					@Override
					public void onFail()
					{
						// TODO Auto-generated method stub
						toastMgr.builder.display("��ȡ��Ϣʧ��", 0);
						mPullToRefreshGridView.onRefreshComplete();
					}
				}, isRefresh, id);
	}

	/**
	 * �ҵ������ؼ�id
	 */
	private void findViews()
	{
		// TODO Auto-generated method stub
		// ����ˢ��
		mPullToRefreshGridView = (PullToRefreshGridView) findViewById(R.id.pulltorefresh_gridview);
		mNoNetWorkLayout = (LinearLayout) findViewById(R.id.layout_no_network);
		layoutPostPetImage = (LinearLayout) findViewById(R.id.layout_post_petImage);
		layoutPostPetImage.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(AllPetListActivity.this, ActivityPostPetImage.class);
				startActivity(intent);
			}
		});

	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
