package com.besideu.zyc.besideu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Button btn_mod;
    private Button btn_deleteher;
    private Button btn_addher;
    private TextView heremail;
    private EditText addemail;
    private ListView applylist;
    private List<Map<String, Object>> apply_list;
    private List<Map<String, Object>> board_list=new ArrayList<Map<String, Object>>();
    private Button btn_handin;
    private EditText message;
    private ListView boardlist;
    private BoardService boardservice = new BoardService();
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情
            boardservice.getBoardArray(MainActivity.this);
            handler.postDelayed(this, 5000);
        }
    };
    public static MainActivity mactivity;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createtoolbar();
        mainready();
        mactivity = this;
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void createtoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_userinfo) {
            setContentView(R.layout.activity_main);
            createtoolbar();
            mainready();
            handler.removeCallbacks(runnable);
            // Handle the camera action
        } else if (id == R.id.nav_board) {
            setContentView(R.layout.activity_board);
            createtoolbar();
            boardready();
        } else if (id == R.id.nav_location) {
            setContentView(R.layout.activity_location);
            createtoolbar();
            handler.removeCallbacks(runnable);
        } else if (id == R.id.nav_besideu) {
            setContentView(R.layout.activity_besideu);
            createtoolbar();
            applylistready();
            besideuready();
            handler.removeCallbacks(runnable);
        } else if (id == R.id.nav_exit) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void boardready() {
        String myemail = getmyemail();
        message = (EditText) findViewById(R.id.message);
        btn_handin = (Button) findViewById(R.id.btn_handin);

        boardlist = (ListView) findViewById(R.id.board_listView);
        boardservice=new BoardService(boardlist,board_list,myemail);
        boardservice.getBoardArray(MainActivity.this);
        btn_handin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
                boardservice.Sendmessage(MainActivity.this, df.format(new Date()), message.getText().toString());
                message.setText("");
            }
        });
        handler.postDelayed(runnable, 5000);
    }

    public void mainready() {

        TextView email = (TextView) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        SQLiteDatabase db = openOrCreateDatabase("myuser", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("select * from myuser", null);
        cursor.moveToNext();
        String s_email = cursor.getString(0);
        String s_pwd = cursor.getString(1);
        email.setText(s_email);
        password.setText(s_pwd);
        cursor.close();
        db.close();

        btn_mod = (Button) findViewById(R.id.mod_pwd);
        btn_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("email", ((TextView) findViewById(R.id.email)).getText());
                params.put("pwd", ((EditText) findViewById(R.id.password)).getText());
                client.post("http://202.112.88.39:8080/android/servlet/ModSvt", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        System.out.println(responseBody);
                        try {
                            String response = new String(responseBody, "UTF-8");
                            if (response.equals("1")) {
                                SQLiteDatabase db = openOrCreateDatabase("myuser", MODE_PRIVATE, null);
                                String sql = "update myuser set pwd='" + ((EditText) findViewById(R.id.password)).getText() + "' where email='" + ((TextView) findViewById(R.id.email)).getText() + "'";
                                db.execSQL(sql);
                                db.close();
                                mainready();
                                Toast.makeText(MainActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(MainActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public void besideuready() {
        System.out.println("asadadwqe");
        btn_deleteher = (Button) findViewById(R.id.btn_deleteher);
        btn_addher = (Button) findViewById(R.id.btn_addemail);
        addemail = (EditText) findViewById(R.id.addeamil);
        heremail = (TextView) findViewById(R.id.heremail);
        final String myemail = getmyemail();
        final Service service = new Service();
        service.Getheremail(heremail, myemail);

        btn_deleteher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.Deleteher(heremail);
            }
        });
        btn_addher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.Addher(myemail, addemail.getText().toString());
            }
        });
    }

    public void applylistready() {
        applylist = (ListView) findViewById(R.id.applylist);
        apply_list = new ArrayList<Map<String, Object>>();
        heremail = (TextView)findViewById(R.id.heremail);
        final String myemail = getmyemail();
        Service service = new Service();
        service.getArray(MainActivity.this, applylist, apply_list, myemail, heremail);
        applylist.getOnItemClickListener();
    }


    public String getmyemail() {
        SQLiteDatabase db = openOrCreateDatabase("myuser", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("select * from myuser", null);
        cursor.moveToNext();
        String myemail = cursor.getString(0);
        cursor.close();
        db.close();
        return myemail;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.besideu.zyc.besideu/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.besideu.zyc.besideu/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
