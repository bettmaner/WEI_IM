package edu.ncu.zww.app.wei_im.mvp.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.leon.lib.settingview.LSettingItem;

import edu.ncu.zww.app.wei_im.MApplication;
import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.base.BaseFragment;
import edu.ncu.zww.app.wei_im.customview.MyBottomDialog;
import edu.ncu.zww.app.wei_im.mvp.contract.MineContract;
import edu.ncu.zww.app.wei_im.mvp.model.bean.ApplicationData;
import edu.ncu.zww.app.wei_im.mvp.model.bean.User;
import edu.ncu.zww.app.wei_im.mvp.presenter.MinePresenter;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment<MineContract.MineView, MinePresenter>
        implements MineContract.MineView {

    private ImageView backImage,headImg;
    private TextView headName;
    private LSettingItem nameView,accountView,quitView;
    private User user;

    public MineFragment() {
        // Required empty public constructor

    }

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_qzone, container, false);

        backImage = view.findViewById(R.id.h_back);
        headImg = view.findViewById(R.id.h_head);
        headName = view.findViewById(R.id.h_name);

        nameView = view.findViewById(R.id.user_name);
        accountView  = view.findViewById(R.id.user_account);
        quitView = view.findViewById(R.id.quit);
        iniData();
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nameView.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                Toast.makeText(mContext, "电脑呢", Toast.LENGTH_SHORT).show();

            }
        });
        quitView.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                MyBottomDialog dialog = new MyBottomDialog(mContext);
                dialog.setQuitClickListen(new MyBottomDialog.QuitItemClickInterface() {
                    @Override
                    public void onClickLogoff() {
                        Toast.makeText(mContext, "退出登录", Toast.LENGTH_SHORT).show();
                        mPresenter.logOff();
                    }

                    @Override
                    public void onClickQuit() {
                        Toast.makeText(mContext, "关闭应用", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
                //ActivityCollector.finishAll();

            }
        });
    }


    private void iniData() {
        /*Glide.with(this)
                .load("https://i02piccdn.sogoucdn.com/3c28af542f2d49f7-9e7c5d699eaea93e-9b1a581472b9596d6fd6cacbc968e12a_qq")
                .bitmapTransform(new BlurTransformation(getContext(), 25), new CenterCrop(MApplication.getInstance()))
                .into(backImage);
        Glide.with(this).load(R.drawable.head_icon)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(headImg);*/
        MultiTransformation multi = new MultiTransformation(new BlurTransformation(25),new CenterCrop());
        Glide.with(this)
                .load("https://i02piccdn.sogoucdn.com/3c28af542f2d49f7-9e7c5d699eaea93e-9b1a581472b9596d6fd6cacbc968e12a_qq")
                .apply(bitmapTransform(multi))
                .into(backImage);
        Glide.with(this).load(R.drawable.head_icon)
                .apply(bitmapTransform(new CircleCrop()))
                .into(headImg);
        user = ApplicationData.getInstance().getUserInfo();
        if (user != null) {
            nameView.setRightText("ghghh");
            accountView.setRightText(String.valueOf(user.getAccount()));
        }
    }

    @Override
    public void error(String info) {

    }

    @Override
    public void showDoing(Integer type) {

    }

    @Override
    public void success(String info) {

    }

    @Override
    public void fail(String info) {

    }
}
