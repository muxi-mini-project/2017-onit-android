package com.example.kolibreath.onit;

import android.content.Context;
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
    private ImageButton imageButton1, imageButton2;
    private int clickstatus = 1;
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
        TextView userName  = (TextView) view.findViewById(R.id.userName);
        TextView dongtaiTime = (TextView) view.findViewById(R.id.dongtaiTime);
        TextView content = (TextView) view.findViewById(R.id.userDongtaiContent);
        TextView deadLine = (TextView) view.findViewById(R.id.dongtaiDeadlineDate);
        final TextView favorNumbers = (TextView) view.findViewById(R.id.likeNumbers);
        TextView commentsNumbers = (TextView) view.findViewById(R.id.commentNumbers);

         final ImageView favor_or_not1 = (ImageView) view.findViewById(R.id.favor_or_not1);

        //getText出了问题
        String str1 = favorNumbers.getText().toString();
        int number = Integer.parseInt(str1)+1;
        final String addedstr = String.valueOf(number);

        final String substractedstr = String.valueOf(number-1);

         favor_or_not1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickstatus==1){
                favor_or_not1.setImageResource(R.drawable.ic_favorite_red_18dp);
                favorNumbers.setText(addedstr);
                    clickstatus=0;
                }else {
                    favor_or_not1.setImageResource(R.drawable.ic_favorite_border_white_18dp);
                    favorNumbers.setText(substractedstr);
                    clickstatus= 1;
                }
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
}
