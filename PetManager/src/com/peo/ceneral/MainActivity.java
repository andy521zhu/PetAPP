/**
 * 主界面
 */
package com.peo.ceneral;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.peo.location.CELLLocation;
import com.peo.location.GPSLocation;
import com.peo.man.LoginMan;
import com.peo.man.PersonalCentral;
import com.peo.stor.StorCentral;
import com.peo.straypet.StrayPetList;
import com.ui.mypet.R;

public class MainActivity extends Activity {

	Button button1,button2,button3,button4;
	GPSLocation getlocation;
	Thread t;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		button1=(Button)findViewById(R.id.personalCentral);//个人中心
		button2=(Button)findViewById(R.id.petShop);
		button3=(Button)findViewById(R.id.petLoose);
		button4=(Button)findViewById(R.id.petFind);
		
		CELLLocation.getLocationByCell(this);
		GPSLocation.getLocal(this);		
		
		button1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it=new Intent(MainActivity.this,PersonalCentral.class);
				startActivity(it);
			}
			
		});
		
		button2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it=new Intent(MainActivity.this,StorCentral.class);
				startActivity(it);
			}
			
		});
		
		button3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it=new Intent(MainActivity.this,StrayPetList.class);
				startActivity(it);
			}
			
		});
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		GPSLocation.removeLocation();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
