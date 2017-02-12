package com.example.kolibreath.onit.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kolibreath.onit.Generics.OtherUserInfo;
import com.example.kolibreath.onit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kolibreath on 2017/2/12.
 */

public class OtherUserMain extends AppCompatActivity  implements View.OnClickListener{

    private TextView otherUserNamefansNumber,otherFansNumber,otherUserName;
    private String otherName;
    private Button confirmToFocus;
    private int clickstatus = 1;
    private LinearLayout otherUserMain;
    private TabLayout otherUserTablayout;
    private OtherUserInfoAdapter adapter;
    private List<OtherUserInfo> otherList = new ArrayList<OtherUserInfo>();
    private ListView otherUserInfoList;
    private int favorClickstatus = 1;


    private void initData(){
            OtherUserInfo info = new OtherUserInfo("我也不能落下余依蕾的进度，为木犀争光",
                    "2017/2/6", "2017/2/14",
                    8, 10);
            otherList.add(info);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirmToFocus:
                if (clickstatus == 1) {
                    confirmToFocus.setText("已关注");
                    Log.d("focus", "onClick: ");
                    clickstatus = 2;
                    Snackbar.make(otherUserMain,"您已经关注了此用户", Snackbar.LENGTH_SHORT).show();
                }else{
                    confirmToFocus.setText("关注");
                    clickstatus=1;
                    Snackbar.make(otherUserMain,"您已经取关了此用户", Snackbar.LENGTH_SHORT).show();
                }
        }
    }

    private void initWidget(){
        otherUserName = (TextView) findViewById(R.id.otherusersNameDisplay);
        confirmToFocus = (Button) findViewById(R.id.confirmToFocus);
        confirmToFocus.setOnClickListener(this);
        otherUserMain = (LinearLayout) findViewById(R.id.otheruserLayout);
        otherUserTablayout  = (TabLayout) findViewById(R.id.otherUserTablayout);
        otherUserTablayout.addTab(otherUserTablayout.newTab().setText("任务列表"));
        otherUserInfoList  = (ListView) findViewById(R.id.otheruserDongtaiRealList);
        otherFansNumber = (TextView) findViewById(R.id.otherFanNumber);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otheruser_dongtai);

        initData();
        initWidget();

        adapter = new OtherUserInfoAdapter(this,R.layout.otheruser_dongtai_item,otherList);
        otherUserInfoList.setAdapter(adapter);
    }

    class OtherUserInfoAdapter extends ArrayAdapter<OtherUserInfo>{

        private int resourceId;

        public OtherUserInfoAdapter(Context context, int resourceId, List<OtherUserInfo> object){
            super(context,resourceId,object);
            this.resourceId = resourceId;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            OtherUserInfo info = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            TextView content = (TextView) view.findViewById(R.id.otherDongtaiContent);
            TextView startTime = (TextView) view.findViewById(R.id.otherStartTime);
            TextView deadline = (TextView) view.findViewById(R.id.otherDongtaiDeadLine);
            final TextView favorNumber = (TextView) view.findViewById(R.id.otherfNumbers);
            final TextView commentNumber = (TextView) view.findViewById(R.id.othercNumbers);
            final ImageView favor = (ImageView) view.findViewById(R.id.otherfavorit);

            content.setText(info.getDongtaiContent());
            startTime.setText(info.getDongtaiStartTime());
            deadline.setText(info.getDongtaiDeadline());
            favorNumber.setText(String.valueOf(info.getFavorNumber()));
            commentNumber.setText(String.valueOf(info.getCommentsNumber()));

            favor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(favorClickstatus==1){
                        favor.setImageResource(R.drawable.ic_favorite_red_18dp);
                        favorClickstatus=2;
                        favorNumber.setText(String.valueOf(otherList.get(position).getFavorNumber()+1));
                        favorNumber.setTextColor(getResources().getColor(R.color.pureRed));
                    }else{
                        favor.setImageResource(R.drawable.ic_favorite_border_white_18dp);
                        favorClickstatus=1;
                        favorNumber.setText(String.valueOf(otherList.get(position).getFavorNumber()));
                        favorNumber.setTextColor(getResources().getColor(R.color.pureWhite));
                    }
                }
            });
            return view;
        }
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
        }
        return super.onOptionsItemSelected(item);
    }


}
