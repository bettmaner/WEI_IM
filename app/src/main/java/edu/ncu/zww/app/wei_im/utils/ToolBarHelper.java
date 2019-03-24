package edu.ncu.zww.app.wei_im.utils;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import edu.ncu.zww.app.wei_im.R;

public class ToolBarHelper {
    private Toolbar mToolbar;

    public ToolBarHelper(Toolbar toolbar) {
        mToolbar = toolbar;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setTitle(String title){
        mToolbar.setTitle("");
        TextView toolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(title);
    }

    public void setBackIcon() {
        mToolbar.setNavigationIcon(R.drawable.ic_back);
    }
}
