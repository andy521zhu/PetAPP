package com.gdut.pet.common.view.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		View view = View.inflate(mContext, R.layout.tray_pet_listview, null);
		TextView trayPetTitle = (TextView) view.findViewById(R.id.trayPetTitle);
		TextView trayPetTime = (TextView) view.findViewById(R.id.trayPetTime);
		TextView trayPetDescript = (TextView) view
				.findViewById(R.id.trayPetDescript);

		trayPetTitle.setText(adapterList.get(position).get("title"));
		trayPetDescript.setText(adapterList.get(position).get("description"));
		trayPetTime.setText(adapterList.get(position).get("time"));

		return view;
	}
}
