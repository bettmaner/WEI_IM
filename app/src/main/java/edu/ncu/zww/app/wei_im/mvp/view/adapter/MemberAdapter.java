package edu.ncu.zww.app.wei_im.mvp.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;

public class MemberAdapter  extends BaseQuickAdapter<Contact,BaseViewHolder> {

    public MemberAdapter(List<Contact> contactList) {
        super(R.layout.member_item,contactList);
    }

    @Override
    protected void convert(BaseViewHolder helper, Contact item) {
        helper.setText(R.id.member_name,item.getName());
        helper.setImageResource(R.id.member_avatar,R.mipmap.ic_avatar);
    }
}
