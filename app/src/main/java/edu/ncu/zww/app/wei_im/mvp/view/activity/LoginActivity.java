package edu.ncu.zww.app.wei_im.mvp.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.base.BaseActivity;
import edu.ncu.zww.app.wei_im.mvp.contract.LRContract;
import edu.ncu.zww.app.wei_im.mvp.model.Model;
import edu.ncu.zww.app.wei_im.mvp.presenter.LoginPresenter;
import edu.ncu.zww.app.wei_im.service.GetMsgService;
import edu.ncu.zww.app.wei_im.utils.LogUtil;
import edu.ncu.zww.app.wei_im.utils.ToolBarHelper;

public class LoginActivity
        extends BaseActivity<LRContract.LRView, LoginPresenter>
        implements LRContract.LRView, View.OnClickListener{

    private EditText account, password;
    private Button loginBtn;
    private TextView forgetPsw, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("咔咔咔2咔咔咔"+Model.getInstance().getGlobalThreadPool());
        initView();
        initListener();


    }

    @Override
    protected void onResume() {
        super.onResume();
//        mPresenter.isNetWorkAvailable(); // 判断网络状态并处理
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void handleToolBar(ToolBarHelper toolBarHelper) {

    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initView() {
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login_btn);
        forgetPsw = findViewById(R.id.forget_password);
        register = findViewById(R.id.register);
    }

    @Override
    protected void initListener() {
        loginBtn.setOnClickListener(this);
        forgetPsw.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn :
                mPresenter.checkFormat(account.getText().toString(), password.getText().toString());
                break;
            case R.id.forget_password :
                break;
            case R.id.register :
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent,1);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    // 获取注册成功的id号
                    String  registerId= data.getStringExtra("registerId");
                    account.setText(registerId);
                    LogUtil.d("LoginActivity获取"+registerId);
                }
        }
    }


    @Override
    public void NetWorkAvailable() {
//        Toast.makeText(this, "网络可用", Toast.LENGTH_SHORT).show();
//
//        // 开启接收信息服务GetMsgService
//        Intent service = new Intent(LoginActivity.this, GetMsgService.class);
//        startService(service);
//        LogUtil.d("已完成开启服务");

    }

    @Override
    public void NetWorkUnAvailable() {
//        toast(this);
    }

    @Override
    public void onCheckFormatFail(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLRSuccess(String info) {
        // 跳转
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        super.finish(); // 记住销毁该activity
    }

    @Override
    public void onLRFail(String errorInfo) {
         Toast.makeText(this, errorInfo, Toast.LENGTH_SHORT).show();
    }


    private void toast(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("温馨提示")
                .setMessage("亲！您的网络连接未打开哦")
                .setPositiveButton("前往打开",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent intent = new Intent(
                                        android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(intent);
                            }
                        }).setNegativeButton("取消", null).create().show();
    }
}
