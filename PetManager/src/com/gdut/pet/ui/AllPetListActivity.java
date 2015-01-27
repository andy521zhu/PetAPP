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

	private PullToRefreshGridView mPullToRefreshGridView;// 下拉刷新
	private LinearLayout mNoNetWorkLayout;// 显示有无网络连接的
	private GridView mGridView;// 下拉刷新的gridview
	private BBSGridListAdapter gridListAdapter;
	private List<Map<String, String>> mGridList;
	private LinearLayout layoutPostPetImage;
	private int refreshID = 0;// 下拉刷新时候传进去的id
	private int loadingID = 0;// 上拉加载时候的id
	private int firstLoadID = 0;// 刚开始进来界面的id
	int _refreshid = 0;

	private Handler handleGridViewDisplay = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 1:
				gridListAdapter = new BBSGridListAdapter(mContext, mGridList);
				// 设置适配器
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
		// 不用解释
		mContext = this;
		findViews();

		mGridList = new ArrayList<Map<String, String>>();

		// 判断网络是否连接, 没有连接就提示,网络没有连接

		mGridView = mPullToRefreshGridView.getRefreshableView();
		mPullToRefreshGridView.setMode(Mode.BOTH);

		// 下拉监听设置
		mPullToRefreshGridView.setOnRefreshListener(new OnRefreshListener2<GridView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView)
			{
				// TODO Auto-generated method stub
				L.i(TAG, "onPullDownToRefresh");
				// ShowToast.ShowToastCenter(mContext, "下拉");
				toastMgr.builder.display("下拉", 1);
				getBBSFromRefresh(false, "0");

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView)
			{
				// TODO Auto-generated method stub
				L.i(TAG, "onPullUpToRefresh");
				// ShowToast.ShowToastCenter(mContext, "上拉");
				toastMgr.builder.display("上拉", 1);
				mPullToRefreshGridView.onRefreshComplete();
			}
		});
		// item 点击
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

		// 得到宠物所有图片(帖子列表)
		L.i(TAG, "getbbslist");
		getBBSList(false, "0");

	}

	/**
	 * 首次进入这个界面 调用这个
	 * 
	 * @param isRefresh
	 * @param id
	 */
	private void getBBSList(boolean isRefresh, String id)
	{
		// TODO Auto-generated method stub

		// 进度Dialog
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
							toastMgr.builder.display("获取帖子失败", 0);
							return;
						}
						else
						{
							toastMgr.builder.display("获取帖子失败", 0);
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
							// 标题
							// map.put("title", "");
							// (String) jsonObject.get("title"));
							// 内容描述
							// map.put("description", (String) jsonObject.get("content"));
							// 发表时间
							// map.put("time", (String) jsonObject.get("time"));
							// 用户名
							// map.put("username", (String) jsonObject.get("username"));
							// 回复数
							// map.put("replynum", (String) jsonObject.get("replynum"));
							// 是否关注
							// map.put("isGuanzhu", (String) jsonObject.get("isGuanzhu"));
							// 点赞数目
							// map.put("likenum", (String) jsonObject.get("likenum"));
							// 用户头像URL
							String userImage = Configs.SERVER_IP_ADDRESS + ((String) jsonObject.get("userImage"));
							L.i(TAG + "userimage", userImage);
							map.put("userImage", userImage);
							map.put("postType", jsonObject.getString("postType"));
							// 宠物图片URL
							String petImage = Configs.SERVER_IP_ADDRESS + (String) jsonObject.get("petImage");
							L.i(TAG + "petimage", petImage);
							map.put("petImage", petImage);
							// 加到list里面
							mGridList.add(0, map);
							// if (refreshID > _refreshid)
							// {
							// refreshID = refreshID;
							// }
							// else
							// refreshID = _refreshid;// 得到最大的一个id

						}
					}
					catch (Exception e)
					{
						// TODO: handle exception
						L.i(TAG, "解析json异常 定位到这里");
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
					toastMgr.builder.display("获取信息失败", 0);
					pd.dismiss();
				}
				toastMgr.builder.display("获取信息成功", 0);
			}
		},
		//
				new GetBBS.FailCallback()
				{

					@Override
					public void onFail()
					{
						// TODO Auto-generated method stub
						toastMgr.builder.display("获取信息失败", 0);
						pd.dismiss();
					}
				}, isRefresh, id);
	}

	/**
	 * 下拉刷新的时候调用
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
							// 标题
							map.put("title", (String) jsonObject.get("title"));
							// 内容描述
							map.put("description", (String) jsonObject.get("content"));
							// 发表时间
							map.put("time", (String) jsonObject.get("time"));
							// 用户名
							map.put("username", (String) jsonObject.get("username"));
							// 回复数
							map.put("replynum", (String) jsonObject.get("replynum"));
							// 是否关注
							map.put("isGuanzhu", (String) jsonObject.get("isGuanzhu"));
							// 点赞数目
							map.put("likenum", (String) jsonObject.get("likenum"));
							// 用户头像URL
							map.put("userImage", (String) jsonObject.get("userImage"));
							// 宠物图片URL
							map.put("petImage", (String) jsonObject.get("petImage"));
							// 加到list里面
							mGridList.add(0, map);
							if (refreshID > _refreshid)
							{
								refreshID = refreshID;
							}
							else
								refreshID = _refreshid;// 得到最大的一个id

						}
					}
					catch (Exception e)
					{
						// TODO: handle exception
						L.i(TAG, "解析json异常 定位到这里");
						L.i(TAG, e.toString());
					}

					Message msg = new Message();
					msg.what = 2;
					handleGridViewDisplay.sendMessage(msg);

				}
				else
				{
					toastMgr.builder.display("获取信息失败", 0);
				}
				toastMgr.builder.display("获取信息成功", 0);
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
						toastMgr.builder.display("获取信息失败", 0);
						mPullToRefreshGridView.onRefreshComplete();
					}
				}, isRefresh, id);
	}

	/**
	 * 找到各个控件id
	 */
	private void findViews()
	{
		// TODO Auto-generated method stub
		// 下拉刷新
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
