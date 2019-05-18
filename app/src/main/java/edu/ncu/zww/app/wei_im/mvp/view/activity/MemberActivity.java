package edu.ncu.zww.app.wei_im.mvp.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;

import com.leon.lib.settingview.LSettingItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.base.BaseActivity;
import edu.ncu.zww.app.wei_im.mvp.contract.MemberContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.presenter.MemberPresenter;
import edu.ncu.zww.app.wei_im.mvp.view.adapter.MemberAdapter;
import edu.ncu.zww.app.wei_im.utils.ToolBarHelper;

public class MemberActivity extends BaseActivity<MemberContract.MemberView,MemberPresenter>
        implements MemberContract.MemberView{

    @BindView(R.id.members_rv)
    RecyclerView recyclerView;
    @BindView(R.id.group_name_view)
    LSettingItem gNameView;

    private MemberAdapter adapter;
    private List<Contact> memberList; // 联系人数据

    /* --------------------------- 继承方法 --------------------- */
    @Override
    protected int getContentViewId() {
        return R.layout.activity_member;
    }

    @Override
    protected void handleToolBar(ToolBarHelper toolBarHelper) {
        toolBarHelper.setTitle("群聊信息");
        toolBarHelper.setBackIcon();
    }

    @Override
    protected MemberPresenter createPresenter() {
        return new MemberPresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

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
    /* --------------------------- end 继承方法 --------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        memberList = new ArrayList<>();
        getMemberLIst();
        initRecycler();
        gNameView.setRightText("未命名");
    }

    private void getMemberLIst() {
        memberList.add(new Contact(12345,"胡同学","asass",1));
        memberList.add(new Contact(12345,"李同学","asass",1));
        memberList.add(new Contact(12345,"李主任","asass",1));
        memberList.add(new Contact(12345,"王院长","asass",1));
        memberList.add(new Contact(12345,"王同学","asass",1));
        memberList.add(new Contact(12345,"周校长","asass",1));
        memberList.add(new Contact(12345,"徐同学","asass",1));
    }

    private void initRecycler() {
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MemberAdapter(memberList);
        recyclerView.setAdapter(adapter);
    }

}
