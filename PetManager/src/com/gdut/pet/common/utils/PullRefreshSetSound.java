package com.gdut.pet.common.utils;

import android.content.Context;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.ui.mypet.R;

public class PullRefreshSetSound
{
	/**
	 * PullToRefreshListView ����ˢ��ʱ�������
	 * 
	 * @param mContext
	 * @param listView
	 */
	public static void setSound(Context mContext, PullToRefreshListView listView)
	{
		// Ϊpulltorefresh�������
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
		listView.setOnPullEventListener(soundListener);
	}

	/**
	 * PullToRefreshGridView ����ˢ��ʱ�������
	 * 
	 * @param mContext
	 * @param gridView
	 */
	public static void setSound(Context mContext, PullToRefreshGridView gridView)
	{

	}
}
