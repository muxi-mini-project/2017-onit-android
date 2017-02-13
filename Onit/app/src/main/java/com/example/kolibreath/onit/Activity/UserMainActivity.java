package com.example.kolibreath.onit.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kolibreath.onit.App;
import com.example.kolibreath.onit.Beans.UserProfileBean;
import com.example.kolibreath.onit.DataBase.NotesDB;
import com.example.kolibreath.onit.InterfaceAdapter.ServiceInterface;
import com.example.kolibreath.onit.InterfaceAdapter.UserOwnDongtaiAdapter;
import com.example.kolibreath.onit.R;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kolibreath on 2017/2/2.
 */

public class UserMainActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout userDongtaiList;
    private ListView lv;
    private Cursor cursor;
    private SQLiteDatabase dbReader;
    private NotesDB notesDB;
    private Button newOnit;
    private UserOwnDongtaiAdapter myAdapter;
    private CircleImageView circleImageView;
    private SQLiteDatabase db;
    private TextView fansNumber,atNumber;
    private String str;
    private TextView rankDisplay, userNameDisplay;
    private UserProfileBean bean;


    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

    Retrofit retrofit;

    ServiceInterface si;

    private android.os.Handler handler = new android.os.Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                   fansNumber.setText(String.valueOf(bean.getFollowers_coumnt()));
                    atNumber.setText(String.valueOf(bean.getFolloweds_count()));
                    rankDisplay.setText(statusResult(0));
               //    Log.d("rankdis", statusResult(bean.getLevel()));
                    Log.d("rankint", String.valueOf(bean.getLevel()));

            }

        }
    };


    private void initWidget(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_for_usercenter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userDongtaiList = (TabLayout) findViewById(R.id.userDongtaiList);
        userDongtaiList.addTab(userDongtaiList.newTab().setText("任务列表"));
        userNameDisplay = (TextView) findViewById(R.id.users_name_display);
        atNumber = (TextView) findViewById(R.id.attention_number);

        fansNumber = (TextView) findViewById(R.id.fans_number);
        rankDisplay = (TextView) findViewById(R.id.rankDisplay);


        lv = (ListView) findViewById(R.id.userDongtaiRealList);
        lv.setVerticalScrollBarEnabled(false);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor = dbReader.rawQuery("select * from " + NotesDB.TABLE_NAME,null);
                cursor.moveToPosition(position);
                Intent i = new Intent(UserMainActivity.this, SelectActivity.class);
                i.putExtra(NotesDB.ID,cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));
                i.putExtra(NotesDB.CONTENT,cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT)));
                i.putExtra(NotesDB.TIME,cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));
                startActivity(i);
            }
        });
        newOnit = (Button) findViewById(R.id.assignNewOnit);
        newOnit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this,CreateNewDongtai.class);
        switch (v.getId()){
            case R.id.assignNewOnit:
                startActivity(i);
        }
    }

    //从数据库中读取数据并且进行判断

    public void selectDB(){
        Cursor cursor = dbReader.query(NotesDB.TABLE_NAME,null,null,null,null,null,null);
        myAdapter = new UserOwnDongtaiAdapter(this,cursor);
        lv.setAdapter(myAdapter);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.usermain_activity);
        super.onCreate(savedInstanceState);
        notesDB = new NotesDB(this);
        dbReader = notesDB.getReadableDatabase();

        circleImageView = (CircleImageView) findViewById(R.id.user_avatar);
        Intent intent = getIntent();
        int bitmapSrc = intent.getIntExtra("key",R.drawable.java);
        circleImageView.setImageResource(bitmapSrc);

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://121.42.12.214:5050/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        si = retrofit.create(ServiceInterface.class);

        Call<UserProfileBean> call = si.getProfile("test",getApp().setUserToken());
        call.enqueue(new Callback<UserProfileBean>() {
            @Override
            public void onResponse(Call<UserProfileBean> call, Response<UserProfileBean> response) {
                bean = response.body();
                Log.d("profile",String.valueOf(bean.getUid()));
                handler.sendEmptyMessage(0);
                Message msg = new Message();
                msg.obj = bean;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Call<UserProfileBean> call, Throwable t) {
                t.printStackTrace();
            }
        });

        initWidget();

        userNameDisplay.setText(getApp().setUserText());


    }
    protected void onResume(){
        super.onResume();
        selectDB();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usermain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                finish();
                break;
            case R.id.logoutInUserMain:
                Intent logoutintent = new Intent(UserMainActivity.this,MainActivity.class);
                logoutintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logoutintent);
                break;
            case R.id.set_avatar:
                Intent i = new Intent(UserMainActivity.this,SelectAvatarActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private String statusResult(int level){

        String str1 = "初涉江湖";
        String str2 = "渐入佳境";
        String str3 = "炉火纯青";
        String str4 = "登峰造极";

        switch (level){
            case 0:
                rankDisplay.setText(str1);
                break;
            case 1:
                rankDisplay.setText(str2);
                break;
            case 2:
                rankDisplay.setText(str3);
                break;
            case 3:
                rankDisplay.setText(str4);
                break;
        }

        return str;
    }
    private App getApp(){
        return  ((App)getApplicationContext());
    }

}
