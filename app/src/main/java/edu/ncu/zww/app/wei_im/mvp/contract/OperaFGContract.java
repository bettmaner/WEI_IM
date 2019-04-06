package edu.ncu.zww.app.wei_im.mvp.contract;
/* 契约类
 *
 * 添加好友/群 创建群聊的契约类
 * 规范定义，定义功能和模板。主要是定义回调类
 *
 */
public class OperaFGContract {

    public interface AddView {
        void onOperSuccess(String info);
        void onOperFail(String erro);
    }


    public interface AddModel {

    }
    /*public interface AddModel {
        void Operate(int id)
    }*/


    public interface LRCallBack { // 在调用model层取完数据后回调
    }

}
