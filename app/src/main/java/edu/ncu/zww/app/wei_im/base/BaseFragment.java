package edu.ncu.zww.app.wei_im.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import java.lang.reflect.Method;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.utils.LogUtil;
import edu.ncu.zww.app.wei_im.utils.ToolBarHelper;


public abstract class BaseFragment<V,T extends BasePresenter<V>> extends Fragment {

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    protected T mPresenter;
    protected Context mContext;

    private ToolBarHelper mToolBarHelper;
    /**
     * 当Fragment进行创建的时候执行的方法
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* MVP部分 1 */
        mContext = getActivity();
        mPresenter = createPresenter();
        if (null != mPresenter) {
            mPresenter.attachView((V)this);
        }

        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
//        Toolbar toolbar = appCompatActivity.findViewById(R.id.toolbar);
//        if(toolbar != null){
//            System.out.println("输出toolbar对象"+toolbar);
//            mToolBarHelper = new ToolBarHelper(toolbar);
//            // 调用执行子类实现的handleToolBar()，通过mToolBarHelper设置toolbar
//            handleToolBar(mToolBarHelper);
//            appCompatActivity.setSupportActionBar(toolbar);
//            //setHasOptionsMenu(true);
//        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

//    /** 加载菜单布局文件 */
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        LogUtil.d("加载布局文件onCreateOptionsMenu");
//        menu.clear();
//        inflater.inflate(R.menu.toolbar, menu);
//    }

//    /** 设置弹出菜单中的图标可用 */
//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        LogUtil.d("设置菜单项图标显示onPrepareOptionsMenu");
//        if(menu != null){
//            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
//                try{
//                    Method m = menu.getClass().getDeclaredMethod(
//                            "setOptionalIconsVisible", Boolean.TYPE);
//                    m.setAccessible(true);
//                    m.invoke(menu, true);
//                }
//                catch(NoSuchMethodException e){}
//                catch(Exception e){}
//            }
//        }
//    }

    /** 最普通简单的Fragment只有onCreateView就行了 */

    @Override
    public void onDestroy() {
        super.onDestroy();
        /* MVP部分 2 */
        if (null != mPresenter) {
            mPresenter.detachView();
        }
    }

    /* MVP部分 3 */
    protected abstract T createPresenter();

    /**
     * 加载页面UI元素
     */
   /* protected abstract void initView();

    protected abstract void initListener();*/

    /**
     * 子类去实现
     */
//    protected abstract void handleToolBar(ToolBarHelper toolBarHelper);
}
