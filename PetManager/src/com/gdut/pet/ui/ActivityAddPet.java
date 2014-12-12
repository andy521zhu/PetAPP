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

	private ImageView image_camera;// 动物头像 点击选取照片
	private Button back_add_pet;// 返回
	private TextView addPetSure;// 确定添加宠物
	private EmojiconEditText edittext_name;// 编辑宠物名字
	private LinearLayout layout_gender;// 宠物性别选择
	private LinearLayout layout_age;// 宠物年龄选择
	private EmojiconEditText text_choose_species;// 宠物品种选择
	private EmojiconEditText edit_choose_food;// 填写食物
	private EmojiconEditText edittext_description;// 宠物描述

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
			toastMgr.builder.display("照片", 0);
			Intent intent = new Intent();
			intent.setClass(ActivityAddPet.this, ActivitySelectPic.class);
			startActivityForResult(intent, TO_SELECT_PHOTO);
			break;
		case R.id.back_add_pet:
			toastMgr.builder.display("返回", 0);
			finish();// 可以在finish里面添加一些资源释放的代码 暂时先不管
			break;
		case R.id.add_pet_sure:
			toastMgr.builder.display("确定", 0);
			postPetToServer();
			break;
		case R.id.edittext_name:
			toastMgr.builder.display("名字", 0);
			break;
		case R.id.layout_gender:
			toastMgr.builder.display("性别", 0);
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
			toastMgr.builder.display("年龄", 0);
			choseAge();
			break;
		case R.id.edit_choose_species:
			toastMgr.builder.display("种类", 0);
			break;

		case R.id.edit_choose_food:
			toastMgr.builder.display("食物", 0);
			break;

		default:
			break;
		}
	}

	/**
	 * 确定 点击之后, 将信息传到网上
	 */
	private void postPetToServer()
	{
		// TODO Auto-generated method stub
		// 宠物照片
		String petImagePathString = mGetPicPath;
		// 宠物名字
		String petName = edittext_name.getText().toString().trim();
		// 性别
		boolean petSex = isMale;
		// 种类
		String petSpeciese = text_choose_species.getText().toString().trim();
		// 年龄
		String petAge = ((TextView) findViewById(R.id.text_add_pet_age))
				.getText().toString().trim();
		// food
		String petFood = edit_choose_food.getText().toString().trim();
		// 描述
		String petDescription = edittext_description.getText().toString()
				.trim();
		// 然后就是联网操作了

	}

	/**
	 * 拿到图片的回调
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO)
		{
			mGetPicPath = data.getStringExtra(ActivitySelectPic.KEY_PHOTO_PATH);
			L.i(TAG, "最终选择的图片=" + mGetPicPath);
			Bitmap bm = BitmapFactory.decodeFile(mGetPicPath);
			image_camera.setImageBitmap(bm);
		}

		super.onActivityResult(requestCode, resultCode, data);

	}

	@SuppressWarnings("deprecation")
	void choseAge()
	{
		// 创建会话框
		final AlertDialog dialog = new AlertDialog.Builder(ActivityAddPet.this)
				.create();
		dialog.setTitle("选择年龄：");
		// 取消dialog 的title
		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 创建布局
		final LinearLayout ll = new LinearLayout(ActivityAddPet.this);
		// 设置布局方式：水平
		ll.setOrientation(LinearLayout.VERTICAL);

		final WheelView category1 = new WheelView(ActivityAddPet.this);
		category1.setVisibleItems(5);
		category1.setCyclic(true);
		category1.setAdapter(new ArrayWheelAdapter<String>(category_str1));

		// 创建参数
		LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

		lp1.gravity = Gravity.CENTER;
		// lp1.weight = (float) 0.6;
		ll.addView(category1, lp1);

		// 为会话创建确定按钮
		dialog.setButton("确定", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				String cat1 = category_str1[category1.getCurrentItem()];
				((TextView) findViewById(R.id.text_add_pet_age)).setText(cat1);
				dialog.dismiss();
			}
		});
		dialog.setButton2("取消", new DialogInterface.OnClickListener()
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
	{ "   已经去世   ", "  小于1岁  ", "  1岁  ", "  2岁  ", "  3岁  ", "  4岁  ",
			"  5岁  ", "  6岁  ", "  7岁  ", "  8岁  ", "  9岁  ", "  10岁  ",
			"  大于10岁  " };

	/**
	 * 去上传文件
	 */
	protected static final int TO_UPLOAD_FILE = 1;
	/**
	 * 上传文件响应
	 */
	protected static final int UPLOAD_FILE_DONE = 2; //
	/**
	 * 选择文件
	 */
	public static final int TO_SELECT_PHOTO = 3;
	/**
	 * 上传初始化
	 */
	private static final int UPLOAD_INIT_PROCESS = 4;
	/**
	 * 上传中
	 */
	private static final int UPLOAD_IN_PROCESS = 5;

	/**
	 * 选择照片之后的路径
	 */
	private String mGetPicPath = "";

}
