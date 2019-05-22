package edu.ncu.zww.app.wei_im.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.base.BaseActivityFlags;
import edu.ncu.zww.app.wei_im.customview.chatview.RecordButton;
import edu.ncu.zww.app.wei_im.customview.chatview.StateButton;
import edu.ncu.zww.app.wei_im.mvp.contract.ChatContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ImgMsgBody;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Message;
import edu.ncu.zww.app.wei_im.mvp.model.bean.MsgSendStatus;
import edu.ncu.zww.app.wei_im.mvp.model.bean.MsgType;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ResultBean;
import edu.ncu.zww.app.wei_im.mvp.presenter.ChatPresenter;
import edu.ncu.zww.app.wei_im.mvp.view.adapter.ChatAdapter;
import edu.ncu.zww.app.wei_im.utils.LogUtil;
import edu.ncu.zww.app.wei_im.utils.NumberUtil;
import edu.ncu.zww.app.wei_im.utils.PictureFileUtil;
import edu.ncu.zww.app.wei_im.utils.ToolBarHelper;
import edu.ncu.zww.app.wei_im.utils.chatutils.ChatUiHelper;

// 聊天Activity
public class ChatActivity extends BaseActivityFlags<ChatContract.ChatView,ChatPresenter>
        implements ChatContract.ChatView,SwipeRefreshLayout.OnRefreshListener {

    private String chatName; // 当前聊天的人/群（即Dialog）的姓名，
    private int chatId,chatType; // 聊天的id，类型（0人1群）

    @BindView(R.id.llContent)
    LinearLayout mLlContent; // 聊天界面主体（除头布局外）布局
    @BindView(R.id.rv_chat_list)
    RecyclerView mRvChat; // 消息列表
    @BindView(R.id.et_content)
    EditText mEtContent; // 输入栏之-文本输入框
    @BindView(R.id.bottom_layout)
    RelativeLayout mRlBottomLayout; // // 底部功能区布局
    @BindView(R.id.ivAdd)
    ImageView mIvAdd; // 输入栏之-加号图标按钮
    @BindView(R.id.ivEmo)
    ImageView mIvEmo; // 输入栏之-表情图标按钮
    @BindView(R.id.btn_send)
    StateButton mBtnSend; // 发送按钮
    @BindView(R.id.ivAudio)
    ImageView mIvAudio; //  输入栏之-按住录音按钮
    @BindView(R.id.btnAudio)
    RecordButton mBtnAudio; //  输入栏之-录音图标按钮
    @BindView(R.id.rlEmotion)
    LinearLayout mLlEmotion; //表情布局
    @BindView(R.id.llAdd)
    LinearLayout mLlAdd; // 添加布局
    @BindView(R.id.swipe_chat)
    SwipeRefreshLayout mSwipeRefresh;//下拉刷新

    private ImageView ivAudio;
    private ChatAdapter mAdapter; // 消息列表适配器
    public Contact user;
    public String  mTargetId="left";
    public static final int       REQUEST_CODE_IMAGE=0000;
    public static final int       REQUEST_CODE_VEDIO=1111;
    public static final int       REQUEST_CODE_FILE=2222;

    public static void actionStart(Context context,String name,int id,int type) {
        Intent intent = new Intent(context,ChatActivity.class);
        intent.putExtra("chatName",name);
        intent.putExtra("chatType",type); // 0人1群
        intent.putExtra("chatId",id);
        context.startActivity(intent);
    }

    // 获取上一个活动传来的数据
    private void getIntentData() {
        Intent intent = getIntent();
        chatName = intent.getStringExtra("chatName");
        chatId = intent.getIntExtra("chatId",0);
        chatType = intent.getIntExtra("chatType",0);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_chat;
    }

    /* --------------------  头布局 ---------------------- */
    @Override
    protected void handleToolBar(ToolBarHelper toolBarHelper) {
        toolBarHelper.setTitle(chatName);
        toolBarHelper.setBackIcon();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_toolbar_menu, menu);
        return true;
    }


    // toolbar菜单按钮点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.to_member:
                if (chatType == 0) {
                    // 个人信息
                    Contact contact = mPresenter.getContact(chatId);
                    ContactInfoActivity.actionStart(ChatActivity.this,contact);
                } else {
                    // 成员
                    MemberActivity.actionStart(ChatActivity.this,chatId);
                }
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
    /* ----------------------  end  ---------------------- */

    @Override
    protected ChatPresenter createPresenter() {
        return new ChatPresenter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getIntentData();
        super.onCreate(savedInstanceState);
        user = mPresenter.getUser();
        initContent();
    }

    protected void initContent() {
        ButterKnife.bind(this) ;
        mAdapter=new ChatAdapter(this, new ArrayList<Message>());
        LinearLayoutManager mLinearLayout=new LinearLayoutManager(this);
        mRvChat.setLayoutManager(mLinearLayout);
        mRvChat.setAdapter(mAdapter);
        mSwipeRefresh.setOnRefreshListener(this);
        initChatUi();

        // 消息列表点击事件
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                // 因为在adpter设置了语音监听，所以此处为语音点击事件响应
                /*if (ivAudio != null) {
                    ivAudio.setBackgroundResource(R.mipmap.audio_animation_list_right_3);
                    ivAudio = null;
                    MediaManager.reset();
                }else{
                    ivAudio = view.findViewById(R.id.ivAudio);
                    MediaManager.reset();
                    ivAudio.setBackgroundResource(R.drawable.audio_animation_right_list);
                    AnimationDrawable drawable = (AnimationDrawable) ivAudio.getBackground();
                    drawable.start();
                    MediaManager.playSound(ChatActivity.this,((AudioMsgBody)mAdapter.getData().get(position).getBody()).getLocalPath(), new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            LogUtil.d("开始播放结束");
                            ivAudio.setBackgroundResource(R.mipmap.audio_animation_list_right_3);
                            MediaManager.release();
                        }
                    });
                }*/
            }
        });

    }

    @Override
    public void onRefresh() {
        //下拉刷新模拟获取历史消息
        List<Message> mReceiveMsgList=new ArrayList<Message>();
        //构建文本消息
        Message mMessgaeText=getBaseReceiveMessage(MsgType.TEXT);
        mMessgaeText.setText("收到的消息");
        mReceiveMsgList.add(mMessgaeText);
        //构建图片消息
        Message mMessgaeImage=getBaseReceiveMessage(MsgType.IMAGE);
        ImgMsgBody image= new ImgMsgBody();
        image.setThumbUrl("http://pic19.nipic.com/20120323/9248108_173720311160_2.jpg");
        mMessgaeImage.setImage(image);
        mReceiveMsgList.add(mMessgaeImage);
        mAdapter.addData(0,mReceiveMsgList);
        mSwipeRefresh.setRefreshing(false);
    }

    private void initChatUi(){
        //mBtnAudio
        final ChatUiHelper mUiHelper= ChatUiHelper.with(this);
        mUiHelper.bindContentLayout(mLlContent)
                .bindttToSendButton(mBtnSend)
                .bindEditText(mEtContent)
                .bindBottomLayout(mRlBottomLayout)
                .bindEmojiLayout(mLlEmotion)
                .bindAddLayout(mLlAdd)
                .bindToAddButton(mIvAdd)
                .bindToEmojiButton(mIvEmo)
                .bindAudioBtn(mBtnAudio)
                .bindAudioIv(mIvAudio)
                .bindEmojiData();
        //底部布局弹出,聊天列表上滑
        mRvChat.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    mRvChat.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mAdapter.getItemCount() > 0) {
                                mRvChat.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                            }
                        }
                    });
                }
            }
        });
        //点击空白区域关闭键盘
        mRvChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mUiHelper.hideBottomLayout(false);
                mUiHelper.hideSoftInput();
                mEtContent.clearFocus();
                mIvEmo.setImageResource(R.mipmap.ic_emoji);
                return false;
            }
        });
        //
        ((RecordButton) mBtnAudio).setOnFinishedRecordListener(new RecordButton.OnFinishedRecordListener() {
            @Override
            public void onFinishedRecord(String audioPath, int time) {
                LogUtil.d("录音结束回调");
                File file = new File(audioPath);
                if (file.exists()) {
                    sendAudioMessage(audioPath,time);
                }
            }
        });

    }

    @OnClick({R.id.btn_send,R.id.rlPhoto,R.id.rlVideo,R.id.rlLocation,R.id.rlFile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                sendTextMsg(mEtContent.getText().toString());
                mEtContent.setText(""); // 发送完后清空输入框内容

                break;
            case R.id.rlPhoto:
                PictureFileUtil.openGalleryPic(ChatActivity.this,REQUEST_CODE_IMAGE);
                break;
            /*case R.id.rlVideo:
                PictureFileUtil.openGalleryAudio(ChatActivity.this,REQUEST_CODE_VEDIO);
                break;
            case R.id.rlFile:
                PictureFileUtil.openFile(ChatActivity.this,REQUEST_CODE_FILE);
                break;*/
            case R.id.rlLocation:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_IMAGE:
                    // 图片选择结果回调
                    List<LocalMedia> selectListPic = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia media : selectListPic) {
                        LogUtil.d("获取图片路径成功:"+  media.getPath());
                        sendImageMessage(media);
                    }
                    break;
                default:
            }
        }
    }

    // 点击发送后被调用，根据text发送文本消息
    private void sendTextMsg(String text)  {
        final Message mMessgae=getBaseSendMessage(MsgType.TEXT); // 先封装消息的基本属性
        mMessgae.setText(text);
        //开始发送
        mAdapter.addData(mMessgae);
        //模拟两秒后发送成功
//        updateMsg(mMessgae);
        mPresenter.sendMessage(mMessgae);

    }

    // 图片消息
    private void sendImageMessage(final LocalMedia media) {
        final Message mMessgae=getBaseSendMessage(MsgType.IMAGE);
        ImgMsgBody mImageMsgBody=new ImgMsgBody();
        mImageMsgBody.setThumbUrl(media.getCompressPath());
        mMessgae.setImage(mImageMsgBody);
        //开始发送
        mAdapter.addData( mMessgae);
        //模拟两秒后发送成功
        updateMsg(mMessgae);
    }

    //语音消息
    private void sendAudioMessage(  final String path,int time) {
        /*final Message mMessgae=getBaseSendMessage(MsgType.AUDIO);
        AudioMsgBody mFileMsgBody=new AudioMsgBody();
        mFileMsgBody.setLocalPath(path);
        mFileMsgBody.setDuration(time);
        mMessgae.setBody(mFileMsgBody);
        //开始发送
        mAdapter.addData( mMessgae);
        //模拟两秒后发送成功
        updateMsg(mMessgae)*/;
    }

    // 发送消息的提前封装，某类型消息空盒子
    private Message getBaseSendMessage(String msgType){
        Message mMessgae=new Message();
        mMessgae.setId(NumberUtil.getUUID());
        mMessgae.setChatType(chatType);
        if (chatType==1) {
            mMessgae.setGroupId(chatId);
        }
        mMessgae.setCreatedAt(new Date());
        mMessgae.setUser(user);
        mMessgae.setReceiveId(chatId);
        mMessgae.setSendStatus(MsgSendStatus.SENDING);
        mMessgae.setMsgType(msgType);
        return mMessgae;
    }

    private Message getBaseReceiveMessage(String msgType){
        Message mMessgae=new Message();
        mMessgae.setId(NumberUtil.getUUID());
        mMessgae.setChatType(chatType);
        mMessgae.setCreatedAt(new Date());
//        mMessgae.setReceiveId(mTargetId);
        //mMessgae.setTargetId(mSenderId);
        mMessgae.setReceiveId(user.getAccount());
        mMessgae.setSendStatus(MsgSendStatus.SENDING);
        mMessgae.setMsgType(msgType);
        return mMessgae;
    }

    // 通知适配器更改item
    private void updateMsg(final Message mMessgae) {
        mRvChat.scrollToPosition(mAdapter.getItemCount() - 1);
        //模拟2秒后发送成功
        new Handler().postDelayed(new Runnable() {
            public void run() {
                int position=0;
                mMessgae.setSendStatus(MsgSendStatus.SENT);
                //更新单个子条目
                for (int i=0;i<mAdapter.getData().size();i++){
                    Message mAdapterMessage=mAdapter.getData().get(i);
                    if (mMessgae.getId().equals(mAdapterMessage.getId())){
                        position=i;
                    }
                }
                mAdapter.notifyItemChanged(position);
            }
        }, 2000);

    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initListener() {
    }


    @Override
    public void onSendResult(Message message) {
        int position=0;
        for (int i=0;i<mAdapter.getData().size();i++){
            Message mAdapterMessage=mAdapter.getData().get(i);
            if (message.getId().equals(mAdapterMessage.getId())){
                position=i;
            }
        }
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void onError(String info) {

    }

}
