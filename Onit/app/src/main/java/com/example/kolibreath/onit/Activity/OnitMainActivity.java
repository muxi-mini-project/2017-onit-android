package com.example.kolibreath.onit.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kolibreath.onit.Beans.FriendsDongtaiBean;
import com.example.kolibreath.onit.R;
import com.example.kolibreath.onit.InterfaceAdapter.ServiceInterface;
import com.example.kolibreath.onit.Generics.Userinfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kolibreath on 2017/2/1.
 */


public class OnitMainActivity extends AppCompatActivity {

    private ListView listView;
    private Userinfo userinfo;

    //ip
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.148.80.246:3000/statuses/v1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(new OkHttpClient())
            .build();

    ServiceInterface si = retrofit.create(ServiceInterface.class);

    private android.os.Handler handler = new android.os.Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    UserInfoAdapter adapter = new UserInfoAdapter(OnitMainActivity.this, R.layout.onitdongtai_item, userinfolist
                            , userinfolist);
                    listView.setAdapter(adapter);
            }
        }
    };

    private List<Userinfo> userinfolist = new ArrayList<>();

    private void initWiget() {
        ImageButton gototheUserCenter = (ImageButton) findViewById(R.id.userCenter);
        FloatingActionButton firstFAB = (FloatingActionButton) findViewById(R.id.firstFAB);
        TextView dateHere = (TextView) findViewById(R.id.dateHere);
        dateHere.setText(getDate());

        final FloatingActionButton secondFAB = (FloatingActionButton) findViewById(R.id.secondFAB);
        final FloatingActionButton thirdFAb = (FloatingActionButton) findViewById(R.id.thirdFAB);
        final TextView assign_onit = (TextView) findViewById(R.id.assign_onit);
        final TextView search_for_bff = (TextView) findViewById(R.id.search_potential_bff);

        gototheUserCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnitMainActivity.this, UserMainActivity.class);
                startActivity(intent);
            }
        });

        firstFAB.setOnClickListener(new View.OnClickListener() {
            int clickState = 1;

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.firstFAB:
                        if (clickState == 1) {
                            secondFAB.setVisibility(View.VISIBLE);
                            thirdFAb.setVisibility(View.VISIBLE);
                            assign_onit.setVisibility(View.VISIBLE);
                            search_for_bff.setVisibility(View.VISIBLE);
                            clickState = 0;
                            break;
                        }
                        if (clickState == 0) {
                            secondFAB.setVisibility(View.GONE);
                            thirdFAb.setVisibility(View.GONE);
                            assign_onit.setVisibility(View.GONE);
                            search_for_bff.setVisibility(View.GONE);
                            clickState = 1;
                            break;
                        }
                        break;

                }
            }
        });
        secondFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnitMainActivity.this, CreateNewDongtai.class);
                startActivity(intent);
            }
        });
        thirdFAb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnitMainActivity.this, SearchforBBFActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onitdongtai);
        listView = (ListView) findViewById(R.id.uesrDongTaiList);
        listView.setVerticalScrollBarEnabled(false);

        initWiget();

        Call<List<FriendsDongtaiBean>> call = si.getFriends();
        call.enqueue(new retrofit2.Callback<List<FriendsDongtaiBean>>() {
            @Override
            public void onResponse(Call<List<FriendsDongtaiBean>> call, Response<List<FriendsDongtaiBean>> response) {
                List<FriendsDongtaiBean> list = response.body();
                for (int i = 0; i < list.size(); i++) {
                    userinfo = new Userinfo(R.drawable.avatar_default,
                            list.get(i).getStarttime()
                            , list.get(i).getText(),
                            String.valueOf(list.get(i).getComments_count()),
                            String.valueOf(list.get(i).getAttitudes_count())
                            , list.get(i).getDeadline(),
                            resultStatus(list.get(i).getStarttime(), list.get(i).getDeadline()
                                    , getDate())
                            , list.get(i).getUser_idstr());
                    userinfolist.add(userinfo);
                }
                handler.sendEmptyMessage(0);
                Message msg = new Message();
                msg.obj = userinfolist;
                handler.sendMessage(msg);
                Log.d("run succeed", list.get(0).getText());
            }

            @Override
            public void onFailure(Call<List<FriendsDongtaiBean>> call, Throwable t) {
                Log.d("run fail", "onFailure: ");
            }
        });

    }

    private int resultStatus(String startTime, String stopTime, String timethis) {
        int status = 0;
        boolean flag = false;

        timethis = getDate();

        int dayStart = Integer.parseInt(startTime.substring(8, startTime.length()));
        Log.d("daystart", String.valueOf(dayStart));
        int monthStart = Integer.parseInt(startTime.substring(5, 7));
        int yearStart = Integer.parseInt(startTime.substring(0, 4));

        int dayEnd = Integer.parseInt(stopTime.substring(8, stopTime.length()));
        int monthEnd = Integer.parseInt(stopTime.substring(5, 7));
        int yearEnd = Integer.parseInt(stopTime.substring(0, 4));

        int dayThis = Integer.parseInt(timethis.substring(8, stopTime.length()));
        int monthThis = Integer.parseInt(timethis.substring(5, 7));
        int yearThis = Integer.parseInt(timethis.substring(0, 4));


        if (flag == false && yearThis <= yearEnd) {
            if (monthThis <= monthEnd) {
                if (dayThis <= dayEnd) {
                    flag = true;
                } else {
                    flag = false;
                    status = 3;
                }
            } else {
                flag = false;
                status = 3;
            }
        } else {
            flag = false;
            status = 3;
        }

        if (flag == true && yearStart <= yearEnd) {
            if (monthStart <= monthEnd) {
                if (dayStart <= dayEnd) {
                    status = 2;
                } else {
                    status = 2;
                }
            } else {
                status = 3;
            }
        } else {
            status = 3;
        }
        return status;

    }

    private String getDate() {
        String datestr = "";
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        datestr = format.format(date);
        return datestr;
    }

    class UserInfoAdapter extends ArrayAdapter<Userinfo> {

        private int resourceId;
        private int onitstatus;
        private List<Userinfo> userinfoList;
        private Userinfo info;
        private ImageButton imageButton1, imageButton2;
        private int clickstatus = 1;


        public UserInfoAdapter(Context context, int resourceId, List<Userinfo> object, List<Userinfo> list) {
            super(context, resourceId, object);
            this.resourceId = resourceId;
            userinfoList = list;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            info = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            ImageView userAvatar = (ImageView) view.findViewById(R.id.userAvatar);
            ImageView comment = (ImageView) view.findViewById(R.id.comments);
            final TextView userName = (TextView) view.findViewById(R.id.userName);
            TextView dongtaiTime = (TextView) view.findViewById(R.id.dongtaiTime);
            TextView content = (TextView) view.findViewById(R.id.userDongtaiContent);
            TextView deadLine = (TextView) view.findViewById(R.id.dongtaiDeadlineDate);
            TextView deadlineString  = (TextView) view.findViewById(R.id.dongtai_deadline_string);
            final TextView favorNumbers = (TextView) view.findViewById(R.id.likeNumbers);
            TextView commentsNumbers = (TextView) view.findViewById(R.id.commentNumbers);

            userAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(OnitMainActivity.this,OtherUserMain.class);
                    i.putExtra("userName",userinfolist.get(0).getUsername());
                    Log.d("username",userinfolist.get(0).getUsername());
                    startActivity(i);
                }
            });

            final ImageView favor_or_not1 = (ImageView) view.findViewById(R.id.favor_or_not1);

            String str1 = favorNumbers.getText().toString();

            int number = Integer.parseInt(str1) + 1;
            final String addedstr = String.valueOf(number);
            final String substractedstr = String.valueOf(number - 1);


            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), Comment.class);
                    startActivity(i);
                }
            });

            Log.d("userinfo", userinfolist.get(1).getFavorNumber());
            final int addUserfavorNumber =Integer.parseInt(userinfolist.get(position).getFavorNumber()) +1;

            favor_or_not1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickstatus == 1) {
                        favor_or_not1.setImageResource(R.drawable.ic_favorite_red_18dp);
                        favorNumbers.setText(String.valueOf(addUserfavorNumber));
                        favorNumbers.setTextColor(getResources().getColor(R.color.pureRed));
                        clickstatus = 0;
                    } else {
                        favor_or_not1.setImageResource(R.drawable.ic_favorite_border_white_18dp);
                        favorNumbers.setText(userinfolist.get(position).getFavorNumber());
                        favorNumbers.setTextColor(getResources().getColor(R.color.pureWhite));
                        clickstatus = 1;
                    }
                }


            });

            userAvatar.setImageResource(info.getUserAvatar());
            userName.setText(info.getUsername());
            dongtaiTime.setText(info.getDongtaitime());
            content.setText(info.getContent());
            deadLine.setText(info.getDongtaiDeadline());


            favorNumbers.setText(info.getFavorNumber());
            commentsNumbers.setText(info.getCommentsNumber());

            //定义：1，2，3 完成 进行中 未完成
            RelativeLayout layout1 = (RelativeLayout) view.findViewById(R.id.replaceable_finished);
            RelativeLayout layout2 = (RelativeLayout) view.findViewById(R.id.replaceable_onit);
            RelativeLayout layout3 = (RelativeLayout) view.findViewById(R.id.replacable_unfinished);

            info = userinfoList.get(position);
            onitstatus = info.getUserOnitStatus();
            switch (onitstatus) {
                case 1:
                    layout1.setVisibility(View.VISIBLE);

                    break;
                case 2:
                    layout2.setVisibility(View.VISIBLE);
                    deadLine.setAlpha(1);
                    deadlineString.setAlpha(1);
                    break;
                case 3:
                    layout3.setVisibility(View.VISIBLE);
                    break;
            }
            return view;
        }
    }
}

