package com.gdut.pet.common.view.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.ui.mypet.R;

public class AllPetPhotoItemGridAdapter extends BaseAdapter
{

	private Context mContext;
	private List<String> list;

	public AllPetPhotoItemGridAdapter()
	{
		// TODO Auto-generated constructor stub
	}

	public AllPetPhotoItemGridAdapter(Context context, List<String> list)
	{
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.list = list;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return list.size();
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
	public View getView(int position, View view, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		final Holder hold;
		if (view == null)
		{
			hold = new Holder();
			view = View.inflate(mContext,
					R.layout.list_myactivity_all_pet_photo_item, null);
			hold.myactivity_all_pet_pic_item = (ImageView) view
					.findViewById(R.id.myactivity_all_pet_pic_item);
			view.setTag(hold);
		}
		else
		{
			hold = (Holder) view.getTag();
		}

		ImageLoader.getInstance().loadImage(list.get(position),
				new ImageLoadingListener()
				{

					@Override
					public void onLoadingStarted(String imageUri, View view)
					{
						// TODO Auto-generated method stub

					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason)
					{
						// TODO Auto-generated method stub

					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage)
					{
						// TODO Auto-generated method stub
						hold.myactivity_all_pet_pic_item
								.setImageBitmap(loadedImage);
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
		ImageView myactivity_all_pet_pic_item;
	}

}
