package com.example.kolibreath.onit.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kolibreath.onit.App;
import com.example.kolibreath.onit.Beans.DongtaiSendBean;
import com.example.kolibreath.onit.DataBase.NotesDB;
import com.example.kolibreath.onit.Generics.UserDongtaiContent;
import com.example.kolibreath.onit.InterfaceAdapter.ServiceInterface;
import com.example.kolibreath.onit.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.kolibreath.onit.R.id.dayofthemonth;
import static com.example.kolibreath.onit.R.id.dongtai_canceled;

/**
 * Created by kolibreath on 2017/2/3.
 */

public class CreateNewDongtai extends AppCompatActivity implements View.OnClickListener{

    private CalendarView calendarView;
    private RelativeLayout relativeLayout;
    private TextView year,dayoftheweek,month,dateofthemonth,textdisplayontoolbar;
    private TabLayout tabLayout;
    //datespecfic表示 日历控件监听的时间显示的标签 具体时间 比如说 2017/2/25
    //dateTemp 表示现在的时间
    private String dateSpecific,dateTemp;
    private TextView ettext;
    private SQLiteDatabase dbWriter;
    private NotesDB notesDB;
    private RelativeLayout createNewDongtai;

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    Retrofit retrofit;
    ServiceInterface si;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dealine_text_button:
                relativeLayout.setVisibility(View.VISIBLE);
                break;
            case dongtai_canceled:
                relativeLayout.setVisibility(View.GONE);
                break;
            case R.id.dongtai_assure:
                final String str[] = {dateSpecific};
                tabLayout = (TabLayout) findViewById(R.id.tablayout);
                for (int i = 0; i < str.length; i++) {
                    tabLayout.addTab(tabLayout.newTab().setText(str[i]));
                }
                relativeLayout.setVisibility(View.GONE);
                break;
        }
    }

    private void initWidget(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_for_createdongtai);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ettext = (TextView) findViewById(R.id.ettext);

        createNewDongtai = new RelativeLayout(this);
        createNewDongtai  = (RelativeLayout) findViewById(R.id.createNewDongtai);

        TextView dongtai_canceled = (TextView) findViewById(R.id.dongtai_canceled);
        TextView dongtai_assure = (TextView) findViewById(R.id.dongtai_assure);
        Button dongtaifinished = (Button) findViewById(R.id.dongtaifinished);
        dongtaifinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateSpecific==null){
                    Snackbar.make(createNewDongtai,"少侠请先选择计划的截至日期",Snackbar.LENGTH_INDEFINITE).setAction("前去选择", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
                }else {

                    final RelativeLayout layout = (RelativeLayout) findViewById(R.id.dialog);
                    final TextView displayDate = (TextView) findViewById(R.id.displayDate);
                    displayDate.setText("你的计划截止于" + dateSpecific);
                    layout.setVisibility(View.VISIBLE);
                    TextView negative = (TextView) findViewById(R.id.textViewNegative);
                    negative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            layout.setVisibility(View.GONE);
                        }
                    });
                    TextView positive = (TextView) findViewById(R.id.textViewPositive);
                    positive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            UserDongtaiContent content = new UserDongtaiContent(ettext.getText().toString(),
                                    dateSpecific);
                            Call<DongtaiSendBean> call= si.sendUserDongtai(getApp().storedUsername,content,
                                    getApp().storedUserToken);
                            call.enqueue(new Callback<DongtaiSendBean>() {
                                @Override
                                public void onResponse(Call<DongtaiSendBean> call, Response<DongtaiSendBean> response) {
                                    DongtaiSendBean bean = response.body();
                                    addDB();
                                    finish();
                                    Log.d("content", "sfaf");
                            Intent intent = new Intent(CreateNewDongtai.this, OnitMainActivity.class);
                            startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<DongtaiSendBean> call, Throwable t) {

                                }
                            });

                        }
                    });
                }
            }
        });

        TextView textView = (TextView) findViewById(R.id.dealine_text_button);
        relativeLayout = (RelativeLayout) findViewById(R.id.calendarview_layout);
        textView.setOnClickListener(this);
        dongtai_assure.setOnClickListener(this);
        dongtai_canceled.setOnClickListener(this);
    }

    private void setTextTime(){
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy年MM月dd日E");
        Date date1 = new Date(System.currentTimeMillis());
        String str = dateFormat1.format(date1);

        String str1c= str.substring(0,5);
        String str2 = str.substring(5,7);
        String str3 = str.substring(8,10);
        String str4 = str.substring(11,13);


        textdisplayontoolbar.setText(str);
        month.setText(str2+"月");
        dateofthemonth.setText(str3);
        dayoftheweek.setText(str4);
        year.setText(str1c);
    }

    private String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        return str;
    }
    public void addDB(){
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.CONTENT,ettext.getText().toString());
        cv.put(NotesDB.TIME,getTime());
        cv.put(NotesDB.ENDTTIME, dateSpecific);
        if(dateSpecific==null){
            Snackbar.make(createNewDongtai,"少侠请先选择计划的截至日期",Snackbar.LENGTH_INDEFINITE).setAction("前去选择", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
        }
        dbWriter.insert(NotesDB.TABLE_NAME,null,cv);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_dongtai);


        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://121.42.12.214:5050/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        si = retrofit.create(ServiceInterface.class);

        calendarView = (CalendarView) findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                dateSpecific = year + "/"+(month+1)+ "/" +dayOfMonth;
            }
        });
        year = (TextView) findViewById(R.id.year);
        month = (TextView) findViewById(R.id.month);
        dateofthemonth = (TextView) findViewById(R.id.dayofthemonth);

        dayoftheweek = (TextView) findViewById(R.id.dayoftheweek);
        textdisplayontoolbar = (TextView) findViewById(R.id.text_display_in_toolbar);


        if (dayofthemonth<getDay()){
            Snackbar.make(relativeLayout,"少侠眼花了吧，今天的日子是"+getDay()+"号",Snackbar.LENGTH_INDEFINITE)
                    .setAction("多谢小哥提醒", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).show();
        }

        notesDB = new NotesDB(this);
        dbWriter = notesDB.getWritableDatabase();

        initWidget();
        setTextTime();
    }

    private int getDay(){
        SimpleDateFormat format = new SimpleDateFormat("dd");
        Date date = new Date();
        String str = format.format(date);
        int day = Integer.parseInt(str);
        return  day;
    }
    private int getMonth(){
        SimpleDateFormat format = new SimpleDateFormat("MM");
        Date date = new Date();
        String str = format.format(date);
        int month = Integer.parseInt(str);
        return  month;
    }

    private App getApp(){
        return  ((App)getApplicationContext());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
