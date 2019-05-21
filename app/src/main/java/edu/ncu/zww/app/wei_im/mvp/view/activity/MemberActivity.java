package edu.ncu.zww.app.wei_im.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;
import com.leon.lib.settingview.LSettingItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.base.BaseActivity;
import edu.ncu.zww.app.wei_im.mvp.contract.MemberContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.GroupInfo;
import edu.ncu.zww.app.wei_im.mvp.presenter.MemberPresenter;
import edu.ncu.zww.app.wei_im.mvp.view.adapter.MemberAdapter;
import edu.ncu.zww.app.wei_im.utils.ToolBarHelper;

import static com.dou361.dialogui.DialogUIUtils.showToast;

public class MemberActivity extends BaseActivity<MemberContract.MemberView,MemberPresenter>
        implements MemberContract.MemberView{

    @BindView(R.id.members_rv)
    RecyclerView recyclerView;
    @BindView(R.id.group_name_view)
    LSettingItem gNameView;
    @BindView(R.id.quit_group_view)
    TextView quitView;

    private MemberAdapter adapter;
    private GroupInfo groupInfo; // 群基本数据
    private List<Contact> memberList; // 联系人数据
    private int groupId;

    public static void actionStart(Context context, int gid) {
        Intent intent = new Intent(context,MemberActivity.class);
        intent.putExtra("gid",gid);
        context.startActivity(intent);
    }

    // 获取上一个活动传来的数据
    private void getIntentData() {
        groupId = getIntent().getIntExtra("gid",0);
    }

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
        gNameView.setRightText(groupInfo.getName());
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
        getIntentData();
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        memberList = new ArrayList<>();
        //getMemberLIst();
        initRecycler();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {
        groupInfo = mPresenter.getGroupInfo(groupId);
        initView();
        mPresenter.getMemberList(groupId);
    }

    /*private void getMemberLIst() {
        memberList.add(new Contact(12345,"胡同学","asass",1));
        memberList.add(new Contact(12345,"李同学","asass",1));
        memberList.add(new Contact(12345,"李主任","asass",1));
        memberList.add(new Contact(12345,"王院长","asass",1));
        memberList.add(new Contact(12345,"王同学","asass",1));
        memberList.add(new Contact(12345,"周校长","asass",1));
        memberList.add(new Contact(12345,"徐同学","asass",1));
    }*/

    private void initRecycler() {
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MemberAdapter(memberList);
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.quit_group_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.quit_group_view:
                String str = "删除并退出后，将不再接收此群聊信息";
                DialogUIUtils.showAlert(this, "", str, "", "", "确定", "取消", false, true, true, new DialogUIListener() {
                    @Override
                    public void onPositive() {
                        mPresenter.quitGroup(groupId);
                    }

                    @Override
                    public void onNegative() {
                        showToast("onNegative");
                    }

                }).show();
                break;
            default:
        }
    }

    @Override
    public void onQuerySuccess(List<Contact> list) {
        memberList.clear();
        memberList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void quitGroupSuccess() {
        startActivity(new Intent(MemberActivity.this,MainActivity.class));
    }

}
