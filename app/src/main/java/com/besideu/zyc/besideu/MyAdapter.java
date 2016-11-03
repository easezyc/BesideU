package com.besideu.zyc.besideu;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.besideu.zyc.besideu.R;
import com.besideu.zyc.besideu.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhuyongchun on 2016/9/11.
 */
public class MyAdapter extends BaseAdapter {
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private String myemail;
    private MyClickListener agreeListener;
    private MyClickListener disagreeListener;

    public MyAdapter(Context context,List<Map<String, Object>> data,String newmyemail,MyClickListener agreeclick,MyClickListener disagreeclick){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
        this.myemail=newmyemail;
        this.agreeListener=agreeclick;
        this.disagreeListener=disagreeclick;
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
        public TextView email;
        public Button agree;
        public Button disagree;
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
        Component component=null;
        if(convertView==null){
            component=new Component();
            //获得组件，实例化组件
            convertView=layoutInflater.inflate(R.layout.applylist, null);
            component.email=(TextView)convertView.findViewById(R.id.tv_applyemail);
            component.agree=(Button)convertView.findViewById(R.id.btn_agree);
            component.disagree=(Button)convertView.findViewById(R.id.btn_disagree);
            convertView.setTag(component);
        }else{
            component=(Component)convertView.getTag();
        }
        //绑定数据
        component.email.setText((String)data.get(position).get("email"));
        component.agree.setOnClickListener(agreeListener);
        component.agree.setTag(position);
        component.disagree.setOnClickListener(disagreeListener);
        component.disagree.setTag(position);
        return convertView;
    }

    public static abstract class MyClickListener implements View.OnClickListener {
                 /**
         81          * 基类的onClick方法
                 */
                 @Override
                public void onClick(View v) {
                         myOnClick((Integer) v.getTag(), v);
                    }
                public abstract void myOnClick(int position, View v);
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
