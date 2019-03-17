package edu.ncu.zww.app.wei_im.mvp.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.ncu.zww.app.wei_im.MainActivity;
import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.mvp.model.Model;

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

        System.out.println("咔咔咔1咔咔咔"+Model.getInstance().getGlobalThreadPool());
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
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
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉状态栏
        handler.sendMessageDelayed(Message.obtain(), 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
