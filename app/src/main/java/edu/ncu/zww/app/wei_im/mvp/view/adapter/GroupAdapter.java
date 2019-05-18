package edu.ncu.zww.app.wei_im.mvp.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.mvp.model.bean.GroupInfo;

public class GroupAdapter  extends BaseQuickAdapter<GroupInfo,BaseViewHolder> {

    //private List<GroupInfo> mGroupList;

    public GroupAdapter(List<GroupInfo> groupList) {
        super(R.layout.group_item,groupList);
        //this.mGroupList = GroupList;
    }


    @Override
    protected void convert(BaseViewHolder helper, GroupInfo item) {
        helper.setText(R.id.item_group_name,item.getName());
        helper.setImageResource(R.id.item_group_avatar,R.mipmap.ic_avatar);
    }
}
