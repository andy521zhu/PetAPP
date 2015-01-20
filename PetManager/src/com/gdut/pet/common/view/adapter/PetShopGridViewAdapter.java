package com.gdut.pet.common.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.gdut.pet.common.view.MyPetImageView;
import com.ui.mypet.R;

public class PetShopGridViewAdapter extends BaseAdapter
{

	private Context mContext;
	private String TAG = "com.gdut.pet.common.view.adapter.PetShopGridViewAdapter";
	private List<String> mList;

	public PetShopGridViewAdapter()
	{

	}

	public PetShopGridViewAdapter(Context mcContext)
	{
		this.mContext = mcContext;
	}

	public PetShopGridViewAdapter(Context context, List<String> list)
	{
		this.mContext = context;
		this.mList = list;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return mList.size();
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
			view = View
					.inflate(mContext, R.layout.activity_pet_shop_item, null);
			hold.image_shop = (MyPetImageView) view
					.findViewById(R.id.image_shop);
			hold.shop_service_food = (ImageView) view
					.findViewById(R.id.shop_service_food);
			hold.shop_service_cut = (ImageView) view
					.findViewById(R.id.shop_service_cut);
			hold.shop_service_dazhen = (ImageView) view
					.findViewById(R.id.shop_service_dazhen);
			hold.shop_service_sell = (ImageView) view
					.findViewById(R.id.shop_service_sell);
			hold.shop_service_jiyang = (ImageView) view
					.findViewById(R.id.shop_service_jiyang);
			view.setTag(hold);
		}
		else
		{
			hold = (Holder) view.getTag();
		}

		// 从网上拉取数据 判断这个商店是否会有那些特殊服务 哈哈 不要邪恶哦
		/**
		 * 这里写网络操作吧
		 */
		if (position % 2 == 0)
		{
			hold.shop_service_dazhen.setVisibility(View.GONE);
		}

		return view;
	}

	static class Holder
	{
		MyPetImageView image_shop;
		ImageView shop_service_food;
		ImageView shop_service_cut;
		ImageView shop_service_dazhen;
		ImageView shop_service_sell;
		ImageView shop_service_jiyang;
	}

}
