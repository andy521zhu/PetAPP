/**
 * ���˶�����б�����
 */
package com.peo.straypet;

import com.peo.ceneral.OthersInfo;

import android.provider.MediaStore.Images;

public class ListData{
		
	Images headPic=null;//ͷ��
	Images lostDogPic [] = new Images[9];//���˶������Ƭ,���9��
	int dogPicNum=0;//��Ƭ����
	String title=null;
	String descript=null;
	String time=null;
	
	OthersInfo master = new OthersInfo(true);//��������Ϣ,����������
}
