package edu.ncu.zww.app.wei_im.mvp.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.base.BaseActivity;
import edu.ncu.zww.app.wei_im.mvp.contract.LRContract;
import edu.ncu.zww.app.wei_im.mvp.presenter.RegisterPresenter;
import edu.ncu.zww.app.wei_im.utils.ToolBarHelper;

public class RegisterActivity
        extends BaseActivity<LRContract.LRView, RegisterPresenter>
        implements LRContract.LRView {

    private EditText email, password;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }


    @Override
    protected void handleToolBar(ToolBarHelper toolBarHelper) {
        toolBarHelper.setTitle("注册");
        toolBarHelper.setBackIcon();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerBtn = findViewById(R.id.register_btn);
    }

    @Override
    protected void initListener() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.checkFormat(email.getText().toString(),
                        password.getText().toString());
                /*TestInput testInput = TestInput.getInstance();
                testInput.setMsg();*/
            }
        });
    }



    @Override
    public void onCheckFormatFail(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLRSuccess(final String info) {
        System.out.println("返回成功信息");
        email.setText("12212121");
        Toast.makeText(this, "info", Toast.LENGTH_SHORT).show();
        System.out.println("成功");
        new AlertDialog.Builder(this)
                .setTitle("注册成功")
                .setMessage("请查收邮箱消息查看账号\n即将进入登录页面")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 返回到登录
                        Intent intent = new Intent();
                        intent.putExtra("registerId",info);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }).show();
    }

    @Override
    public void onLRFail(String errorInfo) {
        Toast.makeText(this, errorInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void NetWorkAvailable() {

    }

    @Override
    public void NetWorkUnAvailable() {

    }
}
