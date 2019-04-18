package edu.ncu.zww.app.wei_im.mvp.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gjiazhe.wavesidebar.WaveSideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.base.BaseFragment;
import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.view.adapter.ContactAdapter;
import edu.ncu.zww.app.wei_im.mvp.view.adapter.MyRecyclerView;
import edu.ncu.zww.app.wei_im.utils.PinYinUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends BaseFragment {
    private WaveSideBar waveSideBar;
    private MyRecyclerView recyclerView;
    private View mHeaderView; // recycler头布局
    private View mEmptyView;    // recycler空布局
    private LinearLayout newFriendsView, groupView; // 头布局的两个item

    private List<Contact> friends;

    public ContactsFragment() {
        // Required empty public constructor
        initData();
    }

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
        ContactAdapter adapter = new ContactAdapter(friends);
        recyclerView.setHeaderView(mHeaderView); // 设置头布局
        recyclerView.setEmptyView(mEmptyView); // 设置空布局
        recyclerView.setAdapter(adapter); // 配置adapter
        // 普通item点击事件
        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String name = friends.get(position).getName();
                String pinyin = PinYinUtils.getPinyin(name);
                Toast.makeText(mContext, pinyin, Toast.LENGTH_SHORT).show();
            }
        });
        // 头布局的新的好友点击事件
        newFriendsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    protected BasePresenter createPresenter() {
        return null;
    }

    private void initData() {
        friends = new ArrayList<>();
        friends.add(new Contact("啊虎"));
        friends.add(new Contact("#白虎"));
        friends.add(new Contact( "常羲"));
//        friends.add(new Contact( "./嫦娥"));
        friends.add(new Contact( "二郎神"));
        friends.add(new Contact( "伏羲"));
        friends.add(new Contact( "观世音"));
        friends.add(new Contact( "精卫"));
        friends.add(new Contact( "夸父"));
        friends.add(new Contact( "女娲"));
        friends.add(new Contact( "哪吒"));
        friends.add(new Contact( "盘古"));
        friends.add(new Contact( "青龙"));
        friends.add(new Contact( "如来"));
        friends.add(new Contact( "孙悟空"));
        friends.add(new Contact( "沙僧"));
        friends.add(new Contact( "顺风耳"));

        friends.add(new Contact( "1羲和"));
        friends.add(new Contact( "玄武"));
        friends.add(new Contact( "猪八戒"));
        friends.add(new Contact( "朱雀"));
        friends.add(new Contact( "祝融"));
        friends.add(new Contact( "太白金星"));
        friends.add(new Contact( "太上老君"));


        Collections.sort(friends, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getPinyin().compareTo(c2.getPinyin());
            }
        });
        List<Contact> notLetter = new ArrayList<>();
        List<Contact> copyContacts = new ArrayList<>(friends);
        for (Contact contact : copyContacts) {
            if (contact.getLetter().equals("#")) {
                notLetter.add(contact);
                friends.remove(contact);
            }
        }
        friends.addAll(notLetter);
        copyContacts.clear();
    }
}
