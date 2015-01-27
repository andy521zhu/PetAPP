package com.gdut.pet.common.info;

import java.util.List;

public class BBSCommentsInfo
{
	// id
	private String id;
	// 用户名
	private String username;

	private String itemNum;
	private String itemContent;
	// 评论
	private String comment;
	private String text;
	private String title;
	private String imgNum;
	// 上传时间
	private String time;
	private String replyNum;
	// 评论者的用户名
	private String usernameComment;

	// 此帖用户的头像连接
	private String userImageUrl;
	// 宠物种类
	private String petSpecies;
	// 宠物名字
	private String petName;
	// 是否关注
	private String isGuanzhu;
	// 此帖中宠物的图片
	private String petImageUrl;
	// 评论个数
	private String commentNum;
	// 点赞个数
	private String likeNum;
	// 帖子状态 正常丢失还是找到
	private String isLost;
	private List<BBSCommentsInfo> commentsInfos;

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * @return the itemNum
	 */
	public String getItemNum()
	{
		return itemNum;
	}

	/**
	 * @param itemNum
	 *            the itemNum to set
	 */
	public void setItemNum(String itemNum)
	{
		this.itemNum = itemNum;
	}

	/**
	 * @return the itemContent
	 */
	public String getItemContent()
	{
		return itemContent;
	}

	/**
	 * @param itemContent
	 *            the itemContent to set
	 */
	public void setItemContent(String itemContent)
	{
		this.itemContent = itemContent;
	}

	/**
	 * @return the comment
	 */
	public String getComment()
	{
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment)
	{
		this.comment = comment;
	}

	/**
	 * @return the text
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the imgNum
	 */
	public String getImgNum()
	{
		return imgNum;
	}

	/**
	 * @param imgNum
	 *            the imgNum to set
	 */
	public void setImgNum(String imgNum)
	{
		this.imgNum = imgNum;
	}

	/**
	 * @return the time
	 */
	public String getTime()
	{
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time)
	{
		this.time = time;
	}

	/**
	 * @return the replyNum
	 */
	public String getReplyNum()
	{
		return replyNum;
	}

	/**
	 * @param replyNum
	 *            the replyNum to set
	 */
	public void setReplyNum(String replyNum)
	{
		this.replyNum = replyNum;
	}

	/**
	 * @return the usernameComment
	 */
	public String getUsernameComment()
	{
		return usernameComment;
	}

	/**
	 * @param usernameComment
	 *            the usernameComment to set
	 */
	public void setUsernameComment(String usernameComment)
	{
		this.usernameComment = usernameComment;
	}

	public String getUserImageUrl()
	{
		return userImageUrl;
	}

	public void setUserImageUrl(String userImageUrl)
	{
		this.userImageUrl = userImageUrl;
	}

	public String getPetSpecies()
	{
		return petSpecies;
	}

	public void setPetSpecies(String petSpecies)
	{
		this.petSpecies = petSpecies;
	}

	public String getPetName()
	{
		return petName;
	}

	public void setPetName(String petName)
	{
		this.petName = petName;
	}

	public String getIsGuanzhu()
	{
		return isGuanzhu;
	}

	public void setIsGuanzhu(String isGuanzhu)
	{
		this.isGuanzhu = isGuanzhu;
	}

	public String getPetImageUrl()
	{
		return petImageUrl;
	}

	public void setPetImageUrl(String petImageUrl)
	{
		this.petImageUrl = petImageUrl;
	}

	public String getCommentNum()
	{
		return commentNum;
	}

	public void setCommentNum(String commentNum)
	{
		this.commentNum = commentNum;
	}

	public String getLikeNum()
	{
		return likeNum;
	}

	public void setLikeNum(String likeNum)
	{
		this.likeNum = likeNum;
	}

	public String getIsLost()
	{
		return isLost;
	}

	public void setIsLost(String isLost)
	{
		this.isLost = isLost;
	}

	public List<BBSCommentsInfo> getCommentsInfos()
	{
		return commentsInfos;
	}

	public void setCommentsInfos(List<BBSCommentsInfo> commentsInfos)
	{
		this.commentsInfos = commentsInfos;
	}

	public void addCommentInfos(BBSCommentsInfo commentsInfo)
	{
		this.commentsInfos.add(commentsInfo);
	}

}
