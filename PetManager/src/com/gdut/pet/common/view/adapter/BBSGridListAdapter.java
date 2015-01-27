package com.gdut.pet.common.view.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.ui.BBSDetailActivityNew;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.ui.mypet.R;

public class BBSGridListAdapter extends BaseAdapter
{

	private Context mContext;
	private List<Map<String, String>> gridList;

	/**
	 * list 里面放的是 每一个girdItem的内容 也就是map map对应着每一个item map里面又存放着一些东西, image_pet的图片地址http://www.baidu.com image_person的图片地址http://www.baidu.com 还应该存放,
	 * 用户名字, 用户id, 注册时间 发表时间等等 获取不获得这些也行, 点进去的时候再获得这些东西 对应着每一个item 点进去就是一个帖子的详细内容
	 */

	public BBSGridListAdapter()
	{

	}

	public BBSGridListAdapter(Context context)
	{
		this.mContext = context;
	}

	public BBSGridListAdapter(Context mContext, List<Map<String, String>> list)
	{
		this.mContext = mContext;
		this.gridList = list;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return gridList.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		final Holder holder;
		if (view == null)
		{
			holder = new Holder();
			view = View.inflate(mContext, R.layout.list_pet_all_pic_item_grid, null);
			// 帖子里面图片获得id
			holder.image_pet = (ImageView) view.findViewById(R.id.image_pet_bbs_detail);
			// 用户头像图片获得id
			holder.image_person = (ImageView) view.findViewById(R.id.image_person_bbs_detail);
			view.setTag(holder);
		}
		else
		{
			holder = (Holder) view.getTag();
		}
		String petUrl = gridList.get(position).get("petImage");
		String userUrl = gridList.get(position).get("userImage");

		// 宠物图片点击, 进入宠物图片详细界面
		holder.image_pet.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				toastMgr.builder.display("the " + position + "clicked", 1);

				Bundle bundle = new Bundle();

				bundle.putString("id", gridList.get(position).get("id"));
				bundle.putString("postType", gridList.get(position).get("postType"));
				// bundle.putString("bbsTitle", "hello");
				// bundle.putString("username", "andy");
				Intent intent = new Intent();
				intent.setClass(mContext, BBSDetailActivityNew.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			}
		});

		// 加载图片
		ImageLoader.getInstance().loadImage(petUrl, new ImageLoadingListener()
		{

			@Override
			public void onLoadingStarted(String imageUri, View view)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
			{
				// TODO Auto-generated method stub
				holder.image_pet.setImageBitmap(loadedImage);
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view)
			{
				// TODO Auto-generated method stub

			}
		});
		// 加载图片
		ImageLoader.getInstance().loadImage(userUrl, new ImageLoadingListener()
		{

			@Override
			public void onLoadingStarted(String imageUri, View view)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
			{
				// TODO Auto-generated method stub
				holder.image_person.setImageBitmap(loadedImage);
				L.i("pullGrid", "the " + position + "  debug");
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view)
			{
				// TODO Auto-generated method stub

			}
		});

		return view;
	}

	static class Holder
	{
		ImageView image_pet;// 帖子里面的图片
		ImageView image_person;// 用户头像图片
	}

}
