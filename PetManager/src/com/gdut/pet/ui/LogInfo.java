package com.gdut.pet.ui;

import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore.Images;

public class LogInfo {
	//��¼����
	public static 	String LOGNAME = ""; // �ֻ�������,�˺�
	public static 	String PASSWORD = "";
	public static 	String STORTNAME = "";
	public static 	boolean IF_LOG = false; 
	public static 	boolean IF_AUTOLOG = false; // �Ƿ��Զ���¼
	public static 	boolean IF_REM_LOG = false; // �Ƿ��ס����
	
	public static int MAN_STOR = 1;	//���˻��ǵ�����1λ�ˣ�0Ϊ��
	public static boolean IFGET = false; // �Ƿ��ѻ�ȡ����

	// �û�����
	public static String NAME = "";
	public static PetInfo MYPETS[]=null;	//����
	public static int SEX = 1; // 0Ϊ�У�1ΪŮ
	public static int BIRTH_Y=0;
	public static int BIRTH_M=0;
	public static int BIRTH_D=0;
	public static String SAY = ""; // ˵˵
	public static String CALLPHONE = "";
	public static String PHONE = "";
	public static String QQ = "";
	public static String E_MAIL = "";
	public static int PETNUM = 0;
	public static String ADDRESS="";
	public static Uri IMAGE_URI=null;
	public static Images IMAGE = null;
	
	//���������
	public static String STOR_NAME="";
	public static String STOR_DESCRIPT = ""; // ����
	public static String STOR_CALLPHONE = "";
	public static String STOR_PHONE = "";
	public static String STOR_QQ = "";
	public static String STOR_E_MAIL = "";
	public static String STOR_ADDRESS="";
	public static Uri STOR_IMAGE_URI = null;
	public static Images STOR_IMAGE = null;
	public static boolean STOR_SERVER_FOOD = false;
	public static boolean STOR_SERVER_HOSP = false;//ҽԺ
	public static boolean STOR_SERVER_SELL = false;
	public static boolean STOR_SERVER_HAILDRESS = false;//����
	public static boolean STOR_SERVER_TRAVEL = false;//����
	public static boolean STOR_SERVER_APPLIANCE = false;
	public static String STOR_SERVER_ANOTHER = "";//����
	
	//��������
	public static double LATITUDE=-1;	//γ������
	public static double LONGITITUDE=-1;	//��������
}
