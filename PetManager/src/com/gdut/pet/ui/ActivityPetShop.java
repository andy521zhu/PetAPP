package com.gdut.pet.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.common.view.adapter.PetShopGridViewAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

public class ActivityPetShop extends Activity
{

	private static final String TAG = "com.gdut.pet.ui.ActivityPetShop";
	private Context mContext;
	private Button aboutBack;

	private PullToRefreshGridView mPullToRefreshGridView;
	private GridView mGridView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pet_shop);
		L.d(TAG, "onCreate");
		mContext = this;
		initView();

		mPullToRefreshGridView
				.setOnRefreshListener(new OnRefreshListener<GridView>()
				{

					@Override
					public void onRefresh(
							PullToRefreshBase<GridView> refreshView)
					{
						// TODO Auto-generated method stub
						toastMgr.builder.display("刷新", 0);
						L.i(TAG, "mPullToRefreshGridView onRefresh");
						mPullToRefreshGridView.onRefreshComplete();
					}
				});
		// 项目点击事件
		mPullToRefreshGridView
				.setOnItemClickListener(new AdapterView.OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id)
					{
						// TODO Auto-generated method stub
						toastMgr.builder.display("itemclick", 0);
						L.i(TAG, "setOnItemClickListener onItemClick");
					}
				});

		// 项目长按事件
		mPullToRefreshGridView.setOnLongClickListener(new OnLongClickListener()
		{

			public boolean onLongClick(View v)
			{
				// TODO Auto-generated method stub
				toastMgr.builder.display("长按", 0);
				L.i(TAG, "setOnLongClickListener onLongClick");
				return true;
			}
		});

		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("a");
		list.add("a");
		list.add("a");
		list.add("a");
		list.add("a");
		PetShopGridViewAdapter adapter = new PetShopGridViewAdapter(mContext,
				list);
		mPullToRefreshGridView.setAdapter(adapter);

	}// oncreate

	private void initView()
	{
		// 返回按钮
		aboutBack = (Button) findViewById(R.id.back_pet_shop);
		aboutBack.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				ActivityPetShop.this.finish();
			}
		});

		mPullToRefreshGridView = (PullToRefreshGridView) findViewById(R.id.pet_shop_pull_torefresh_gridview);
		mGridView = mPullToRefreshGridView.getRefreshableView();

	}

	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		L.d(TAG, "onResume");
		super.onStart();
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		L.d(TAG, "onResume");
		super.onResume();
		// 友盟
		MobclickAgent.onResume(mContext);
	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		// 友盟
		MobclickAgent.onPause(mContext);
	}

}
