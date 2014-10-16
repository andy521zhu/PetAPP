package com.gdut.pet.config;

public class Configs
{
	public static final String CHARSET = "utf-8";

	public static final String LOGIN_PATH = "http://10.21.63.145:8080/PetMgr/servlet/MobileLoginControler";
	public static final String REGISTE_PATH = "http://10.21.63.145:8080/PetMgr/servlet/MobileLoginControler";
	public static final String GET_USET_DATA_PATH = "http://10.21.63.145:8080/PetMgr/servlet/MobileGetData";
	// ?poststype=1&firstid=1&lastid=2
	public static final String GET_BBS_PATH = "http://10.21.63.145:8080/PetMgr/servlet/MobileGetPosts";
	// ?type=1&postId=8386ece7077849bf98e4222ee344c16d
	public static final String GET_BBS_DETAIL_PATH = "http://10.21.63.145:8080/PetMgr/servlet/MobilePostMgr";
	// ?type=2&title=aaaaa&content=bbbbb&contactName=cc&contactPhone=123&contactEmail=abc@qq.com&contactQq=123&postType=1
	public static final String POST_BBS_PATH = "http://10.21.63.145:8080/PetMgr/servlet/MobilePostMgr";
	// 上传用户数据URL
	public static final String UPLOAD_USER_INFO_PATH = "";

	// 超时
	public static int timeout = 5000;
	public static int SO_TIMEOUT = 10 * 1000; // 设置等待数据超时时间10秒钟

	// 登录的时候选择记住密码 放在sp里面的
	public static final String REMEMBER_PASSWORD = "rememberpwd";// 记住密码
	public static final String AUTOLOGIN = "autologin";// 自动登录
	public static final String USERDATA_SP = "userdata";
	public static final String IS_LOGIN = "islogin";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String LOGIN_NAME = "login_name";

	public static final int RESULT_STATUS_SUCCESS = 1;
	public static final int RESULT_STATUS_FAIL = 2;
	public static final int RESULT_STATUS_PASSWORD_ERROR = 3;

	public static final String ACTION = "action";

	public static final String IS_FROM_REG = "isfromreg";

}
