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
		// ���ذ�ť
		backButton = (Button) findViewById(R.id.bbs_back_detail);
		// ����¼�
		backButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				BBSDetailActivityNew.this.finish();
			}
		});
		// �û�ͷ��
		userHead = (ImageView) findViewById(R.id.image_person_bbs_detail);
		// �û�����
		username = (TextView) findViewById(R.id.text_person_name_bbs_detail);
		// �Ƿ��Ѿ���ע
		imageFollow = (ImageView) findViewById(R.id.image_follow_bbs_detail);
		// ���û�й�ע ����͹�ע ���ù�ע����ȡ��?????���ǹ�ע��ȡ����?????/
		imageFollow.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				imageFollow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.followed));
				times++;
				// ���������������
				// ֪ͨ�������Ѿ���ע
				toastMgr.builder.display("�Ѿ���ע", 0);
				if (times > 1)
				{
					toastMgr.builder.display("�Ѿ���ע��,�����ظ���ע", 0);
				}
			}
		});
		// ��������
		textPetSpecies = (TextView) findViewById(R.id.text_pet_species_bbs_detail);
		// ��������
		textPetName = (TextView) findViewById(R.id.text_pet_name_bbs_detail);
		// �ϴ�ʱ��
		textPostTime = (TextView) findViewById(R.id.text_time_bbs_detail);

		// ����ͼƬ
		petImage = (MyPetImageView) findViewById(R.id.image_pet_bbs_detail);
		// ����ͼƬ����
		petImageProgressBar = (ProgressBar) findViewById(R.id.progressbar_bbs_detail);
		// ͼƬ��������
		textContent = (TextView) findViewById(R.id.text_description_bbs_detail);
		// ���۸���
		textCommentNum = (TextView) findViewById(R.id.text_comment_bbs_detail);
		// �޵ĸ���
		textLikeNum = (TextView) findViewById(R.id.text_like_bbs_detail);
		// ����
		imageShare = (ImageView) findViewById(R.id.image_share);
		imageShare.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				showShare();
				L.i(TAG, "����");
			}
		});
		//
		editTextComment = (EmojiconEditText) findViewById(R.id.edittext_bbs_detail_comment);
		// ��������
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
		// �����б�
		bbsCommentList = (MyDetailsListView) findViewById(R.id.bbs_detail_commemt_list1);

		// ����
		emoji_background = (FrameLayout) findViewById(R.id.emoji_background);
		// ����õ�����
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
		 * ��ʾ�Ƿ���ﶪʧ��
		 * 
		 */
		textIsPetLost = (TextView) findViewById(R.id.text_is_pet_lost_bbs_detail);
		imageIsPetLost = (ImageView) findViewById(R.id.image_is_pet_lost_bbs_detail);

	}

	/**
	 * �õ����id�����ӵ�������Ϣ
	 * 
	 * @param id
	 *            ͨ�����id ȥ�õ����id�����ӵ�������Ϣ
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
					toastMgr.builder.display("��ȡ��Ϣʧ��", 0);
				}
				else
				{
					// ����json
					MyJson decode = new MyJson(mContext, result);
					commentList = decode.getBBSDetailAndComment();
					if (commentList.equals("") || commentList == null)
					{
						toastMgr.builder.display("��ȡ����ʧ��,��������������" + "", 0);
						return;
					}
					Message msg = new Message();
					msg.obj = commentList;
					msg.what = 1;
					handler.sendMessage(msg);

				}
			}
		},
		// ʧ��
				new GetBBSDetail.FailCallback()
				{

					@Override
					public void onFail()
					{
						// TODO Auto-generated method stub

					}
				},
				// ����id
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
				// ����ͼƬ
				getImageFromUrl(userImage, petImage);
				L.i(TAG, userImage);
				L.i(TAG, petImage);
				// /����ʱ��
				String thetime = comlist.get(0).getTime();
				textPostTime.setText(thetime);
				/**
				 * �ж��ǲ��Ƕ�ʧ�� ����Ƕ�ʧ�� ��ô����ʾ��ʧ
				 * 
				 */
				String isLost = comlist.get(0).getIsLost();
				if (isLost.equals("1"))// ����
				{
					textIsPetLost.setVisibility(View.GONE);
					imageIsPetLost.setVisibility(View.GONE);
				}
				else if (isLost.equals("2"))// ��ʧ
				{
					textIsPetLost.setVisibility(View.VISIBLE);
					textIsPetLost.setText("����\n��ʧ");
					imageIsPetLost.setVisibility(View.VISIBLE);
					imageIsPetLost.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pet_lost_emoji));
				}
				else if (isLost.equals("3"))// �ҵ�
				{
					textIsPetLost.setVisibility(View.VISIBLE);
					textIsPetLost.setText("����\n�ҵ�");
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

	// �첽��������ͼƬ
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

	// �������� �������� ���Ż��ˬһ����
	private static final String TAG = "com.gdut.pet.ui.BBSDetailActivityNew";
	int times = 0;

	private ActionBar mActionBar;
	private Context mContext;
	private Button bbs_replay_btn;
	private Button backButton;
	private ImageView userHead;// ������ �û�ͷ��
	private TextView username;// ������ �û�����
	private TextView title;// ������ ����
	private ImageView imageFollow;// ��ע
	private TextView textPetSpecies;// ��������
	private TextView textPetName;// ��������
	private TextView textPostTime;// �ϴ�ʱ��
	private TextView textContent;// ������ ��Ҫ����
	private ImageView contentImage;// ������ �����е�ͼ��
	private TextView replyNum;// ������ �ظ�������
	private LinearLayout progressBar;
	private MyPetImageView petImage;// ����ͼƬ
	private ProgressBar petImageProgressBar;// ���س���ͼƬʱ�������
	private TextView textCommentNum;// ���۵���Ŀ
	private TextView textLikeNum;// ���޵���Ŀ
	private ImageView imageShare;// ����
	private EmojiconEditText editTextComment;// ���۵������
	private Button commentSent;// ��������
	private ImageView image_emoji_key_board_only;// ����Ϳ��Ե����������
	private ImageView image_emoji_keyboard;// �������ʾ ����Ϳ����������
	private FrameLayout emoji_background;// ����ı���

	// �Ƿ�ʧ
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
	 * ����ĺ���
	 */

	private void showShare()
	{
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// �ر�sso��Ȩ
		oks.disableSSOWhenAuthorize();

		// ����ʱNotification��ͼ�������
		oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		// title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
		oks.setTitle(getString(R.string.share));
		// titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		oks.setTitleUrl("http://sharesdk.cn");
		// text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		oks.setText("���Ƿ����ı�");
		// imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		oks.setImagePath("/sdcard/test.jpg");// ȷ��SDcard������ڴ���ͼƬ
		// url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		oks.setUrl("http://sharesdk.cn");
		// comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
		oks.setComment("���ǲ��������ı�");
		// site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
		oks.setSite(getString(R.string.app_name));
		// siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
		oks.setSiteUrl("http://sharesdk.cn");

		// ��������GUI
		oks.show(this);
	}

}
