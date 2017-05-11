package com.xingen.sqlitepractice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xingen.sqlitepractice.R;
import com.xingen.sqlitepractice.orm.city.City;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：新根  on 2016/11/4.
 * <p/>
 * 博客链接：http://blog.csdn.net/hexingen
 */
public class SelectCityAdapter extends BaseAdapter  {
    private Context context;
    private List<City> list;
    private int gradColor, yellowColor;
    public SelectCityAdapter(Context context) {
        this.context = context;

        this.list=new ArrayList<>();
        this.gradColor=Color.rgb(88, 88, 88);
        this.yellowColor=Color.parseColor("#e68f17");
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_selectcity_listview, null);
            viewHolder.name_tv = (TextView) convertView.findViewById(R.id.item_selectcity_name);
            viewHolder.line_tv=(TextView) convertView.findViewById(R.id.item_selectcity_line_tv);
            viewHolder.line_tv.setBackgroundColor(Color.parseColor("#d9d9d9"));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name_tv.setText(list.get(position).getCname());
        setShowName(viewHolder.name_tv, position, parent);
        return convertView;
    }

    private void setShowName(TextView tv, int position, ViewGroup parent) {
        String city = list.get(position).getCname();
        tv.setText(city);
        if (position == currentPosition) {
            tv.setTextColor(yellowColor);
            ((ListView) parent).setSelection(position);
        } else {
            tv.setTextColor(gradColor);
        }
    }

    public static class ViewHolder {
        TextView name_tv,line_tv;
    }
    private int currentPosition = -1;
    public void addData(List<City> cityList) {
        currentPosition = -1;
        this.list.clear();
        this.list.addAll(cityList);
        this.notifyDataSetChanged();
    }

}
