package com.gdut.pet.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdut.pet.common.network.GetBBS;
import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.view.adapter.BBSListviewAdapter;
import com.gdut.pet.config.Configs;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.ui.mypet.R;

public class MyFriendsActivity extends Activity
{

	private PullToRefreshListView mPullToRefreshListView;
	// private ArrayAdapter<String> mAdapter;
	private BBSListviewAdapter mAdapter;
	private LinkedList<String> mListItems;
	private Context mContext;

	ListView actualListView;
	private List<Map<String, String>> list;
	private SharedPreferences userdataSP;

	private static final String TAG = "com.gdut.pet.ui.MyFriendsActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myfriends);

		mContext = this;
		userdataSP = getSharedPreferences(Configs.USERDATA_SP,
				Context.MODE_PRIVATE);
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_listView);

		// 列表拉到最后一个的时候 提示用户已经到底了
		mPullToRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener()
				{

					@Override
					public void onLastItemVisible()
					{
						// TODO Auto-generated method stub
						Toast.makeText(mContext, "Last Item...",
								Toast.LENGTH_SHORT).show();
					}
				});

		actualListView = mPullToRefreshListView.getRefreshableView();
		// Need to use the Actual ListView when registering for Context Menu
		// registerForContextMenu(actualListView);

		// Set a listener to be invoked when the list should be refreshed.
		mPullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>()
				{

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView)
					{
						// TODO Auto-generated method stub
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						Log.i(TAG, label);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						// Do work to refresh the list here.
						if (userdataSP.getBoolean(Configs.IS_LOGIN, false))
						{
							getBBSList(true, "0");

						}
					}
				});

		mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mStrings));

		// mAdapter = new ArrayAdapter<String>(mContext,
		// android.R.layout.simple_list_item_1, mListItems);
		list = new ArrayList<Map<String, String>>();
		// for (int i = 0; i < 10; i++)
		// {
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("time", "2014.10.0" + i);
		// map.put("description", "asdga");
		// map.put("title", "nihao");
		// list.add(0, map);
		// }
		if (userdataSP.getBoolean(Configs.IS_LOGIN, false))
		{
			// 已经登陆了
			getBBSList(false, "0");
		}
		else
		{

		}

		// item click
		mPullToRefreshListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener()
				{

					@SuppressWarnings("unchecked")
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id)
					{
						// TODO Auto-generated method stub
						@SuppressWarnings("unused")
						HashMap<String, String> mapItem;

						mapItem = (HashMap<String, String>) list
								.get(position - 1);
						String id1 = mapItem.get("id");

						TextView titleTextView = (TextView) view
								.findViewById(R.id.Item_MainTitle);
						TextView usernameTextView = (TextView) view
								.findViewById(R.id.Item_UserName);
						String text = titleTextView.getText().toString();
						String username = usernameTextView.getText().toString();
						Bundle bundle = new Bundle();
						bundle.putString("id", id1);
						bundle.putString("bbsTitle", text);
						bundle.putString("username", username);
						Intent intent = new Intent();
						intent.setClass(MyFriendsActivity.this,
								BBSDetailActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});

	}

	// 得到bbs的列表数据
	private void getBBSList(boolean isRefresh, String id)
	{

		// 进度Dialog
		final ProgressDialog pd = ProgressDialog.show(mContext, getResources()
				.getString(R.string.connecting),
				getResources().getString(R.string.connecting_to_server));

		new GetBBS(Configs.GET_BBS_PATH, new PersistentCookieStore(mContext),
				new GetBBS.SuccessCallback()
				{

					@Override
					public void onSuccess(String result)
					{
						// TODO Auto-generated method stub
						// 得到返回的数据,进行json解析
						if (result != null)
						{
							try
							{
								JSONArray jsonArray = new JSONArray(result);
								for (int i = 0; i < jsonArray.length(); i++)
								{
									JSONObject jsonObject = jsonArray
											.getJSONObject(i);
									Map<String, String> map = new HashMap<String, String>();

									map.put("id", (String) jsonObject.get("id"));

									map.put("title",
											(String) jsonObject.get("title"));
									map.put("description",
											(String) jsonObject.get("content"));
									map.put("time",
											(String) jsonObject.get("time"));
									map.put("username",
											(String) jsonObject.get("username"));
									map.put("replynum",
											(String) jsonObject.get("replynum"));
									list.add(0, map);

								}

							}
							catch (JSONException e)
							{
								// TODO Auto-generated catch block
								System.out.println(e.toString());
								e.printStackTrace();
							}
							Message msg = new Message();
							msg.what = 1;
							handler.sendMessage(msg);
							pd.dismiss();
						}

					}
				}, new GetBBS.FailCallback()
				{

					@Override
					public void onFail()
					{
						// TODO Auto-generated method stub
						pd.dismiss();
					}
				},
				// 是否是从刷新过去的
				isRefresh,
				//
				id);
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]>
	{

		@Override
		protected String[] doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
			}
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result)
		{
			// TODO Auto-generated method stub
			Map<String, String> map = new HashMap<String, String>();
			map.put("time", "2014.10.04");
			map.put("description", "asdga");
			map.put("title", "Added after refresh...");
			list.add(0, map);
			int sum = list.size();
			// mListItems.addFirst("Added after refresh...");
			mAdapter.notifyDataSetChanged();

			// Call onRefreshComplete when the list has been refreshed.
			mPullToRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}

	}

	private String[] mStrings =
	{ "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance",
			"Ackawi", "Acorn", "Adelost", "Affidelice au Chablis",
			"Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler", "Abbaye de Belloc",
			"Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu",
			"Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler" };

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		L.i(TAG, "onresume");
		super.onResume();
	}

	Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			// TODO Auto-generated method stub
			switch (msg.what)
			{
			case 1:
				mAdapter = new BBSListviewAdapter(mContext, list);
				/**
				 * Add Sound Event Listener
				 */
				SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(
						mContext);
				soundListener.addSoundEvent(State.PULL_TO_REFRESH,
						R.raw.pull_event);
				soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
				soundListener.addSoundEvent(State.REFRESHING,
						R.raw.refreshing_sound);
				mPullToRefreshListView.setOnPullEventListener(soundListener);

				actualListView.setAdapter(mAdapter);
				// mListItems.addFirst("Added after refresh...");
				mAdapter.notifyDataSetChanged();

				// Call onRefreshComplete when the list has been
				// refreshed.
				mPullToRefreshListView.onRefreshComplete();
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	// onResume
	public void invisibleOnScreen()
	{
		Log.d(TAG, "invisibleOnScreen");

	}

	// onPause
	public void goneOnScreen()
	{
		Log.d(TAG, "goneOnScreen");

	}

}
