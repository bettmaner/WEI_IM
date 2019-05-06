package edu.ncu.zww.app.wei_im.mvp.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;

import java.util.ArrayList;
import java.util.List;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.base.BaseActivity;
import edu.ncu.zww.app.wei_im.mvp.contract.OperaFGContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Invitation;
import edu.ncu.zww.app.wei_im.mvp.presenter.AddContactPresenter;
import edu.ncu.zww.app.wei_im.mvp.view.adapter.AddContactAdapter;
import edu.ncu.zww.app.wei_im.customview.MyRecyclerView;
import edu.ncu.zww.app.wei_im.utils.ToolBarHelper;

import static com.dou361.dialogui.DialogUIUtils.showToast;

/* 联系人添加界面 */
public class AddContactActivity
        extends BaseActivity<OperaFGContract.AddContactView,AddContactPresenter>
        implements OperaFGContract.AddContactView,View.OnClickListener{

    private SearchView searchView;
    private LinearLayout queryLayout;
    private TextView queryFriend, queryGroup, typeText;

    private Integer type; // 0找人，1找群

    private MyRecyclerView recyclerView;
    private View mHeaderView; // recycler头布局
    private View mEmptyView;    // recycler空布局
    private AddContactAdapter adapter;


    private List<Contact> contactList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();

        adapter = new AddContactAdapter(contactList);
        adapter.setHasStableIds(true);
        setAdapterListen(); // 设置recycler点击事件

        recyclerView.setAdapter(adapter);
        recyclerView.setHeaderView(mHeaderView);
        recyclerView.setEmptyView(mEmptyView);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_contact;
    }

    @Override
    protected void handleToolBar(ToolBarHelper toolBarHelper) {
        toolBarHelper.setTitle("添加联系人");
        toolBarHelper.setBackIcon();
    }

    // toolbar返回图标点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected AddContactPresenter createPresenter() {
        return new AddContactPresenter();
    }

    @Override
    protected void initView() {
        searchView = findViewById(R.id.searchView); // 搜索框

        queryLayout = findViewById(R.id.queryLayout); // 显示输入搜索内容出现的布局
        queryFriend = findViewById(R.id.query_friend); // 显示的找人TextView
        queryGroup = findViewById(R.id.query_group);   // 显示的找群TextView

        recyclerView = findViewById(R.id.recycler_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        /* 加载外部布局 */
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.add_contact_item_header, recyclerView, false);
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.add_contact_item_empty, recyclerView, false);
        typeText = mHeaderView.findViewById(R.id.type_text);
    }

    @Override
    protected void initListener() {
        queryFriend.setOnClickListener(this);
        queryGroup.setOnClickListener(this);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            // 当输入框内容变化时
            @Override
            public boolean onQueryTextChange(String s) {
                recyclerView.setVisibility(View.GONE); // 隐藏列表
                if(TextUtils.isEmpty(s)) { // 无内容则隐藏
                    queryLayout.setVisibility(View.GONE);
                } else {
                    queryFriend.setText("找人："+s);
                    queryGroup.setText("找群："+s);
                    queryLayout.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query_friend:
                // 点击找人
                final String friCondition = queryFriend.getText().toString().substring(3);
                type = 0;
                // queryContacts的参数：账号，类型（0人1群），
                mPresenter.queryContacts(friCondition,type);
                break;
            case R.id.query_group:
                // 点击找群
                final String groupCondition = queryGroup.getText().toString().substring(3);
                type = 1;
                mPresenter.queryContacts(groupCondition,type);
                break;
            default:
        }
    }

    private void setAdapterListen() {
        adapter.itemSetOnclick(new AddContactAdapter.ItemClickInterface() {
            @Override
            public void onClick(View view) {
                // 因为view可能是item或按钮，所以要处理
                String tag = view.getTag().toString();
                int flag = tag.indexOf(':');
                String vType = tag.substring(0,flag);
                // 得到点击位置
                int position = Integer.parseInt(tag.substring(flag+1));
                Contact contact = contactList.get(position);

                switch (vType) {
                    case "iBtn":
                        mPresenter.preAddContact(contact,"添加好友呗",type); // 添加联系人
                        break;
                    case "item":
                        System.out.println("item。位置："+position+"，name:"+contact.getName());
                        Toast.makeText(AddContactActivity.this, "item"+contact.getName(), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
            }
        });
    }

    // 查找联系人返回的list
    @Override
    public void onQuerySuccess(List list) {
        // 1.隐藏找人/群这个布局视图，显示列表视图
        queryLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        // 2. 标注结果的类型，人？群
        String typeStr = type==0?"人":"群";
        typeText.setText(typeStr);
        // 3.显示列表
        System.out.println("匹配到的人数"+list.size());
        contactList.clear();
        contactList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    // 添加联系人成功
    @Override
    public void onAddSuccess(String info) {
        DialogUIUtils.showAlert(this, "标题", info, "", "", "确定", "", true, true, true, new DialogUIListener() {
            @Override
            public void onPositive() {
            }

            @Override
            public void onNegative() {
            }
        }).show();
    }

    // 添加联系人失败
    @Override
    public void onFail(String info) {
        System.out.println("联系人添加失败，"+info);
        DialogUIUtils.showToastTopLong(info);
    }

    // 异常
    @Override
    public void onError(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.closeRealm();
    }
}
