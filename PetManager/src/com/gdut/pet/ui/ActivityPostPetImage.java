package com.gdut.pet.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdut.pet.common.info.LocatedInfo;
import com.gdut.pet.common.network.PostBBSNetWork;
import com.gdut.pet.common.network.PostBBSNetWork.FailCallback;
import com.gdut.pet.common.network.UploadUtil;
import com.gdut.pet.common.network.UploadUtil.OnUploadProcessListener;
import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.common.utils.BaiduLocate;
import com.gdut.pet.common.utils.CompressPictureTool;
import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.config.Configs;
import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

public class ActivityPostPetImage extends Activity implements OnUploadProcessListener
{

	private static final String TAG = "com.gdut.pet.ui.ActivityPostPetImage";
	private Context mContext;

	private Button backButton;
	private TextView confirmButton;
	private EditText replyEditText;
	private String bbs_content;// 发帖内容

	private String bbs_type;// 发帖的类型, 1 丢失, 2 流浪 3. 普通
	private ImageView imagePostPet;
	private CheckBox checkBoxPetLost;
	private CheckBox checkBoxPetFound;
	private boolean isPetLostChecked;
	private boolean isPetFoundChecked;
	private boolean isPicSelected = false;
	private String mGetPicPath;// 从相册里面得到的图片的路径
	private String compressedPicPath;// 压缩后的图片的位置路径
	private BaiduLocate locate;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_post_pet_image);
		mContext = this;
		L.i(TAG, "onCreate");
		// 定位 测试
		locate = new BaiduLocate(mContext);
		locate.registerLocationListener();

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
				intent.setClass(ActivityPostPetImage.this, ActivitySelectPic.class);
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
		// 确定发帖 海燕选择宠物类型我擦
		confirmButton = (TextView) findViewById(R.id.post_pet_confirm);
		confirmButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
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
				if (isPicSelected == false)
				{
					toastMgr.builder.displayCenter("请选择上传图片", 0);
					return;
				}
				// 给发帖类型赋值
				if (isPetFoundChecked && isPetLostChecked != true)
				{
					bbs_type = "2";// 流浪
				}
				else if (isPetFoundChecked != true && isPetLostChecked)
				{
					bbs_type = "1";// 丢失
				}
				else if (isPetFoundChecked != true && isPetLostChecked != true)
				{
					bbs_type = "3";// 普通
				}

				LocatedInfo info = locate.getLocation();
				locate.stopLocation();

				// 得到用户写的内容
				bbs_content = replyEditText.getText().toString();

				// 确认评论 的一些逻辑

				SharedPreferences petPreferences = getSharedPreferences("petdata", MODE_PRIVATE);
				Set<String> petNameSet = petPreferences.getStringSet("petName", null);
				if (petNameSet == null || petNameSet.equals("") || petNameSet.isEmpty())
				{
					toastMgr.builder.display("还没有添加宠物，不能发图，请添加宠物", 0);
					return;
				}
				String[] petnameStrings;
				Iterator<String> it = petNameSet.iterator();
				List<String> list1 = new ArrayList<String>();
				while (it.hasNext())// 判断是否有下一个
				{
					list1.add(it.next());
				}
				petnameStrings = (String[]) list1.toArray(new String[0]);

				// 提示用户选择要上传照片的宠物
				new AlertDialog.Builder(mContext).setTitle("选择宠物").setMultiChoiceItems(petnameStrings, null, null)
						.setPositiveButton("选取", new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface arg0, int arg1)
							{
								// TODO Auto-generated method stub
								// toastMgr.builder.display("OK", 0);
								L.i(TAG, "confirmButton click");

								// 这里联网操作
								final UploadUtil uploadUtil = UploadUtil.getInstance();
								uploadUtil.setOnUploadProcessListener(ActivityPostPetImage.this);
								if (compressedPicPath == null || compressedPicPath.equals(""))
								{
									toastMgr.builder.display("选择图片出错,请重新选择", 0);
									return;
								}
								else
								{
									final File file = new File(compressedPicPath);
									final Map<String, String> params = new HashMap<String, String>();
									params.put("orderId", "11111");
									new Thread()
									{
										public void run()
										{
											super.run();
											uploadUtil.uploadFileMy(mContext, file, "pic",
											// "http://10.21.63.113:8080/TestForPet/servlet/getAction?action=testUploadImage",
													Configs.UPLOAD_BBS_IMAGE_PATH, params);
										};
									}.start();

								}

							}
						}).setNegativeButton("取消", new DialogInterface.OnClickListener()
						{

							@Override
							public void onClick(DialogInterface arg0, int arg1)
							{
								// TODO Auto-generated method stub
								toastMgr.builder.display("取消", 0);
								return;
							}
						}).show();

			}
		});

		// 宠物丢失找到的标记
		checkBoxPetLost = (CheckBox) findViewById(R.id.checkBox_pet_lost);
		checkBoxPetFound = (CheckBox) findViewById(R.id.checkBox_pet_found);

		// 帖子内容
		replyEditText = (EditText) findViewById(R.id.bbs_reply_content);
	}

	/**
	 * 选择图片后 执行的代码
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO)
		{
			mGetPicPath = data.getStringExtra(ActivitySelectPic.KEY_PHOTO_PATH);
			L.i(TAG, "最终选择的图片=" + mGetPicPath);
			// 这里 图片还是要压缩的
			CompressPictureTool compressPictureTool = new CompressPictureTool(mGetPicPath);
			Bitmap bm = compressPictureTool.compressPic();
			// Bitmap bm = BitmapFactory.decodeFile(mGetPicPath);
			imagePostPet.setImageBitmap(bm);
			// 保存一下看看/storage/sdcard0/DCIM/Camera
			String picName = mGetPicPath.substring(mGetPicPath.lastIndexOf("/"));
			String picPath = mGetPicPath.substring(0, mGetPicPath.lastIndexOf("/"));
			// picname = /IMG_20150119_100917.jpg picpath = /storage/sdcard0/DCIM/Camera
			Log.i(TAG, "picname = " + picName + "   picpath = " + picPath);
			File toSave = new File(picPath + "/" + "the_temp" + ".png");

			FileOutputStream fOut = null;
			try
			{
				fOut = new FileOutputStream(toSave);
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			bm.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			try
			{
				fOut.flush();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			try
			{
				fOut.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			// 得到压缩后的图片的路径
			compressedPicPath = toSave.getPath();
			isPicSelected = true;

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
		String imagePaht = "";
		// 上传成功的Code
		if (responseCode == UPLOAD_SUCCESS_CODE)
		{
			// 解析message这个json
			try
			{
				JSONObject jsonObject = new JSONObject(message);
				imagePaht = (String) jsonObject.get("path");
				String temp = imagePaht.replace("\\", "/");
				imagePaht = temp;
				temp = "<img src=" + imagePaht + ">";
				imagePaht = temp;
				bbs_content = bbs_content + imagePaht;
			}
			catch (Exception e)
			{
				// TODO: handle exception
			}

		}
		// 上传失败的Code 服务器error
		else if (responseCode == UPLOAD_SERVER_ERROR_CODE)
		{

		}

		toastMgr.builder.display("上传完成", 0);

		// 图片上传成功,接下来就是上传 说说了
		if (bbs_type.equals("") || bbs_type == null)
		{
			bbs_type = "3";// 默认是普通的
		}
		LocatedInfo info = locate.getLocation();
		ActivityPostPetImage.this.finish();
		new PostBBSNetWork(Configs.POST_BBS_PATH, "postBBS", new PersistentCookieStore(mContext),
		//
				new PostBBSNetWork.SuccessCallback()
				{

					@Override
					public void onSuccess(String result)
					{
						// TODO Auto-generated method stub
						try
						{
							JSONObject jsonObject = new JSONObject(result);
							String status = jsonObject.getString("status");
							if (status.equals("1"))
							{
								toastMgr.builder.display("发帖成功", 0);
							}
							else
							{
								toastMgr.builder.display("网络错误, 发帖失败", 0);
							}
						}
						catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				},
				//
				new FailCallback()
				{

					@Override
					public void onFail()
					{
						// TODO Auto-generated method stub

					}
				},
				//
				bbs_content, bbs_type, info.lontitute + "", info.latitute + "");
	}

	/**
	 * 
	 * 非常奇怪啊 我真的不知道为什么 明天来到 就要仔细查下 写个博客 记录一下
	 * 
	 * 
	 */

	@Override
	public void onUploadProcess(int uploadSize)
	{
		// TODO Auto-generated method stub
		// toastMgr.builder.displayCenter("upload pecent is " + uploadSize + "%", 0);
		nowPecent = (int) uploadSize / fileTotalSize;
		if (nowPecent >= 100)
		{
			nowPecent = 100;
		}
		else if (nowPecent < 0)
		{
			nowPecent = 0;
		}

	}

	@Override
	public void initUpload(int fileSize)
	{
		// TODO Auto-generated method stub
		// toastMgr.builder.display("file size is " + fileSize, 0);
		fileTotalSize = fileSize;
		Message msg = new Message();
		msg.what = 2;
		// uploadProgressHandler.handleMessage(msg);

	}

	private Handler uploadProgressHandler;

	private int fileTotalSize = 0;
	private int upLoadedSize = 0;
	private int nowPecent = 0;
	// 进度Dialog
	private ProgressDialog mProgressDialog;

	/***
	 * 上传成功
	 */
	public static final int UPLOAD_SUCCESS_CODE = 1;
	/**
	 * 文件不存在
	 */
	public static final int UPLOAD_FILE_NOT_EXISTS_CODE = 2;
	/**
	 * 服务器出错
	 */
	public static final int UPLOAD_SERVER_ERROR_CODE = 3;
	protected static final int WHAT_TO_UPLOAD = 1;
	protected static final int WHAT_UPLOAD_DONE = 2;

}
