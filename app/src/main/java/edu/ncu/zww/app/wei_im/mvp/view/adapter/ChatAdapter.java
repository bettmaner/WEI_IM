package edu.ncu.zww.app.wei_im.mvp.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import java.io.File;
import java.util.List;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ImgMsgBody;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Message;
import edu.ncu.zww.app.wei_im.mvp.model.bean.MsgSendStatus;
import edu.ncu.zww.app.wei_im.mvp.model.bean.MsgType;
import edu.ncu.zww.app.wei_im.utils.chatutils.GlideUtils;

/**
 * 聊天活动中的聊天列表适配器
 * */
public class ChatAdapter extends BaseQuickAdapter<Message,BaseViewHolder> {


    private static final int TYPE_SEND_TEXT=1;
    private static final int TYPE_RECEIVE_TEXT=2;
    private static final int TYPE_SEND_IMAGE=3;
    private static final int TYPE_RECEIVE_IMAGE=4;
    private static final int TYPE_SEND_VIDEO=5;
    private static final int TYPE_RECEIVE_VIDEO=6;
    private static final int TYPE_SEND_FILE=7;
    private static final int TYPE_RECEIVE_FILE=8;
    private static final int TYPE_SEND_AUDIO=9;
    private static final int TYPE_RECEIVE_AUDIO=10;

    private static final int SEND_TEXT = R.layout.item_text_send;
    private static final int RECEIVE_TEXT = R.layout.item_text_receive;
    private static final int SEND_IMAGE = R.layout.item_image_send;
    private static final int RECEIVE_IMAGE = R.layout.item_image_receive;
    private static final int SEND_VIDEO = R.layout.item_video_send;
    private static final int RECEIVE_VIDEO = R.layout.item_video_receive;
    private static final int SEND_FILE = R.layout.item_file_send;
    private static final int RECEIVE_FILE = R.layout.item_file_receive;
    private static final int RECEIVE_AUDIO = R.layout.item_audio_receive;
    private static final int SEND_AUDIO = R.layout.item_audio_send;
    /*
    private static final int SEND_LOCATION = R.layout.item_location_send;
    private static final int RECEIVE_LOCATION = R.layout.item_location_receive;*/

    private static final String mUserAccount = String.valueOf(ApplicationData.getInstance().getUserInfo().getAccount());

