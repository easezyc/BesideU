package com.besideu.zyc.besideu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by zhuyongchun on 2016/9/11.
 */
public class Service {
    protected AsyncHttpClient client = new AsyncHttpClient();
    protected String mark="";
    protected boolean ok;
    public void Getheremail(final TextView textview,String myemail){
        RequestParams params = new RequestParams();
        params.put("myemail", myemail);
        client.post("http://202.112.88.39:8080/android/servlet/GetheremailSvt",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    String response = new String(responseBody, "UTF-8");
                    Log.i("suc",response);
                    textview.setText(response);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mark="error";
            }
        });
    }
    public void Deleteher(final TextView heremail){
        RequestParams params = new RequestParams();
        params.put("heremail", heremail.getText().toString());
        System.out.println(heremail+"pp");
        client.post("http://202.112.88.39:8080/android/servlet/Deleteher",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String response = new String(responseBody, "UTF-8");
                    mark=response;
                    if(mark.equals("1")){
                        heremail.setText("");
                        Toast.makeText(MainActivity.mactivity, "删除成功", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.mactivity, "删除失败", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println(error);
            }
        });
    }
    public boolean Addher(String myemail,String heremail){
        RequestParams params = new RequestParams();
        params.put("myemail", myemail);
        params.put("heremail", heremail);
        client.post("http://202.112.88.39:8080/android/servlet/Addher",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(responseBody);
                try {
                    String response = new String(responseBody, "UTF-8");
                    mark=response;
                    if(mark.equals("1")){
                        Toast.makeText(MainActivity.mactivity, "申请成功", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.mactivity, "申请失败", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mark="0";
            }
        });
        if(mark.equals("1"))return true;
        else return false;
    }
    public void getArray(final Context context,final ListView listview,final List<Map<String, Object>> list,final String myemail,final TextView textview){
        RequestParams params = new RequestParams();
        params.put("myemail", myemail);
        client.post("http://202.112.88.39:8080/android/servlet/Getapplylist",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    System.out.println("21313");
                    list.clear();
                    String response = new String(responseBody, "UTF-8");
                    JSONArray array = new JSONArray(response);
                    for(int i=0;i<array.length();i++)
                    {
                        Map<String, Object> map=new HashMap<String, Object>();
                        map.put("email", array.getString(i));
                        list.add(map);

                    }
                    MyAdapter adapter=new MyAdapter(context, list,myemail,new MyAdapter.MyClickListener() {
                        @Override
                        public void myOnClick(int position, View v) {
                            Agreeadd(context,myemail,(String)list.get(position).get("email"),list,listview,textview);
                        };
                    },new MyAdapter.MyClickListener() {
                        @Override
                        public void myOnClick(int position, View v) {
                            Denyher(context,myemail,(String)list.get(position).get("email"),list,listview,textview);

                        };
                    });
                    listview.setAdapter(adapter);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("error"+error);
                Toast.makeText(MainActivity.mactivity, "获取列表失败", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void Agreeadd(final Context context,final String myemail, String heremail,final List<Map<String, Object>> list,final ListView listview,final TextView textview){
        RequestParams params = new RequestParams();
        params.put("myemail", myemail);
        params.put("heremail", heremail);
        System.out.println("agreeadd");
        client.post("http://202.112.88.39:8080/android/servlet/Agreeadd",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(responseBody);
                try {
                    String response = new String(responseBody, "UTF-8");
                    if(response.equals("1")){
                        getArray(context,listview,list,myemail,textview);
                        Getheremail(textview,myemail);
                        Toast.makeText(MainActivity.mactivity, "添加成功", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.mactivity, "添加失败", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mark="0";
            }
        });
    }
    public void Denyher(final Context context,final String myemail,String heremail,final List<Map<String, Object>> list,final ListView listview,final TextView textview){
        RequestParams params = new RequestParams();
        params.put("myemail", myemail);
        params.put("heremail", heremail);
        client.post("http://202.112.88.39:8080/android/servlet/Denyher",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(responseBody);
                try {
                    String response = new String(responseBody, "UTF-8");
                    if(response.equals("1")){
                        getArray(context,listview,list,myemail,textview);
                        Toast.makeText(MainActivity.mactivity, "拒绝成功", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.mactivity, "拒绝失败", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mark="0";
            }
        });
    }
}
