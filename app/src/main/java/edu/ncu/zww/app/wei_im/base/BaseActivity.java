package edu.ncu.zww.app.wei_im.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import edu.ncu.zww.app.wei_im.commons.Constants;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;


public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {

    protected T mPresenter;

    /**
     * 广播接收者，接收GetMsgService发送过来的消息
     */
    private BroadcastReceiver MsgReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            TranObject msg = (TranObject) intent
                    .getSerializableExtra(Constants.MSGKEY);
            if (msg != null) {//如果不是空，说明是消息广播
                // System.out.println("MyActivity:" + msg);
                mPresenter.getMessage(msg);// 处理收到的消息
            } else {//如果是空消息，说明是关闭应用的广播
                close();
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 创建Presenter
        mPresenter = createPresenter();
        mPresenter.attachView((V)this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION);
        registerReceiver(MsgReceiver, intentFilter);    // 注册接受消息广播
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MsgReceiver);    // 注销接受消息广播
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract T createPresenter();

    // 以下自定义拓展


    /**
     * 子类直接调用这个方法关闭应用
     */
    public void close() {
        Intent i = new Intent();
        i.setAction(Constants.ACTION);
        sendBroadcast(i);
        finish();
    }

    /**
     * 加载页面UI元素
     */
    protected abstract void initView();

    protected abstract void initListener();


}
