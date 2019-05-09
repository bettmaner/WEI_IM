package edu.ncu.zww.app.wei_im.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.customview.chatview.RecordButton;
import edu.ncu.zww.app.wei_im.customview.chatview.StateButton;
import edu.ncu.zww.app.wei_im.mvp.contract.ChatContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Message;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ResultBean;
import edu.ncu.zww.app.wei_im.mvp.presenter.ChatPresenter;
import edu.ncu.zww.app.wei_im.mvp.view.adapter.ChatAdapter;
import edu.ncu.zww.app.wei_im.utils.LogUtil;
import edu.ncu.zww.app.wei_im.utils.ToolBarHelper;
import edu.ncu.zww.app.wei_im.utils.chatutils.ChatUiHelper;

public class ChatTestActivity extends AppCompatActivity
        implements ChatContract.ChatView,SwipeRefreshLayout.OnRefreshListener {

    private String chatName,chatId; // 当前聊天的人/群（即Dialog）的姓名和id，

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
    public static final String 	  mSenderId="right";
    public static final String     mTargetId="left";
    public static final int       REQUEST_CODE_IMAGE=0000;
    public static final int       REQUEST_CODE_VEDIO=1111;
    public static final int       REQUEST_CODE_FILE=2222;

    public static void actionStart(Context context, String name, String id) {
        Intent intent = new Intent(context,ChatTestActivity.class);
        intent.putExtra("chatName",name);
        intent.putExtra("chatId",id);
        context.startActivity(intent);
    }

    // 获取上一个活动传来的数据
    private void getIntentData() {
        Intent intent = getIntent();
        chatName = intent.getStringExtra("chatName");
        chatId = intent.getStringExtra("chatId");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_test);

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

        // 消息点击事件
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
        /*//下拉刷新模拟获取历史消息
        List<Message> mReceiveMsgList=new ArrayList<Message>();
        //构建文本消息
        Message mMessgaeText=getBaseReceiveMessage(MsgType.TEXT);
        TextMsgBody mTextMsgBody=new TextMsgBody();
        mTextMsgBody.setMessage("收到的消息");
        mMessgaeText.setBody(mTextMsgBody);
        mReceiveMsgList.add(mMessgaeText);
        //构建图片消息
        Message mMessgaeImage=getBaseReceiveMessage(MsgType.IMAGE);
        ImageMsgBody mImageMsgBody=new ImageMsgBody();
        mImageMsgBody.setThumbUrl("http://pic19.nipic.com/20120323/9248108_173720311160_2.jpg");
        mMessgaeImage.setBody(mImageMsgBody);
        mReceiveMsgList.add(mMessgaeImage);
        //构建文件消息
        Message mMessgaeFile=getBaseReceiveMessage(MsgType.FILE);
        FileMsgBody mFileMsgBody=new FileMsgBody();
        mFileMsgBody.setDisplayName("收到的文件");
        mFileMsgBody.setSize(12);
        mMessgaeFile.setBody(mFileMsgBody);
        mReceiveMsgList.add(mMessgaeFile);
        mAdapter.addData(0,mReceiveMsgList);
        mSwipeRefresh.setRefreshing(false);*/
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

    @Override
    public void onSendSuccess(ResultBean result) {

    }

    @Override
    public void onSendFail(ResultBean result) {

    }

    @Override
    public void onError(String info) {

    }

}
