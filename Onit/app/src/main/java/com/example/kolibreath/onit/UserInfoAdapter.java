package com.example.kolibreath.onit;

import android.content.Context;
import android.support.annotation.NonNull;
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
    public UserInfoAdapter(Context context, int resourceId, List<Userinfo> object,List<Userinfo> list){
        super(context,resourceId,object);
        this.resourceId = resourceId;
        userinfoList = list;
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
        TextView deadLine = (TextView) view.findViewById(R.id.dongtaiDeadlineDate);
        TextView favorNumbers = (TextView) view.findViewById(R.id.fNumbers);
        TextView commentsNumbers = (TextView) view.findViewById(R.id.cNumbers);

        final ImageButton imageButton1 = (ImageButton) view.findViewById(R.id.favor_or_not1);
        final ImageButton imageButton2 = (ImageButton) view.findViewById(R.id.favor_or_not2);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            int clickstatus =1;
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.favor_or_not1:
                        if (clickstatus==1){
                            imageButton2.setVisibility(View.VISIBLE);
                            clickstatus=0;
                        }
                        break;
                }
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
       // favorNumbers.setText(info.getFavorNumber());
        //commentsNumbers.setText(info.getCommentsNumber());


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
}
