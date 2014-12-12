package com.gdut.pet.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdut.pet.common.info.BBSCommentsInfo;
import com.gdut.pet.common.network.GetBBSDetail;
import com.gdut.pet.common.tools.MyJson;
import com.gdut.pet.common.tools.PersistentCookieStore;
import com.gdut.pet.common.utils.toastMgr;
import com.gdut.pet.common.view.MyDetailsListView;
import com.gdut.pet.common.view.adapter.BBSCommentListAdapter;
import com.gdut.pet.config.Configs;
import com.ui.mypet.R;
import com.umeng.analytics.MobclickAgent;

public class BBSDetailActivity extends Activity
{

	private static final String TAG = "com.gdut.pet.ui.BBSDetailActivity";
	private ActionBar mActionBar;
	private Context mContext;
	private Button bbs_replay_btn;
	private Button backButton;
	private ImageView userHead;// ������ �û�ͷ��
	private TextView username;// ������ �û�ͷ��
	private TextView title;// ������ ����
	private TextView content;// ������ ��Ҫ����
	private ImageView contentImage;// ������ �����е�ͼ��
	private TextView replyNum;// ������ �ظ�������
	private LinearLayout progressBar;

	private MyDetailsListView bbsCommentList;
	private List<BBSCommentsInfo> commentList;
	// private List<BBSCommentsInfo> commentList;
	private BBSCommentListAdapter mAdapter;
	private String bbsid;// �������ӵ�id
	private String bbsTitle;// �������ӵ�title
	private String bbsUsername;// �������ӵ��û���

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bbs_detail_1);
		commentList = new ArrayList<BBSCommentsInfo>();

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		bbsid = bundle.getString("id");
		bbsTitle = bundle.getString("bbsTitle");
		bbsUsername = bundle.getString("username");
		mContext = this;

		findIDs();
		// for (int i = 0; i < 10; i++)
		// {
		// BBSCommentsInfo info1 = new BBSCommentsInfo();
		// info1.setId("1");
		// info1.setItemContent("womensfagagasgasg");
		// info1.setItemNum("1");
		// info1.setUsername("andy");
		// commentList.add(info1);
		// }

		bbs_replay_btn = (Button) findViewById(R.id.bbs_head_comment_btn);
		bbs_replay_btn.setFocusable(false);
		bbs_replay_btn.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(BBSDetailActivity.this, BBSReplyActivity.class);
				startActivity(intent);
			}
		});

		backButton = (Button) findViewById(R.id.bbs_detail_back_detail);
		backButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		});

		getBBSDetail(bbsid);

	}

	private void findIDs()
	{
		userHead = (ImageView) findViewById(R.id.Detail_UserHead);
		username = (TextView) findViewById(R.id.Detail_UserName);
		title = (TextView) findViewById(R.id.Item_MainTitle);
		content = (TextView) findViewById(R.id.Detail_MainText);
		contentImage = (ImageView) findViewById(R.id.Detail_MainImg);
		replyNum = (TextView) findViewById(R.id.Item_PeplyNum);
		bbsCommentList = (MyDetailsListView) findViewById(R.id.bbs_detail_commemt_list);
		progressBar = (LinearLayout) findViewById(R.id.Detail__progressBar);
	}

	/**
	 * �õ����id�����ӵ�������Ϣ
	 * 
	 * @param id
	 *            ͨ�����id ȥ�õ����id�����ӵ�������Ϣ
	 */
	private void getBBSDetail(String id)
	{

		new GetBBSDetail(Configs.GET_BBS_DETAIL_PATH,
				new PersistentCookieStore(mContext),
				new GetBBSDetail.SuccessCallback()
				{

					@Override
					public void onSuccess(String result)
					{
						// TODO Auto-generated method stub
						if (result == null)
						{
							// ShowToast.ShowToast1(mContext, "��ȡ��Ϣʧ��");
							toastMgr.builder.display("��ȡ��Ϣʧ��", 0);

						}
						else
						{
							// ����json
							MyJson decode = new MyJson(mContext, result);
							commentList = decode.getBBSDetailAndComment();
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
				id);
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
				mAdapter = new BBSCommentListAdapter(mContext, comlist);

				bbsCommentList.setVisibility(View.VISIBLE);
				bbsCommentList.setAdapter(mAdapter);
				progressBar.setVisibility(View.GONE);
				break;

			default:
				break;
			}
		}

	};

	protected void onResume()
	{
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(mContext);
	}

}
