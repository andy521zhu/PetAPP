package com.gdut.pet.common.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.common.view.MyPetImageView;
import com.ui.mypet.R;

public class BBSPetFoundListViewAdapter extends BaseAdapter
{

	private Context mContext;
	private List<String> adapterList;

	public BBSPetFoundListViewAdapter()
	{

	}

	public BBSPetFoundListViewAdapter(Context mContext)
	{
		this.mContext = mContext;
	}

	public BBSPetFoundListViewAdapter(Context mContext, List<String> list)
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
			view = View.inflate(mContext, R.layout.activity_pet_found_bbs_item,
					null);
			hold.image_person = (ImageView) view
					.findViewById(R.id.image_person_bbs_detail);
			hold.image_is_vip = (ImageView) view.findViewById(R.id.image_v);
			hold.text_person_name = (TextView) view
					.findViewById(R.id.text_person_name_bbs_detail);
			hold.image_follow = (ImageView) view
					.findViewById(R.id.image_follow_bbs_detail);
			hold.text_time = (TextView) view.findViewById(R.id.text_time_bbs_detail);
			hold.text_pet_name = (TextView) view
					.findViewById(R.id.text_pet_name_bbs_detail);
			hold.image_gender = (ImageView) view
					.findViewById(R.id.image_gender_add_pet);
			hold.text_pet_species = (TextView) view
					.findViewById(R.id.text_pet_species_bbs_detail);
			hold.image_pet = (MyPetImageView) view.findViewById(R.id.image_pet_bbs_detail);
			hold.progressBar = (ProgressBar) view
					.findViewById(R.id.progressbar);
			hold.text_description = (TextView) view
					.findViewById(R.id.text_description_bbs_detail);
			hold.layout_comment = (LinearLayout) view
					.findViewById(R.id.layout_comment);
			hold.text_comment = (TextView) view.findViewById(R.id.text_comment_bbs_detail);
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
		hold.image_person.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				toastMgr.builder.display("头像点击", 0);
			}
		});
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

}
