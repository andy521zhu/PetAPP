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
	 * list ����ŵ��� ÿһ��girdItem������ Ҳ����map map��Ӧ��ÿһ��item map�����ִ����һЩ����, image_pet��ͼƬ��ַhttp://www.baidu.com image_person��ͼƬ��ַhttp://www.baidu.com ��Ӧ�ô��,
	 * �û�����, �û�id, ע��ʱ�� ����ʱ��ȵ� ��ȡ�������ЩҲ��, ���ȥ��ʱ���ٻ����Щ���� ��Ӧ��ÿһ��item ���ȥ����һ�����ӵ���ϸ����
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
			// ��������ͼƬ���id
			holder.image_pet = (ImageView) view.findViewById(R.id.image_pet_bbs_detail);
			// �û�ͷ��ͼƬ���id
			holder.image_person = (ImageView) view.findViewById(R.id.image_person_bbs_detail);
			view.setTag(holder);
		}
		else
		{
			holder = (Holder) view.getTag();
		}
		String petUrl = gridList.get(position).get("petImage");
		String userUrl = gridList.get(position).get("userImage");

		// ����ͼƬ���, �������ͼƬ��ϸ����
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

		// ����ͼƬ
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
		// ����ͼƬ
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
		ImageView image_pet;// ���������ͼƬ
		ImageView image_person;// �û�ͷ��ͼƬ
	}

}
