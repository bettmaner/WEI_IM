package edu.ncu.zww.app.wei_im.mvp.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;
import com.dou361.dialogui.listener.DialogUIListener;
import com.gjiazhe.wavesidebar.WaveSideBar;
import com.luck.picture.lib.tools.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.base.BaseActivity;
import edu.ncu.zww.app.wei_im.mvp.contract.ContactContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Dialog;
import edu.ncu.zww.app.wei_im.mvp.model.bean.GroupInfo;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ResultBean;
import edu.ncu.zww.app.wei_im.mvp.presenter.SelectorPresenter;
import edu.ncu.zww.app.wei_im.mvp.view.adapter.ListSelectAdapter;
import edu.ncu.zww.app.wei_im.utils.ContactSortUtil;
import edu.ncu.zww.app.wei_im.utils.ToolBarHelper;

// 联系人多选，用于选择好友创建群聊
public class SelectorActivity extends BaseActivity<ContactContract.SelectorView,SelectorPresenter>
        implements ContactContract.SelectorView {

    @BindView(R.id.friends_rv)
    RecyclerView friendListView; // 好友列表
    @BindView(R.id.index_bar)
    WaveSideBar indexBar;
    @BindView(R.id.select_total)
    TextView selTotalView;
    @BindView(R.id.submit_btn)
    TextView submitBtn;

    private ListSelectAdapter adapter;
    private List<Contact> contactList; // 联系人数据
    private Map<Integer,Contact> selectedMap = new HashMap<>(); // 选中的数据
    private int selTotal = 0; // 选中数量

    private BuildBean loadingDialog; // 加载框

    /* ---------------------- 继承方法 -------------------- */
    @Override
    protected int getContentViewId() {
        return R.layout.activity_selector;
    }

    @Override
    protected void handleToolBar(ToolBarHelper toolBarHelper) {
        toolBarHelper.setTitle("创建群聊");
        toolBarHelper.setBackIcon();
    }

    @Override
    protected SelectorPresenter createPresenter() {
        return new SelectorPresenter();
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
    /* ---------------------- end 继承方法 -------------------- */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        contactList = ContactSortUtil.sortFriends(ApplicationData.getInstance().getFriendList());
        //getContactLIst();
        initRecycler();
        initWaveSideBar();
    }

    /*private void getContactLIst() {
        contactList.add(new Contact(12345,"胡同学","asass",1));
        contactList.add(new Contact(12345,"李同学","asass",1));
        contactList.add(new Contact(12345,"李主任","asass",1));
        contactList.add(new Contact(12345,"王某","asass",1));
        contactList.add(new Contact(12345,"王同学","asass",1));
        contactList.add(new Contact(12345,"赵同学","asass",1));
        contactList.add(new Contact(12345,"赵老师","asass",1));
    }*/

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        friendListView.setLayoutManager(layoutManager);
        friendListView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter = new ListSelectAdapter(contactList);
        friendListView.setAdapter(adapter);

        //条目点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                view.findViewById(R.id.check_box).performClick(); // 点击该条目的选项框
            }
        });

        //条目子控件(复选框)点击事件
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CheckBox checkBox = (CheckBox) view;
                Contact contact = contactList.get(position); // 获取该联系人
                Integer account = contact.getAccount();
                if (checkBox.isChecked()) {
                    selTotalView.setText(String.valueOf(++selTotal));
                    selectedMap.put(account,contact); // 放入map集合中
                } else {
                    selTotalView.setText(String.valueOf(--selTotal));
                    selectedMap.remove(account); // 从map集合中移除
                }
            }
        });
    }

    private void initWaveSideBar() {
        indexBar.setIndexItems("A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
                "V", "W", "X", "Y", "Z","#");
        indexBar.setLazyRespond(false);//false:列表随侧边栏的滚动滚动
        indexBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i = 0,size=contactList.size(); i < size; i++) {
                    if (index.equals(contactList.get(i).getLetter())) {
                        // recyclerView.scrollToPosition(i);
                        //或者
                        ((LinearLayoutManager) friendListView.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }

    @OnClick({R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit_btn:
                mPresenter.submit(selectedMap);
                break;

        }
    }

    // 完成按钮事件验证成功
    @Override
    public void onSubmitVailOk(){
        final String[] groupName = {""};
        DialogUIUtils.showAlert(this, "群名称", "", "请输入群名称", "",
                "确定", "取消", false, true, true,
                new DialogUIListener() {
                    @Override
                    public void onPositive() {
                    }

                    @Override
                    public void onNegative() {
                    }

                    @Override
                    public void onGetInput(CharSequence input1, CharSequence input2) {
                        //super.onGetInput(input1, input2);
                        groupName[0] = input1.toString();
                        if (!"".equals(groupName[0])) {
                            mPresenter.createGroup(groupName[0],selectedMap);
                            onLoading();
                            System.out.println(groupName);
                        }
                    }
                }).show();
    }


    @Override
    public void onCreatedSuccess(GroupInfo groupInfo) {
        DialogUIUtils.dismiss(loadingDialog);
        int chatId = groupInfo.getGid();
        String name = groupInfo.getName();
        ChatActivity.actionStart(SelectorActivity.this,name,chatId,1);
    }

    @Override
    public void onCreatedFail(ResultBean result) {

    }


    @Override
    public void onLoading() {
        loadingDialog = DialogUIUtils.showLoading(this,"处理中", false,
                false,false,false);
        loadingDialog.show();
    }

    @Override
    public void onError(String info) {
        DialogUIUtils.showToastLong(info);
    }
}
