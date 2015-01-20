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
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.ui.mypet.R;

public class ActivityPetFoundBBS extends Activity
{

	private static final String TAG = "com.gdut.pet.ui.ActivityPetFoundBBS";
	private Context mContext;
	private PullToRefreshListView petfoundPullToRefresh;
	private Button back;
	private List<BBSCommentsInfo> foundList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pet_found_bbs);
		L.i(TAG, "oncreate");
		mContext = this;
		findViews();

		// 下拉
		petfoundPullToRefresh
				.setOnRefreshListener(new OnRefreshListener<ListView>()
				{

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView)
					{
						// TODO Auto-generated method stub
						toastMgr.builder.display("下拉", 0);
						petfoundPullToRefresh.onRefreshComplete();
					}
				});
		// 最后一个item显示
		petfoundPullToRefresh
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener()
				{

					@Override
					public void onLastItemVisible()
					{
						// TODO Auto-generated method stub
						toastMgr.builder.display("last item", 0);
					}
				});

		// 为pulltorefresh添加声音
		SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(
				mContext);
		soundListener
				.addSoundEvent(
						com.handmark.pulltorefresh.library.PullToRefreshBase.State.PULL_TO_REFRESH,
						R.raw.pull_event);
		soundListener
				.addSoundEvent(
						com.handmark.pulltorefresh.library.PullToRefreshBase.State.RESET,
						R.raw.reset_sound);
		soundListener
				.addSoundEvent(
						com.handmark.pulltorefresh.library.PullToRefreshBase.State.REFRESHING,
						R.raw.refreshing_sound);
		petfoundPullToRefresh.setOnPullEventListener(soundListener);

		foundList = new ArrayList<BBSCommentsInfo>();
		getFoundBBSDetailList();
		L.i(TAG, "getFoundBBSDetailList");
	}

	private Handler foundListHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 1:
				L.i(TAG, "handleMessage");
				BBSPetLostListViewAdapterReal adapter = new BBSPetLostListViewAdapterReal(
						mContext, foundList);
				petfoundPullToRefresh.setAdapter(adapter);
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 
	 */
	void getFoundBBSDetailList()
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
						if (result.equals("") || null == result)
						{
							toastMgr.builder.display("网络错误", 0);
							return;
						}
						MyJson myJson = new MyJson(mContext, result);
						foundList = myJson.getLostFoundBBSDetail();
						Message msg = new Message();
						msg.what = 1;
						foundListHandler.sendMessage(msg);

					}
				},
				//
				new GetLostFoundBBSDetail.FailCallback()
				{

					@Override
					public void onFail()
					{
						// TODO Auto-generated method stub

					}
				},
				//
				"0",
				//
				"3");
	}

	/**
	 * get Objects
	 */
	void findViews()
	{
		petfoundPullToRefresh = (PullToRefreshListView) findViewById(R.id.pet_found_pull_refresh_listView);
		back = (Button) findViewById(R.id.bbs_back_found_bbs);
		back.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View paramView)
			{
				// TODO Auto-generated method stub
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

}
