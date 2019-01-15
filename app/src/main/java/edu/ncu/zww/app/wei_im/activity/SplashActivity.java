package edu.ncu.zww.app.wei_im.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.ncu.zww.app.wei_im.MainActivity;
import edu.ncu.zww.app.wei_im.R;

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if(isFinishing()) {
                return;
            }

            //判断进入主界面还是登录界面
            toMainnOrLogin();

        }
    };

    //判断进入主界面还是登录界面
    private void toMainnOrLogin() {
        new Thread() {
            @Override
            public void run() {

                if (false) {    // 如果登陆过
                    // 获取到当前登录用户的信息

                    // 跳转到主页面
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {    // 没登录过
                    // 跳转到登录界面
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();

            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        System.out.println("启动页说不定还说不定还不是的不是不会");
        handler.sendMessageDelayed(Message.obtain(), 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
