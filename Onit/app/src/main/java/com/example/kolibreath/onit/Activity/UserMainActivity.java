package com.example.kolibreath.onit.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kolibreath.onit.App;
import com.example.kolibreath.onit.DataBase.NotesDB;
import com.example.kolibreath.onit.R;
import com.example.kolibreath.onit.InterfaceAdapter.UserOwnDongtaiAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kolibreath on 2017/2/2.
 */

public class UserMainActivity extends AppCompatActivity implements View.OnClickListener{

    private TabLayout userDongtaiList;
    private ListView lv;
    private Cursor cursor;
    private SQLiteDatabase dbReader;
    private NotesDB notesDB;
    private Button newOnit;
    private UserOwnDongtaiAdapter myAdapter;
    private CircleImageView circleImageView;
    private SQLiteDatabase db;
    private TextView fansNumber;
    private String str;
    private TextView rankDisplay,userNameDisplay;

    private void initWidget(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_for_usercenter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userDongtaiList = (TabLayout) findViewById(R.id.userDongtaiList);
        userDongtaiList.addTab(userDongtaiList.newTab().setText("任务列表"));
        userNameDisplay = (TextView) findViewById(R.id.users_name_display);

        fansNumber = (TextView) findViewById(R.id.fans_number);
        rankDisplay = (TextView) findViewById(R.id.rankDisplay);

        rankDisplay.setText(statusResult());

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

        initWidget();

        userNameDisplay.setText(getApp().setUserText());
        statusResult();


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

    private String statusResult(){
        int number = Integer.parseInt(fansNumber.getText().toString());

        String str1 = "初涉江湖";
        String str2 = "渐入佳境";
        String str3 = "炉火纯青";
        String str4 = "登峰造极";

        if(number<5){
            str = str1;
        }
        if(number>6&&number<15){
            str=str2;
        }
        if (number>16&&number<35){
            str = str3;
        }
        if (number>36&&number<45){
            str = str4;
        }
        return str;
    }
    private App getApp(){
        return  ((App)getApplicationContext());
    }

}