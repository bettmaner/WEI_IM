package edu.ncu.zww.app.wei_im.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.commons.Constants;
import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;
import edu.ncu.zww.app.wei_im.utils.ActivityCollector;
import edu.ncu.zww.app.wei_im.utils.LogUtil;
import edu.ncu.zww.app.wei_im.utils.ToolBarHelper;


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
                LogUtil.d(this+"获取广播消息"+msg);
                mPresenter.getMessage(msg);// 处理收到的消息
            } else {//如果是空消息，说明是关闭应用的广播
                close();
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* 状态栏透明处理 */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /*// 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);*/
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        /* 标题栏toolbar设置 */
        setContentView(getContentViewId());
        Toolbar toolbar = findViewById(R.id.toolbar);

        if(toolbar != null){
            System.out.println("准备设置toolbar");
            ToolBarHelper mToolBarHelper = new ToolBarHelper(toolbar);
            handleToolBar(mToolBarHelper);
            setSupportActionBar(toolbar);
        }

        // 创建Presenter
        mPresenter = createPresenter();
        mPresenter.attachView((V)this);

        // 加入活动管理类便于打印该activity存在情况,记得onDestroy对应移除
        ActivityCollector.addActivity(this);
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
        ActivityCollector.removeActivity(this);
    }

    /**
     * 子类去实现
     */
    protected abstract int getContentViewId();

    protected abstract void handleToolBar(ToolBarHelper toolBarHelper);

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
