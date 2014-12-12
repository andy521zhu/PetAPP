package com.gdut.pet.common.view.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.view.MyPetImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.ui.mypet.R;

public class MyActivityAllPetPhotoGridViewAdapter extends BaseAdapter
{

	private Context mContext;
	private List<String> list;
	DisplayImageOptions options;
	ImageLoader imageLoader;

	public MyActivityAllPetPhotoGridViewAdapter()
	{
		// TODO Auto-generated constructor stub
	}

	public MyActivityAllPetPhotoGridViewAdapter(Context mcContext,
			List<String> list)
	{
		this.list = list;
		this.mContext = mcContext;
		options = new DisplayImageOptions.Builder()
				.showImageOnFail(R.drawable.camera)
				.resetViewBeforeLoading(false).delayBeforeLoading(1000)
				.cacheInMemory(false).cacheOnDisc(false)
				.considerExifParams(false)
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.displayer(new SimpleBitmapDisplayer()).handler(new Handler())
				.build();
		this.imageLoader = ImageLoader.getInstance();
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
			hold.myactivity_all_pet_pic_item = (MyPetImageView) view
					.findViewById(R.id.myactivity_all_pet_pic_item);
			view.setTag(hold);
		}
		else
		{
			hold = (Holder) view.getTag();
		}

		final String urlString = list.get(position);

		ImageLoader.getInstance().loadImage(urlString,
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
						L.i("----------", "onLoadingComplete");
						L.i("MyActivityAllPetPhotoGridViewAdapter", urlString);
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
		MyPetImageView myactivity_all_pet_pic_item;
	}

}
