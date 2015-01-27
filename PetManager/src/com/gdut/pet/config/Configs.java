package com.gdut.pet.config;

public class Configs
{
	public static final String CHARSET = "utf-8";

	public static final String SERVER_IP_ADDRESS = "http://10.21.63.184:8080";

	public static final String LOGIN_PATH = "http://10.21.63.184:8080/PetWebsiteMgr/servlet/MobileLoginControler";
	public static final String REGISTE_PATH = "http://10.21.63.184:8080/PetWebsiteMgr/servlet/MobileLoginControler";
	// public static final String GET_USET_DATA_PATH =
	// "http://10.21.63.145:8080/PetMgr/servlet/MobileGetData";
	// // ?poststype=1&firstid=1&lastid=2
	// public static final String GET_BBS_PATH =
	// "http://10.21.63.145:8080/PetMgr/servlet/MobileGetPosts";
	// // ?type=1&postId=8386ece7077849bf98e4222ee344c16d
	// public static final String GET_BBS_DETAIL_PATH =
	// "http://10.21.63.145:8080/PetMgr/servlet/MobilePostMgr";
	// //
	// ?type=2&title=aaaaa&content=bbbbb&contactName=cc&contactPhone=123&contactEmail=abc@qq.com&contactQq=123&postType=1
	// public static final String POST_BBS_PATH =
	// "http://10.21.63.145:8080/PetMgr/servlet/MobilePostMgr";
	// // �ϴ��û�����URL
	// public static final String UPLOAD_USER_INFO_PATH = "";

	// "http://10.21.63.145:8080/PetMgr/servlet/MobileUploadImage?action=1",
	// public static final String LOGIN_PATH =
	// "http://10.21.63.113:8080/TestForPet/servlet/getAction";
	// public static final String REGISTE_PATH =
	// "http://10.21.63.113:8080/TestForPet/servlet/getAction";
	public static final String GET_USET_DATA_PATH = "http://10.21.63.184:8080/PetWebsiteMgr/servlet/MobileGetData";
	// ?poststype=1&firstid=1&lastid=2 ��ö�������http://localhost:8080/PetWebcde/servlet/MobileGetPosts
	public static final String GET_BBS_PATH = "http://10.21.63.184:8080/PetWebsiteMgr/servlet/MobileGetPosts";
	// ?type=1&postId=8386ece7077849bf98e4222ee344c16d �õ��������� �Լ����������
	public static final String GET_BBS_DETAIL_PATH = "http://10.21.63.184:8080/PetWebsiteMgr/servlet/MobilePostMgr";
	// ?type=2&title=aaaaa&content=bbbbb&contactName=cc&contactPhone=123&contactEmail=abc@qq.com&contactQq=123&postType=1
	public static final String POST_BBS_PATH = "http://10.21.63.184:8080/PetWebsiteMgr/servlet/MobilePostMgr";
	// �ϴ��û�����URL
	public static final String UPLOAD_USER_INFO_PATH = "http://10.21.63.184:8080/TestForPet/servlet/getAction";
	// �õ���ע�������б�
	public static final String GET_GUANZHU_BBS_LIST_PATH = "http://10.21.63.184:8080/TestForPet/servlet/getAction";
	//
	public static final String GET_USER_ALL_ADDED_PET_LIST_PATH = "http://10.21.63.113:8080/TestForPet/servlet/getAction";
	//
	public static final String GET_LOST_FOUND_BBS_PATH = "http://10.21.63.113:8080/TestForPet/servlet/getAction";

	public static final String UPLOAD_BBS_IMAGE_PATH = "http://10.21.63.184:8080/PetWebsiteMgr/servlet/MobileUploadImage?action=1";
	// ��ʱ
	public static int timeout = 5000;
	public static int SO_TIMEOUT = 10 * 1000; // ���õȴ����ݳ�ʱʱ��10����

	// ��¼��ʱ��ѡ���ס���� ����sp�����
	public static final String REMEMBER_PASSWORD = "rememberpwd";// ��ס����
	public static final String AUTOLOGIN = "autologin";// �Զ���¼
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

	// �ٶȵ�ͼ��key
	public static final String BAIDU_MAP_KEY = "622PXiozIGStPGR4MHOEpNh1";
	// �ٶ�������api_key
	public static final String BAIDU_YUN_LOGIN_TYPE_API_KEY = "0";
	public static final String BAIDU_YUN_PUSH_API_KEY = "622PXiozIGStPGR4MHOEpNh1";

	/**
	 * ���������� ��ʧ �ҵ� ������NORMAL = 1 ��ʧ��LOST = 2 �ҵ���FOUND = 3
	 */
	private static final int BBS_NORMAL = 0X1;
	private static final int BBS_LOST = 0X2;
	private static final int BBS_FOUND = 0X3;

	/**
	 * �����Ƕ��˹��ĳ���
	 * 
	 */
	public static final String PUBLISHER_ID = "56OJzIlIuN/Kc3gK+B";
	public static final String INLINE_PPID = "16TLmgZoAp6T4NUOYnmqNBok";

}
