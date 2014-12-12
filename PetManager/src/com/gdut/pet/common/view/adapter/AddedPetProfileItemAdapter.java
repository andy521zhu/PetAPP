package com.gdut.pet.common.view.adapter;

import java.io.UnsupportedEncodingException;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdut.pet.common.info.AddedPetInfo;
import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.rockerhieu.emojicon.EmojiconTextView;
import com.ui.mypet.R;

public class AddedPetProfileItemAdapter extends BaseAdapter
{

	private Context mContext;
	private List<AddedPetInfo> list;
	private static final String TAG = "view.adapter.AddedPetProfileItemAdapter";

	public AddedPetProfileItemAdapter()
	{

	}

	public AddedPetProfileItemAdapter(Context context, List<AddedPetInfo> list)
	{
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

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View view, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		final Holder hold;
		if (view == null)
		{
			hold = new Holder();
			view = View.inflate(mContext, R.layout.list_added_pet_profile_item,
					null);
			hold.added_pet_avatar = (ImageView) view
					.findViewById(R.id.added_pet_avatar);
			hold.added_pet_name = (EmojiconTextView) view
					.findViewById(R.id.added_pet_name);
			hold.layoutadded_pet_gender_species = (LinearLayout) view
					.findViewById(R.id.layoutadded_pet_gender_species);
			hold.added_pet_gender = (ImageView) view
					.findViewById(R.id.added_pet_gender);
			hold.added_pet_species = (TextView) view
					.findViewById(R.id.added_pet_species);
			hold.added_pet_age = (TextView) view
					.findViewById(R.id.added_pet_age);
			hold.layout_added_pet_profile = (RelativeLayout) view
					.findViewById(R.id.layout_added_pet_profile1);
			view.setTag(hold);
		}
		else
		{
			hold = (Holder) view.getTag();
		}

		String petsex = list.get(position).getPetsex();

		String imageurl = list.get(position).getPetImage();
		L.i(TAG, "imageurl = " + imageurl);

		ImageLoader.getInstance().loadImage(imageurl,
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
						// 设置图片
						hold.added_pet_avatar.setImageBitmap(loadedImage);
					}

					@Override
					public void onLoadingCancelled(String imageUri, View view)
					{
						// TODO Auto-generated method stub

					}
				});

		hold.added_pet_name.setText(list.get(position).getPetname());
		// 性别图片
		if (petsex.equals("female"))
		{
			hold.layoutadded_pet_gender_species.setBackground(mContext
					.getResources().getDrawable(R.drawable.shape_pet_female));
		}
		else
		{
			hold.layoutadded_pet_gender_species.setBackground(mContext
					.getResources().getDrawable(R.drawable.shape_pet_male));
		}
		// 宠物种类
		byte[] species;
		String speciesUTF8;
		try
		{
			species = list.get(position).getPetspeciese().getBytes();
			speciesUTF8 = new String(species, "UTF-8");
			L.i(TAG, "种类 = " + speciesUTF8);
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			speciesUTF8 = "泰迪";
			e.printStackTrace();
		}

		hold.added_pet_species.setText("泰迪");
		// 年龄
		hold.added_pet_age.setText(list.get(position).getPetage() + "岁");
		hold.layout_added_pet_profile.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				toastMgr.builder.display("click", 0);
				Intent intent = new Intent();
				// 哪个用户的那个宠物 用户id 宠物id
				// 但是这里只是本用户的宠物
			}
		});

		return view;
	}

	static class Holder
	{
		ImageView added_pet_avatar;// 添加了的宠物的照片
		EmojiconTextView added_pet_name;// 添加了的宠物的名字
		LinearLayout layoutadded_pet_gender_species;// 添加了的宠物的性别的背景
		ImageView added_pet_gender;// 性别图片
		TextView added_pet_species;// 种类名字
		TextView added_pet_age;// 年龄
		RelativeLayout layout_added_pet_profile;
	}

}
