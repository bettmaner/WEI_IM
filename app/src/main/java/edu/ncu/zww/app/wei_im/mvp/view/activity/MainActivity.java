package edu.ncu.zww.app.wei_im.mvp.view.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.mvp.view.fragment.ContactsFragment;
import edu.ncu.zww.app.wei_im.mvp.view.fragment.DiscoveryFragment;
import edu.ncu.zww.app.wei_im.mvp.view.fragment.MsgFragment;
import edu.ncu.zww.app.wei_im.mvp.view.fragment.QzoneFragment;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView msgTab;    // 消息按钮
    private TextView friTab;    // 好友按钮
    private TextView disTab;    // 发现按钮
    private TextView qzoTab;    // 动态按钮

    private MsgFragment msgFra;
    private ContactsFragment contFra;
    private DiscoveryFragment discFra;
    private QzoneFragment qzoneFra;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 状态栏透明处理 */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        /* 初始化视图控件 */
        initView();
        fragmentManager = getSupportFragmentManager();
        msgTab.performClick(); // 执行msgTab点击事件，默认选中首页按钮
    }

    protected void initView() {
        msgTab = findViewById(R.id.menu_message);
        friTab = findViewById(R.id.menu_friends);
        disTab = findViewById(R.id.menu_discovery);
        qzoTab = findViewById(R.id.menu_qzone);
        new QBadgeView(this).bindTarget(friTab)
                .setGravityOffset(9,true).setBadgeNumber(12);
        new QBadgeView(this).bindTarget(msgTab)
                .setGravityOffset(9,true).setBadgeNumber(99);
        new QBadgeView(this).bindTarget(disTab)
                .setGravityOffset(9,true).setBadgeNumber(-1);
        new QBadgeView(this).bindTarget(qzoTab)
                .setGravityOffset(9,true);
        initListener();
    }

    protected void initListener() {
        msgTab.setOnClickListener(this);
        friTab.setOnClickListener(this);
        disTab.setOnClickListener(this);
        qzoTab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        switch (v.getId()) {
            case R.id.menu_message :
                setSelected();  // 按钮全设为未选中状态
                msgTab.setSelected(true);
                if (msgFra == null) {
                    msgFra = new MsgFragment();
                    transaction.add(R.id.fl_content,msgFra);
                } else {
                    transaction.show(msgFra);
                }
                break;
            case R.id.menu_friends :
                setSelected();
                friTab.setSelected(true);
                if (contFra == null) {
                    contFra = new ContactsFragment();
                    transaction.add(R.id.fl_content,contFra);
                } else {
                    transaction.show(contFra);
                }
                break;
            case R.id.menu_discovery :
                setSelected();
                disTab.setSelected(true);
                if (discFra == null) {
                    discFra = new DiscoveryFragment();
                    transaction.add(R.id.fl_content,discFra);
                } else {
                    transaction.show(discFra);
                }
                break;
            case R.id.menu_qzone :
                setSelected();
                qzoTab.setSelected(true);
                if (qzoneFra == null) {
                    qzoneFra = new QzoneFragment();
                    transaction.add(R.id.fl_content,qzoneFra);
                } else {
                    transaction.show(qzoneFra);
                }
                break;
            default:
        }
        transaction.commit();
    }

    //重置所有导航imageView的选中状态
    private void setSelected(){
        msgTab.setSelected(false);
        friTab.setSelected(false);
        disTab.setSelected(false);
        qzoTab.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(msgFra != null) fragmentTransaction.hide(msgFra);
        if(contFra != null) fragmentTransaction.hide(contFra);
        if(discFra != null) fragmentTransaction.hide(discFra);
        if(qzoneFra != null) fragmentTransaction.hide(qzoneFra);
    }

}
