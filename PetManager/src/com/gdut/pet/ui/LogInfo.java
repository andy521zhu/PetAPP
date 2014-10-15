package com.gdut.pet.ui;

import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore.Images;

public class LogInfo {
	//登录数据
	public static 	String LOGNAME = ""; // 手机或邮箱,账号
	public static 	String PASSWORD = "";
	public static 	String STORTNAME = "";
	public static 	boolean IF_LOG = false; 
	public static 	boolean IF_AUTOLOG = false; // 是否自动登录
	public static 	boolean IF_REM_LOG = false; // 是否记住密码
	
	public static int MAN_STOR = 1;	//主人还是店主，1位人，0为店
	public static boolean IFGET = false; // 是否已获取数据

	// 用户数据
	public static String NAME = "";
	public static PetInfo MYPETS[]=null;	//宠物
	public static int SEX = 1; // 0为男，1为女
	public static int BIRTH_Y=0;
	public static int BIRTH_M=0;
	public static int BIRTH_D=0;
	public static String SAY = ""; // 说说
	public static String CALLPHONE = "";
	public static String PHONE = "";
	public static String QQ = "";
	public static String E_MAIL = "";
	public static int PETNUM = 0;
	public static String ADDRESS="";
	public static Uri IMAGE_URI=null;
	public static Images IMAGE = null;
	
	//宠物店数据
	public static String STOR_NAME="";
	public static String STOR_DESCRIPT = ""; // 描述
	public static String STOR_CALLPHONE = "";
	public static String STOR_PHONE = "";
	public static String STOR_QQ = "";
	public static String STOR_E_MAIL = "";
	public static String STOR_ADDRESS="";
	public static Uri STOR_IMAGE_URI = null;
	public static Images STOR_IMAGE = null;
	public static boolean STOR_SERVER_FOOD = false;
	public static boolean STOR_SERVER_HOSP = false;//医院
	public static boolean STOR_SERVER_SELL = false;
	public static boolean STOR_SERVER_HAILDRESS = false;//美容
	public static boolean STOR_SERVER_TRAVEL = false;//旅游
	public static boolean STOR_SERVER_APPLIANCE = false;
	public static String STOR_SERVER_ANOTHER = "";//其它
	
	//坐标数据
	public static double LATITUDE=-1;	//纬度坐标
	public static double LONGITITUDE=-1;	//经度坐标
}
