package edu.ncu.zww.app.wei_im.mvp.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.mvp.view.fragment.ContactsFragment;
import edu.ncu.zww.app.wei_im.mvp.view.fragment.DiscoveryFragment;
import edu.ncu.zww.app.wei_im.mvp.view.fragment.MsgFragment;
import edu.ncu.zww.app.wei_im.mvp.view.fragment.QzoneFragment;
import edu.ncu.zww.app.wei_im.utils.LogUtil;
import edu.ncu.zww.app.wei_im.utils.ToolBarHelper;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private ToolBarHelper mToolBarHelper;
    private boolean setMenuShow;    // 是否显示menu
    private boolean isFirst;        // 是否是第一次加载
    private final String[] title = {"消息","好友","发现","动态"};

    private TextView msgTab, friTab, disTab, qzoTab;   // 消息、好友、发现、动态按钮
    private Badge msgNumView,friNumView,disNumView,qzoNumView; //底部导航按钮对应的红点信息数框

    private FragmentManager fragmentManager;
    private MsgFragment msgFra;
    private ContactsFragment contFra;
    private DiscoveryFragment discFra;
    private QzoneFragment qzoneFra;

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
        setMenuShow = isFirst = true; //默认显示toolbar右边菜单
    }

    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        setToolBar();

        msgTab = findViewById(R.id.menu_message);
        friTab = findViewById(R.id.menu_friends);
        disTab = findViewById(R.id.menu_discovery);
        qzoTab = findViewById(R.id.menu_qzone);

        msgNumView = new QBadgeView(this).bindTarget(msgTab)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(12,0,true);
        friNumView = new QBadgeView(this).bindTarget(friTab)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(27,5,true);
        disNumView = new QBadgeView(this).bindTarget(disTab)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(27,5,true);
        qzoNumView = new QBadgeView(this).bindTarget(qzoTab)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(27,5,true);
        msgNumView.setBadgeNumber(12);
       initListener();
    }

    protected void initListener() {
        msgTab.setOnClickListener(this);
        friTab.setOnClickListener(this);
        disTab.setOnClickListener(this);
        qzoTab.setOnClickListener(this);
    }

    /** ToolBar.1 - 初始化设置toolbar  */
    protected void setToolBar(){
        if(toolbar != null) {
            System.out.println("输出toolbar对象" + toolbar);
            mToolBarHelper = new ToolBarHelper(toolbar);
            // 调用执行子类实现的handleToolBar()，通过mToolBarHelper设置toolbar
            mToolBarHelper.setTitle("消息");
            setSupportActionBar(toolbar); // 将toolbar设为ActionBar，可以使用ActionBar方法
            mToolBarHelper.setLogo(R.drawable.ic_add_friend);
//            toolbar.setOverflowIcon(); // 修改主菜单图标
        }
    }

    /** ToolBar.2 - 加载菜单布局文件 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    /** ToolBar.3 - 设置菜单下拉菜单有图标 */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        LogUtil.d("设置菜单项图标显示onPrepareOptionsMenu");
        if(menu != null){
            if(menu.getClass().getSimpleName().equals("MenuBuilder")  && isFirst){
                try{
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }
                catch(NoSuchMethodException e){}
                catch(Exception e){}
            }
            menu.setGroupVisible(R.id.addMenu,setMenuShow);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        switch (v.getId()) {
            case R.id.menu_message :
                preChangeFra(0,msgTab);
                if (msgFra == null) {
                    msgFra = new MsgFragment();
                    transaction.add(R.id.fl_content,msgFra);
                } else {
                    transaction.show(msgFra);
                }
                break;
            case R.id.menu_friends :
                preChangeFra(1,friTab);
                if (contFra == null) {
                    contFra = new ContactsFragment();
                    transaction.add(R.id.fl_content,contFra);
                } else {
                    transaction.show(contFra);
                }
                msgNumView.setBadgeNumber(20);
                break;
            case R.id.menu_discovery :
                preChangeFra(2,disTab);
                if (discFra == null) {
                    discFra = new DiscoveryFragment();
                    transaction.add(R.id.fl_content,discFra);
                } else {
                    transaction.show(discFra);
                }
                //toolbar.setVisibility(View.GONE);
                break;
            case R.id.menu_qzone :
                preChangeFra(3,qzoTab);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newGroupChat:
                Toast.makeText(this, "创建群聊", Toast.LENGTH_SHORT).show();
                break;
            case R.id.addFriend:
                startActivity(new Intent(this, AddContactActivity.class));
                break;
            default:
        }
        return true;
    }

    // 切换Fragment前完成的动作
    private void preChangeFra(int index,View view){
        setSelected();  // 按钮全设为未选中状态
        view.setSelected(true);
        mToolBarHelper.setTitle(title[index]);  // 更换title
        if (index < 2) {    // 即点中前两个tab。消息或好友页面
            setMenuShow = true; // 显示顶部toolbar的右边memu，图标‘+’
        } else {
            setMenuShow = false;
        }
        isFirst = false;
        invalidateOptionsMenu(); // 是之前菜单失效从而回调onPrepareOptionsMenu
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
