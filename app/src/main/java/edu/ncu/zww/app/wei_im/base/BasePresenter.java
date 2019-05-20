package edu.ncu.zww.app.wei_im.base;

import java.lang.ref.WeakReference;

import edu.ncu.zww.app.wei_im.mvp.model.bean.TranObject;

// 防止所持的view都销毁了，但presenter一直持有，导致内存泄漏。
public abstract class BasePresenter<T> {

    /**
     * view的弱引用
     */
    protected WeakReference<T> mViewRef;

    /**
     * 关联
     * @param view
     */
    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view);
    }

    protected T getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * 解除关联
     */
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
        }
        close();
    }

    /**
     *  传递给子类的消息对象
     * */
    public abstract void getMessage(TranObject msg);

    public abstract void close();
}
