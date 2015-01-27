package com.gdut.pet.ui;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.gdut.pet.common.info.BBSCommentsInfo;
import com.gdut.pet.common.network.GetBBSDetail;
import com.gdut.pet.common.tools.MyJson;
import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.common.utils.L;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.common.view.MyDetailsListView;
import com.gdut.pet.common.view.MyPetImageView;
import com.gdut.pet.common.view.adapter.BBSCommentListAdapter;
import com.gdut.pet.config.Configs;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;
import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

public class BBSDetailActivityNew extends Activity implements EmojiconsFragment.OnEmojiconBackspaceClickedListener,
		EmojiconGridFragment.OnEmojiconClickedListener
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bbs_detail);
		mContext = this;
		findIDs();

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String id = bundle.getString("id");
		String postType = bundle.getString("postType");
		getBBSDetail(id, postType);
		// setEmojiconFragment(false);

	}

	private void findIDs()
	{
		// 返回按钮
		backButton = (Button) findViewById(R.id.bbs_back_detail);
		// 点击事件
		backButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				BBSDetailActivityNew.this.finish();
			}
		});
		// 用户头像
		userHead = (ImageView) findViewById(R.id.image_person_bbs_detail);
		// 用户名字
		username = (TextView) findViewById(R.id.text_person_name_bbs_detail);
		// 是否已经关注
		imageFollow = (ImageView) findViewById(R.id.image_follow_bbs_detail);
		// 如果没有关注 点击就关注 设置关注不能取消?????还是关注能取消呢?????/
		imageFollow.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				imageFollow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.followed));
				times++;
				// 下面进行联网操作
				// 通知服务器已经关注
				toastMgr.builder.display("已经关注", 0);
				if (times > 1)
				{
					toastMgr.builder.display("已经关注了,不可重复关注", 0);
				}
			}
		});
		// 宠物种类
		textPetSpecies = (TextView) findViewById(R.id.text_pet_species_bbs_detail);
		// 宠物名字
		textPetName = (TextView) findViewById(R.id.text_pet_name_bbs_detail);
		// 上传时间
		textPostTime = (TextView) findViewById(R.id.text_time_bbs_detail);

		// 宠物图片
		petImage = (MyPetImageView) findViewById(R.id.image_pet_bbs_detail);
		// 宠物图片进度
		petImageProgressBar = (ProgressBar) findViewById(R.id.progressbar_bbs_detail);
		// 图片内容描述
		textContent = (TextView) findViewById(R.id.text_description_bbs_detail);
		// 评论个数
		textCommentNum = (TextView) findViewById(R.id.text_comment_bbs_detail);
		// 赞的个数
		textLikeNum = (TextView) findViewById(R.id.text_like_bbs_detail);
		// 分享
		imageShare = (ImageView) findViewById(R.id.image_share);
		imageShare.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				showShare();
				L.i(TAG, "分享");
			}
		});
		//
		editTextComment = (EmojiconEditText) findViewById(R.id.edittext_bbs_detail_comment);
		// 发送评论
		commentSent = (Button) findViewById(R.id.send_bbs_detail_comment);
		commentSent.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				L.i(TAG, editTextComment.getText().toString());
				username.setText(editTextComment.getText().toString());
			}
		});
		// 评论列表
		bbsCommentList = (MyDetailsListView) findViewById(R.id.bbs_detail_commemt_list1);

		// 表情
		emoji_background = (FrameLayout) findViewById(R.id.emoji_background);
		// 点击得到表情
		image_emoji_key_board_only = (ImageView) findViewById(R.id.image_emoji);
		image_emoji_key_board_only.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				image_emoji_key_board_only.setVisibility(View.GONE);
				image_emoji_keyboard.setVisibility(View.VISIBLE);
				emoji_background.setVisibility(View.VISIBLE);
				// setEmojiconFragment(false);
			}
		});
		image_emoji_keyboard = (ImageView) findViewById(R.id.image_emoji_keyboard);
		image_emoji_keyboard.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				image_emoji_key_board_only.setVisibility(View.VISIBLE);
				image_emoji_keyboard.setVisibility(View.GONE);
				emoji_background.setVisibility(View.GONE);
			}
		});

		/**
		 * 显示是否宠物丢失的
		 * 
		 */
		textIsPetLost = (TextView) findViewById(R.id.text_is_pet_lost_bbs_detail);
		imageIsPetLost = (ImageView) findViewById(R.id.image_is_pet_lost_bbs_detail);

	}

	/**
	 * 得到这个id的帖子的所有信息
	 * 
	 * @param id
	 *            通过这个id 去得到这个id的帖子的所有信息
	 */
	private void getBBSDetail(String id, String postType)
	{

		new GetBBSDetail(Configs.GET_BBS_DETAIL_PATH, new PersistentCookieStore(mContext), new GetBBSDetail.SuccessCallback()
		{

			@Override
			public void onSuccess(String result)
			{
				// TODO Auto-generated method stub
				if (result == null)
				{
					toastMgr.builder.display("获取信息失败", 0);
				}
				else
				{
					// 解析json
					MyJson decode = new MyJson(mContext, result);
					commentList = decode.getBBSDetailAndComment();
					if (commentList.equals("") || commentList == null)
					{
						toastMgr.builder.display("获取数据失败,服务器数据有误" + "", 0);
						return;
					}
					Message msg = new Message();
					msg.obj = commentList;
					msg.what = 1;
					handler.sendMessage(msg);

				}
			}
		},
		// 失败
				new GetBBSDetail.FailCallback()
				{

					@Override
					public void onFail()
					{
						// TODO Auto-generated method stub

					}
				},
				// 帖子id
				id, postType);
	}

	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			// TODO Auto-generated method stub
			switch (msg.what)
			{
			case 1:
				@SuppressWarnings("unchecked")
				List<BBSCommentsInfo> comlist = (List<BBSCommentsInfo>) msg.obj;
				String userImage = comlist.get(0).getUserImageUrl();
				String petImage = comlist.get(0).getPetImageUrl();
				// 加载图片
				getImageFromUrl(userImage, petImage);
				L.i(TAG, userImage);
				L.i(TAG, petImage);
				// /设置时间
				String thetime = comlist.get(0).getTime();
				textPostTime.setText(thetime);
				/**
				 * 判断是不是丢失的 如果是丢失的 那么就显示丢失
				 * 
				 */
				String isLost = comlist.get(0).getIsLost();
				if (isLost.equals("1"))// 正常
				{
					textIsPetLost.setVisibility(View.GONE);
					imageIsPetLost.setVisibility(View.GONE);
				}
				else if (isLost.equals("2"))// 丢失
				{
					textIsPetLost.setVisibility(View.VISIBLE);
					textIsPetLost.setText("宠物\n丢失");
					imageIsPetLost.setVisibility(View.VISIBLE);
					imageIsPetLost.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pet_lost_emoji));
				}
				else if (isLost.equals("3"))// 找到
				{
					textIsPetLost.setVisibility(View.VISIBLE);
					textIsPetLost.setText("宠物\n找到");
					imageIsPetLost.setVisibility(View.VISIBLE);
					imageIsPetLost.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pet_found_emoji));
				}

				String commentNumString = comlist.get(0).getCommentNum() + "";
				textCommentNum.setText(commentNumString);
				String likeNumString = comlist.get(0).getLikeNum();
				textLikeNum.setText(likeNumString);

				//
				mAdapter = new BBSCommentListAdapter(mContext, comlist.get(0).getCommentsInfos());

				bbsCommentList.setVisibility(View.VISIBLE);
				bbsCommentList.setAdapter(mAdapter);
				break;

			default:
				break;
			}
		}

	};

	// 异步加载网络图片
	private void getImageFromUrl(String userurl, String peturl)
	{
		ImageLoader.getInstance().loadImage(userurl, new ImageLoadingListener()
		{

			@Override
			public void onLoadingStarted(String imageUri, View view)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
			{
				// TODO Auto-generated method stub
				userHead.setImageBitmap(loadedImage);
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view)
			{
				// TODO Auto-generated method stub

			}
		});
		ImageLoader.getInstance().loadImage(peturl, new ImageLoadingListener()
		{

			@Override
			public void onLoadingStarted(String imageUri, View view)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
			{
				// TODO Auto-generated method stub
				petImage.setImageBitmap(loadedImage);
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view)
			{
				// TODO Auto-generated method stub

			}
		});
	}

	protected void onResume()
	{
		super.onResume();
		MobclickAgent.onResume(this);
		L.i(TAG, "onresume");
	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(mContext);
		L.i(TAG, "onPause");
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		L.i(TAG, "onDestroy");
	}

	//
	// private void setEmojiconFragment(boolean useSystemDefault)
	// {
	// getSupportFragmentManager()
	// .beginTransaction()
	// .replace(R.id.emoji_background,
	// EmojiconsFragment.newInstance(useSystemDefault))
	// .commit();
	// }

	// 变量定义 放在下面 看着会更爽一点呢
	private static final String TAG = "com.gdut.pet.ui.BBSDetailActivityNew";
	int times = 0;

	private ActionBar mActionBar;
	private Context mContext;
	private Button bbs_replay_btn;
	private Button backButton;
	private ImageView userHead;// 帖子中 用户头像
	private TextView username;// 帖子中 用户名字
	private TextView title;// 帖子中 标题
	private ImageView imageFollow;// 关注
	private TextView textPetSpecies;// 宠物种类
	private TextView textPetName;// 宠物名字
	private TextView textPostTime;// 上传时间
	private TextView textContent;// 帖子中 主要内容
	private ImageView contentImage;// 帖子中 内容中的图像
	private TextView replyNum;// 帖子中 回复的数量
	private LinearLayout progressBar;
	private MyPetImageView petImage;// 宠物图片
	private ProgressBar petImageProgressBar;// 加载宠物图片时候进度条
	private TextView textCommentNum;// 评论的数目
	private TextView textLikeNum;// 被赞的数目
	private ImageView imageShare;// 评论
	private EmojiconEditText editTextComment;// 评论的输入框
	private Button commentSent;// 发送评论
	private ImageView image_emoji_key_board_only;// 点击就可以调出表情键盘
	private ImageView image_emoji_keyboard;// 点击后显示 点击就可以收起键盘
	private FrameLayout emoji_background;// 表情的背景

	// 是否丢失
	private int PetLostOrFound = 0X0;
	private TextView textIsPetLost;
	private ImageView imageIsPetLost;

	private MyDetailsListView bbsCommentList;
	private List<BBSCommentsInfo> commentList;
	// private List<BBSCommentsInfo> commentList;
	private BBSCommentListAdapter mAdapter;

	@Override
	public void onEmojiconClicked(Emojicon emojicon)
	{
		// TODO Auto-generated method stub
		EmojiconsFragment.input(editTextComment, emojicon);
	}

	@Override
	public void onEmojiconBackspaceClicked(View v)
	{
		// TODO Auto-generated method stub
		EmojiconsFragment.backspace(editTextComment);
	}

	/**
	 * 分享的函数
	 */

	private void showShare()
	{
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字
		oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("我是分享文本");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");

		// 启动分享GUI
		oks.show(this);
	}

}
