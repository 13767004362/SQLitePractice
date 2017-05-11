package com.xingen.sqlitepractice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xingen.sqlitepractice.R;
import com.xingen.sqlitepractice.orm.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${新根} on 2017/5/7 0007.
 * blog: http://blog.csdn.net/hexingen
 */
public class MessageAdapter extends BaseAdapter {
    private Context context;
    private List<Message> list;
    public MessageAdapter(Context context){
        this.context=context;
        this.list=new ArrayList<>();
    }
    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=View.inflate(context, R.layout.listview_item,null);
            viewHolder.content_tv=(TextView)convertView.findViewById(R.id.item_content);
            viewHolder.date_tv=(TextView) convertView.findViewById(R.id.item_date);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        Message message=list.get(position);
        viewHolder.content_tv.setText(message.getContent());
        viewHolder.date_tv.setText(message.getDate());
        return convertView;
    }
    private static class ViewHolder{
        public TextView content_tv,date_tv;
    }

    /**
     * 对外更改数据源的方法
     * @param list
     */
    public void addData(List<Message> list){
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }
}
