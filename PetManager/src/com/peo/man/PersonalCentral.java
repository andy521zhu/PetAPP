//��������	--ע�� ��¼ �� ��ȡ��������
package com.peo.man;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.peo.ceneral.*;
import com.ui.mypet.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class PersonalCentral extends ListActivity{

	boolean ifLogin = LogInfo.IF_LOG;
	ListView lv;
	List<Map<String, Object>> list;
	SimpleAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		list = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(this,getData(),R.layout.pet_listview,
				new String[]{"petImage","petName","petSex"},
				new int[]{R.id.petImage,R.id.petName,R.id.petSex});
		lv=this.getListView();
		LayoutInflater infla = LayoutInflater.from(this);   
	    View headView = infla.inflate(R.layout.activity_personal, null); 
	    lv.addHeaderView(headView);		
	    setListAdapter(adapter);
		
	    if(!ifLogin){
			//��ʾ��¼
			showDialog(this);
		}
	    else{
			getPeraonalData();
		}
	    
	    headView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(PersonalCentral.this,AllMessage.class);
				startActivity(intent);
			}
	    	
	    });
	    
	    lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(PersonalCentral.this, "arg2="+arg2+"arg3="+arg3 , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(PersonalCentral.this,PetMessage.class);
				Bundle bundle=new Bundle();
				bundle.putInt("petId", arg2-1);//arg2��0��ʼ�����У�arg3��1��ʼ������
				intent.putExtras(bundle);
				startActivity(intent);
			}
	    	
	    });
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(LogInfo.MAN_STOR==1 && LogInfo.IF_LOG){
			getPeraonalData();
			list.clear();
			list=getData();
            adapter.notifyDataSetChanged();
		}
	}
	//��ȡlist����
	private List<Map<String, Object>> getData() {

		Map<String, Object> map;
		for (int i = 0; i < LogInfo.PETNUM; i++) {
			map=new HashMap<String, Object>();
			map.put("petImage", R.drawable.ic_launcher);
			map.put("petName", LogInfo.MYPETS[i].name);
			map.put("petSay", LogInfo.MYPETS[i].descript);
			list.add(map);
		}
		return list;
	}

	//��ȡ��������
	public void getPeraonalData(){
		//����Ϊ��������
		getFalseData();		
	}
	
	//��ʾ�Ի���
	private void showDialog(Context context) {  
        AlertDialog.Builder builder = new AlertDialog.Builder(context);  
        builder.setIcon(R.drawable.ic_launcher);  
        builder.setTitle("�r(�s���t)�q");   
        builder.setMessage("����û�е�½����ѡ��ע����¼����...");  
        
        builder.setPositiveButton("ע��",  
                new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {  
                    	Intent intent = new Intent(PersonalCentral.this,Register.class);
                        startActivity(intent);
                    }  
                });  
        
        builder.setNeutralButton("��¼",  
                new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {  
                    	LogInfo.MAN_STOR = 1;
                        Intent intent=new Intent(PersonalCentral.this,LoginMan.class);
                        startActivity(intent);
                        
                    }  
                });   
        
        builder.show();
    }  
	
	public void getFalseData(){
		LogInfo.NAME = "he";
		LogInfo.SEX = 1; // 0Ϊ�У�1ΪŮ
		LogInfo.SAY = "hai"; // ˵˵
		LogInfo.CALLPHONE = "123";
		LogInfo.PHONE = "3123";
		LogInfo.QQ = "234234";
		LogInfo.E_MAIL = "wen.3";
		LogInfo.PETNUM = 2;
		LogInfo.ADDRESS = "american";
		LogInfo.MYPETS = new PetInfo[2];
		LogInfo.MYPETS[0] = new PetInfo("pet1");
		LogInfo.MYPETS[0].age=1;
		LogInfo.MYPETS[0].birth_y=2012;
		LogInfo.MYPETS[0].birth_m=2;
		LogInfo.MYPETS[0].birth_d=1;
		LogInfo.MYPETS[0].color="yellow";
		LogInfo.MYPETS[0].descript="I am a boy";
		LogInfo.MYPETS[1] = new PetInfo("pet2");
		LogInfo.MYPETS[1].age=2;
		LogInfo.MYPETS[1].birth_y=2011;
		LogInfo.MYPETS[1].birth_m=4;
		LogInfo.MYPETS[1].birth_d=6;
		LogInfo.MYPETS[1].color="blue";
		LogInfo.MYPETS[1].descript="I am a girl";
	}
}