    public ChatAdapter(Context context, List<Message> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<Message>() {
            @Override
            protected int getItemType(Message entity) { // 根据你的实体类来判断布局类型

                // 根据Message的getSenderId()，判断消息是发送还是接收
                boolean isSend = entity.getSenderId().equals(mUserAccount);
                // 再获取发送/接收类型
                if (MsgType.TEXT==entity.getMsgType()) {
                    return isSend ? TYPE_SEND_TEXT : TYPE_RECEIVE_TEXT;
                }else if(MsgType.IMAGE==entity.getMsgType()){
                     return isSend ? TYPE_SEND_IMAGE : TYPE_RECEIVE_IMAGE;
                }else if(MsgType.VIDEO==entity.getMsgType()){
                     return isSend ? TYPE_SEND_VIDEO : TYPE_RECEIVE_VIDEO;
                 }else if(MsgType.FILE==entity.getMsgType()){
                     return isSend ? TYPE_SEND_FILE : TYPE_RECEIVE_FILE;
                 }else if(MsgType.AUDIO==entity.getMsgType()){
                     return isSend ? TYPE_SEND_AUDIO : TYPE_RECEIVE_AUDIO;
                 }
                return 0;
            }
        });
        // 注册对应类型的布局，即根据setMultiTypeDelegate得到类型选择对应布局
        getMultiTypeDelegate() .registerItemType(TYPE_SEND_TEXT, SEND_TEXT)
                .registerItemType(TYPE_RECEIVE_TEXT,RECEIVE_TEXT)
                .registerItemType(TYPE_SEND_IMAGE, SEND_IMAGE)
                .registerItemType(TYPE_RECEIVE_IMAGE, RECEIVE_IMAGE)
                .registerItemType(TYPE_SEND_VIDEO, SEND_VIDEO)
                .registerItemType(TYPE_RECEIVE_VIDEO, RECEIVE_VIDEO)
                .registerItemType(TYPE_SEND_FILE, SEND_FILE)
                .registerItemType(TYPE_RECEIVE_FILE, RECEIVE_FILE)
                .registerItemType(TYPE_SEND_AUDIO, SEND_AUDIO)
                .registerItemType(TYPE_RECEIVE_AUDIO, RECEIVE_AUDIO);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        setContent(helper, item); // 设置item内容
        setStatus(helper, item); // 设置item发送状态
        setOnClick(helper, item); // item点击事件

    }


    private void setStatus(BaseViewHolder helper, Message item) {
        String msgType = item.getMsgType();
        if (msgType.equals(MsgType.TEXT)) {
            //只需要设置自己发送的状态
            String sentStatus = item.getStatus();
            boolean isSend = item.getSenderId().equals(mUserAccount);
            if (isSend){
                if (sentStatus.equals(MsgSendStatus.SENDING)) {
                    helper.setVisible(R.id.chat_item_progress, true).setVisible(R.id.chat_item_fail, false);
                } else if (sentStatus.equals(MsgSendStatus.FAILED) ) {
                    helper.setVisible(R.id.chat_item_progress, false).setVisible(R.id.chat_item_fail, true);
                } else if (sentStatus.equals(MsgSendStatus.SENT)) {
                    helper.setVisible(R.id.chat_item_progress, false).setVisible(R.id.chat_item_fail, false);
                }
            }
        } else if (msgType.equals(MsgType.IMAGE)) {
            boolean isSend = item.getSenderId().equals(mUserAccount); // 我是否是发送方
            if (isSend) {
                String sentStatus = item.getStatus();
                if (sentStatus.equals(MsgSendStatus.SENDING)) {
                    helper.setVisible(R.id.chat_item_progress, false).setVisible(R.id.chat_item_fail, false);
                } else if (sentStatus.equals(MsgSendStatus.FAILED)) {
                    helper.setVisible(R.id.chat_item_progress, false).setVisible(R.id.chat_item_fail, true);
                } else if (sentStatus.equals(MsgSendStatus.SENT)) {
                    helper.setVisible(R.id.chat_item_progress, false).setVisible(R.id.chat_item_fail, false);
                }
            } else {

            }
        }

    }

    // 显示对应消息内容
    private void setContent(BaseViewHolder helper, Message item) {
        if (item.getMsgType().equals(MsgType.TEXT)){
           helper.setText(R.id.chat_item_content_text, item.getText());
        } else if (item.getMsgType().equals(MsgType.IMAGE)){
            ImgMsgBody img = item.getImage();
            if (TextUtils.isEmpty(img.getThumbPath())){
                GlideUtils.loadChatImage(mContext,img.getThumbUrl(),(ImageView) helper.getView(R.id.bivPic));
            }else{
                File file = new File(img.getThumbPath());
                if (file.exists()) {
                    GlideUtils.loadChatImage(mContext,img.getThumbPath(),(ImageView) helper.getView(R.id.bivPic));
                }else {
                    GlideUtils.loadChatImage(mContext,img.getThumbUrl(),(ImageView) helper.getView(R.id.bivPic));
                }
            }
        }/*else if(item.getMsgType().equals(MsgType.VIDEO)){
            VideoMsgBody msgBody = (VideoMsgBody) item.getBody();
            File file = new File(msgBody.getExtra());
            if (file.exists()) {
                GlideUtils.loadChatImage(mContext,msgBody.getExtra(),(ImageView) helper.getView(R.id.bivPic));
            }else {
                GlideUtils.loadChatImage(mContext,msgBody.getExtra(),(ImageView) helper.getView(R.id.bivPic));
            }
        }else if(item.getMsgType().equals(MsgType.FILE)){
            FileMsgBody msgBody = (FileMsgBody) item.getBody();
            helper.setText(R.id.msg_tv_file_name, msgBody.getDisplayName() );
            helper.setText(R.id.msg_tv_file_size, msgBody.getSize()+"B" );
        }else if(item.getMsgType().equals(MsgType.AUDIO)){
            AudioMsgBody msgBody = (AudioMsgBody) item.getBody();
            helper.setText(R.id.tvDuration, msgBody.getDuration()+"\"" );
        }*/
    }



    // 消息点击事件
    private void setOnClick(BaseViewHolder helper, Message item) {
        /*MsgBody msgContent = item.getBody();
        if (msgContent instanceof AudioMsgBody){ // 语音
            helper.addOnClickListener(R.id.rlAudio);
        }*/
    }

}
