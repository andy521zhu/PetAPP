package com.gdut.pet.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ListView;

import com.gdut.pet.common.info.BBSCommentsInfo;
import com.gdut.pet.common.network.GetGuanzhuBBSDetail;
import com.gdut.pet.common.tools.MyJson;
import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.PullRefreshSetSound;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.common.view.adapter.BBSPetLostListViewAdapter;
import com.gdut.pet.config.Configs;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ui.mypet.R;

public class BBSGuanzhuActivity extends Activity
{
	private static final String TAG = "com.gdut.pet.ui.BBSGuanzhuActivity";

	private Context mContext;

	// 这里adapter就用宠物丢失里面的了
	private BBSPetLostListViewAdapter adapter;
	private List<String> listAdapter;
	private List<BBSCommentsInfo> guanzhuList;

	private PullToRefreshListView guanzhu_pull_refresh_listView;// 关注的用户的帖子的下拉列表
	private ListView mGuanzhuRefreshListView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guanzhu);

		mContext = this;
		L.i(TAG, "oncreate");
		findViews();
		guanzhuList = new ArrayList<BBSCommentsInfo>();
		listAdapter = new ArrayList<String>();
		listAdapter.add("1");
		listAdapter.add("1");
		listAdapter.add("1");
		listAdapter.add("1");
		listAdapter.add("1");
		getGuanzhuBBSList();

	}

	private void getGuanzhuBBSList()
	{
		// TODO Auto-generated method stub

		new GetGuanzhuBBSDetail(Configs.GET_GUANZHU_BBS_LIST_PATH,
				new PersistentCookieStore(mContext),
				new GetGuanzhuBBSDetail.SuccessCallback()
				{

					@Override
					public void onSuccess(String result)
					{
						// TODO Auto-generated method stub
						if (result == null)
						{
							toastMgr.builder.display("获取信息失败", 0);
						}
						else
						{
							// 解析json
							MyJson decode = new MyJson(mContext, result);
							guanzhuList = decode.getBBSDetail();
							Message msg = new Message();
							msg.obj = guanzhuList;
							msg.what = 1;
							guanzhuListHandler.sendMessage(msg);
						}
					}
				}, new GetGuanzhuBBSDetail.FailCallback()
				{

					@Override
					public void onFail()
					{
						// TODO Auto-generated method stub

					}
				}, "0");
	}

	private Handler guanzhuListHandler = new Handler()
	{
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 1:
				List<BBSCommentsInfo> _guanzhuList = (List<BBSCommentsInfo>) msg.obj;
				adapter = new BBSPetLostListViewAdapter(mContext, _guanzhuList);
				mGuanzhuRefreshListView.setAdapter(adapter);
				// mGuanzhuRefreshListView.notifyAll();
				break;

			default:
				break;
			}
		};
	};

	void findViews()
	{
		guanzhu_pull_refresh_listView = (PullToRefreshListView) findViewById(R.id.guanzhu_pull_refresh_listView);
		mGuanzhuRefreshListView = guanzhu_pull_refresh_listView
				.getRefreshableView();
		guanzhu_pull_refresh_listView
				.setOnRefreshListener(new OnRefreshListener<ListView>()
				{

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView)
					{
						// TODO Auto-generated method stub
						L.i(TAG, "onResume");
						toastMgr.builder.display("刷新", 0);
						guanzhu_pull_refresh_listView.onRefreshComplete();
					}
				});
		// 设置刷新时候的声音
		PullRefreshSetSound.setSound(mContext, guanzhu_pull_refresh_listView);

		//

	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		L.i(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		L.i(TAG, "onStart");
		super.onStart();
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		L.i(TAG, "onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onStop()
	{
		// TODO Auto-generated method stub
		L.i(TAG, "onStop");
		super.onStop();
	}

}
