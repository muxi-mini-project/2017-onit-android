package com.example.kolibreath.onit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CalendarView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kolibreath on 2017/2/3.
 */

public class CreateNewDongtai extends AppCompatActivity implements View.OnClickListener{

    private CalendarView calendarView;
    private RelativeLayout relativeLayout;
    private TextView year,dayoftheweek,month,dateofthemonth,textdisplayontoolbar;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dealine_text_button:
                relativeLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.dongtai_canceled:
                relativeLayout.setVisibility(View.GONE);
                break;
        }
    }

    private void initWidget(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_for_createdongtai);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView dongtai_canceled = (TextView) findViewById(R.id.dongtai_canceled);
        TextView dongtai_assure = (TextView) findViewById(R.id.dongtai_assure);


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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_dongtai);

        calendarView = (CalendarView) findViewById(R.id.calendar);
        year = (TextView) findViewById(R.id.year);
        month = (TextView) findViewById(R.id.month);
        dateofthemonth = (TextView) findViewById(R.id.dayofthemonth);
        dayoftheweek = (TextView) findViewById(R.id.dayoftheweek);
        textdisplayontoolbar = (TextView) findViewById(R.id.text_display_in_toolbar);
        initWidget();
        setTextTime();
    }
}
