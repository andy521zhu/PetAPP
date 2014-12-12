package com.gdut.pet.common.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.gdut.pet.common.info.AddedPetInfo;
import com.gdut.pet.common.info.BBSCommentsInfo;
import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;

public class MyJson
{
	private static final String TAG = "com.gdut.pet.common.tools.MyJson";

	private Context mContext;
	private String json;

	public MyJson()
	{
	}

	public MyJson(Context mContext, String json)
	{
		this.mContext = mContext;
		this.json = json;
	}

	/**
	 * ����json ������ϸ��Ϣ,�����۵�json�Ľ���
	 * 
	 * @return
	 */
	public List<BBSCommentsInfo> getBBSDetailAndComment()
	{
		// ���� ��Ҫ���������״̬���ж� status���� �Ҳ�������

		List<BBSCommentsInfo> list = new ArrayList<BBSCommentsInfo>();
		try
		{
			JSONObject object = new JSONObject(json);
			JSONObject bbsObject = object.getJSONObject("bbs");

			JSONArray commentArray = object.getJSONArray("comment");
			for (int i = 0; i < commentArray.length(); i++)
			{
				BBSCommentsInfo info = new BBSCommentsInfo();
				info.setId(bbsObject.getString("id"));
				info.setItemContent(bbsObject.getString("content"));
				info.setTitle(bbsObject.getString("title"));
				info.setImgNum(bbsObject.getString("imgNum"));
				// �ϴ�ʱ��
				info.setTime(bbsObject.getString("time"));
				// �û���
				info.setUsername(bbsObject.getString("username"));
				// �û�ͷ��
				info.setUserImageUrl(bbsObject.getString("userImage"));
				info.setIsGuanzhu(bbsObject.getString("isGuanzhu"));
				// û��petname
				// info.setPetName()
				// ����ͼƬ��ַ
				info.setPetImageUrl(bbsObject.getString("petImage"));
				// ���۸���
				info.setCommentNum(bbsObject.getString("replynum"));
				// ���޸���
				info.setLikeNum(bbsObject.getString("likenum"));
				/**
				 * �����Ƿ�ʧ �ǲ��Ƕ�ʧ����
				 */
				info.setIsLost(bbsObject.getString("isLost"));

				JSONObject commentObject = commentArray.getJSONObject(i);
				info.setText(commentObject.getString("text"));
				info.setUsernameComment(commentObject
						.getString("usernameComment"));
				// �����Ժ����Ż���,����Ժ���ʱ��Ļ�,��Ϊÿ�����comment���Ὣ�������������Ҳ��ӽ��� ���������
				list.add(info);
			}
			return list;

		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			L.i(TAG, e.toString());
			L.i(TAG, "����json������");
			toastMgr.builder.display("MyJson decode -----����", 0);
			return null;
		}

	}

