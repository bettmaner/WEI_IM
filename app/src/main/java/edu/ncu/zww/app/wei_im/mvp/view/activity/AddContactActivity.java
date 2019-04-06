package edu.ncu.zww.app.wei_im.mvp.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.base.BaseActivity;
import edu.ncu.zww.app.wei_im.mvp.contract.OperaFGContract;
import edu.ncu.zww.app.wei_im.mvp.presenter.OperaFGPresenter;
import edu.ncu.zww.app.wei_im.utils.ToolBarHelper;

/* 联系人添加界面 */
public class AddContactActivity
        extends BaseActivity<OperaFGContract.AddView,OperaFGPresenter>
        implements OperaFGContract.AddView,View.OnClickListener{

    private SearchView searchView;
    private LinearLayout queryLayout;
    private TextView queryFriend,queryGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_contact;
    }

    @Override
    protected void handleToolBar(ToolBarHelper toolBarHelper) {
        toolBarHelper.setTitle("添加联系人");
    }

    @Override
    protected OperaFGPresenter createPresenter() {
        return new OperaFGPresenter();
    }

    @Override
    protected void initView() {
        searchView = findViewById(R.id.searchView);
        queryLayout = findViewById(R.id.queryLayout);
        queryFriend = findViewById(R.id.query_friend);
        queryGroup = findViewById(R.id.query_group);
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
                if(TextUtils.isEmpty(s)) {
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
    public void onOperSuccess(String info) {

    }

    @Override
    public void onOperFail(String erro) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query_friend:
                final String friCondition = queryFriend.getText().toString().substring(3);
                break;
            case R.id.query_group:
                final String groupCondition = queryGroup.getText().toString().substring(3);
                break;
            default:
        }
    }
}
