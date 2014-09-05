/**
 * 流浪动物的列表数据
 */
package com.peo.straypet;

import com.peo.ceneral.OthersInfo;

import android.provider.MediaStore.Images;

public class ListData{
		
	Images headPic=null;//头像
	Images lostDogPic [] = new Images[9];//流浪动物的照片,最多9张
	int dogPicNum=0;//照片数量
	String title=null;
	String descript=null;
	String time=null;
	
	OthersInfo master = new OthersInfo(true);//发帖人信息,参数代表人
}
