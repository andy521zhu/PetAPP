//显示用户详细信息，并提供更改
package com.peo.man;

import com.peo.ceneral.LogInfo;
import com.ui.mypet.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AllMessage extends Activity{
	
	EditText editName,editSay,editCallPhone,editPhone,editEmail,editQQ,editAddress;
	ImageView editImg;
	TextView myNameView,birthD,birthM,birthY,asd,dsa;//生日
	RadioGroup sex;
	MyDateClickListener md;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_message);
		
		myNameView=(TextView)findViewById(R.id.myNameView);
		editName=(EditText)findViewById(R.id.editName);		
		editSay=(EditText)findViewById(R.id.editSay);
		editCallPhone=(EditText)findViewById(R.id.editCallPhone);
		editPhone=(EditText)findViewById(R.id.editPhone);
		editEmail=(EditText)findViewById(R.id.editEmail);
		editQQ=(EditText)findViewById(R.id.editQQ);
		editAddress=(EditText)findViewById(R.id.editAddress);
		editImg=(ImageView)findViewById(R.id.myPic);
		sex=(RadioGroup)findViewById(R.id.mySexGroup);
		birthY=(TextView)findViewById(R.id.myBirthYView);
		birthM=(TextView)findViewById(R.id.myBirthMView);
		birthD=(TextView)findViewById(R.id.myBirthDView);
		asd=(TextView)findViewById(R.id.asd);
		dsa=(TextView)findViewById(R.id.dsa);
		
		md=new MyDateClickListener();
		readData();
	}
	
	public void readData(){
		editName.setText(LogInfo.NAME);
		myNameView.setText(LogInfo.NAME);
		if (LogInfo.SEX == 1) { // 女
			sex.check(R.id.womanButton);
		} else
			sex.check(R.id.manButton);
		editSay.setText(LogInfo.SAY);
		editCallPhone.setText(LogInfo.CALLPHONE);
		editPhone.setText(LogInfo.PHONE);
		editEmail.setText(LogInfo.E_MAIL);
		editQQ.setText(LogInfo.QQ);
		editAddress.setText(LogInfo.ADDRESS);
		editImg.setImageURI(LogInfo.IMAGE_URI);
		birthY.setText(LogInfo.BIRTH_Y+"");
		birthM.setText(LogInfo.BIRTH_M+"");
		birthD.setText(LogInfo.BIRTH_D+"");	
		
		//点击响应
		birthY.setOnClickListener(md);
		birthM.setOnClickListener(md);
		birthD.setOnClickListener(md);
		asd.setOnClickListener(md);
		dsa.setOnClickListener(md);
	}
	
	public boolean checkChanged(){
		boolean tag=false;	//false为无更改，true为有更改
		if(editName.getText().toString().isEmpty()){
			Toast.makeText(this, "用户名称为空，将不进行更改", Toast.LENGTH_SHORT).show();
		}else if (!editName.getText().toString().equals(LogInfo.NAME)) {
			LogInfo.NAME = editName.getText().toString();
			tag=true;
		}
		//性别
		if (sex.getCheckedRadioButtonId() == R.id.manButton && LogInfo.SEX == 1
				|| sex.getCheckedRadioButtonId() == R.id.womanButton && LogInfo.SEX == 0) {
			LogInfo.SEX ^= 1; 
			tag = true;
		}
		if (!editSay.getText().toString().equals(LogInfo.SAY)) {
			LogInfo.SAY = editSay.getText().toString();
			tag=true;
		}
		if (!editCallPhone.getText().toString().equals(LogInfo.CALLPHONE)) {
			LogInfo.CALLPHONE = editCallPhone.getText().toString();
			tag=true;
		}
		if (!editPhone.getText().toString().equals(LogInfo.PHONE)) {
			LogInfo.PHONE = editPhone.getText().toString();
			tag=true;
		}
		if (!editEmail.getText().toString().equals(LogInfo.E_MAIL)) {
			LogInfo.E_MAIL = editEmail.getText().toString();
			tag=true;
		}
		if (!editQQ.getText().toString().equals(LogInfo.QQ)) {
			LogInfo.QQ = editQQ.getText().toString();
			tag=true;
		}
		if (!editAddress.getText().toString().equals(LogInfo.ADDRESS)) {
			LogInfo.ADDRESS = editAddress.getText().toString();
			tag=true;
		}
		
		// 日期
		if (Integer.parseInt(birthY.getText().toString()) != LogInfo.BIRTH_Y
				|| Integer.parseInt(birthM.getText().toString()) != LogInfo.BIRTH_M
				|| Integer.parseInt(birthD.getText().toString()) != LogInfo.BIRTH_D) {
			LogInfo.BIRTH_Y = (int) Integer.parseInt(birthY
					.getText().toString());
			LogInfo.BIRTH_M = (int) Integer.parseInt(birthM
					.getText().toString());
			LogInfo.BIRTH_D = (int) Integer.parseInt(birthD
					.getText().toString());
			tag = true;
		}
		return tag;
	}
	
	public class MyDateClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new DatePickerDialog(AllMessage.this, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					birthY.setText(year+"");
					birthM.setText(monthOfYear+"");
					birthD.setText(dayOfMonth+"");
					System.out.println("setdate");
				}
				
			}, LogInfo.BIRTH_Y, LogInfo.BIRTH_M, LogInfo.BIRTH_D).show();
		}
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(checkChanged()){
			//上传数据
			Toast.makeText(this, "数据更改，上传数据", Toast.LENGTH_SHORT).show();
		}
		super.onDestroy();
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}
}
