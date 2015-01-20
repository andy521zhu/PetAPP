/**
 * 用于存储浏览他人的数据
 */
package com.gdut.pet.ui;

import android.net.Uri;
import android.provider.MediaStore.Images;

public class OthersInfo {
	
	public OthersInfo(boolean tag){
		if(tag)
			this.man_stor=1;//人
		else
			this.man_stor=0;
	}
	
	public 	String logName = ""; // 手机或邮箱,账号
	
	public int man_stor = 1;	//主人还是店主，1位人，0为店
	public boolean if_get = false; // 是否已获取数据

	// 用户数据
	public String name = "";
	public PetInfo mypets[]=null;	//宠物
	public int sex = 1; // 0为男，1为女
	public int birthY=0;
	public int birthM=0;
	public int birtyD=0;
	public String say = ""; // 说说
	public String callphone = "";
	public String phone = "";
	public String QQ = "";
	public String email = "";
	public int petnum = 0;
	public String address="";//住址
	public Uri image_uri=null;
	public Images image = null;
	
	//宠物店数据
	public String stor_name = "";
	public String stor_descript = ""; // 描述
	public String stor_cellphone = "";
	public String stor_phone = "";
	public String stor_qq = "";
	public String stor_email = "";
	public String stor_address = "";// 住址
	public Uri stor_image_uri = null;
	public Images stor_image = null;
	public boolean stor_server_food = false;
	public boolean stor_server_hosp = false;// 医院
	public boolean stor_server_sell = false;
	public boolean stor_server_hairdress = false;// 美容
	public boolean stor_server_travel = false;// 旅游
	public boolean stor_server_appliance = false;
	public String stor_server_another = "";// 其它

	// 坐标数据
	public double latitude = -1; // 纬度坐标
	public double longititude = -1; // 经度坐标
}
