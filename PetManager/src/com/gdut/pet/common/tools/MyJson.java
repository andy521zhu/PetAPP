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
	 * 解析json 帖子详细信息,和评论的json的解析
	 * 
	 * @return
	 */
	public List<BBSCommentsInfo> getBBSDetailAndComment()
	{
		// 不行 还要在里面添加状态的判断 status忘了 我擦擦擦擦

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
				// 上传时间
				info.setTime(bbsObject.getString("time"));
				// 用户名
				info.setUsername(bbsObject.getString("username"));
				// 用户头像
				info.setUserImageUrl(bbsObject.getString("userImage"));
				info.setIsGuanzhu(bbsObject.getString("isGuanzhu"));
				// 没有petname
				// info.setPetName()
				// 宠物图片地址
				info.setPetImageUrl(bbsObject.getString("petImage"));
				// 评论个数
				info.setCommentNum(bbsObject.getString("replynum"));
				// 点赞个数
				info.setLikeNum(bbsObject.getString("likenum"));
				/**
				 * 宠物是否丢失 是不是丢失帖子
				 */
				info.setIsLost(bbsObject.getString("isLost"));

				JSONObject commentObject = commentArray.getJSONObject(i);
				info.setText(commentObject.getString("text"));
				info.setUsernameComment(commentObject
						.getString("usernameComment"));
				// 这里以后再优化吧,如果以后有时间的话,因为每次添加comment都会将上面的帖子内容也添加进来 变得冗余了
				list.add(info);
			}
			return list;

		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			L.i(TAG, e.toString());
			L.i(TAG, "解析json出错了");
			toastMgr.builder.display("MyJson decode -----出错", 0);
			return null;
		}

	}

	/**
	 * 得到帖子的详细信息
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
				// 上传时间
				info.setTime(bbsObject.getString("time"));
				// 用户名
				info.setUsername(bbsObject.getString("username"));
				// 用户头像
				info.setUserImageUrl(bbsObject.getString("userImage"));
				L.i(TAG, "userimage = " + bbsObject.getString("userImage"));
				info.setIsGuanzhu(bbsObject.getString("isGuanzhu"));
				// 没有petname
				// info.setPetName()
				// 宠物图片地址
				info.setPetImageUrl(bbsObject.getString("petImage"));
				// 评论个数
				info.setCommentNum(bbsObject.getString("replynum"));
				// 点赞个数
				info.setLikeNum(bbsObject.getString("likenum"));
				// 这里以后再优化吧,如果以后有时间的话,因为每次添加comment都会将上面的帖子内容也添加进来 变得冗余了
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
	 * 得到宠物丢失或者找到的帖子的详细信息 list
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
				// 上传时间
				info.setTime(bbsObject.getString("time"));
				// 用户名
				info.setUsername(bbsObject.getString("username"));
				// 用户头像
				info.setUserImageUrl(bbsObject.getString("userImage"));
				L.i(TAG, "userimage = " + bbsObject.getString("userImage"));
				info.setIsGuanzhu(bbsObject.getString("isGuanzhu"));
				// 没有petname
				// info.setPetName()
				// 宠物图片地址
				info.setPetImageUrl(bbsObject.getString("petImage"));
				// 评论个数
				info.setCommentNum(bbsObject.getString("replynum"));
				// 点赞个数
				info.setLikeNum(bbsObject.getString("likenum"));
				// 是LOST 还是FOUND帖子
				info.setIsLost(bbsObject.getString("isLostFound"));
				// 这里以后再优化吧,如果以后有时间的话,因为每次添加comment都会将上面的帖子内容也添加进来 变得冗余了
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
	 * 得到这个用户的所有宠物的照片的网络操作
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
				list.add("fail");// 网络错误
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
	 * 得到这个用户的所有添加的宠物信息列表
	 */
	public List<AddedPetInfo> getUserAllAddedPetList()
	{

		List<AddedPetInfo> list = new ArrayList<AddedPetInfo>();
		try
		{
			JSONObject object = new JSONObject(json);
			String statusString = object.getString("status");
			// 失败
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
