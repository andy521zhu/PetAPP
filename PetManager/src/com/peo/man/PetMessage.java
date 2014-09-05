/**
 * 显示及修改宠物的数据,---并上传数据---图像问题没解决
 */
package com.peo.man;


import com.peo.ceneral.LogInfo;
import com.ui.mypet.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PetMessage extends Activity{
	
	ImageView img;
	EditText petName,petAge,petSay,petStyle,petLong,petWeight,petType,petColor,petState;
	TextView petNameView,birthD,birthM,birthY,qwe,ewq;//生日
	CheckBox petImmune,petSterilizations;
	RadioGroup sex;
	Button last,next;
	int myt=0,petId=0;	//宠物编号
	MyDateClickListener md;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pet_message);
		
		img=(ImageView)findViewById(R.id.petImageView);
		petName=(EditText)findViewById(R.id.editPetName);
		petAge=(EditText)findViewById(R.id.editPetAge);
		petSay=(EditText)findViewById(R.id.editPetDescript);
		petStyle=(EditText)findViewById(R.id.editPetStyle);
		petLong=(EditText)findViewById(R.id.editPetLong);
		petWeight=(EditText)findViewById(R.id.editPetWeight);
		petType=(EditText)findViewById(R.id.editPetType);
		petColor=(EditText)findViewById(R.id.editPetColor);
		petState=(EditText)findViewById(R.id.editPetState);
		petNameView=(TextView)findViewById(R.id.petNameView);
		petImmune=(CheckBox)findViewById(R.id.checkPetImmune);
		petSterilizations=(CheckBox)findViewById(R.id.checkPetSterilizations);
		sex=(RadioGroup)findViewById(R.id.petSexGroup);
		last=(Button)findViewById(R.id.lastPetButton);
		next=(Button)findViewById(R.id.nextPetButton);
		birthY=(TextView)findViewById(R.id.petBirthYView);
		birthM=(TextView)findViewById(R.id.petBirthMView);
		birthD=(TextView)findViewById(R.id.petBirthDView);
		qwe=(TextView)findViewById(R.id.qwe);
		ewq=(TextView)findViewById(R.id.ewq);
		
		Intent intent=getIntent();
		Bundle bundle;
		bundle=intent.getExtras();
		petId=bundle.getInt("petId");
		
		readPet(petId);
		
		next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (petId < LogInfo.PETNUM) {
					if(petId >= 0 && checkChanged(petId)){
						Toast.makeText(PetMessage.this, "数据更改，上传数据", Toast.LENGTH_SHORT).show();
						//上传数据
					}
					petId++;
					readPet(petId);
				}else
					Toast.makeText(PetMessage.this, "已经没有更多宠物了哦", Toast.LENGTH_SHORT).show();
			}
			
		});
		
		last.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(petId>0){
					if(petId < LogInfo.PETNUM && checkChanged(petId)){
						Toast.makeText(PetMessage.this, "数据更改，上传数据", Toast.LENGTH_SHORT).show();
						//上传数据
					}
					petId--;
					readPet(petId);
					
				}else
					Toast.makeText(PetMessage.this, "这已经是第一只宠物了哦", Toast.LENGTH_SHORT).show();
			}
			
		});
		
		md=new MyDateClickListener();
		
	}
	
	public class MyDateClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//弹出日期选择对话框
			new DatePickerDialog(PetMessage.this, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					birthY.setText(year+"");
					birthM.setText(monthOfYear+"");
					birthD.setText(dayOfMonth+"");
					System.out.println("setdate");
				}
				
			}, LogInfo.MYPETS[petId].birth_y, LogInfo.MYPETS[petId].birth_m, LogInfo.MYPETS[petId].birth_d).show();
		    
			System.out.println("dateclick");
		}
		
	}
	
	public void readPet(int id){
		//id是否有效
		if(LogInfo.MYPETS != null && id<LogInfo.PETNUM && id>=0){
			
			petNameView.setText(LogInfo.MYPETS[id].name);
			petName.setText(LogInfo.MYPETS[id].name);
			// petBirthday.setText(LogInfo.MYPETS[id].birth_y+"."+LogInfo.MYPETS[id].birth_m+"."+LogInfo.MYPETS[id].birth_d);
//			petBirth.updateDate(LogInfo.MYPETS[id].birth_y,
//					LogInfo.MYPETS[id].birth_m, LogInfo.MYPETS[id].birth_d);
			birthY.setText(LogInfo.MYPETS[id].birth_y+"");
			birthM.setText(LogInfo.MYPETS[id].birth_m+"");
			birthD.setText(LogInfo.MYPETS[id].birth_d+"");			
			petAge.setText(LogInfo.MYPETS[id].age + "");
			petSay.setText(LogInfo.MYPETS[id].descript);
			petType.setText(LogInfo.MYPETS[id].type);
			petStyle.setText(LogInfo.MYPETS[id].style);
			petLong.setText(LogInfo.MYPETS[id].length + "");
			petWeight.setText(LogInfo.MYPETS[id].weight + "");//此处int型如果不加""会报错
			petColor.setText(LogInfo.MYPETS[id].color);
			petState.setText(LogInfo.MYPETS[id].state);
			if (LogInfo.MYPETS[id].if_sterilization == 1) { // 绝育
				petSterilizations.setChecked(true);
			} else
				petSterilizations.setChecked(false);
			if (LogInfo.MYPETS[id].if_immune == 1) { // 免疫
				petImmune.setChecked(true);
			} else
				petImmune.setChecked(false);
			if (LogInfo.MYPETS[id].sex == 1) { // 女
				sex.check(R.id.girlButton);
			} else
				sex.check(R.id.boyButton);
			
			if (myt == 0) {
				// 设置日期改动
				birthY.setOnClickListener(md);
				birthM.setOnClickListener(md);
				birthD.setOnClickListener(md);
				qwe.setOnClickListener(md);
				ewq.setOnClickListener(md);
				myt++;
			}
		}
	}
	
	//检查是否有改动
	public boolean checkChanged(int id){
		boolean tag=false;	//false为无更改，true为有更改
		if(petName.getText().toString().isEmpty()){
			Toast.makeText(this, "用户名称为空，将不进行更改", Toast.LENGTH_SHORT).show();
		}else if (!petName.getText().toString().equals(LogInfo.MYPETS[id].name)) {
			LogInfo.NAME = petName.getText().toString();
			System.out.println("name");
			tag=true;
		}
		if (!petAge.getText().toString().equals(LogInfo.MYPETS[id].age + "")) {
			LogInfo.MYPETS[id].age = Integer.parseInt(petAge.getText().toString());
			tag=true;
			System.out.println("age");
		}
		if (!petSay.getText().toString().equals(LogInfo.MYPETS[id].descript)) {
			LogInfo.MYPETS[id].descript = petSay.getText().toString();
			tag=true;
			System.out.println("say");
		}
		if (!petType.getText().toString().equals(LogInfo.MYPETS[id].type)) {
			LogInfo.MYPETS[id].type = petType.getText().toString();
			tag=true;
			System.out.println("tyoe");
		}
		if (!petStyle.getText().toString().equals(LogInfo.MYPETS[id].style)) {
			LogInfo.MYPETS[id].style = petStyle.getText().toString();
			tag=true;
			System.out.println("style");
		}
		if (!petLong.getText().toString().equals(LogInfo.MYPETS[id].length+"")) {//数字不会自动转成字符串
			LogInfo.MYPETS[id].length = Integer.parseInt(petLong.getText().toString());
			tag=true;
			System.out.println("long");
		}
		if (!petWeight.getText().toString().equals(LogInfo.MYPETS[id].weight+"")) {
			LogInfo.MYPETS[id].weight = Integer.parseInt(petWeight.getText().toString());
			tag=true;
			System.out.println("wei");
		}
		if (!petColor.getText().toString().equals(LogInfo.MYPETS[id].color)) {
			LogInfo.MYPETS[id].color = petColor.getText().toString();
			tag=true;
			System.out.println("col");
		}
		if (!petState.getText().toString().equals(LogInfo.MYPETS[id].state)) {
			LogInfo.MYPETS[id].state = petState.getText().toString();
			tag=true;
			System.out.println("state");
		}
		//绝育
		if(petSterilizations.isChecked() == true && LogInfo.MYPETS[id].if_sterilization == 0
				|| petSterilizations.isChecked() == false && LogInfo.MYPETS[id].if_sterilization == 1){
			LogInfo.MYPETS[id].if_sterilization ^= 1;	//	取反
			tag=true;
			System.out.println("Steril");
		}
		//免疫
		if(petImmune.isChecked() == true && LogInfo.MYPETS[id].if_immune == 0
				|| petImmune.isChecked() == false && LogInfo.MYPETS[id].if_immune == 1){
			LogInfo.MYPETS[id].if_immune ^= 1;	//	取反
			tag=true;
			System.out.println("Immune");
		}
		//性别
		if(sex.getCheckedRadioButtonId()==R.id.boyButton && LogInfo.MYPETS[id].sex == 1
				|| sex.getCheckedRadioButtonId()==R.id.girlButton && LogInfo.MYPETS[id].sex == 0){
			LogInfo.MYPETS[id].sex ^= 1;	//	取反
			tag=true;
			System.out.println("sex");
		}
		//日期
		if (Integer.parseInt(birthY.getText().toString()) != LogInfo.MYPETS[id].birth_y
				|| Integer.parseInt(birthM.getText().toString()) != LogInfo.MYPETS[id].birth_m
				|| Integer.parseInt(birthD.getText().toString()) != LogInfo.MYPETS[id].birth_d) {
			LogInfo.MYPETS[id].birth_y = (int)Integer.parseInt(birthY.getText().toString());
			LogInfo.MYPETS[id].birth_m = (int)Integer.parseInt(birthM.getText().toString());
			LogInfo.MYPETS[id].birth_d = (int)Integer.parseInt(birthD.getText().toString());
			tag = true;
			System.out.println("day");
		}
		return tag;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(petId>=0 && petId<LogInfo.PETNUM && checkChanged(petId)){
			Toast.makeText(this, "数据更改，上传数据", Toast.LENGTH_SHORT).show();
			//上传数据
		}
		super.onDestroy();
	}
}
