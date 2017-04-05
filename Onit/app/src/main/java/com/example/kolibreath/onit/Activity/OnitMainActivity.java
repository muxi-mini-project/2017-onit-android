package com.example.kolibreath.onit.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kolibreath.onit.Beans.SingleDongtaiBean;
import com.example.kolibreath.onit.Beans.StringBean;
import com.example.kolibreath.onit.Beans.UserAttentionBean;
import com.example.kolibreath.onit.Beans.UserDongtaiListBean;
import com.example.kolibreath.onit.Generics.FollowedUserList;
import com.example.kolibreath.onit.Generics.Useid;
import com.example.kolibreath.onit.Generics.Userinfo;
import com.example.kolibreath.onit.InterfaceAdapter.ServiceInterface;
import com.example.kolibreath.onit.R;
import com.example.kolibreath.onit.Utils.ActivityContainer;
import com.example.kolibreath.onit.Utils.App;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.kolibreath.onit.Utils.App.storedUserToken;

/**
 * Created by kolibreath on 2017/2/1.
 */


public class OnitMainActivity extends AppCompatActivity {

    private ActivityContainer container;
    //userInfo类用于显示界面上有的评论数目 点赞数 之类的
    private Userinfo userinfo;
    //用户关注的其他用户的列表
    private List<Integer> userAttentionList;
    private ListView listView;
    //暂时用来存放用户所关注的对象 只是一个例子 关注的对象在主方法中手动添加的

    private List<Integer> followedUserId = new ArrayList<>();

    private List<String>  followedUsername = new ArrayList<>();

    private List<Integer> followedUserDongtaiIdList = new ArrayList<>();

    private List<Userinfo> theKeyList = new ArrayList<>();

    //match两个list
    private List<FollowedUserList> matchFollowedUserList = new ArrayList<>();



    //或者直接调用这个接口 获取用户的timeline 获取用户的list
    UserMainActivity usermain = new UserMainActivity();

    //ip
    Retrofit retrofit ;
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    ServiceInterface si;

    //获取当前用户所关注的其他用户的id
    //id 获取失败暂时使用自己写的id代替

    private List<Userinfo> userinfolist = new ArrayList<>();

