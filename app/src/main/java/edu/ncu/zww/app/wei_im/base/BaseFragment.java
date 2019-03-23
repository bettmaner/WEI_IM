package edu.ncu.zww.app.wei_im.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;


public abstract class BaseFragment<V,T extends BasePresenter<V>> extends Fragment {

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    protected T mPresenter;
    protected Context mContext;


    /**
     * 当Fragment进行创建的时候执行的方法
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    /** 最普通简单的Fragment只有onCreateView就行了 */

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.detachView();
        }
    }

    protected abstract T createPresenter();

    /**
     * 加载页面UI元素
     */
   /* protected abstract void initView();

    protected abstract void initListener();*/
}
