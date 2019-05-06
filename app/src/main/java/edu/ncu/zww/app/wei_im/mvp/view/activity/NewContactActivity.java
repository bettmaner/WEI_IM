package edu.ncu.zww.app.wei_im.mvp.view.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;

import java.util.ArrayList;
import java.util.List;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.base.BaseActivity;
import edu.ncu.zww.app.wei_im.customview.MyRecyclerView;
import edu.ncu.zww.app.wei_im.mvp.contract.OperaFGContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Invitation;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ResultBean;
import edu.ncu.zww.app.wei_im.mvp.presenter.NewContactPresenter;
import edu.ncu.zww.app.wei_im.mvp.view.adapter.InvitationAdapter;
import edu.ncu.zww.app.wei_im.utils.ToolBarHelper;

/* 新朋友跳转的Activity，即好友请求状态界面 */
public class NewContactActivity
        extends BaseActivity<OperaFGContract.NewContactView, NewContactPresenter>
        implements OperaFGContract.NewContactView{

    private MyRecyclerView recyclerView;
    private View mHeaderView; // recycler头布局
    private View mEmptyView;    // recycler空布局
    private InvitationAdapter adapter;
    private int position;

    private List<Invitation> invitationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        invitationList = mPresenter.queryInvitations();

        adapter = new InvitationAdapter(invitationList);
        adapter.setHasStableIds(true);
        setAdapterListen(); // 设置recycler点击事件

        recyclerView.setAdapter(adapter);
        recyclerView.setHeaderView(mHeaderView);
        recyclerView.setEmptyView(mEmptyView);


    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_new_contact;
    }

    @Override
    protected void handleToolBar(ToolBarHelper toolBarHelper) {
        toolBarHelper.setTitle("新朋友");
        toolBarHelper.setBackIcon();
    }

    @Override
    protected NewContactPresenter createPresenter() {
        return new NewContactPresenter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recycler_invitation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        mHeaderView = LayoutInflater.from(this).inflate(R.layout.add_contact_item_header, recyclerView, false);
        TextView headerText =  mHeaderView.findViewById(R.id.type_text);
        headerText.setText("邀请通知");
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.add_contact_item_empty, recyclerView, false);
        TextView emptyText = mEmptyView.findViewById(R.id.empty_text);
        emptyText.setText("暂时还没有邀请");


    }

    @Override
    protected void initListener() {

    }

    private void setAdapterListen() {
        adapter.itemSetOnclick(new InvitationAdapter.ItemClickInterface() {
            @Override
            public void onClick(View view) {
                // 因为view可能是item或按钮，所以要处理
                String tag = view.getTag().toString();
                int flag = tag.indexOf(':');
                String vType = tag.substring(0,flag);
                // 得到点击位置
                position = Integer.parseInt(tag.substring(flag+1));
                Invitation invitation = invitationList.get(position);

                switch (vType) {
                    case "iBtn":
                        // 能点击只有“接受”状态
                        Toast.makeText(NewContactActivity.this, "button"+invitation.getName(), Toast.LENGTH_SHORT).show();
                        mPresenter.accessInvitation(invitation);
                        break;
                    case "item":
                        System.out.println("item。位置："+position+"，name:"+invitation.getName());
                        Toast.makeText(NewContactActivity.this, "item"+invitation.getName(), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
            }
        });
    }

    @Override
    public void accessSuccess(ResultBean result) {
        System.out.println(invitationList);
        DialogUIUtils.showAlert(this, "好友邀请", "成功添加该好友", "", "", "确定", "", true, true, true, new DialogUIListener() {
            @Override
            public void onPositive() {
            }

            @Override
            public void onNegative() {

            }

        }).show();

        adapter = new InvitationAdapter(invitationList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void accessFail(ResultBean result) {

    }

    @Override
    public void onError(String info) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.closeRealm();
    }

}
