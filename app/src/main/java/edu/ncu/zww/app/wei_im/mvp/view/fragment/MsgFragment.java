package edu.ncu.zww.app.wei_im.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.Date;
import java.util.List;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.base.BaseFragment;
import edu.ncu.zww.app.wei_im.mvp.contract.MsgContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Dialog;
import edu.ncu.zww.app.wei_im.mvp.model.bean.DialogsFactory;
import edu.ncu.zww.app.wei_im.mvp.presenter.MsgPresenter;
import edu.ncu.zww.app.wei_im.mvp.view.activity.ChatActivity;
import edu.ncu.zww.app.wei_im.mvp.view.activity.ChatTestActivity;
import edu.ncu.zww.app.wei_im.utils.MsgDataFormatter;

public class MsgFragment extends BaseFragment<MsgContract.MsgView,MsgPresenter>
                        implements MsgContract.MsgView,
                        DialogsListAdapter.OnDialogClickListener<Dialog>,
                        DialogsListAdapter.OnDialogLongClickListener<Dialog> {


    protected ImageLoader imageLoader;
    // adapter参数是自定义Dialog对话框，其包含了Message
    protected DialogsListAdapter<Dialog> dialogsAdapter;
    // DialogsList是用于显示和管理消息列表的组件.相当于recyclerView
    private DialogsList dialogsList;
    private List<Dialog> dialogList = DialogsFactory.getDialogs();


    @Override
    protected MsgPresenter createPresenter() {
        return new MsgPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_msg, container, false);
        imageLoader = new MyImageLoader(); // 实现图片加载机制
        dialogsList = view.findViewById(R.id.dialogsList);
        initAdapter();
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = dialogList.get(0);
                dialog.setLastMessage(DialogsFactory.getMessage(new Date(),2));
                dialogsAdapter.upsertItem(dialog);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    // 初始化adapter
    private void initAdapter() {
        dialogsAdapter = new DialogsListAdapter<>(imageLoader);
        dialogsAdapter.setItems(dialogList); // 存入列表数据,item是Dialog
        dialogsAdapter.setDatesFormatter(new MsgDataFormatter());
        dialogsAdapter.setOnDialogClickListener(this);
        dialogsAdapter.setOnDialogLongClickListener(this);

        dialogsList.setAdapter(dialogsAdapter);
    }

    // 消息Item点击事件
    @Override
    public void onDialogClick(Dialog dialog) {
        ChatActivity.actionStart(mContext,dialog.getDialogName(),dialog.getId());
//        ChatTestActivity.actionStart(mContext,dialog.getDialogName(),dialog.getId());
    }

    // 消息Item长按事件
    @Override
    public void onDialogLongClick(Dialog dialog) {

    }

    // 实现ChatKit插件的图片加载机制
    class MyImageLoader implements ImageLoader {
        @Override
        public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
            Glide.with(mContext)
                    .load(R.drawable.head_icon)
                    .into(imageView);
            /*Glide.with(mContext)
                    .load(R.drawable.head_icon)
                    .bitmapTransform(new CropCircleTransformation(getContext()))
                    .into(imageView);*/
        }
    }
}
