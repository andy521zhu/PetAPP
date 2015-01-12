package com.gdut.pet.getuiReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;

public class MyPushReceiver extends BroadcastReceiver
{

	private static final String TAG = "GetuiSdkDemo";

	@Override
	public void onReceive(Context context, Intent intent)
	{
		// TODO Auto-generated method stub
		Bundle bundle = intent.getExtras();
		L.i(TAG, "onReceive() action=" + bundle.getInt("action"));
		switch (bundle.getInt(PushConsts.CMD_ACTION))
		{
		case PushConsts.GET_MSG_DATA:
			L.i(TAG, "switch:PushConsts.GET_MSG_DATA");
			// 获得穿透数据
			/*
			 * String appid = bundle.getString("appid"); L.i(TAG, "appid = " +
			 * appid); String taskid = bundle.getString("taskid"); L.i(TAG,
			 * "taskid = " + taskid); String messageid =
			 * bundle.getString("messageid"); L.i(TAG, "messageid = " +
			 * messageid); String mydata = bundle.getString("mydata"); L.i(TAG,
			 * "mydata = " + mydata); boolean result =
			 * PushManager.getInstance().sendFeedbackMessage( context, taskid,
			 * messageid, 9001); L.i(TAG, "result = " + result);
			 */
			byte[] payload = bundle.getByteArray("payload");
			if (payload != null)
			{
				try
				{
					JSONObject object = new JSONObject(new String(payload));
					String payLoadData = object.getString("mypet");
					toastMgr.builder.display(payLoadData, 0);
				}
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					L.i(TAG, "JSONObject 异常");
					e.printStackTrace();
				}
				String dataString = new String(payload);
				L.i(TAG, "dataString = " + dataString);
			}
			break;
		case PushConsts.GET_CLIENTID:
			L.i(TAG, "switch:PushConsts.GET_CLIENTID");
			String cid = bundle.getString("clientid");
			Tag[] tagaaTags = new Tag[1];
			tagaaTags[0] = new Tag();
			tagaaTags[0].setName("1234");
			PushManager.getInstance().setTag(context, tagaaTags);
			L.d("GetuiSdkDemo", "Got ClientID:" + cid);
			break;
		case PushConsts.THIRDPART_FEEDBACK:
			L.i(TAG, "switch:PushConsts.THIRDPART_FEEDBACK");
			break;

		default:
			break;
		}
	}

}
