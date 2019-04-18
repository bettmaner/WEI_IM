package edu.ncu.zww.app.wei_im.mvp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;

/**
 * Created by ${cqc} on 2016/11/30.
 */

public class ContactAdapter extends RecyclerView.Adapter {

    private List<Contact> contacts;

    public ContactAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.contact_item, null);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        Contact contact = contacts.get(position);
        MyHolder holder = (MyHolder) viewHolder;

        //显示index（每个item组成部分是index和好友信息）
        // 如果该contact是第一个或者与前一个contact的letter不同，则显示tv_index并赋值
        if (position == 0 || !contact.getLetter().equals(contacts.get(position - 1).getLetter())) {
            holder.tv_index.setVisibility(View.VISIBLE);
            holder.tv_index.setText(contact.getLetter());
        } else {
            holder.tv_index.setVisibility(View.GONE);
        }

        holder.tv_name.setText(contact.getName());

        if (mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    private class MyHolder extends RecyclerView.ViewHolder {

        TextView tv_index;
        TextView tv_name;

        public MyHolder(View itemView) {
            super(itemView);
            tv_index =  itemView.findViewById(R.id.tv_index);
            tv_name =  itemView.findViewById(R.id.tv_name);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
