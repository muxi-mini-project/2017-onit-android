package com.example.kolibreath.onit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kolibreath on 2017/2/3.
 */

public class FriendsUsedDongtaiAdapter extends BaseAdapter{

    private List<UserInformation> userData;
    private Context context;
    private int resourceId;

    public FriendsUsedDongtaiAdapter(List<UserInformation> userData,int resourceIde
                                    , Context context){
        this.userData = userData;
        this.context = context;
        this.resourceId =  resourceIde;
    }

    @Override
    public int getCount() {
        return userData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.onitdongtai_item,parent,false);
        //ImageView userAvatar = (ImageView) convertView.findViewById(R.id.user_avatar_display_dongtai);
        TextView userName = (TextView) convertView.findViewById(R.id.users_name_display_dongtai);
        TextView dongtai_time = (TextView) convertView.findViewById(R.id.dongtai_time);
        TextView dongtai_content = (TextView) convertView.findViewById(R.id.dongtai_contentstrs);
        TextView favor_or_not_number = (TextView) convertView.findViewById(R.id.favor_or_not_number);
        TextView comments = (TextView) convertView.findViewById(R.id.comments_number);
        TextView deadlineDate = (TextView) convertView.findViewById(R.id.dongtai_deadline_date);

        //userAvatar.setImageResource(userData.get(position).getUserAvatarURL());
        userName.setText(userData.get(position).getUsername());
        dongtai_time.setText(userData.get(position).getUserdongtaiStartDate());
        dongtai_content.setText(userData.get(position).getUserdongtaiContent());
        favor_or_not_number.setText(userData.get(position).getUserfavaorNumber());
        comments.setText(userData.get(position).getUserCommnentNumber());
        deadlineDate.setText(userData.get(position).getUserOnitDeadLine());

        return convertView;
    }
}
