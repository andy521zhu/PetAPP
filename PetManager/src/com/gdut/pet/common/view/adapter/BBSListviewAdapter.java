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
			// �û�ͷ��
			hold.item_UserHead = (ImageView) view
					.findViewById(R.id.Item_UserHead);
			// �û���
			hold.username = (TextView) view.findViewById(R.id.Item_UserName);
			// ���ӱ���
			hold.title = (TextView) view.findViewById(R.id.Item_MainTitle);
			// ��������
			hold.contentTextDescription = (TextView) view
					.findViewById(R.id.Item_MainText);
			// ����ͼƬ
			hold.contentPic = (ImageView) view.findViewById(R.id.Item_MainImg);
			// �ظ���
			hold.reply_num = (TextView) view.findViewById(R.id.Item_PeplyNum);
			view.setTag(hold);
		}
		else
		{
			hold = (Holder) view.getTag();
		}

		// �û�ͷ������
		// item_UserHead.setBackground(null);
		// �û�������
		hold.username.setText(adapterList.get(position).get("username"));
		// ���ӱ�������
		hold.title.setText(adapterList.get(position).get("title"));
		// ������������
		hold.contentTextDescription.setText(adapterList.get(position).get(
				"description"));
		// ��������ͼƬ����
		// contentPic.setBackground(background);
		// ���ӻظ�������
		hold.reply_num.setText("�ظ� : "
				+ adapterList.get(position).get("replynum"));

		// �û�ͷ���� ��ʾ��ͼ
		hold.item_UserHead.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				// ShowToast.ShowToast1(mContext, "�����ʾ��ͼ�Ĺ���");
				toastMgr.builder.display("�����ʾ��ͼ�Ĺ���", Toast.LENGTH_SHORT);
			}
		});
		// �������������ͼƬ�����
		hold.contentPic.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				// ShowToast.ShowToast1(mContext, "�����ʾ��ͼ�Ĺ���");
				toastMgr.builder.display("�����ʾ��ͼ�Ĺ���", Toast.LENGTH_SHORT);
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
