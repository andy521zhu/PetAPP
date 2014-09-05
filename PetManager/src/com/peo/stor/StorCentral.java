/**
 * 商城的主菜单页面，
 */
package com.peo.stor;

import com.peo.ceneral.LogInfo;
import com.peo.man.LoginMan;
import com.ui.mypet.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StorCentral extends Activity{
	Button storRegister,storLogin,nearStor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stor_central);
		
		storRegister=(Button)findViewById(R.id.storRegisterButton);
		storLogin=(Button)findViewById(R.id.storLoginButton);
		nearStor=(Button)findViewById(R.id.nearPetStorButton);
		
		storRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(StorCentral.this,RegisterStor.class);
				startActivity(intent);
			}
		});
		
		storLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LogInfo.MAN_STOR=0;//商店
				Intent intent = new Intent(StorCentral.this,LoginMan.class);
				startActivity(intent);
			}
		});
		
		nearStor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
