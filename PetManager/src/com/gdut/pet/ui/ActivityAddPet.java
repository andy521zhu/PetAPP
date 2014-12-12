package com.gdut.pet.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.mywheelview.ArrayWheelAdapter;
import com.gdut.pet.mywheelview.WheelView;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.ui.mypet.R;

public class ActivityAddPet extends Activity implements View.OnClickListener
{
	private static final String TAG = "com.gdut.pet.ui.ActivityAddPet";
	private Context mContext;

	private ImageView image_camera;// ����ͷ�� ���ѡȡ��Ƭ
	private Button back_add_pet;// ����
	private TextView addPetSure;// ȷ����ӳ���
	private EmojiconEditText edittext_name;// �༭��������
	private LinearLayout layout_gender;// �����Ա�ѡ��
	private LinearLayout layout_age;// ��������ѡ��
	private EmojiconEditText text_choose_species;// ����Ʒ��ѡ��
	private EmojiconEditText edit_choose_food;// ��дʳ��
	private EmojiconEditText edittext_description;// ��������

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_pet);
		mContext = this;
		L.i(TAG, "oncreate");
		findViews();
	}

	private void findViews()
	{
		// TODO Auto-generated method stub
		image_camera = (ImageView) findViewById(R.id.image_camera_add_pet);
		back_add_pet = (Button) findViewById(R.id.back_add_pet);
		addPetSure = (TextView) findViewById(R.id.add_pet_sure);
		edittext_name = (EmojiconEditText) findViewById(R.id.edittext_name);
		layout_gender = (LinearLayout) findViewById(R.id.layout_gender);
		layout_age = (LinearLayout) findViewById(R.id.layout_age_add_pet);
		text_choose_species = (EmojiconEditText) findViewById(R.id.edit_choose_species);
		edit_choose_food = (EmojiconEditText) findViewById(R.id.edit_choose_food);
		edittext_description = (EmojiconEditText) findViewById(R.id.edittext_description);

		image_camera.setOnClickListener(this);
		back_add_pet.setOnClickListener(this);
		addPetSure.setOnClickListener(this);
		edittext_name.setOnClickListener(this);
		layout_gender.setOnClickListener(this);
		layout_age.setOnClickListener(this);
		text_choose_species.setOnClickListener(this);
		edit_choose_food.setOnClickListener(this);

	}

	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		L.i(TAG, "onStart");
		super.onStart();
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		L.i(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		L.i(TAG, "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.image_camera_add_pet:
			toastMgr.builder.display("��Ƭ", 0);
			Intent intent = new Intent();
			intent.setClass(ActivityAddPet.this, ActivitySelectPic.class);
			startActivityForResult(intent, TO_SELECT_PHOTO);
			break;
		case R.id.back_add_pet:
			toastMgr.builder.display("����", 0);
			finish();// ������finish�������һЩ��Դ�ͷŵĴ��� ��ʱ�Ȳ���
			break;
		case R.id.add_pet_sure:
			toastMgr.builder.display("ȷ��", 0);
			postPetToServer();
			break;
		case R.id.edittext_name:
			toastMgr.builder.display("����", 0);
			break;
		case R.id.layout_gender:
			toastMgr.builder.display("�Ա�", 0);
			if (isMale)
			{
				((ImageView) findViewById(R.id.image_gender_add_pet))
						.setImageDrawable(getResources().getDrawable(
								R.drawable.signup_owner_icon_gender_female));
				isMale = false;
			}
			else
			{
				((ImageView) findViewById(R.id.image_gender_add_pet))
						.setImageDrawable(getResources().getDrawable(
								R.drawable.signup_owner_icon_gender_male));
				isMale = true;
			}
			break;
		case R.id.layout_age_add_pet:
			toastMgr.builder.display("����", 0);
			choseAge();
			break;
		case R.id.edit_choose_species:
			toastMgr.builder.display("����", 0);
			break;

		case R.id.edit_choose_food:
			toastMgr.builder.display("ʳ��", 0);
			break;

		default:
			break;
		}
	}

	/**
	 * ȷ�� ���֮��, ����Ϣ��������
	 */
	private void postPetToServer()
	{
		// TODO Auto-generated method stub
		// ������Ƭ
		String petImagePathString = mGetPicPath;
		// ��������
		String petName = edittext_name.getText().toString().trim();
		// �Ա�
		boolean petSex = isMale;
		// ����
		String petSpeciese = text_choose_species.getText().toString().trim();
		// ����
		String petAge = ((TextView) findViewById(R.id.text_add_pet_age))
				.getText().toString().trim();
		// food
		String petFood = edit_choose_food.getText().toString().trim();
		// ����
		String petDescription = edittext_description.getText().toString()
				.trim();
		// Ȼ���������������

	}

	/**
	 * �õ�ͼƬ�Ļص�
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO)
		{
			mGetPicPath = data.getStringExtra(ActivitySelectPic.KEY_PHOTO_PATH);
			L.i(TAG, "����ѡ���ͼƬ=" + mGetPicPath);
			Bitmap bm = BitmapFactory.decodeFile(mGetPicPath);
			image_camera.setImageBitmap(bm);
		}

		super.onActivityResult(requestCode, resultCode, data);

	}

	@SuppressWarnings("deprecation")
	void choseAge()
	{
		// �����Ự��
		final AlertDialog dialog = new AlertDialog.Builder(ActivityAddPet.this)
				.create();
		dialog.setTitle("ѡ�����䣺");
		// ȡ��dialog ��title
		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// ��������
		final LinearLayout ll = new LinearLayout(ActivityAddPet.this);
		// ���ò��ַ�ʽ��ˮƽ
		ll.setOrientation(LinearLayout.VERTICAL);

		final WheelView category1 = new WheelView(ActivityAddPet.this);
		category1.setVisibleItems(5);
		category1.setCyclic(true);
		category1.setAdapter(new ArrayWheelAdapter<String>(category_str1));

		// ��������
		LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

		lp1.gravity = Gravity.CENTER;
		// lp1.weight = (float) 0.6;
		ll.addView(category1, lp1);

		// Ϊ�Ự����ȷ����ť
		dialog.setButton("ȷ��", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				String cat1 = category_str1[category1.getCurrentItem()];
				((TextView) findViewById(R.id.text_add_pet_age)).setText(cat1);
				dialog.dismiss();
			}
		});
		dialog.setButton2("ȡ��", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		dialog.setView(ll);
		dialog.show();
		WindowManager.LayoutParams layoutParams = dialog.getWindow()
				.getAttributes();
		layoutParams.width = 200;
		layoutParams.height = LayoutParams.WRAP_CONTENT;
		// dialog.getWindow().setAttributes(layoutParams);
		dialog.getWindow().setLayout(400, LayoutParams.WRAP_CONTENT);
	}

	/**
	 * 
	 */

	private static boolean isMale = true;

	public String category_str1[] = new String[]
	{ "   �Ѿ�ȥ��   ", "  С��1��  ", "  1��  ", "  2��  ", "  3��  ", "  4��  ",
			"  5��  ", "  6��  ", "  7��  ", "  8��  ", "  9��  ", "  10��  ",
			"  ����10��  " };

	/**
	 * ȥ�ϴ��ļ�
	 */
	protected static final int TO_UPLOAD_FILE = 1;
	/**
	 * �ϴ��ļ���Ӧ
	 */
	protected static final int UPLOAD_FILE_DONE = 2; //
	/**
	 * ѡ���ļ�
	 */
	public static final int TO_SELECT_PHOTO = 3;
	/**
	 * �ϴ���ʼ��
	 */
	private static final int UPLOAD_INIT_PROCESS = 4;
	/**
	 * �ϴ���
	 */
	private static final int UPLOAD_IN_PROCESS = 5;

	/**
	 * ѡ����Ƭ֮���·��
	 */
	private String mGetPicPath = "";

}
