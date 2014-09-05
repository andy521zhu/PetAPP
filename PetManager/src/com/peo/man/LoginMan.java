/**
 * 登录页面， 
 */
package com.peo.man;

import com.peo.ceneral.JudgeFormal;
import com.peo.ceneral.LogInfo;
import com.ui.mypet.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginMan extends Activity{
	
	EditText logName,logPass;
	CheckBox remPass,autoLog;
	Button log,cancal;
	TextView forgetPass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_man);
		
		logName=(EditText)findViewById(R.id.loginName);
		logPass=(EditText)findViewById(R.id.loginPassWord);
		remPass=(CheckBox)findViewById(R.id.remPass);
		autoLog=(CheckBox)findViewById(R.id.autoLogin);
		log=(Button)findViewById(R.id.loginIn);
		cancal=(Button)findViewById(R.id.retOut);
		forgetPass=(TextView)findViewById(R.id.forgetPass);
		
		log.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(logName.getText().toString().isEmpty()){
					Toast.makeText(LoginMan.this, "账号不能为空", Toast.LENGTH_SHORT).show();
				}
				else if(logPass.getText().toString().isEmpty()){
					Toast.makeText(LoginMan.this, "密码不能为空", Toast.LENGTH_SHORT).show();
				}
				else if(!JudgeFormal.isLogical(logName.getText().toString()) || !JudgeFormal.isEmail(logName.getText().toString()) 
						&& !JudgeFormal.isMobileNO(logName.getText().toString())){
					Toast.makeText(LoginMan.this, "非法账号，请输入注册时输入的手机号或邮箱！", Toast.LENGTH_SHORT).show();
				}
				else{	//输入数据合法
					if(login(logName.getText().toString(), logPass.getText().toString())){
						//读取数据
						getManData();
						Toast.makeText(LoginMan.this, "登录成功", Toast.LENGTH_SHORT).show();
						LogInfo.IF_LOG=true;
						finish();
					}
					else{
						Toast.makeText(LoginMan.this, "账号或密码错误，登录失败", Toast.LENGTH_SHORT).show();
					}
				}
			}
			
		});
		
		cancal.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
		
		//侦听
		forgetPass.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()==KeyEvent.ACTION_DOWN){
					forgetPass.setTextColor(Color.rgb(192, 192, 192));
				}
				else 
					forgetPass.setTextColor(Color.RED);
				return true;
			}
			
		});
	}
	
	//登录函数
	public boolean login(String name, String passWord){
		
		return true;
	}
	
	//读取登录数据函数
	public void getManData(){
		if(remPass.isChecked()){
		//	Toast.makeText(LoginMan.this, "记住密码", Toast.LENGTH_SHORT).show();
		}
		if(autoLog.isChecked()){
		//	Toast.makeText(LoginMan.this, "自动登录", Toast.LENGTH_SHORT).show();
		}
	}
	
	
}
