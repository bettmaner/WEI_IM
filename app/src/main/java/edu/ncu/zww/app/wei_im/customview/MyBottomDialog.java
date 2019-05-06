package edu.ncu.zww.app.wei_im.customview;

import android.content.Context;
import android.view.View;

import edu.ncu.zww.app.wei_im.R;

public class MyBottomDialog extends BottomDialogBase implements
        View.OnClickListener{

    private QuitItemClickInterface mQuitInterface;

    public MyBottomDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate() {
        setContentView(R.layout.mine_quit_dialog);
        findViewById(R.id.logoff).setOnClickListener(this);
        findViewById(R.id.close_app).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logoff:
                if (mQuitInterface!=null) {
                    mQuitInterface.onClickLogoff();
                }
                break;
            case R.id.close_app:
                if (mQuitInterface!=null) {
                    mQuitInterface.onClickQuit();
                }
                break;
            default:
        }
        dismiss(); // 关闭弹框
    }


    // 传入接口监听
    public void setQuitClickListen(QuitItemClickInterface clickInterface) {
        this.mQuitInterface = clickInterface;
    }


    public interface QuitItemClickInterface{
        void onClickLogoff(); // 点击退出登录
        void onClickQuit(); // 点击关闭IM
    }

}
