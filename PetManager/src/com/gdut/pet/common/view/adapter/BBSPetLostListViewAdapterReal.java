package com.gdut.pet.common.view.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gdut.pet.common.info.BBSCommentsInfo;
import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.common.view.MyPetImageView;
import com.gdut.pet.ui.BBSDetailActivityNew;
import com.gdut.pet.ui.MyActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.ui.mypet.R;

public class BBSPetLostListViewAdapterReal extends BaseAdapter
{
	private String TAG = "com.gdut.pet.common.view.adapter.BBSPetLostListViewAdapterReal";
	private Context mContext;
	private List<BBSCommentsInfo> adapterList;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;

	public BBSPetLostListViewAdapterReal()
	{

	}

	public BBSPetLostListViewAdapterReal(Context mContext)
	{
		this.mContext = mContext;
	}

	public BBSPetLostListViewAdapterReal(Context mContext,
			List<BBSCommentsInfo> list)
	{
		this.mContext = mContext;
		this.adapterList = list;
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
	public View getView(final int position, View view, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		final Holder hold;
		if (view == null)
		{
			hold = new Holder();
			view = View.inflate(mContext, R.layout.activity_pet_lost_bbs_item,
					null);
			hold.image_person = (ImageView) view
					.findViewById(R.id.image_person_bbs_detail);
			hold.image_is_vip = (ImageView) view.findViewById(R.id.image_v);
			hold.text_person_name = (TextView) view
					.findViewById(R.id.text_person_name_bbs_detail);
			hold.image_follow = (ImageView) view
					.findViewById(R.id.image_follow_bbs_detail);
			hold.text_time = (TextView) view
					.findViewById(R.id.text_time_bbs_detail);
			hold.text_pet_name = (TextView) view
					.findViewById(R.id.text_pet_name_bbs_detail);
			hold.image_gender = (ImageView) view
					.findViewById(R.id.image_gender_add_pet);
			hold.text_pet_species = (TextView) view
					.findViewById(R.id.text_pet_species_bbs_detail);
			hold.image_pet = (MyPetImageView) view
					.findViewById(R.id.image_pet_bbs_detail);
			hold.progressBar = (ProgressBar) view
					.findViewById(R.id.progressbar);
			hold.text_description = (TextView) view
					.findViewById(R.id.text_description_bbs_detail);
			hold.layout_comment = (LinearLayout) view
					.findViewById(R.id.layout_comment);
			hold.text_comment = (TextView) view
					.findViewById(R.id.text_comment_bbs_detail);
			hold.layout_like = (LinearLayout) view
					.findViewById(R.id.layout_like);
			hold.text_like_count = (TextView) view
					.findViewById(R.id.text_like_count);
			hold.text_is_pet_lost_bbs_detail = (TextView) view
					.findViewById(R.id.text_is_pet_lost_bbs_detail);
			hold.image_is_pet_lost_bbs_detail = (ImageView) view
					.findViewById(R.id.image_is_pet_lost_bbs_detail);
			hold.image_share = (ImageView) view.findViewById(R.id.image_share);
			view.setTag(hold);
		}
		else
		{
			hold = (Holder) view.getTag();
		}
		// �û�ͷ���� ת���û��Ľ���
		hold.image_person.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// ���ͷ�� �õ������û���Ϣ
				// TODO Auto-generated method stub
				toastMgr.builder.display("ͷ����", 0);
				Intent intent = new Intent();
				L.i("BBSLOST", "intent");
				// ���ﲻӦ���ǽ��û����� ������һ������
				intent.setClass(mContext.getApplicationContext(),
						MyActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.getApplicationContext().startActivity(intent);
			}
		});

		hold.text_description.setText(adapterList.get(position)
				.getItemContent());
		// ��ע���
		hold.image_follow.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				// �����ж��Ƿ��ע
				// �����������ж�
				hold.image_follow.setImageResource(R.drawable.followed);
			}
		});

		//
		String userImageURL = adapterList.get(position).getUserImageUrl();
		String petImageURL = adapterList.get(position).getPetImageUrl();
		getImageFromUrl(userImageURL, petImageURL, hold);
		hold.text_comment.setText(adapterList.get(position).getCommentNum());
		hold.text_like_count.setText(adapterList.get(position).getLikeNum());
		hold.layout_like.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				String newNum = hold.text_like_count.getText().toString() + 1;
				hold.text_like_count.setText(newNum);
				// �����������
			}
		});
		hold.text_time.setText(adapterList.get(position).getTime());
		// ������� ������ϸ���� Ȼ��ſ�������
		hold.layout_comment.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View paramView)
			{
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();

				bundle.putString("id", adapterList.get(position).getId());
				Intent intent = new Intent();
				intent.setClass(mContext, BBSDetailActivityNew.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			}
		});

		// ��ʧ�ҵ�
		String isPetLost = adapterList.get(position).getIsLost();
		if (isPetLost.equals("2"))
		{
			hold.text_is_pet_lost_bbs_detail.setVisibility(View.VISIBLE);
			hold.text_is_pet_lost_bbs_detail.setText("����\n��ʧ");
			hold.image_is_pet_lost_bbs_detail.setVisibility(View.VISIBLE);
			hold.image_is_pet_lost_bbs_detail.setImageDrawable(mContext
					.getResources().getDrawable(R.drawable.pet_lost_emoji));
		}
		else if (isPetLost.equals("3"))
		{
			hold.text_is_pet_lost_bbs_detail.setVisibility(View.VISIBLE);
			hold.text_is_pet_lost_bbs_detail.setText("����\n�ҵ�");
			hold.image_is_pet_lost_bbs_detail.setVisibility(View.VISIBLE);
			hold.image_is_pet_lost_bbs_detail.setImageDrawable(mContext
					.getResources().getDrawable(R.drawable.pet_found_emoji));
		}

		return view;
	}

	/**
	 * Holder
	 */
	static class Holder
	{
		ImageView image_person;// �û�ͷ��
		ImageView image_is_vip;// �û��Ƿ���vip �ǵĻ� ����ʾ
		TextView text_person_name;// �û�����
		ImageView image_follow;// �Ƿ��ע
		TextView text_time;// �ϴ� ����ʱ��
		TextView text_pet_name;// ��������
		ImageView image_gender;// �����Ա�
		TextView text_pet_species;// ��������
		MyPetImageView image_pet;// ������������ͼƬ ��ͼѽ
		ProgressBar progressBar;// ͼƬ�ļ��ؽ�����
		TextView text_description;// ͼƬ���µ�һ������ �ϻ�

		LinearLayout layout_comment;// ��� ����ʾ���� ���� �ͽ�����һ�������
		TextView text_comment;// ���۸���
		LinearLayout layout_like;// ϲ�� ��
		TextView text_like_count;// �޵ĸ���
		TextView text_is_pet_lost_bbs_detail;// ��ʶ �Ƿ�ʧ
		ImageView image_is_pet_lost_bbs_detail;// ��ʶ�Ƿ�ʧ��ͼƬ

		ImageView image_share;// ����
	}

	// �첽��������ͼƬ
	@SuppressWarnings("unused")
	private void getImageFromUrl(String userurl, String peturl,
			final Holder _hold)
	{
		this.imageLoader.loadImage(userurl, new ImageLoadingListener()
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
				_hold.image_person.setImageBitmap(loadedImage);
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view)
			{
				// TODO Auto-generated method stub

			}
		});
		this.imageLoader.loadImage(peturl, new ImageLoadingListener()
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
				_hold.image_pet.setImageBitmap(loadedImage);
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view)
			{
				// TODO Auto-generated method stub

			}
		});
	}

}
