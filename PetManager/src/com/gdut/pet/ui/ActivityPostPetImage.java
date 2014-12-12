package com.gdut.pet.ui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdut.pet.common.network.UploadUtil;
import com.gdut.pet.common.network.UploadUtil.OnUploadProcessListener;
import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

public class ActivityPostPetImage extends Activity implements
		OnUploadProcessListener
{

	private static final String TAG = "com.gdut.pet.ui.ActivityPostPetImage";
	private Context mContext;

	private Button backButton;
	private TextView confirmButton;
	private EditText replyEditText;
	private ImageView imagePostPet;
	private CheckBox checkBoxPetLost;
	private CheckBox checkBoxPetFound;
	private boolean isPetLostChecked;
	private boolean isPetFoundChecked;
	private String mGetPicPath;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_post_pet_image);
		mContext = this;
		L.i(TAG, "onCreate");
		findViews();
	}

	void findViews()
	{
		// 选择图片
		imagePostPet = (ImageView) findViewById(R.id.image_post_pet_image);
		imagePostPet.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ActivityPostPetImage.this,
						ActivitySelectPic.class);
				startActivityForResult(intent, TO_SELECT_PHOTO);

			}
		});
		// 返回
		backButton = (Button) findViewById(R.id.post_pet_back);
		backButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				L.i(TAG, "finish()---back click");
				finish();
			}
		});
		// 确定发帖 海燕选择宠物类型我擦 妈蛋
		confirmButton = (TextView) findViewById(R.id.post_pet_confirm);
		confirmButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				// 确认评论 的一些逻辑
				L.i(TAG, "confirmButton click");
				toastMgr.builder.display("确认发图", 0);
				/**
				 * 判断是否选择了宠物
				 */
				isPetFoundChecked = checkBoxPetFound.isChecked();
				isPetLostChecked = checkBoxPetLost.isChecked();
				if (isPetLostChecked == true && isPetFoundChecked == true)
				{
					toastMgr.builder.displayCenter("丢失和找到不能同时选择, 请重新选择", 0);
					return;
				}
				// 这里联网操作
				UploadUtil uploadUtil = UploadUtil.getInstance();
				uploadUtil
						.setOnUploadProcessListener(ActivityPostPetImage.this);
				File file = new File(mGetPicPath);
				Map<String, String> params = new HashMap<String, String>();
				params.put("orderId", "11111");
				uploadUtil
						.uploadFileMy(
								mContext,
								file,
								"pic",
								// "http://10.21.63.113:8080/TestForPet/servlet/getAction?action=testUploadImage",
								"http://10.21.63.145:8080/PetMgr/servlet/MobileUploadImage?action=1",
								params);

			}
		});

		// 宠物丢失找到的标记
		checkBoxPetLost = (CheckBox) findViewById(R.id.checkBox_pet_lost);
		checkBoxPetFound = (CheckBox) findViewById(R.id.checkBox_pet_found);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO)
		{
			mGetPicPath = data.getStringExtra(ActivitySelectPic.KEY_PHOTO_PATH);
			L.i(TAG, "最终选择的图片=" + mGetPicPath);
			Bitmap bm = BitmapFactory.decodeFile(mGetPicPath);
			imagePostPet.setImageBitmap(bm);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(mContext);
	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(mContext);
	}

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

	@Override
	public void onUploadDone(int responseCode, String message)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onUploadProcess(int uploadSize)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void initUpload(int fileSize)
	{
		// TODO Auto-generated method stub

	}

}
