package edu.ncu.zww.app.wei_im.mvp.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.ncu.zww.app.wei_im.R;
import edu.ncu.zww.app.wei_im.base.BaseFragment;
import edu.ncu.zww.app.wei_im.base.BasePresenter;
import edu.ncu.zww.app.wei_im.utils.ToolBarHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends BaseFragment {


    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

//    @Override
//    protected void handleToolBar(ToolBarHelper toolBarHelper) {
//        toolBarHelper.setTitle("通讯录");
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.newGroupChat:
//                Toast.makeText(mContext, "创建群聊", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.addFriend:
//                Toast.makeText(mContext, "添加好友", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.addGroupChat:
//                Toast.makeText(mContext, "加入群聊", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
