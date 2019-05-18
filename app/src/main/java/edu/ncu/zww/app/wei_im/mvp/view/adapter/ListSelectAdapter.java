package edu.ncu.zww.app.wei_im.mvp.view.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;

public class ListSelectAdapter extends BaseQuickAdapter<Contact,BaseViewHolder> {

    private List<Contact> mContactList;

    public ListSelectAdapter(List<Contact> contactList) {
        super(R.layout.selector_item,contactList);
        mContactList = contactList;
    }

    @Override
    protected void convert(BaseViewHolder helper, Contact item) {
        helper.setText(R.id.friend_name,item.getName())
                .addOnClickListener(R.id.check_box);
        helper.setImageResource(R.id.friend_avatar,R.mipmap.ic_avatar);

        int position = helper.getAdapterPosition();
        //显示index（每个item组成部分是index和好友信息）
        // 如果该contact是第一个或者与前一个contact的letter不同，则显示tv_index并赋值
        if (position == 0 || !item.getLetter().equals(mContactList.get(position - 1).getLetter())) {
            helper.setVisible(R.id.tv_index,true);
            helper.setText(R.id.tv_index,item.getLetter());
        }
    }
}
