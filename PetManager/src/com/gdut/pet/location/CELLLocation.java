package com.gdut.pet.location;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;


public class CELLLocation {

	static SCell cell;
	
	//基站方式获取位置信息,该方法即为外部调用的接口
    @SuppressWarnings("unused")
	public static void getLocationByCell(Context context){
    	
    	try{
    		cell = getCellInfo(context);
    		SItude itude = getITude(cell);
    		String location = getLocation(itude);
    		showResult(cell, location);
    	}catch(Exception e){
    		
    	}

    }
    
    
    private static SCell getCellInfo(Context context) throws Exception{
    	SCell sCell = new SCell();
    	TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    	GsmCellLocation location = (GsmCellLocation)mTelephonyManager.getCellLocation();
    	if(location == null){
    		throw new Exception("Get Cell Failed");
    	}
    	
    	
		String operator = mTelephonyManager.getNetworkOperator();
		int mcc = Integer.parseInt(operator.substring(0, 3));
		int mnc = Integer.parseInt(operator.substring(3));
		int cid = location.getCid();
		int lac = location.getLac();

		sCell.setMCC(mcc);
		sCell.setMNC(mnc);
		sCell.setCID(cid);
		sCell.setLAC(lac);
    	return sCell;
    }
    
    
    public static SItude getITude(SCell cell) throws Exception{
    	SItude itude = new SItude();
    	HttpClient client = new DefaultHttpClient();
    	
    	HttpPost post = new HttpPost("http://www.google.com/loc/json");
    	try 
		{
			JSONObject holder = new JSONObject();
			holder.put("version", "1.1.0");
			holder.put("host", "maps.google.com");
			holder.put("address_language", "zh_CN");
			holder.put("request_address", true);
			holder.put("radio_type", "gsm");
			holder.put("carrier", "HTC");

			JSONObject tower = new JSONObject();
			tower.put("mobile_country_code", cell.getMCC());
			tower.put("mobile_network_code", cell.getMNC());
			tower.put("cell_id", cell.getCID());
			tower.put("location_area_code", cell.getLAC());
			JSONArray towerarray = new JSONArray();
			towerarray.put(tower);
			holder.put("cell_towers", towerarray);

			StringEntity query = new StringEntity(holder.toString());
			post.setEntity(query);
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			BufferedReader buffReader = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer strBuff = new StringBuffer();
			String result = null;
			while ((result = buffReader.readLine()) != null) 
			{
				strBuff.append(result);
			}

			JSONObject json = new JSONObject(strBuff.toString());
			JSONObject subjson = new JSONObject(json.getString("location"));
			itude.setLatitude(subjson.getString("latitude"));
			itude.setLongitude(subjson.getString("longitude"));
			Log.i("Itude", itude.getLatitude() + itude.getLongitude());
		} 
		catch (Exception e) 
		{
			Log.e(e.getMessage(), e.toString());
			throw new Exception("Failed to get latitude/longitude:"+ e.getMessage());
		} 
		finally 
		{
			post.abort();
			client = null;
		}
		return itude;
    }
    
    /**
     * 获取位置信息
     * @param itude
     * @return
     * @throws Exception
     */
    private static String getLocation(SItude itude) throws Exception{
    	String resultString = "";
		String urlString = String.format("http://maps.google.cn/maps/geo?key=abcdefg&q=%s,%s",
				itude.getLatitude(), itude.getLongitude());
		Log.i("URL", urlString);

		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(urlString);
		try 
		{

			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			BufferedReader buffReader = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer strBuff = new StringBuffer();
			String result = null;
			while ((result = buffReader.readLine()) != null) 
			{
				strBuff.append(result);
			}
			resultString = strBuff.toString();

			if (resultString != null && resultString.length() > 0) 
			{
				JSONObject jsonObject = new JSONObject(resultString);
				JSONArray jsonArray = new JSONArray(jsonObject.get("Placemark").toString());
				resultString = "";
				for (int i = 0; i < jsonArray.length(); i++) 
				{
					resultString = jsonArray.getJSONObject(i).getString("address");
				}
			}
		} 
		catch (Exception e) 
		{
			throw new Exception("Failed to get phy address" + e.getMessage());
		} 
		finally 
		{
			get.abort();
			client = null;
		}
		return resultString;
    }
    
    
    private static void showResult(SCell cell, String location){
    	System.out.println(String.format("基站信息:mcc:%d,mnc:%d,lac:%d,cid:%d",	cell.getMCC(), cell.getMNC(), cell.getLAC(), cell.getCID()));
    	System.out.println("物理位置:" + location);
    }
}
