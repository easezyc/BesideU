package com.besideu.zyc.besideu;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by zhuyongchun on 2016/9/12.
 */
public class BoardService {
    protected AsyncHttpClient client = new AsyncHttpClient();
    private ListView listview;
    private List<Map<String, Object>> list;
    private String myemail;
    public BoardService(ListView newlistview,List<Map<String, Object>> newlist,String newemail){
        listview=newlistview;
        list=newlist;
        myemail=newemail;
    }
    public BoardService(){

    }
    public void setEmail(String newemail){
        myemail=newemail;
    }
    public void Sendmessage(final Context context,String time,String content){
        RequestParams params = new RequestParams();
        params.put("myemail", myemail);
        params.put("time", time);
        params.put("content", content);
        client.post("http://202.112.88.39:8080/android/servlet/Sendmessage",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    String response = new String(responseBody, "UTF-8");
                    Log.i("suc",response);
                    getBoardArray(context);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
            }
        });
    }
    public void getBoardArray(final Context context){
        RequestParams params = new RequestParams();
        params.put("myemail", myemail);
        client.post("http://202.112.88.39:8080/android/servlet/Getboardlist",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    list.clear();
                    String response = new String(responseBody, "UTF-8");
                    JSONArray array = new JSONArray(response);
                    for(int i=0;i<array.length();i++)
                    {
                        Map<String, Object> map=new HashMap<String, Object>();
                        map.put("email", array.getJSONObject(i).getString("email1"));
                        map.put("time", array.getJSONObject(i).getString("time"));
                        map.put("content", array.getJSONObject(i).getString("content"));
                        list.add(map);

                    }
                    BoardAdapter adapter=new BoardAdapter(context, list,myemail);
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
}
