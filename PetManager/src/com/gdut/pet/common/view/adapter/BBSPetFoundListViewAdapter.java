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
				toastMgr.builder.display("ͷ����", 0);
			}
		});
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

		ImageView image_share;// ����
	}

}
