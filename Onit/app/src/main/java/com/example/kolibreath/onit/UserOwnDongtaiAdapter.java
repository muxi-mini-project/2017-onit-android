package com.example.kolibreath.onit;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/12.
 */

public class UserOwnDongtaiAdapter extends BaseAdapter {

    private Context context;
    private Cursor cursor;
    private RelativeLayout layout;

    public UserOwnDongtaiAdapter(Context context,Cursor cursor){
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
        layout = (RelativeLayout) inflater.inflate(R.layout.userown_dongtai_item,null);

        TextView contenttv = (TextView) layout.findViewById(R.id.userownDongtaiContent);
        TextView timetv = (TextView) layout.findViewById(R.id.userowndongtaiTime);
        TextView endtime = (TextView) layout.findViewById(R.id.userowndongtaiDeadlineDate);

        cursor.moveToPosition(position);

        String content = cursor.getString(cursor.getColumnIndex("content"));
        String time = cursor.getString(cursor.getColumnIndex("time"));
        String edtime = cursor.getString(cursor.getColumnIndex("endtime"));
        contenttv.setText(content);
        timetv.setText(time);
        endtime.setText(edtime);

        return layout;

    }

}