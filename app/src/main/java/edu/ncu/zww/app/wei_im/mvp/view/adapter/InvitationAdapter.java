package edu.ncu.zww.app.wei_im.mvp.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Invitation;
import edu.ncu.zww.app.wei_im.mvp.model.bean.StatusText;

public class InvitationAdapter extends RecyclerView.Adapter<InvitationAdapter.ViewHolder>
        implements View.OnClickListener{

    private List<Invitation> mInvitationList;
    private LayoutInflater inflater;
    private InvitationAdapter.ItemClickInterface mItemClickInterface; // item按钮点击接口

    /**
     * 每个item的holder，通过该类操作item的控件
     * 所以主要完成内容就是获取item视图的组件，以便动态修改内容
     * */
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name,content;
        TextView itemBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.contact_icon);
            name = itemView.findViewById(R.id.contact_name);
            content = itemView.findViewById(R.id.contact_account);
            itemBtn = itemView.findViewById(R.id.item_btn);
        }
    }

    public InvitationAdapter(List<Invitation> mInvitationList) {
        this.mInvitationList = mInvitationList;
    }

    @NonNull
    @Override
    public InvitationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        if(inflater==null){ // 避免多次初始化
            inflater=LayoutInflater.from(parent.getContext());
        }
        // 获取item的布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_contact_item, parent, false);

        // 返回带有view实例的holder
        return new InvitationAdapter.ViewHolder(view);
    }

    /**
     * 在每个子项滚动到屏幕中时执行， 用于将子项数据进行赋值.
     * @param viewHolder 表示之前onCreateViewHolder返回的对象
     * @param i 代表被点中的item在list中的位置
     * */
    @Override
    public void onBindViewHolder(@NonNull final InvitationAdapter.ViewHolder viewHolder, final int i) {
        final Invitation invitation = mInvitationList.get(i);
        viewHolder.icon.setImageResource(R.mipmap.ic_avatar); // 此处要改
        viewHolder.name.setText(invitation.getName());
        viewHolder.content.setText(invitation.getInfo());
        viewHolder.itemBtn.setText(invitation.getStatus());
        if (StatusText.CONTACT_SELECT.equals(invitation.getStatus())) {
            viewHolder.itemBtn.setEnabled(true);
        } else {
            viewHolder.itemBtn.setEnabled(false);
        }

        viewHolder.itemBtn.setTag("iBtn:"+i);
        viewHolder.itemView.setTag("item:" + i);

        viewHolder.itemBtn.setOnClickListener(this);
        viewHolder.itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mItemClickInterface!=null) {
            // item和按钮事件统一使用buttonInterface回调给外部处理，有外部判断处理
            mItemClickInterface.onClick(v);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mInvitationList.size();
    }

    /**
     * item设置点击事件
     * 提供给外部调用此方法，并实现buttonInterface接口，实现外部点击处理
     */
    public void itemSetOnclick(InvitationAdapter.ItemClickInterface mItemClickInterface){
        this.mItemClickInterface = mItemClickInterface;
    }

    /**
     * 按钮点击事件对应的接口
     */
    public interface ItemClickInterface{
        // 在adapter内按钮点击事件后回调此方法给外部
        void onClick(View view);
    }
}
