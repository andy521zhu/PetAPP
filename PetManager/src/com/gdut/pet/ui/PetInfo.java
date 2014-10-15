package com.gdut.pet.ui;

import android.provider.MediaStore.Images;

//宠物数据元
public class PetInfo {
	
	public String name = "";
	public Images image = null;
	public String type = "";
	public String style="";// 品种
	public int age;
	public int birth_y=0;
	public int birth_m=0;
	public int birth_d=0;
	public int sex = 1; // 0为男，1为女
	public int length = 0;
	public int weight = 0;
	public String color = "";
	public String descript = "";// 描述==简介
	public int if_sterilization = 0;// 绝育情况
	public int if_immune = 0;// 免疫状况
	public String record = "";// 咬人记录等
	public String state="";// 状态
	public Images[] imgs=null;// 图集
	
	public PetInfo(String name){
		this.name=name;
	}
}
