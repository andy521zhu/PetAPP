//注册
package com.peo.man;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.peo.ceneral.JudgeFormal;
import com.peo.ceneral.LogInfo;
import com.ui.mypet.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//对于多个包的class记得注册为public
public class Register extends Activity{
	
	EditText register,myName,passWord,surePassWord;
	Button registerButton,returnButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		register=(EditText)findViewById(R.id.registerText);
		myName=(EditText)findViewById(R.id.myNameText);
		passWord=(EditText)findViewById(R.id.passWordText);
		surePassWord=(EditText)findViewById(R.id.surePassWordText);
		registerButton=(Button)findViewById(R.id.registerButton);
		returnButton=(Button)findViewById(R.id.returnButton);
		
		focuseChangeListener();
		
		registerButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(register.getText().toString().isEmpty() 
						|| !JudgeFormal.isMobileNO(register.getText().toString()) 
						&& !JudgeFormal.isEmail(register.getText().toString())){
					Toast.makeText(Register.this, "请填入有效手机号或邮箱名", Toast.LENGTH_SHORT).show();
				}
				else if(myName.getText().toString().isEmpty()){
					Toast.makeText(Register.this, "请填入用户名", Toast.LENGTH_SHORT).show();
				}
				else if(!JudgeFormal.isLogical(myName.getText().toString())){
					Toast.makeText(Register.this, "用户名含非法字符", Toast.LENGTH_SHORT).show();
				}
				else if(passWord.getText().toString().isEmpty()
						|| surePassWord.getText().toString().isEmpty()){
					Toast.makeText(Register.this, "请填入密码", Toast.LENGTH_SHORT).show();
				}
				else if(passWord.getText().length()<6){
					Toast.makeText(Register.this, "密码长度要大于5", Toast.LENGTH_SHORT).show();
				}
				else if(! passWord.getText().toString().equals(surePassWord.getText().toString())){
					Toast.makeText(Register.this, "输入的密码不一置", Toast.LENGTH_SHORT).show();
				}
				else{
					//成功提示登录
					showDialog(Register.this);
					
					LogInfo.NAME=myName.getText().toString();
					LogInfo.LOGNAME=register.getText().toString();
					LogInfo.PASSWORD=passWord.getText().toString();
				}
			}
			
		});
		
		returnButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
	}
	
	//提示对话框
	public void showDialog(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("╮(╯▽╰)╭");
		builder.setMessage("注册成功，点击前往登录");

		builder.setNeutralButton("立即登录", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				LogInfo.MAN_STOR = 1;	//登录为人
				Intent intent = new Intent(Register.this, LoginMan.class);
				startActivity(intent);
				finish();
			}
		});

		builder.show();
	}
	
	//焦点改变的响应
	public void focuseChangeListener(){
		register.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!register.isFocused()){	
					if (register.getText().toString().isEmpty()
							|| !JudgeFormal.isMobileNO(register.getText().toString())
							&& !JudgeFormal.isEmail(register.getText().toString())) {
						Toast.makeText(Register.this, "请填入有效手机号或邮箱名", Toast.LENGTH_SHORT).show();
					}
				}
			}
			
		});
		myName.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!myName.isFocused()){
					if (myName.getText().toString().isEmpty()) {
						Toast.makeText(Register.this, "请填入用户名", Toast.LENGTH_SHORT).show();
					} else if (!JudgeFormal.isLogical(myName.getText().toString())) {
						Toast.makeText(Register.this, "用户名必须为数字字母组合", Toast.LENGTH_SHORT).show();
					}
				}
			}
			
		});
		
		passWord.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!passWord.isFocused()){
					if (passWord.getText().toString().isEmpty()) {
						Toast.makeText(Register.this, "请填入密码", Toast.LENGTH_SHORT).show();
					} else if (passWord.getText().length() < 6) {
						Toast.makeText(Register.this, "密码长度至少为6位", Toast.LENGTH_SHORT).show();
					} else if(!JudgeFormal.isPassword(passWord.getText().toString())){
						Toast.makeText(Register.this, "密码只能为数字，字母组合", Toast.LENGTH_SHORT).show();
					}
				}
			}
			
		});
		
		surePassWord.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!surePassWord.isFocused()){
					if(surePassWord.getText().toString().isEmpty()){
						Toast.makeText(Register.this, "请输入确认密码",Toast.LENGTH_SHORT).show();
					}
					if (!passWord.getText().toString()
							.equals(surePassWord.getText().toString())) {
						Toast.makeText(Register.this, "输入的密码不一致",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
			
		});
		
		
	}
	
	
	@Override
	public void finish(){
//		System.out.println("finish Register Activity");
		super.finish();
	}
}
