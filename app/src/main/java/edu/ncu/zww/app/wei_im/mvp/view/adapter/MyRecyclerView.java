package edu.ncu.zww.app.wei_im.mvp.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 *  自定义 RecyclerView
 *  使用和平常的RecyclerView一样，只是多了设置头尾空布局的这3个方法
 *  */
public class MyRecyclerView extends RecyclerView {

    private View mHeaderView;
    private View mFooterView;
    private View mEmptyView;
    private BaseAdapter mAdapter;

    public MyRecyclerView(@NonNull Context context) {
        super(context);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     *  改动的是内部BaseAdapter.
     *  不过和平常一样使用该方法设定各种adapter
     *  */
    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter != null) mAdapter = new BaseAdapter(adapter); //创建基于外部Adapter的内部Adapter
        super.setAdapter(mAdapter);
    }

    /* 设置头布局 */
    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
        if (null != mAdapter) mAdapter.notifyItemInserted(0);
    }

    /* 设置尾布局 */
    public void setFooterView(View footerView) {
        this.mFooterView = footerView;
        if (null != mAdapter) mAdapter.notifyItemInserted(mAdapter.getItemCount() - 1);
    }

    /* 设置空布局 */
    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
        if (null != mAdapter) mAdapter.notifyDataSetChanged();
    }

    private class BaseAdapter extends Adapter<ViewHolder> {

        private Adapter mDataAdapter;
        private final int ITEM_TYPE_HEADER = 0;
        private final int ITEM_TYPE_FOOTER = 1;
        private final int ITEM_TYPE_EMPTY = 2;
        private final int ITEM_TYPE_DATA = 3;


        public BaseAdapter(Adapter mDataAdapter) {
            this.mDataAdapter = mDataAdapter;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            switch (viewType) {
                case ITEM_TYPE_HEADER:
                    return new ViewHolder(mHeaderView) {
                    };
                case ITEM_TYPE_FOOTER:
                    return new ViewHolder(mFooterView) {
                    };
                case ITEM_TYPE_EMPTY:
                    return new ViewHolder(mEmptyView) {
                    };
                default:
                    return mDataAdapter.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            int type = getItemViewType(position);
            if (type == ITEM_TYPE_HEADER || type == ITEM_TYPE_FOOTER || type == ITEM_TYPE_EMPTY)
                return;
            mDataAdapter.onBindViewHolder(holder, getRealDataPosition(position));
        }

        @Override
        public int getItemCount() {
            int itemCount = mDataAdapter.getItemCount();
            if (null != mEmptyView && mDataAdapter.getItemCount() == 0) itemCount++;
            if (null != mHeaderView) itemCount++;
            if (null != mFooterView) itemCount++;
            return itemCount;
        }

        @Override
        public int getItemViewType(int position) {
            if (null != mHeaderView && position == 0)
                return ITEM_TYPE_HEADER;
            if (null != mFooterView && position == getItemCount() - 1)
                return ITEM_TYPE_FOOTER;
            if (null != mEmptyView && mDataAdapter.getItemCount() == 0)
                return ITEM_TYPE_EMPTY;
            return ITEM_TYPE_DATA;
        }

        private int getRealDataPosition(int position) {
            if (null != mHeaderView) position--;
            return position;
        }
    }
}
