package com.gdut.pet.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.gdut.pet.config.Configs;
import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

public class MapActivity extends Activity
{
	BMapManager mBMapMan = null;
	MapView mMapView = null;
	RadioGroup rg;
	private static StringBuilder sb;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	private long exitTime = 0;
	double lat, lon;
	ConnectivityManager conn;
	EditText et;
	MKSearch mMKSearch = null;
	private TextView firstText = null;
	MyLocationOverlay myLocationOverlay = null;
	LocationData locData = new LocationData();
	public Object mToast;
	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init(Configs.BAIDU_MAP_KEY, null);
		mContext = this;
		setContentView(R.layout.activity_nearby_map);
		Initialatize();

		myLocationOverlay = new MyLocationOverlay(mMapView);
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.setLocationMode(LocationMode.FOLLOWING);
		myLocationOverlay.setData(locData);

	}

	// 初始化
	public void Initialatize()
	{
		mLocationClient = new LocationClient(MapActivity.this); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		mLocationClient.setAK(Configs.BAIDU_MAP_KEY);
		LocationSetting();

		mLocationClient.start();
		if (mLocationClient != null && mLocationClient.isStarted())
		{
			conn = (ConnectivityManager) getSystemService(MapActivity.CONNECTIVITY_SERVICE);
			NetworkInfo info = conn.getActiveNetworkInfo();
			if (info != null && info.isAvailable())
			{
				mLocationClient.requestLocation();
			}
			else
			{
				mLocationClient.requestOfflineLocation();
			}
		}
		else
			Log.d("LocSDK3", "locClient is null or not started");
		mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true);// 设置启用内置的缩放控件

		OnTouchListener listener = new OnTouchListener()
		{
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1)
			{
				mMapView.setBuiltInZoomControls(true);
				int x = (int) arg1.getX();
				int y = (int) arg1.getY();

				GeoPoint currentPt = mMapView.getProjection().fromPixels(x, y);
				double a = (currentPt.getLatitudeE6()) / 1E6;
				double b = (currentPt.getLongitudeE6()) / 1E6;
				firstText = (TextView) findViewById(R.id.firstText);
				firstText.setText("当前经度: " + b + "当前纬度：" + a);

				return false;
			}

		};

		mMapView.setOnTouchListener(listener);

		rg = (RadioGroup) findViewById(R.id.rg);
		rg.check(R.id.rb1);
		// 视图切换
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				// TODO Auto-generated method stub
				if (checkedId == R.id.rb2)
				{
					mMapView.setSatellite(true);
				}
				else
				{
					mMapView.setSatellite(false);
				}
			}
		});

		et = (EditText) findViewById(R.id.et);

	}

	// 标记当前位置
	public void mark()
	{
		MyLocationOverlay myLocationOverlay = new MyLocationOverlay(mMapView);
		LocationData locData = new LocationData();
		locData.latitude = lat;
		locData.longitude = lon;
		myLocationOverlay.setData(locData);
		mMapView.getOverlays().add(myLocationOverlay);
		mMapView.refresh();
		mMapView.getController().animateTo(
				new GeoPoint((int) (locData.latitude * 1e6),
						(int) (locData.longitude * 1e6)));

	}

	// 位置监听器
	public class MyLocationListener implements BDLocationListener
	{
		@Override
		public void onReceiveLocation(BDLocation location)
		{
			if (location == null)
				return;
			// 获取定位的经纬度
			lat = location.getLatitude();
			lon = location.getLongitude();
			Toast.makeText(MapActivity.this, "当前经度：" + lon + "当前纬度" + lat,
					Toast.LENGTH_LONG).show();
			final double aa = lat - 0.02;
			final double ao = lon - 0.02;
			final double ba = lat + 0.02;
			final double bo = lon + 0.02;

			final GeoPoint mGeoPoint = new GeoPoint(
					(int) (location.getLatitude() * 1e6),
					(int) (location.getLongitude() * 1e6));
			// 获取定位的街道
			String str = location.getCity() + location.getDistrict()
					+ location.getStreet() + location.getStreetNumber();
			Toast.makeText(MapActivity.this, "当前位置：" + str, Toast.LENGTH_LONG)
					.show();
			MapController mMapController = mMapView.getController();
			// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
			mMapController.setCenter(new GeoPoint((int) (lat * 1E6),
					(int) (lon * 1E6)));// 设置地图中心点

			mMapController.setZoom(18);// 设置地图zoom级别
			mark();
			mMKSearch = new MKSearch();
			mMKSearch.init(mBMapMan, new MySearchListener());// 注意，MKSearchListener只支持一个，以最后一次设置为准
			findViewById(R.id.btn).setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					sb = new StringBuilder();

					// mMKSearch.poiSearchInCity("广州", et.getText().toString());

					GeoPoint ptLB = new GeoPoint((int) (aa * 1E6),
							(int) (ao * 1E6));

					GeoPoint ptRT = new GeoPoint((int) (ba * 1E6),
							(int) (bo * 1E6));
					mMKSearch.poiSearchInbounds(et.getText().toString(), ptLB,
							ptRT);

				}
			});

		}

		@Override
		public void onReceivePoi(BDLocation poiLocation)
		{
			if (poiLocation == null)
			{
				return;
			}
			// 获取定位的经纬度
			lat = poiLocation.getLatitude();
			lon = poiLocation.getLongitude();
			// 获取定位的街道
			String str = poiLocation.getCity() + poiLocation.getDistrict()
					+ poiLocation.getStreet() + poiLocation.getStreetNumber();
		}
	}

	// 设置定位参数
	public void LocationSetting()
	{
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(50000);// 设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		option.setPoiNumber(5); // 最多返回POI个数
		option.setPoiDistance(1000); // poi查询距离
		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
		mLocationClient.setLocOption(option);
	}

	// 搜索监听器
	public class MySearchListener implements MKSearchListener
	{
		@Override
		public void onGetAddrResult(MKAddrInfo result, int iError)
		{
			// 返回地址信息搜索结果
		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult result,
				int iError)
		{
			// 返回驾乘路线搜索结果
		}

		@Override
		public void onGetPoiResult(MKPoiResult result, int type, int error)
		{
			// 返回poi搜索结果
			if (error == MKEvent.ERROR_RESULT_NOT_FOUND)
			{
				Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG)
						.show();
				return;
			}
			else if (error != 0 || result == null)
			{
				Toast.makeText(MapActivity.this, "搜索出错啦..", Toast.LENGTH_LONG)
						.show();
				return;
			}

			// 将poi结果显示到地图上
			PoiOverlay poiOverlay = new PoiOverlay(MapActivity.this, mMapView);
			poiOverlay.setData(result.getAllPoi());
			mMapView.getOverlays().clear();
			mMapView.getOverlays().add(poiOverlay);
			mMapView.refresh();
			if (result.getPageIndex() == 0)
			{
				sb.append("共搜索到").append(result.getNumPois()).append("个POI\n");
			}
			// MKPoiInfo poiInfo = result.getPoi(0);
			for (MKPoiInfo info : result.getAllPoi())
			{
				sb.append("名称：").append(info.name).append("\n");
				sb.append("地址：").append(info.address).append("\n");
				// if (info.pt != null) {

				// mMapView.getController().animateTo(poiInfo.pt);
				// OverlayItem overlayItem = new OverlayItem(info.pt, "我的宠物店",
				// "宠物店");
				// break;
				// }
			}

			// 通过AlertDialog显示当前页搜索到的POI
			MKPoiInfo poiInfo = result.getPoi(0);
			mMapView.getController().setCenter(poiInfo.pt);
			new AlertDialog.Builder(MapActivity.this)
					.setTitle("搜索到的POI信息")
					.setMessage(sb.toString())
					.setPositiveButton("关闭",
							new DialogInterface.OnClickListener()
							{
								public void onClick(DialogInterface dialog,
										int whichButton)
								{
									dialog.dismiss();
								}
							}).create().show();
		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult result,
				int iError)
		{
			// 返回公交搜索结果
		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult result,
				int iError)
		{
			// 返回步行路线搜索结果
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult result, int iError)
		{
			// 返回公交车详情信息搜索结果
		}

		public void onGetSuggestionResult(MKSuggestionResult result, int iError)
		{
			// 返回联想词信息搜索结果
		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult result, int type,
				int error)
		{
			// 在此处理短串请求返回结果.
		}

		/* 
		 * 
		 */
		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1)
		{
			// TODO Auto-generated method stub

		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN)
		{
			if ((System.currentTimeMillis() - exitTime) > 2000)
			{
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			}
			else
			{
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy()
	{
		mMapView.destroy();
		if (mBMapMan != null)
		{
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause()
	{
		mMapView.onPause();
		if (mBMapMan != null)
		{
			mBMapMan.stop();
		}
		MobclickAgent.onPause(mContext);
		super.onPause();
	}

	@Override
	protected void onResume()
	{
		mMapView.onResume();
		if (mBMapMan != null)
		{
			mBMapMan.start();
		}
		MobclickAgent.onResume(mContext);
		super.onResume();
	}

}
