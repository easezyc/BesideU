package com.besideu.zyc.besideu;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by zhuyongchun on 2016/9/12.
 */
public class BoardAdapter extends BaseAdapter{
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private String myemail;

    public BoardAdapter(Context context,List<Map<String, Object>> data,String newmyemail){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
        this.myemail=newmyemail;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    /**
     * 组件集合，对应list.xml中的控件
     * @author Administrator
     */
    public final class Component{
        public TextView user,time,content;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return data.size();
    }
    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String nowuser=(String)data.get(position).get("email");
        Component component=null;
            component=new Component();
            //获得组件，实例化组件
            if(nowuser.equals(myemail)) {
                convertView = layoutInflater.inflate(R.layout.ilist, null);
                component.time = (TextView) convertView.findViewById(R.id.tv_itime);
                component.user = (TextView) convertView.findViewById(R.id.tv_i);
                component.content = (TextView) convertView.findViewById(R.id.tv_icontent);
                convertView.setTag(component);
            }
            else{
                convertView = layoutInflater.inflate(R.layout.ulist, null);
                component.time = (TextView) convertView.findViewById(R.id.tv_utime);
                component.user = (TextView) convertView.findViewById(R.id.tv_u);
                component.content = (TextView) convertView.findViewById(R.id.tv_ucontent);
                convertView.setTag(component);
            }
//        }else{
//            component=(Component)convertView.getTag();
//        }
        //绑定数据
        component.time.setText((String)data.get(position).get("time"));
        component.content.setText((String)data.get(position).get("content"));
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
