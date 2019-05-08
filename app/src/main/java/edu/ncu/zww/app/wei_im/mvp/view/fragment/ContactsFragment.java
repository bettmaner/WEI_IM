package edu.ncu.zww.app.wei_im.mvp.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.gjiazhe.wavesidebar.WaveSideBar;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;
import java.util.List;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.base.BaseFragment;
import edu.ncu.zww.app.wei_im.mvp.contract.ContactContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Dialog;
import edu.ncu.zww.app.wei_im.mvp.presenter.ContactPresenter;
import edu.ncu.zww.app.wei_im.mvp.view.activity.NewContactActivity;
import edu.ncu.zww.app.wei_im.mvp.view.adapter.ContactAdapter;
import edu.ncu.zww.app.wei_im.customview.MyRecyclerView;
import edu.ncu.zww.app.wei_im.utils.PinYinUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends BaseFragment<ContactContract.ContactView,ContactPresenter>
        implements ContactContract.ContactView,
        DialogsListAdapter.OnDialogClickListener<Dialog>,
        DialogsListAdapter.OnDialogLongClickListener<Dialog>  {
    private WaveSideBar waveSideBar;
    private MyRecyclerView recyclerView;
    private View mHeaderView; // recycler头布局
    private View mEmptyView;    // recycler空布局
    private LinearLayout newFriendsView, groupView; // 头布局的两个item
    private ContactAdapter adapter;
    private List<Contact> friends;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_contacts, container, false);
        waveSideBar = view.findViewById(R.id.waveSideBar_friends);
        recyclerView = view.findViewById(R.id.friends_rv);
        mHeaderView = LayoutInflater.from(mContext)
                .inflate(R.layout.contact_item_header, null);
        mEmptyView = LayoutInflater.from(mContext)
                .inflate(R.layout.add_contact_item_empty, null);
        TextView textView =  mEmptyView.findViewById(R.id.empty_text);
        textView.setText("暂无好友");
        // 以下两个为头布局的view，新的好友和群
        newFriendsView = mHeaderView.findViewById(R.id.item_new_friends);
        groupView = mHeaderView.findViewById(R.id.item_group);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
        initWaveSideBar();
//        mPresenter.queryFriends();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.queryFriends();
    }

    private void initWaveSideBar() {
        waveSideBar.setIndexItems("A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
                "V", "W", "X", "Y", "Z","#");
        waveSideBar.setLazyRespond(false);//false:列表随侧边栏的滚动滚动
        waveSideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i = 0,size=friends.size(); i < size; i++) {
                    if (index.equals(friends.get(i).getLetter())) {
                        // recyclerView.scrollToPosition(i);
                        //或者
                         ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setItemAnimator(new DefaultItemAnimator()); // 平滑移动
        // 添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        recyclerView.setHeaderView(mHeaderView); // 设置头布局
        recyclerView.setEmptyView(mEmptyView); // 设置空布局
//        // 配置adapter
//        adapter = new ContactAdapter(friends);
//        recyclerView.setAdapter(adapter);
//        // 普通item点击事件
//        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                String name = friends.get(position).getName();
//                String pinyin = PinYinUtils.getPinyin(name);
//                Toast.makeText(mContext, pinyin, Toast.LENGTH_SHORT).show();
//            }
//        });
        // 头布局的新的好友点击事件
        newFriendsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,NewContactActivity.class));
                Toast.makeText(mContext, "跳转到新的好友界面", Toast.LENGTH_SHORT).show();
            }
        });
        // 头布局的新的群事件
        groupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "跳转到群界面", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected ContactPresenter createPresenter() {
        return new ContactPresenter();
    }

    @Override
    public void onQuerySuccess(List friendlist) {
        if (friends == null) {
            friends = new ArrayList<>();
        } else {
            friends.clear();
        }
        friends.addAll(friendlist);

        if (adapter==null) {
            // 配置adapter
            adapter = new ContactAdapter(friends);
            recyclerView.setAdapter(adapter);
            // 普通item点击事件
            adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String name = friends.get(position).getName();
                    final String pinyin = PinYinUtils.getPinyin(name);
//                    Toast.makeText(mContext, pinyin, Toast.LENGTH_SHORT).show();

                    PopupMenu popupMenu = new PopupMenu(mContext,view);
                    popupMenu.getMenuInflater().inflate(R.menu.contact_item_popup_menu, popupMenu.getMenu());

                    // 弹出式菜单的菜单项注册点击事件
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.to_message:
                                    Toast.makeText(mContext, "跳转发消息", Toast.LENGTH_SHORT).show();
//                                    System.out.println("菜单删除键");
//                                    fruitList.remove(i);
//                                    adapter.notifyItemRemoved(i);
////                                adapter.notifyDataSetChanged();
                                    break;
                                case R.id.delete_contact:
                                    Toast.makeText(mContext, "删除联系人", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }

        //adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onError(String info) {

    }

    @Override
    public void onDialogClick(Dialog dialog) {

    }

    @Override
    public void onDialogLongClick(Dialog dialog) {

    }
}
