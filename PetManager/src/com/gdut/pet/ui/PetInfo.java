package com.gdut.pet.ui;

import android.provider.MediaStore.Images;

//��������Ԫ
public class PetInfo {
	
	public String name = "";
	public Images image = null;
	public String type = "";
	public String style="";// Ʒ��
	public int age;
	public int birth_y=0;
	public int birth_m=0;
	public int birth_d=0;
	public int sex = 1; // 0Ϊ�У�1ΪŮ
	public int length = 0;
	public int weight = 0;
	public String color = "";
	public String descript = "";// ����==���
	public int if_sterilization = 0;// �������
	public int if_immune = 0;// ����״��
	public String record = "";// ҧ�˼�¼��
	public String state="";// ״̬
	public Images[] imgs=null;// ͼ��
	
	public PetInfo(String name){
		this.name=name;
	}
}
