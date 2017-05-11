package com.xingen.sqlitepractice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xingen.sqlitepractice.R;
import com.xingen.sqlitepractice.orm.province.Province;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：新根  on 2016/11/4.
 * <p/>
 * 博客链接：http://blog.csdn.net/hexingen
 */
public class SelectProvinceAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
    private  final  String TAG= SelectCityAdapter.class.getSimpleName();
    private List<Province> list;
    private Context context;
    private int gradColor,yellowColor;
    private  Callbck callback;
    public SelectProvinceAdapter(Context context,Callbck callback){
        this.context=context;
        this.list=new ArrayList<>();
        this.gradColor=Color.rgb(88, 88, 88);
        this.yellowColor=Color.parseColor("#e68f17");
        this.currentCitName="北京市";
        this.callback=callback;
        this.neddLoad=true;
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
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=View.inflate(context, R.layout.item_selectcity_listview,null);
            viewHolder.name_tv=(TextView) convertView.findViewById(R.id.item_selectcity_name);
            viewHolder.line_tv=(TextView) convertView.findViewById(R.id.item_selectcity_line_tv);
            viewHolder.line_tv.setBackgroundColor(Color.parseColor("#ededed"));
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        setShowName(viewHolder.name_tv,position,parent);
        return convertView;
    }
    private int pid;

    private void setShowName(TextView tv,int position,ViewGroup parent) {
        String name=list.get(position).getPname();
        tv.setText(name);
        tv.setGravity(Gravity.CENTER);
        if(currentCitName.equals(name)){
              pid=list.get(position).getPid();
              currentPostion=position;
              tv.setTextColor(yellowColor);
            (( ListView)parent).setSelection(position);
            loadCity();
        }else{
            tv.setTextColor(gradColor);
        }
    }
    public void loadCity(){
        if(neddLoad){
            neddLoad=false;
            callback.queryCitys(pid);
        }
    }
   public  interface  Callbck {
        void queryCitys(int pid);
    }
    private int currentPostion;
    private String currentCitName;
    public String getCurrentCitName(){
        return  currentCitName;
    }

    public static class  ViewHolder{
        TextView name_tv,line_tv;
    }
    public boolean neddLoad;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if(currentPostion!=position){
                   neddLoad=true;
                   currentPostion=position;
                   currentCitName=list.get(position).getPname();
                   this.notifyDataSetChanged();
               }
    }
    public void addData(List<Province> list){
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

}
