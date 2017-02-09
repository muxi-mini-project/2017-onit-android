package com.example.kolibreath.onit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kolibreath on 2017/2/4.
 */

public class UserInfoAdapter extends ArrayAdapter<Userinfo>{

    private int resourceId;
    private int onitstatus;
    private List<Userinfo> userinfoList;
    private Userinfo info;
    private TextView userName, userAvatar, dongtaiTime, content,deadLine, favorNumbers,commentsNumbers;
    private ImageButton imageButton1, imageButton2;
    public UserInfoAdapter(Context context, int resourceId, List<Userinfo> object,List<Userinfo> list){
        super(context,resourceId,object);
        this.resourceId = resourceId;
        userinfoList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         Userinfo info = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        ImageView userAvatar = (ImageView) view.findViewById(R.id.userAvatar);
        userName  = (TextView) view.findViewById(R.id.userName);
        dongtaiTime = (TextView) view.findViewById(R.id.dongtaiTime);
        content = (TextView) view.findViewById(R.id.userDongtaiContent);
        deadLine = (TextView) view.findViewById(R.id.dongtaiDeadlineDate);
        favorNumbers = (TextView) view.findViewById(R.id.likeNumbers);
        commentsNumbers = (TextView) view.findViewById(R.id.commentNumbers);

        imageButton1 = (ImageButton) view.findViewById(R.id.favor_or_not1);
        imageButton2 = (ImageButton) view.findViewById(R.id.favor_or_not2);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("setText", "onClick: ");
                imageButton2.setVisibility(View.VISIBLE);
                String i = returnAddNumers();
                favorNumbers.setText(i);
                Log.d("textNumber", i);
                }


        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton2.setVisibility(View.GONE);
            }
        });

        userAvatar.setImageResource(info.getUserAvatar());
        userName.setText(info.getUsername());
        dongtaiTime.setText(info.getDongtaitime());
        content.setText(info.getContent());
        deadLine.setText(info.getDongtaiDeadline());

        //不能转型怎么办？
        favorNumbers.setText(info.getFavorNumber());
        commentsNumbers.setText(info.getCommentsNumber());



        RelativeLayout layout1 = (RelativeLayout) view.findViewById(R.id.replaceable_finished);
        RelativeLayout layout2 = (RelativeLayout) view.findViewById(R.id.replaceable_onit);
        RelativeLayout layout3 = (RelativeLayout) view.findViewById(R.id.replacable_unfinished);

        info = userinfoList.get(position);
        onitstatus = info.getUserOnitStatus();
        switch (onitstatus){
            case 1:
                layout1.setVisibility(View.VISIBLE);
                break;
            case 2:
                layout2.setVisibility(View.VISIBLE);
                break;
            case  3:
                layout3.setVisibility(View.VISIBLE);
                break;
        }
        return view;
    }
    private String returnAddNumers(){
        String str1 = favorNumbers.getText().toString();
        int number = Integer.parseInt(str1)+1;
        String str = String.valueOf(number);
        return  str;
    }
}
