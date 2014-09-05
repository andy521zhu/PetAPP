/**
 * 获得GPS坐标
 */
package com.peo.location;

import com.peo.ceneral.LogInfo;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GPSLocation {
	//位置更新监听器
	private static LocationListener myLocationListener;
	private static LocationManager loacationManager;
	private static Location location;
//	static List list;static int i;
	
	public static void getLocal(Context context) {
		// TODO Auto-generated method stub
		
		myLocationListener = new LocationListener() {

			@Override
			public void onProviderEnabled(String provider) {
				
			}

			@Override
			public void onProviderDisabled(String provider) {
				
				updateView(null);
			}

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				
				updateView(location);
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
		};
		
		loacationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		loacationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,0,myLocationListener);
		location=loacationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//		for(i=0;i<list.size();i++){
//			System.out.println(list.get(i));
//		}
	}
	
	public static void removeLocation(){
		if(myLocationListener != null){
			loacationManager.removeUpdates(myLocationListener);// 移除
		}
	}

	// 更新位置信息
	private static void updateView(Location location) {
		if (location == null) {
			System.out.println("null");
			return;
		}
		LogInfo.LATITUDE = location.getLatitude();
		LogInfo.LONGITITUDE = location.getLongitude();
		System.out.println("LOGinfo:" + LogInfo.LATITUDE + " " + LogInfo.LONGITITUDE);
	}
		
}
