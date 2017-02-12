package com.example.kolibreath.onit;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by  on Kolibreath 2017/2/7.
 */

public class UserOwnDongtaiAdapter extends BaseAdapter{

    private Context context;
    private Cursor cursor;
    private String date;
    private int yearEnd, monthEnd, dayEnd;
    private int yearStart, monthStart, dayStart;
    private int clickstatus = 1;


    public UserOwnDongtaiAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return cursor.getPosition();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.userown_dongtai_item, parent,false);

        TextView contenttv = (TextView) layout.findViewById(R.id.userownDongtaiContent);
        TextView timetv = (TextView) layout.findViewById(R.id.userowndongtaiTime);
        TextView endtime = (TextView) layout.findViewById(R.id.userowndongtaiDeadlineDate);
        final TextView jiezhishijian = (TextView) layout.findViewById(R.id.jieshushishijian);
        final TextView usedongtaiFinishedtime = (TextView) layout.findViewById(R.id.userowndongtaiFinishedTime);

        final ImageView flagWhite = (ImageView) layout.findViewById(R.id.flagWhite);

       final Button changetofinished = (Button) layout.findViewById(R.id.changetoFinished);

        ImageView favoriv = (ImageView) layout.findViewById(R.id.favorit);


        favoriv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.favorit:
                        if (clickstatus == 1) {
                            ((ImageView)v).setImageResource(R.drawable.ic_favorite_red_18dp);
                            clickstatus = 0;
                            Log.d("clickstatus", String.valueOf(clickstatus));
                        }else{
                            ((ImageView)v).setImageResource(R.drawable.ic_favorite_border_white_18dp);
                            clickstatus = 1;
                            Log.d("process", String.valueOf(clickstatus));
                        }
                        break;
                }
            }

        });


        cursor.moveToPosition(position);

        //从数据库中读取数据 把提取出来的时间 和 截止时间比较
        String content = cursor.getString(cursor.getColumnIndex("content"));
        String time = cursor.getString(cursor.getColumnIndex("time"));
        String edtime = cursor.getString(cursor.getColumnIndex("endtime"));

        final RelativeLayout onit = (RelativeLayout) layout.findViewById(R.id.user_onit);
        final RelativeLayout finished = (RelativeLayout) layout.findViewById(R.id.user_finished);
        final RelativeLayout unfinished = (RelativeLayout) layout.findViewById(R.id.user_unfinished);


        String timenow = getDate();

        //从系统时间“当前时间”截取出来的部分数据： yearStart 应该命名为yearnow
        Log.d("timenow", timenow);
        yearStart = Integer.parseInt(timenow.substring(0, 4));
        Log.d("yearStart", timenow.substring(0, 4));
        monthStart = Integer.parseInt(timenow.substring(5, 7));
        Log.d("monthStart", timenow.substring(5, 7));
        dayStart = Integer.parseInt(timenow.substring(8, 10));

        //从数据库“截至日期”截取出来的部分数据
        Log.d("edtime", edtime);
        yearEnd = Integer.parseInt(edtime.substring(0, 4));
        monthEnd = Integer.parseInt(edtime.substring(5, 6));
        dayEnd = Integer.parseInt(edtime.substring(7, edtime.length()));
        Log.d("endtime", edtime);


        if (yearStart <= yearEnd) {
            Log.d("yearsure", "true");
            if (monthStart <= monthEnd) {
                Log.d("montnsure", "true");
                if (dayStart <= dayEnd) {
                    Log.d("daysure", "true");
                    onit.setVisibility(View.VISIBLE);
                    unfinished.setVisibility(View.GONE);
                } else {
                    Log.d("daywrong dayst", timenow.substring(8, 10));
                    Log.d("dayend dayed", edtime.substring(7, 8));
                    onit.setVisibility(View.VISIBLE);
                    unfinished.setVisibility(View.GONE);
                }
            } else {
                Log.d("monthfalse",timenow.substring(8, 10));
                onit.setVisibility(View.GONE);
                unfinished.setVisibility(View.VISIBLE);
            }
        } else {
            onit.setVisibility(View.GONE);
            unfinished.setVisibility(View.VISIBLE);
        }

        //显示完成的日期
        changetofinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("changetofinshed", "onClick: ");
                changetofinished.setVisibility(View.GONE);
                finished.setVisibility(View.VISIBLE);
                onit.setVisibility(View.GONE);

                jiezhishijian.setVisibility(View.VISIBLE);
                flagWhite.setVisibility(View.VISIBLE);
                usedongtaiFinishedtime.setVisibility(View.VISIBLE);
                usedongtaiFinishedtime.setText(getDate());
            }
        });

        contenttv.setText(content);
        timetv.setText(time);
        endtime.setText(edtime);
        return layout;

    }

    //获取今天的日期
    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String str = format.format(date);
        return str;
    }

}

