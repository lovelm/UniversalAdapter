package com.heisenberg.weichat;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Heisenberg on 2016/3/16.
 */
public abstract class UniversalAdapter<T> extends BaseAdapter {

    private ArrayList<T> mData;
    private int mLayoutRes;

    public UniversalAdapter() {
    }

    public UniversalAdapter(int mLayoutRes, ArrayList<T> mData) {
        this.mLayoutRes = mLayoutRes;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.bind(parent.getContext(), convertView, parent, mLayoutRes
                , position);
        bindView(holder, getItem(position));
        return holder.getItemView();
    }

    /**
     * 必须实现的用来设置数据的方法，holder来绑定组件并设置数据
     * @param holder
     * @param obj Bean类，传入数据
     */
    public abstract void bindView(ViewHolder holder, T obj);


    /*add data to adapter*/
    public void add(T data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(data);
        notifyDataSetChanged();
    }

    /*add data to adapter in specific position*/
    public void add(int position, T data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(position, data);
        notifyDataSetChanged();
    }

    /*remove data from adapter*/
    public void remove(T data) {
        if (mData != null) {
            mData.remove(data);
        }
        notifyDataSetChanged();
    }

    /*remove a specific position data from adapter*/
    public void remove(int position) {
        if (mData != null) {
            mData.remove(position);
        }
        notifyDataSetChanged();
    }

    /*remove all data from adapter*/
    public void clear() {
        if (mData != null) {
            mData.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 通用viewHolder，内部封装各种方法
     */
    public static class ViewHolder {
        private SparseArray<View> mViews;   //存储ListView 的 item中的View
        private View mCovertView;                  //存放convertView
        private int position;               //游标
        private Context context;            //Context上下文

        /*构造方法，完成相关初始化*/
        private ViewHolder(Context context, ViewGroup parent, int layoutId) {
            mViews = new SparseArray<>();
            this.context = context;
            View convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            convertView.setTag(this);
            mCovertView = convertView;
        }

        /**
         * 绑定ViewHolder与item
         *
         * @param context
         * @param convertView
         * @param parent
         * @param layoutId
         * @param position
         * @return viewHolder
         */
        public static ViewHolder bind(Context context, View convertView, ViewGroup parent, int layoutId, int position) {

            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder(context, parent, layoutId);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                viewHolder.mCovertView = convertView;
            }
            viewHolder.position = position;
            return viewHolder;
        }

        /**
         * 获取控件
         *
         * @param viewId
         * @param <T>
         * @return
         */
        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mCovertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        /**
         * 获取当前条目
         *
         * @return
         */
        public View getItemView() {
            return mCovertView;
        }

        /**
         * 获取当前条目位置
         *
         * @return
         */
        public int getItemPosition() {
            return position;
        }

        /**
         * 设置文字
         *
         * @param viewId
         * @param text
         * @return
         */
        public ViewHolder setText(int viewId, CharSequence text) {
            View view = getView(viewId);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
            return this;
        }

        /**
         * 设置图片
         *
         * @param viewId
         * @param drawableRes
         * @return
         */
        public ViewHolder setImageResource(int viewId, int drawableRes) {
            View view = getView(viewId);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(drawableRes);
            } else {
                view.setBackgroundResource(drawableRes);
            }
            return this;
        }

        /**
         * 设置点击监听
         *
         * @param viewId
         * @param listener
         * @return
         */
        public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
            getView(viewId).setOnClickListener(listener);
            return this;
        }

    }
}
