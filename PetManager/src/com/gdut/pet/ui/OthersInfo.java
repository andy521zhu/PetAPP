/**
 * ���ڴ洢������˵�����
 */
package com.gdut.pet.ui;

import android.net.Uri;
import android.provider.MediaStore.Images;

public class OthersInfo {
	
	public OthersInfo(boolean tag){
		if(tag)
			this.man_stor=1;//��
		else
			this.man_stor=0;
	}
	
	public 	String logName = ""; // �ֻ�������,�˺�
	
	public int man_stor = 1;	//���˻��ǵ�����1λ�ˣ�0Ϊ��
	public boolean if_get = false; // �Ƿ��ѻ�ȡ����

	// �û�����
	public String name = "";
	public PetInfo mypets[]=null;	//����
	public int sex = 1; // 0Ϊ�У�1ΪŮ
	public int birthY=0;
	public int birthM=0;
	public int birtyD=0;
	public String say = ""; // ˵˵
	public String callphone = "";
	public String phone = "";
	public String QQ = "";
	public String email = "";
	public int petnum = 0;
	public String address="";//סַ
	public Uri image_uri=null;
	public Images image = null;
	
	//���������
	public String stor_name = "";
	public String stor_descript = ""; // ����
	public String stor_cellphone = "";
	public String stor_phone = "";
	public String stor_qq = "";
	public String stor_email = "";
	public String stor_address = "";// סַ
	public Uri stor_image_uri = null;
	public Images stor_image = null;
	public boolean stor_server_food = false;
	public boolean stor_server_hosp = false;// ҽԺ
	public boolean stor_server_sell = false;
	public boolean stor_server_hairdress = false;// ����
	public boolean stor_server_travel = false;// ����
	public boolean stor_server_appliance = false;
	public String stor_server_another = "";// ����

	// ��������
	public double latitude = -1; // γ������
	public double longititude = -1; // ��������
}
