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
import android.widget.TextView;
import android.widget.Toast;

import com.gdut.pet.common.tools.BitmapByteConverter;
import com.gdut.pet.common.utils.BitmapSerializable;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.ui.ImageShowerActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.ui.mypet.R;

public class BBSListviewAdapter extends BaseAdapter
{
	private Context mContext;
	private List<Map<String, String>> adapterList;

	public BBSListviewAdapter(Context mContext, List<Map<String, String>> list)
	{
		this.mContext = mContext;
		this.adapterList = list;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return adapterList.size();
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
			view = View.inflate(mContext, R.layout.bbslistview, null);
			// 用户头像
			hold.item_UserHead = (ImageView) view
					.findViewById(R.id.Item_UserHead);
			// 用户名
			hold.username = (TextView) view.findViewById(R.id.Item_UserName);
			// 帖子标题
			hold.title = (TextView) view.findViewById(R.id.Item_MainTitle);
			// 帖子内容
			hold.contentTextDescription = (TextView) view
					.findViewById(R.id.Item_MainText);
			// 内容图片
			hold.contentPic = (ImageView) view.findViewById(R.id.Item_MainImg);
			// 回复数
			hold.reply_num = (TextView) view.findViewById(R.id.Item_PeplyNum);
			view.setTag(hold);
		}
		else
		{
			hold = (Holder) view.getTag();
		}

		// 用户头像设置
		// item_UserHead.setBackground(null);
		// 用户名设置
		hold.username.setText(adapterList.get(position).get("username"));
		// 帖子标题设置
		hold.title.setText(adapterList.get(position).get("title"));
		// 帖子内容设置
		hold.contentTextDescription.setText(adapterList.get(position).get(
				"description"));
		// 帖子内容图片设置
		// contentPic.setBackground(background);
		// 帖子回复数设置
		hold.reply_num.setText("回复 : "
				+ adapterList.get(position).get("replynum"));

		// 用户头像点击 显示大图
		hold.item_UserHead.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				// ShowToast.ShowToast1(mContext, "添加显示大图的功能");
				toastMgr.builder.display("添加显示大图的功能", Toast.LENGTH_SHORT);
			}
		});
		// 帖子内容里面的图片被点击
		hold.contentPic.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				// ShowToast.ShowToast1(mContext, "添加显示大图的功能");
				toastMgr.builder.display("添加显示大图的功能", Toast.LENGTH_SHORT);
				hold.contentPic.setDrawingCacheEnabled(true);
				Bitmap bitmap = hold.contentPic.getDrawingCache();
				Bitmap bitmap2 = Bitmap.createBitmap(bitmap);
				hold.contentPic.setDrawingCacheEnabled(false);
				BitmapSerializable bs = new BitmapSerializable();
				bs.setBitmap(BitmapByteConverter.getBytes(bitmap2));
				Bundle bundle = new Bundle();
				bundle.putSerializable("bitmap", bs);
				Intent intent = new Intent();
				intent.setClass(mContext, ImageShowerActivity.class);
				intent.putExtras(bundle);
				// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
			}
		});
		String url = "http://img1.imgtn.bdimg.com/it/u=2629294794,212791471&fm=23&gp=0.jpg";
		ImageLoader.getInstance().loadImage(url, new ImageLoadingListener()
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
				hold.contentPic.setImageBitmap(loadedImage);
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
		ImageView item_UserHead;
		TextView username;
		TextView title;
		TextView contentTextDescription;
		ImageView contentPic;
		TextView reply_num;
	}
}
