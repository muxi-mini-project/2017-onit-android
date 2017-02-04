package com.example.kolibreath.onit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kolibreath on 2017/2/4.
 */

public class UserInfoAdapter extends ArrayAdapter<Userinfo>{

    private int resourceId;

    public UserInfoAdapter(Context context, int resourceId, List<Userinfo> object){
        super(context,resourceId,object);
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Userinfo info = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        ImageView userAvatar = (ImageView) view.findViewById(R.id.userAvatar);
        TextView userName  = (TextView) view.findViewById(R.id.userName);
        TextView dongtaiTime = (TextView) view.findViewById(R.id.dongtaiTime);
        TextView content = (TextView) view.findViewById(R.id.userDongtaiContent);
        TextView favorNumber = (TextView) view.findViewById(R.id.likeNumber);
        TextView commentsNumber  = (TextView) view.findViewById(R.id.commentsNumber);
        TextView deadLine = (TextView) view.findViewById(R.id.dongtaiDeadlineDate);

        userAvatar.setImageResource(info.getUserAvatar());
        userName.setText(info.getUsername());
        dongtaiTime.setText(info.getDongtaitime());
        content.setText(info.getContent());
        favorNumber.setText(info.getFavorNumber());
        commentsNumber.setText(info.getCommentsNumber());
        deadLine.setText(info.getDongtaiDeadline());


        return convertView;
    }
}
