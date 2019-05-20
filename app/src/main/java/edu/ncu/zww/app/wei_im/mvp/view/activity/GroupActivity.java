package edu.ncu.zww.app.wei_im.mvp.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.base.BaseActivity;
import edu.ncu.zww.app.wei_im.mvp.contract.ContactContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.GroupInfo;
import edu.ncu.zww.app.wei_im.mvp.presenter.GroupPresenter;
import edu.ncu.zww.app.wei_im.mvp.view.adapter.GroupAdapter;
import edu.ncu.zww.app.wei_im.utils.ToolBarHelper;

public class GroupActivity extends BaseActivity<ContactContract.ContactView, GroupPresenter>
        implements ContactContract.ContactView {

    RecyclerView groupListView; // 好友列表
    View bottomView; // 底部布局
    TextView footer_text; // 底部文本内容
    private GroupAdapter adapter;

    private List<GroupInfo> groupList; // 群列表

    // -----------------------  继承方法 ------------------------
    @Override
    protected int getContentViewId() {
        return R.layout.activity_group;
    }

    @Override
    protected void handleToolBar(ToolBarHelper toolBarHelper) {
        toolBarHelper.setTitle("群");
        toolBarHelper.setBackIcon();
    }

    @Override
    protected GroupPresenter createPresenter() {
        return new GroupPresenter();
    }

    @SuppressLint("ResourceType")
    @Override
    protected void initView() {
        groupListView = findViewById(R.id.groups_rv);
        bottomView = getLayoutInflater().inflate(R.layout.footer_item,null);
        footer_text = bottomView.findViewById(R.id.footer_text);
    }

    @Override
    protected void initListener() {

    }
    // -----------------------  end! 继承方法 -----------------------

    // toolbar菜单按钮点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initRecycler();
        mPresenter.getGroupList();

    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        groupListView.setLayoutManager(layoutManager);
        //groupListView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter = new GroupAdapter(new ArrayList<GroupInfo>());
        groupListView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GroupInfo group = groupList.get(position);
                String name = group.getName();
                int gid = group.getGid();
                ChatActivity.actionStart(GroupActivity.this,name,gid,1);
                finish();
            }
        });
    }


    @Override
    public void onQuerySuccess(List list) {
        this.groupList = list;
        adapter.addData(0,list);
        footer_text.setText("共"+list.size()+"个群聊");
        adapter.addFooterView(bottomView);
    }

    @Override
    public void onError(String info) {

    }
}
