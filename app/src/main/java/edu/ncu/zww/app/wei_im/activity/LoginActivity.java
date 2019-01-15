package edu.ncu.zww.app.wei_im.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.base.BaseActivity;
import edu.ncu.zww.app.wei_im.contract.LoginContract;
import edu.ncu.zww.app.wei_im.presenter.LoginPresenter;

public class LoginActivity
        extends BaseActivity<LoginContract.LoginView, LoginPresenter>
        implements LoginContract.LoginView, View.OnClickListener{

    private EditText name, password;
    private Button loginBtn;
    private TextView forgetPsw, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        System.out.println("登录页说不定还说不定还不是的不是不会");
        initView();
        initListener();
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initView() {
        name = findViewById(R.id.name);
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
    public void onCheckFormatFail(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFail(String errorInfo) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn :
                mPresenter.checkFormat(name.getText().toString(), password.getText().toString());
                break;
            case R.id.forget_password :
                break;
            case R.id.register :
                break;
        }
    }
}
