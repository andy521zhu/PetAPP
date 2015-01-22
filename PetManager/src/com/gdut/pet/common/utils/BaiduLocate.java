package com.gdut.pet.common.utils;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.gdut.pet.common.info.LocatedInfo;

public class BaiduLocate
{
	private Context mContext;
	private LocationClient locationClient = null;
	private static final int UPDATE_TIME = 5000;
	private static int LOCATION_COUTNS = 0;
	private LocatedInfo info;
	public BaiduLocate()
	{

	}

	public BaiduLocate(Context mContext)
	{
		this.mContext = mContext;
	}

	public void registerLocationListener()
	{
		locationClient = new LocationClient(mContext);
		info = new LocatedInfo();
		// 设置定位条件
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setCoorType("bd0911");
		option.setPriority(LocationClientOption.NetWorkFirst);
		option.setScanSpan(UPDATE_TIME);
		locationClient.setLocOption(option);

		// 注册位置坚挺
		locationClient.registerLocationListener(new BDLocationListener()
		{

			@Override
			public void onReceivePoi(BDLocation location)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onReceiveLocation(BDLocation location)
			{
				// TODO Auto-generated method stub
				if (location == null)
				{
					return;
				}
				info.time = location.getTime();
				info.errorCode = location.getLocType();
				info.radius = location.getRadius();
				info.locationType = location.getLocType();
				info.lontitute = location.getLongitude();
				info.latitute = location.getLatitude();
				if (location.getLocType() == BDLocation.TypeGpsLocation)
				{
					info.sateliteNumber = location.getSatelliteNumber();
					info.speed = location.getSpeed();
				}
				else if (location.getLocType() == BDLocation.TypeNetWorkLocation)
				{
					info.address = location.getAddrStr();
				}
			}
		});

	}

	public LocatedInfo getLocation()
	{
		if (locationClient == null)
		{
			return null;
		}
		if (locationClient.isStarted())
		{
			locationClient.stop();
		}
		else
		{
			locationClient.start();
		}
		return info;
	}

	public void stopLocation()
	{
		if (locationClient != null && locationClient.isStarted())
		{
			locationClient.stop();
			locationClient = null;
		}
	}

}
