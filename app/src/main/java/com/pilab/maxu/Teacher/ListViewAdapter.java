package com.pilab.maxu.Teacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pilab.maxu.R;

import java.util.LinkedList;

/**
 * Created by andersen on 2018/11/3.
 */

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private LinkedList<Sign> datas;

    //构造函数需要传入两个必要的参数：上下文对象和数据源
    public ListViewAdapter(Context context, LinkedList<Sign> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Sign sign = (Sign) getItem(i);
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_sign_history, null);
            viewHolder = new ViewHolder();
            viewHolder.classname = view.findViewById(R.id.history_classname);
            viewHolder.state = view.findViewById(R.id.history_state);
            viewHolder.bili = view.findViewById(R.id.history_bili);
            viewHolder.code = view.findViewById(R.id.history_code);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.classname.setText(sign.getClassname());
        viewHolder.code.setText(sign.getNum());
        viewHolder.bili.setText(sign.getBili());
        viewHolder.state.setText(sign.getState());
        return view;
    }

    class ViewHolder {
        TextView classname;
        TextView state;
        TextView code;
        TextView bili;
    }
}

