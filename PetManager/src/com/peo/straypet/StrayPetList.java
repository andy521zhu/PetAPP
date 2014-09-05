/**
 * 丢失宠物的列表   
 */
/**
 * @author Administrator
 *
 */
package com.peo.straypet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ui.mypet.R;

public class StrayPetList extends ListActivity
{
	ListView lv;
	List<Map<String, Object>> list;
	SimpleAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		list = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(
				this,
				getData(MyListData.getStrayPetData()),
				R.layout.tray_pet_listview,
				new String[]
				{ "masterImage", "title", "descript", "time", "pic0", "pic1",
						"pic2", "pic3", "pic4", "pic5", "pic6", "pic7", "pic8" },
				new int[]
				{ R.id.trayMasterPic, R.id.trayPetTitle, R.id.trayPetDescript,
						R.id.trayPetTime, R.id.trayPetPic_0, R.id.trayPetPic_1,
						R.id.trayPetPic_2, R.id.trayPetPic_3,
						R.id.trayPetPic_4, R.id.trayPetPic_5,
						R.id.trayPetPic_6, R.id.trayPetPic_7, R.id.trayPetPic_8 });
		lv = this.getListView();
		setListAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(StrayPetList.this, ItemDetail.class);
				Bundle bundle = new Bundle();
				bundle.putInt("listId", arg2 - 1);
				intent.putExtras(bundle);
				startActivity(intent);
			}

		});
	}

	// 获取list数据
	private List<Map<String, Object>> getData(ListData[] mydata)
	{

		Map<String, Object> map;
		for (int i = 0; i < mydata.length; i++)
		{
			map = new HashMap<String, Object>();
			map.put("masterImage", R.drawable.ic_launcher); // 要改
			map.put("title", mydata[i].title);
			map.put("descript", mydata[i].descript);
			map.put("time", mydata[i].time);
			for (int j = 0; j < mydata[i].dogPicNum; j++)
			{
				map.put("pic" + j, R.drawable.ic_launcher); // mydata[i].lostDogPic
			}
			list.add(map);
		}
		return list;
	}

}

class MyListData
{

	static ListData[] mydata;

	// 从网络中获得数据
	public static ListData[] getStrayPetData()
	{
		// 这里使用虚假数据
		mydata = new ListData[2];
		mydata[0] = new ListData();
		mydata[0].title = "广州某地一流浪犬";
		mydata[0].descript = "heheheh";
		mydata[0].time = "2小时前";
		mydata[0].master.name = "me";
		mydata[0].dogPicNum = 8;

		mydata[1] = new ListData();
		mydata[1].title = "北京某地一流浪犬";
		mydata[1].time = "2014.7.7";
		mydata[1].descript = "hahahah";
		mydata[1].master.name = "she";
		// mydata[1].lostDogPic = new Images();
		mydata[1].dogPicNum = 5;
		return mydata;

	}
}