	/**
	 * �õ����ӵ���ϸ��Ϣ
	 * 
	 * @return
	 */
	public List<BBSCommentsInfo> getBBSDetail()
	{

		List<BBSCommentsInfo> list = new ArrayList<BBSCommentsInfo>();
		try
		{

			JSONArray detailArray = new JSONArray(json);
			L.i(TAG, "detailArray = " + detailArray.length());
			for (int i = 0; i < detailArray.length(); i++)
			{
				BBSCommentsInfo info = new BBSCommentsInfo();
				L.i(TAG, "i = " + i);
				JSONObject bbsObject = detailArray.getJSONObject(i);
				info.setId(bbsObject.getString("id"));
				info.setItemContent(bbsObject.getString("content"));
				info.setTitle(bbsObject.getString("title"));
				info.setImgNum(bbsObject.getString("imgNum"));
				// �ϴ�ʱ��
				info.setTime(bbsObject.getString("time"));
				// �û���
				info.setUsername(bbsObject.getString("username"));
				// �û�ͷ��
				info.setUserImageUrl(bbsObject.getString("userImage"));
				L.i(TAG, "userimage = " + bbsObject.getString("userImage"));
				info.setIsGuanzhu(bbsObject.getString("isGuanzhu"));
				// û��petname
				// info.setPetName()
				// ����ͼƬ��ַ
				info.setPetImageUrl(bbsObject.getString("petImage"));
				// ���۸���
				info.setCommentNum(bbsObject.getString("replynum"));
				// ���޸���
				info.setLikeNum(bbsObject.getString("likenum"));
				// �����Ժ����Ż���,����Ժ���ʱ��Ļ�,��Ϊÿ�����comment���Ὣ�������������Ҳ��ӽ��� ���������
				list.add(info);
			}
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * �õ����ﶪʧ�����ҵ������ӵ���ϸ��Ϣ list
	 * 
	 * @return List<BBSCommentsInfo>
	 */
	public List<BBSCommentsInfo> getLostFoundBBSDetail()
	{

		List<BBSCommentsInfo> list = new ArrayList<BBSCommentsInfo>();
		try
		{

			JSONArray detailArray = new JSONArray(json);
			L.i(TAG, "detailArray = " + detailArray.length());
			for (int i = 0; i < detailArray.length(); i++)
			{
				BBSCommentsInfo info = new BBSCommentsInfo();
				L.i(TAG, "i = " + i);
				JSONObject bbsObject = detailArray.getJSONObject(i);
				info.setId(bbsObject.getString("id"));
				info.setItemContent(bbsObject.getString("content"));
				info.setTitle(bbsObject.getString("title"));
				info.setImgNum(bbsObject.getString("imgNum"));
				// �ϴ�ʱ��
				info.setTime(bbsObject.getString("time"));
				// �û���
				info.setUsername(bbsObject.getString("username"));
				// �û�ͷ��
				info.setUserImageUrl(bbsObject.getString("userImage"));
				L.i(TAG, "userimage = " + bbsObject.getString("userImage"));
				info.setIsGuanzhu(bbsObject.getString("isGuanzhu"));
				// û��petname
				// info.setPetName()
				// ����ͼƬ��ַ
				info.setPetImageUrl(bbsObject.getString("petImage"));
				// ���۸���
				info.setCommentNum(bbsObject.getString("replynum"));
				// ���޸���
				info.setLikeNum(bbsObject.getString("likenum"));
				// ��LOST ����FOUND����
				info.setIsLost(bbsObject.getString("isLostFound"));
				// �����Ժ����Ż���,����Ժ���ʱ��Ļ�,��Ϊÿ�����comment���Ὣ�������������Ҳ��ӽ��� ���������
				list.add(info);
			}
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * �õ�����û������г������Ƭ���������
	 */
	public List<String> getUserAllPetPics()
	{
		List<String> list = new ArrayList<String>();
		try
		{
			JSONObject object = new JSONObject(json);
			String statusString = object.getString("status");
			if (!statusString.equals("success"))
			{
				//
				list.add("fail");// �������
				return list;
			}
			JSONArray petImageArray = object.getJSONArray("allpetimage");
			for (int i = 0; i < petImageArray.length(); i++)
			{
				L.i(TAG, i + "");
				L.i(TAG, petImageArray.getJSONObject(i).getString("petImage"));
				list.add(petImageArray.getJSONObject(i).getString("petImage"));
			}

		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return list;

	}

	/**
	 * �õ�����û���������ӵĳ�����Ϣ�б�
	 */
	public List<AddedPetInfo> getUserAllAddedPetList()
	{

		List<AddedPetInfo> list = new ArrayList<AddedPetInfo>();
		try
		{
			JSONObject object = new JSONObject(json);
			String statusString = object.getString("status");
			// ʧ��
			if (!statusString.equals("success"))
			{
				//
				list = null;
				return list;
			}
			JSONArray addedPetArray = object.getJSONArray("alladdedpet");
			for (int i = 0; i < addedPetArray.length(); i++)
			{
				AddedPetInfo info = new AddedPetInfo();
				Map<String, String> map = new HashMap<String, String>();
				L.i(TAG, i + "");
				L.i(TAG, addedPetArray.getJSONObject(i).getString("petImage"));
				info.setPetid(addedPetArray.getJSONObject(i).getString("petid"));
				info.setPetname(addedPetArray.getJSONObject(i).getString(
						"petname"));
				info.setPetsex(addedPetArray.getJSONObject(i).getString(
						"petsex"));
				info.setPetspeciese(addedPetArray.getJSONObject(i).getString(
						"petspeciese"));
				info.setPetage(addedPetArray.getJSONObject(i).getString(
						"petage"));
				info.setPetImage(addedPetArray.getJSONObject(i).getString(
						"petImage"));
				list.add(info);
			}

		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return list;
	}

}
