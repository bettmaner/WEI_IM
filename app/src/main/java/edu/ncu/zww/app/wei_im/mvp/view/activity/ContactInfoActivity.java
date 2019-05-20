package edu.ncu.zww.app.wei_im.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.base.BaseActivity;
import edu.ncu.zww.app.wei_im.mvp.contract.ContactInfoContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.Contact;
import edu.ncu.zww.app.wei_im.mvp.presenter.ContactInfoPresenter;
import edu.ncu.zww.app.wei_im.utils.PictureFileUtil;
import edu.ncu.zww.app.wei_im.utils.ToolBarHelper;

public class ContactInfoActivity
        extends BaseActivity<ContactInfoContract.ContactInfoView,ContactInfoPresenter>
        implements ContactInfoContract.ContactInfoView {

    @BindView(R.id.contact_avatar)
    ImageView avatarView;
    @BindView(R.id.contact_name)
    TextView nameView;
    @BindView(R.id.contact_account)
    TextView accountView;
    @BindView(R.id.to_chat_activity)
    RelativeLayout toChatView;
    @BindView(R.id.make_friend)
    RelativeLayout makeFriView;

    private Contact mContact;

    public static void actionStart(Context context,Contact contact) {
        Intent intent = new Intent(context,ContactInfoActivity.class);
        Bundle bundle =new Bundle();
        bundle.putSerializable("contact",contact);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    // 获取上一个活动传来的数据
    private void getIntentData() {
        Intent intent = getIntent();
        mContact = (Contact) intent.getSerializableExtra("contact");
    }

    // -----------------------  继承方法 ------------------------
    @Override
    protected int getContentViewId() {
        return R.layout.activity_contact_info;
    }

    @Override
    protected void handleToolBar(ToolBarHelper toolBarHelper) {
        toolBarHelper.setTitle("");
        toolBarHelper.setBackIcon();
    }

    @Override
    protected ContactInfoPresenter createPresenter() {
        return new ContactInfoPresenter();
    }

    @Override
    protected void initView() {
        //avatarView
        ButterKnife.bind(this);
        nameView.setText(mContact.getName());
        accountView.setText(mContact.getAccount().toString());
        if (mContact.getIsContact() == 1) {
            toChatView.setVisibility(View.VISIBLE);
        } else {
            makeFriView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void getContact(Integer account) {

    }

    @Override
    public void onError(String info) {

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
        getIntentData(); // 获取上个活动传来的数据
        super.onCreate(savedInstanceState);
        initView();
    }

    @OnClick({R.id.to_chat_activity,R.id.make_friend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.to_chat_activity:
                ChatActivity.actionStart(ContactInfoActivity.this,
                        mContact.getName(),mContact.getAccount(),0);
                finish();
                break;
            case R.id.make_friend:
                mPresenter.preAddContact(mContact,"添加好友呗"); // 添加联系人
                break;
            default:
        }
    }

    public void onAddSuccess(String info) {
        DialogUIUtils.showAlert(this, "标题", info, "", "", "确定", "", true, true, true, new DialogUIListener() {
            @Override
            public void onPositive() {
                finish();
            }

            @Override
            public void onNegative() {
                finish();
            }
        }).show();
    }

    @Override
    public void onFail(String info) {
        System.out.println("联系人添加失败，"+info);
        DialogUIUtils.showToastTopLong(info);
    }

}
