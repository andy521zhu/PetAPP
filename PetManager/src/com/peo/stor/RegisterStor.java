/**
 * �����ע��ҳ�棬
 * 
 * û��setContentView(R.layout.activity_register_stor);�ᱨ��ָ���쳣
 * phone.setBackgroundColor(Color.rgb(245, 245, 245));//������ɫ�ķ���
 * phone.requestFocus();//��ý���
 */
package com.peo.stor;

import com.peo.ceneral.JudgeFormal;
import com.peo.ceneral.LogInfo;
import com.peo.man.LoginMan;
import com.peo.man.Register;
import com.ui.mypet.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterStor extends Activity{
	EditText storRegister,password,surePassword,name,address,phone,number,qq,email,startTime,stopTime,anotherServer;
	CheckBox phoneBox,numberBox,qqBox,emailBox;
	CheckBox hospBox,foodBox,hairdressingBox,sellBox,applianceBox,travelBox,anotherBox;
	Button register,cancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_stor);		
		
		storRegister=(EditText)findViewById(R.id.regStorRegisterText);
		name=(EditText)findViewById(R.id.regStorNameText);
		password=(EditText)findViewById(R.id.regStorPassWordText);
		surePassword=(EditText)findViewById(R.id.regSureStorPassWordText);
		address=(EditText)findViewById(R.id.regStorAddress);
		phone=(EditText)findViewById(R.id.regStorPhone);
		number=(EditText)findViewById(R.id.regStorNumber);
		qq=(EditText)findViewById(R.id.regStorQQ);
		email=(EditText)findViewById(R.id.regStorEmail);
		startTime=(EditText)findViewById(R.id.regStartTime);
		stopTime=(EditText)findViewById(R.id.regStopTime);
		anotherServer=(EditText)findViewById(R.id.regAnotherServerText);
		
		phoneBox=(CheckBox)findViewById(R.id.regCheckStorPhone);
		numberBox=(CheckBox)findViewById(R.id.regCheckStorNumber);
		qqBox=(CheckBox)findViewById(R.id.regCheckStorQQ);
		emailBox=(CheckBox)findViewById(R.id.regCheckStorEmail);
		hospBox=(CheckBox)findViewById(R.id.regHospital);
		foodBox=(CheckBox)findViewById(R.id.regPetFood);
		hairdressingBox=(CheckBox)findViewById(R.id.regHairdressing);
		sellBox=(CheckBox)findViewById(R.id.regSellPet);
		applianceBox=(CheckBox)findViewById(R.id.regPetappliance);
		travelBox=(CheckBox)findViewById(R.id.regTravel);
		anotherBox=(CheckBox)findViewById(R.id.regAnother);
		
		register=(Button)findViewById(R.id.storRegisterButton);
		cancel=(Button)findViewById(R.id.storReturnButton);
		
		focuseChangeListener();
		
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (storRegister.getText().toString().isEmpty()
						|| !JudgeFormal.isMobileNO(storRegister.getText()
								.toString())
						&& !JudgeFormal.isEmail(storRegister.getText()
								.toString())) {
					Toast.makeText(RegisterStor.this, "��������Ч�ֻ��Ż�������",
							Toast.LENGTH_SHORT).show();
				} else if (name.getText().toString().isEmpty()
						|| !JudgeFormal.isLogical(name.getText().toString())) {
					Toast.makeText(RegisterStor.this, "�����������",
							Toast.LENGTH_SHORT).show();
				} else if (password.getText().toString().isEmpty()
						|| password.getText().length() < 6
						|| !JudgeFormal.isPassword(password.getText().toString())) {
					Toast.makeText(RegisterStor.this, "������6λ���ϵ�����,����ֻ�ܰ������ֺ���ĸ",
							Toast.LENGTH_SHORT).show();
				} else if (surePassword.getText().toString().isEmpty()) {
					Toast.makeText(RegisterStor.this, "������ȷ������",
							Toast.LENGTH_SHORT).show();
				} else if (!password.getText().toString()
						.equals(surePassword.getText().toString())) {
					Toast.makeText(RegisterStor.this, "��������벻һ��",
							Toast.LENGTH_SHORT).show();
				} else if (phoneBox.isChecked()
						&& (phone.getText().toString().isEmpty() || !JudgeFormal
								.isMobileNO(phone.getText().toString()))) {
					Toast.makeText(RegisterStor.this, "�����ֻ���������",
							Toast.LENGTH_SHORT).show();
				} else if (numberBox.isChecked()
						&& number.getText().toString().isEmpty()) {
					Toast.makeText(RegisterStor.this, "����̻���������",
							Toast.LENGTH_SHORT).show();
				} else if (qqBox.isChecked()
						&& (qq.getText().toString().isEmpty() || !JudgeFormal
								.isQQNumber(qq.getText().toString()))) {
					Toast.makeText(RegisterStor.this, "����QQ����",
							Toast.LENGTH_SHORT).show();
				} else if (emailBox.isChecked()
						&& (email.getText().toString().isEmpty() || !JudgeFormal
								.isEmail(email.getText().toString()))) {
					Toast.makeText(RegisterStor.this, "������������",
							Toast.LENGTH_SHORT).show();
				} else if(!hospBox.isChecked()
						|| !foodBox.isChecked() 
						|| !hairdressingBox.isChecked() 
						|| !sellBox.isChecked() 
						|| !applianceBox.isChecked() 
						|| !travelBox.isChecked() 
						|| !anotherBox.isChecked()){
					Toast.makeText(RegisterStor.this, "������ѡ��һ����������",
							Toast.LENGTH_SHORT).show();
				}else{
					//�ɳɹ�ע��
					if(true){
						showDialog(RegisterStor.this);
					}
				}
			}
		});
			
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	//��ʾ�Ի���
	public void showDialog(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("�r(�s���t)�q");
		builder.setMessage("ע��ɹ������ǰ����¼");

		builder.setNeutralButton("������¼", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				LogInfo.MAN_STOR = 0;		//���õ�¼��Ϊ�̵�
				Intent intent = new Intent(RegisterStor.this, LoginMan.class);
				startActivity(intent);
				finish();
			}
		});

		builder.show();
	}
	
	public void focuseChangeListener() {
		storRegister.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!storRegister.isFocused()) {
					if (storRegister.getText().toString().isEmpty()
							|| !JudgeFormal.isMobileNO(storRegister.getText()
									.toString())
							&& !JudgeFormal.isEmail(storRegister.getText()
									.toString())) {
						Toast.makeText(RegisterStor.this, "��������Ч�ֻ��Ż�������",
								Toast.LENGTH_SHORT).show();
					}
				}
			}

		});
		name.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!name.isFocused()) {
					if (name.getText().toString().isEmpty()) {
						Toast.makeText(RegisterStor.this, "���������",
								Toast.LENGTH_SHORT).show();
					} else if (!JudgeFormal
							.isLogical(name.getText().toString())) {
						Toast.makeText(RegisterStor.this, "�������ܺ��зǷ��ַ�",
								Toast.LENGTH_SHORT).show();
					}
				}
			}

		});

		password.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!password.isFocused()) {
					if (password.getText().toString().isEmpty()) {
						Toast.makeText(RegisterStor.this, "����������",
								Toast.LENGTH_SHORT).show();
					} else if (password.getText().length() < 6) {
						Toast.makeText(RegisterStor.this, "���볤������Ϊ6λ",
								Toast.LENGTH_SHORT).show();
					} else if(!JudgeFormal.isPassword(password.getText().toString())){
						Toast.makeText(RegisterStor.this, "ֻ��Ϊ������ĸ���",
								Toast.LENGTH_SHORT).show();
					}
				}
			}

		});

		surePassword.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!surePassword.isFocused()) {
					if (surePassword.getText().toString().isEmpty()) {
						Toast.makeText(RegisterStor.this, "������ȷ������",
								Toast.LENGTH_SHORT).show();
					}
					if (!password.getText().toString()
							.equals(surePassword.getText().toString())) {
						Toast.makeText(RegisterStor.this, "��������벻һ��",
								Toast.LENGTH_SHORT).show();
					}
				}
			}

		});

		phone.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!phone.isFocused()) {
					if (phone.getText().toString().isEmpty()) {
						Toast.makeText(RegisterStor.this, "�������ֻ�����",
								Toast.LENGTH_SHORT).show();
					}
					if (!JudgeFormal.isMobileNO(phone.getText().toString())) {
						Toast.makeText(RegisterStor.this, "��������Ч�ֻ�����",
								Toast.LENGTH_SHORT).show();
					}
				}
			}

		});
		
		number.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!number.isFocused()) {
					if (number.getText().toString().isEmpty()) {
						Toast.makeText(RegisterStor.this, "������绰����",
								Toast.LENGTH_SHORT).show();
					}
					else if (!JudgeFormal.isQQNumber(number.getText().toString())) {
						Toast.makeText(RegisterStor.this, "��������Ч�绰����",
								Toast.LENGTH_SHORT).show();
					}
				}
			}

		});
		
		qq.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!qq.isFocused()) {
					if (qq.getText().toString().isEmpty()) {
						Toast.makeText(RegisterStor.this, "������QQ����",
								Toast.LENGTH_SHORT).show();
					}
					if (!JudgeFormal.isQQNumber(qq.getText().toString())) {
						Toast.makeText(RegisterStor.this, "�����QQ���벻�Ϸ�",
								Toast.LENGTH_SHORT).show();
					}
				}
			}

		});
		
		email.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!email.isFocused()) {
					if (email.getText().toString().isEmpty()) {
						Toast.makeText(RegisterStor.this, "������QQ����",
								Toast.LENGTH_SHORT).show();
					}
					if (!JudgeFormal.isEmail(email.getText().toString())) {
						Toast.makeText(RegisterStor.this, "�����QQ���벻�Ϸ�",
								Toast.LENGTH_SHORT).show();
					}
				}
			}

		});
		
		phoneBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					phone.setFocusableInTouchMode(true);
					phone.setTextColor(Color.BLACK);
					phone.requestFocus();
					System.out.println("hehehehehe");
				}else
					phone.setFocusable(false);
					phone.setTextColor(Color.rgb(192, 192, 192));
			}
		});

		numberBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					number.setFocusableInTouchMode(true);
					number.setTextColor(Color.BLACK);
					number.requestFocus();
				}else{
					number.setFocusable(false);
					number.setTextColor(Color.rgb(192,192,192));
				}
			}
		});
		qqBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					qq.setFocusableInTouchMode(true);
					qq.setTextColor(Color.BLACK);
					qq.requestFocus();
				}else{
					qq.setFocusable(false);
					qq.setTextColor(Color.rgb(192,192,192));
				}
			}
		});
		emailBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					email.setFocusableInTouchMode(true);
					email.setTextColor(Color.BLACK);
					email.requestFocus();
				}else{
					email.setFocusable(false);
					email.setTextColor(Color.rgb(192,192,192));
				}
			}
		});

	}
}
