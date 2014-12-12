package com.gdut.pet.ui;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;

import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.PullRefreshSetSound;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.common.view.adapter.AllPetPhotoItemGridAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.ui.mypet.R;

public class ActivityAllPetPic extends Activity
{
	private static final String TAG = "com.gdut.pet.ui.ActivityAllPetPic";
	private Context mContext;
	private Button back_pet_pic;
	private PullToRefreshGridView allPetPicGridView;
	private GridView mGridView;
	private List<String> gridList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_all_pet_pic);
		mContext = this;
		findViews();
		L.i(TAG, "onCreate");

		gridList = getIntent().getExtras().getStringArrayList("petpiclist");

		// gridList = new ArrayList<String>();
		// gridList.add("asd");
		// gridList.add("asd");
		// gridList.add("asd");
		// gridList.add("asd");
		// gridList.add("asd");
		AllPetPhotoItemGridAdapter adapter = new AllPetPhotoItemGridAdapter(
				mContext, gridList);
		mGridView.setAdapter(adapter);

		allPetPicGridView
				.setOnRefreshListener(new OnRefreshListener<GridView>()
				{

					@Override
					public void onRefresh(
							PullToRefreshBase<GridView> refreshView)
					{
						// TODO Auto-generated method stub
						toastMgr.builder.display("舒心", 0);
						allPetPicGridView.onRefreshComplete();
					}
				});
		PullRefreshSetSound.setSound(mContext, allPetPicGridView);

	}

	void findViews()
	{
		back_pet_pic = (Button) findViewById(R.id.back_pet_pic);
		back_pet_pic.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				ActivityAllPetPic.this.finish();
			}
		});
		// 下拉刷新
		allPetPicGridView = (PullToRefreshGridView) findViewById(R.id.all_pet_pic_pulltorefresh_gridview);
		mGridView = allPetPicGridView.getRefreshableView();
	}

	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
		L.i(TAG, "onStart");
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		L.i(TAG, "onResume");
	}

	@Override
	protected void onStop()
	{
		// TODO Auto-generated method stub
		super.onStop();
		L.i(TAG, "onStop");
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		L.i(TAG, "ondestroy");
	}

}
