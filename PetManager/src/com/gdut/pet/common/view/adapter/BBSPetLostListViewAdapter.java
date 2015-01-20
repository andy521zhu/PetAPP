package com.gdut.pet.common.view.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.gdut.pet.ui.MyActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.ui.mypet.R;

public class BBSPetLostListViewAdapter extends BaseAdapter
{
	private String TAG = "com.gdut.pet.common.view.adapter.BBSPetLostListViewAdapter";
	private Context mContext;
	private List<BBSCommentsInfo> adapterList;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;
	String userimageString;
	String petimageString;

	public BBSPetLostListViewAdapter()
	{

	}

	public BBSPetLostListViewAdapter(Context mContext)
	{
		this.mContext = mContext;
	}

	public BBSPetLostListViewAdapter(Context mContext,
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
	public View getView(int position, View view, ViewGroup parent)
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
			hold.image_share = (ImageView) view.findViewById(R.id.image_share);
			view.setTag(hold);
		}
		else
		{
			hold = (Holder) view.getTag();
		}
		// 用户头像点击 转到用户的界面
		hold.image_person.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				toastMgr.builder.display("头像点击", 0);
				Intent intent = new Intent();
				L.i("BBSLOST", "intent");
				// 这里不应该是叫用户界面 而是另一个名字
				intent.setClass(mContext.getApplicationContext(),
						MyActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.getApplicationContext().startActivity(intent);
			}
		});

		hold.text_description.setText("宠物丢失了  好桑心啊");
		// 关注点击
		hold.image_follow.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				// 这里判断是否关注
				// 从网络里面判断
				hold.image_follow.setImageResource(R.drawable.followed);
			}
		});

		L.i(TAG, "the postion" + position);
		userimageString = adapterList.get(position).getUserImageUrl();
		L.i(TAG, "the id" + adapterList.get(position).getId());
		petimageString = adapterList.get(position).getPetImageUrl();

		L.i(TAG, "the userimageString" + userimageString);
		L.i(TAG, "the petimageString" + petimageString);

		getImageFromUrl(userimageString, petimageString, hold);

		return view;
	}

	/**
	 * Holder
	 */
	static class Holder
	{
		ImageView image_person;// 用户头像
		ImageView image_is_vip;// 用户是否是vip 是的话 就显示
		TextView text_person_name;// 用户名字
		ImageView image_follow;// 是否关注
		TextView text_time;// 上传 发表时间
		TextView text_pet_name;// 宠物名字
		ImageView image_gender;// 宠物性别
		TextView text_pet_species;// 宠物种类
		MyPetImageView image_pet;// 帖子里面宠物的图片 大图呀
		ProgressBar progressBar;// 图片的加载进度条
		TextView text_description;// 图片底下的一段描述 废话

		LinearLayout layout_comment;// 点击 就显示评论 我想 就进入另一个界面吧
		TextView text_comment;// 评论个数
		LinearLayout layout_like;// 喜欢 赞
		TextView text_like_count;// 赞的个数

		ImageView image_share;// 分享
	}

	// 异步加载网络图片
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
