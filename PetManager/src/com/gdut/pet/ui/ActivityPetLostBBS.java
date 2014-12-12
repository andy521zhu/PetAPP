package com.gdut.pet.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.gdut.pet.common.info.BBSCommentsInfo;
import com.gdut.pet.common.network.GetLostFoundBBSDetail;
import com.gdut.pet.common.tools.MyJson;
import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.common.view.adapter.BBSPetLostListViewAdapterReal;
import com.gdut.pet.config.Configs;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.ui.mypet.R;

public class ActivityPetLostBBS extends Activity
{

	private static final String TAG = "com.gdut.pet.ui.ActivityPetLostBBS";
	private Context mContext;
	private PullToRefreshListView petLostPullToRefresh;
	private Button back;

	/**
	 * 得到帖子列表数据 这里进行显示 updateUI
	 */
	private Handler lostFoundHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
			case 1:
				BBSPetLostListViewAdapterReal adapter = new BBSPetLostListViewAdapterReal(
						mContext, lostFoundList);
				petLostPullToRefresh.setAdapter(adapter);
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
		setContentView(R.layout.activity_pet_lost_bbs);
		L.i(TAG, "oncreate");
		mContext = this;
		findViews();

		// 刷新监听事件
		petLostPullToRefresh
				.setOnRefreshListener(new OnRefreshListener<ListView>()
				{

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView)
					{
						// TODO Auto-generated method stub
						toastMgr.builder.display("刷新", 0);
						petLostPullToRefresh.onRefreshComplete();
					}
				});

		// 添加舒心时候的声音
		addSoundToPullRefresh();
		// 添加适配器
		List<String> list = new ArrayList<String>();
		lostFoundList = new ArrayList<BBSCommentsInfo>();
		getPetLostBBSList();

	}

	void getPetLostBBSList()
	{
		new GetLostFoundBBSDetail(Configs.GET_LOST_FOUND_BBS_PATH,
		//
				new PersistentCookieStore(mContext),
				//
				new GetLostFoundBBSDetail.SuccessCallback()
				{

					@Override
					public void onSuccess(String result)
					{
						// TODO Auto-generated method stub
						if (result.equals("") || result == null)
						{
							toastMgr.builder.display("网络错误", 0);
							return;
						}
						MyJson myJson = new MyJson(mContext, result);
						lostFoundList = myJson.getLostFoundBBSDetail();
						Message msg = new Message();
						msg.what = 1;
						msg.obj = lostFoundList;
						lostFoundHandler.sendMessage(msg);

					}
				},
				//
				new GetLostFoundBBSDetail.FailCallback()
				{

					@Override
					public void onFail()
					{
						// TODO Auto-generated method stub
						toastMgr.builder.display("网络错误", 0);
						return;
					}
				},
				//
				"0",
				// 2 想得到丢失的列表
				"2");
	}

	/**
	 * 为下拉列表添加声音
	 */
	private void addSoundToPullRefresh()
	{
		SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(
				mContext);
		soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
		soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
		soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
		petLostPullToRefresh.setOnPullEventListener(soundListener);
	}

	void findViews()
	{
		petLostPullToRefresh = (PullToRefreshListView) findViewById(R.id.pet_lost_pull_refresh_listView);
		back = (Button) findViewById(R.id.bbs_back_lost_bbs);
		back.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				// 尽量在这里面释放所有的 没用的资源
				finish();
			}
		});
	}

	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		L.i(TAG, "onStart");
		super.onStart();
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		L.i(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		L.i(TAG, "onDestroy");
		super.onDestroy();
	}

	private List<BBSCommentsInfo> lostFoundList;

}
