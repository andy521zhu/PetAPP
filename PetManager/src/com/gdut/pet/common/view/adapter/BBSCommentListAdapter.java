package com.gdut.pet.common.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gdut.pet.common.info.BBSCommentsInfo;
import com.rockerhieu.emojicon.EmojiconTextView;
import com.ui.mypet.R;

public class BBSCommentListAdapter extends BaseAdapter
{

	private Context mContext;
	private List<BBSCommentsInfo> list;

	public BBSCommentListAdapter(Context mContext, List<BBSCommentsInfo> list)
	{
		this.mContext = mContext;
		this.list = list;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		if (list == null)
		{
			return 0;
		}
		else
		{

			return list.size();
		}
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return list.get(position);
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
		Holder hold;
		if (view == null)
		{
			hold = new Holder();
			view = View.inflate(mContext, R.layout.bbs_comment_list_item, null);
			hold.userName = (EmojiconTextView) view.findViewById(R.id.Detail_Item_UserName);
			hold.ItemID = (TextView) view.findViewById(R.id.Detail_Item_Num);
			hold.ItemContent = (EmojiconTextView) view.findViewById(R.id.Detail_Item_Value);
			view.setTag(hold);
		}
		else
		{
			hold = (Holder) view.getTag();
		}

		// 填充内容 评论者的用户名
		hold.userName.setText(list.get(position).getUsernameComment());
		// 楼层数
		hold.ItemID.setText(position + 1 + "");
		// 得到评论的内容
		hold.ItemContent.setText(list.get(position).getText());

		hold.userName.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				// ShowToast.ShowToast1(mContext, "人物点击");
				// toastMgr.builder.display("人物点击", 0);
			}
		});

		return view;
	}

	static class Holder
	{
		EmojiconTextView userName;
		TextView ItemID;
		EmojiconTextView ItemContent;
	}

}