    private void initWiget() {
        ImageButton gototheUserCenter = (ImageButton) findViewById(R.id.userCenter);
        FloatingActionButton firstFAB = (FloatingActionButton) findViewById(R.id.firstFAB);
        TextView dateHere = (TextView) findViewById(R.id.dateHere);
        dateHere.setText(getDate());

        listView = (ListView) findViewById(R.id.uesrDongTaiList);
        listView.setVerticalScrollBarEnabled(false);

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
        ActivityContainer.getInstance().addActivity(this);
        final Button button = (Button) findViewById(R.id.removeActivity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityContainer.getInstance().removeActivity(OnitMainActivity.this);
            }
        });

        initWiget();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://121.42.12.214:5050/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        si = retrofit.create(ServiceInterface.class);

        //获取用户关注的人的id >>获取用户关注的人的用户名
        getUserAttetionUserList();

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

    //获取用户的id>>id全部转化为username>>usename作为参数去取得任务表
    private void getUserAttetionUserList(){
        Log.d("process!!", "getUserAttetionUserList: ");
        Call<UserAttentionBean> call = si.getUserAttentionList(App.storedUsername, storedUserToken);
        call.enqueue(new Callback<UserAttentionBean>() {
            @Override
            public void onResponse(Call<UserAttentionBean> call, Response<UserAttentionBean> response) {
                UserAttentionBean bean = response.body();
                followedUserId = bean.getUser_ids();
                getUserDongtaiListAsUserName(followedUserId);
            }
            @Override
            public void onFailure(Call<UserAttentionBean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    //获取用户关注和自己的任务id
    //讲用户的id和username转化
    private void getUserDongtaiListAsUserName(final List<Integer> followedUseId) {
        Log.d("process!!", "getUserDongtaiListAsUserName: ");
        final int size = followedUseId.size();
        for (int i = 0; i<size; i++) {
            Useid useid = new Useid(followedUserId.get(i));
            Call<StringBean> call1 = si.transUserNameToUid(useid);
            final int finalI = i;
            call1.enqueue(new Callback<StringBean>() {
                @Override
                public void onResponse(Call<StringBean> call, Response<StringBean> response) {
                    StringBean bean = response.body();
                    String username = bean.getUsername();
                    followedUsername.add(username);
                    if(finalI ==size-1){
                        Log.d("followedUsername", followedUsername.size()+"");
                        getFollowedUserDongtai(followedUsername);
                    }
                }
                @Override
                public void onFailure(Call<StringBean> call, Throwable t) {
                }
            });
        }
    }

    //获取自己和用户关注的用户的任务的id list 通过用户名查询
    private void getFollowedUserDongtai(final List<String> followedUsername){
        Log.d("process!!", "getFollowedUserDongtai: ");
        final int size = followedUsername.size();
        for(int i=0;i<size;i++){
            Call<UserDongtaiListBean> call = si.getUserDongtaiList("ybao",App.storedUserToken);
            final int finalI = i;
            final int finalI1 = i;
            call.enqueue(new Callback<UserDongtaiListBean>() {
                @Override
                public void onResponse(Call<UserDongtaiListBean> call, Response<UserDongtaiListBean> response) {
                    UserDongtaiListBean bean = response.body();
                    //list >> 任务id
                    followedUserDongtaiIdList = bean.getResults();
                    String username = followedUsername.get(finalI1);
                    FollowedUserList followedUserList = new FollowedUserList(username,followedUserDongtaiIdList);
                    matchFollowedUserList.add(followedUserList);
                    if(finalI ==size-1){
                        getSingleDongtai(matchFollowedUserList);
                    }
                }
                @Override
                public void onFailure(Call<UserDongtaiListBean> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    //获取id所对应的单条任务的id
    private void getSingleDongtai(List<FollowedUserList> list) {
        Log.d("process!!", "getSingleDongtai: ");
        //list中只有连个关注对象
        final int size = list.size();
        for (int i = 0; i < size; i++) {
            //获取储存的username对应的任务id
            final String username = list.get(i).getUsername();
            Log.d("process!!", username);
            List<Integer> dongtaiList = list.get(i).getMatchedId();
            Log.d("process!!", "getSingleDongtai: list size" + dongtaiList.size());
            Log.d("process!!", "getSingleDongtai: " + dongtaiList.get(0));
            int size2 = dongtaiList.size();
               for (int j = 0; j < size2; j++) {
                Call<SingleDongtaiBean> call = si.getSingleDongtai(username, dongtaiList.get(j), storedUserToken);
                final int finalI = i;
                call.enqueue(new Callback<SingleDongtaiBean>() {
                    @Override
                    public void onResponse(Call<SingleDongtaiBean> call, Response<SingleDongtaiBean> response) {
                        SingleDongtaiBean bean = response.body();
                        String commentCount = bean.getComments_count()+"";
                        //这个status是n，，，
                        //int status = Integer.parseInt(bean.getStatus());
                        Userinfo info = new Userinfo(R.drawable.python, bean.getCreated_at(), bean.getText(),
                                commentCount, "2", bean.getDeadline(),1, username);
                        Log.d("final success", "onResponse: ");
                        userinfolist.add(info);
                        if (finalI == size - 1) {
                            Log.d("userinfosize", userinfolist.size() + "");
                            Context context = OnitMainActivity.this;
                            UserInfoAdapter adapter = new UserInfoAdapter(context, R.layout.onitdongtai_item, userinfolist);
                            listView.setAdapter(adapter);
                        }
                    }
                    @Override
                    public void onFailure(Call<SingleDongtaiBean> call, Throwable t) {
                        Log.d("final fail", "onResponse: ");
                    }
                });
            }
            }
    }


    class UserInfoAdapter extends ArrayAdapter<Userinfo> {

        private int resourceId;
        private int onitstatus;
        private List<Userinfo> userinfoList;
        private Userinfo info;
        private ImageButton imageButton1, imageButton2;
        private int clickstatus = 1;


        public UserInfoAdapter(Context context, int resourceId, List<Userinfo> object) {
            super(context, resourceId, object);
            this.resourceId = resourceId;
            userinfoList = object;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            info = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            CircleImageView userAvatar = (CircleImageView) view.findViewById(R.id.userAvatar);
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

