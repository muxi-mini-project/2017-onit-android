package com.example.kolibreath.onit.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kolibreath.onit.App;
import com.example.kolibreath.onit.Beans.IdBean;
import com.example.kolibreath.onit.Beans.SingleDongtaiBean;
import com.example.kolibreath.onit.Beans.UserProfileBean;
import com.example.kolibreath.onit.DataBase.NotesDB;
import com.example.kolibreath.onit.Generics.OwnOnlineDongtai;
import com.example.kolibreath.onit.Generics.voidClass;
import com.example.kolibreath.onit.InterfaceAdapter.ServiceInterface;
import com.example.kolibreath.onit.InterfaceAdapter.UserOwnDongtaiAdapter;
import com.example.kolibreath.onit.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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

/**
 * Created by kolibreath on 2017/2/2.
 */

public class UserMainActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout userDongtaiList;
    private ListView lv;
    private Cursor cursor;
    private SQLiteDatabase dbReader;
    private NotesDB notesDB;
    private Button newOnit;
    private UserOwnDongtaiAdapter myAdapter;
    private CircleImageView circleImageView;
    private SQLiteDatabase db;
    private TextView fansNumber,atNumber;
    private String str;
    private TextView rankDisplay, userNameDisplay;
    private UserProfileBean bean;
    //idbean 用户列表储存在全局变量
    private IdBean idbean;
    private List<OwnOnlineDongtai> OList = new ArrayList<OwnOnlineDongtai>();
    private List<Integer> idList;
    private SingleDongtaiBean bean3;
    private List<Integer> rankList;

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    Retrofit retrofit;
    ServiceInterface si;

    //这个handler的作用是用来显示用户数据的 比如粉丝数等
    //只用写一个handler 用数字判断即可
    private android.os.Handler handler = new android.os.Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                   fansNumber.setText(String.valueOf(bean.getFollowers_coumnt()));
                    atNumber.setText(String.valueOf(bean.getFolloweds_count()));
                    rankDisplay.setText(statusResult(0));
                    break;
                case 1:
                    idList = idbean.getResult();
                    break;
                case 2:
                    OwnOnlineAdapter OAdapter = new OwnOnlineAdapter(UserMainActivity.this, R.layout.userown_dongtai_item
                            , OList);
                    OwnOnlineDongtai ownOnlineDongtai = new OwnOnlineDongtai();
                    Collections.sort(OList,ownOnlineDongtai);
                    lv.setAdapter(OAdapter);
                    lv.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                        @Override
                        public void onCreateContextMenu(ContextMenu menu, View v,
                                                        ContextMenu.ContextMenuInfo menuInfo) {
                            menu.setHeaderTitle("确定删除");
                            menu.add(0,0,0,"删除");
                        }
                    });
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.usermain_activity);
        super.onCreate(savedInstanceState);
        notesDB = new NotesDB(this);
        dbReader = notesDB.getReadableDatabase();

        initWidget();
        changAvatar();

        userNameDisplay.setText(App.storedUsername);

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://121.42.12.214:5050/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        si = retrofit.create(ServiceInterface.class);

        getUserProfile();
        getUserDongtaiId();
        }


        class OwnOnlineAdapter extends ArrayAdapter<OwnOnlineDongtai> {
            private int resourceId;
            private int clickstatus = 1;

            public OwnOnlineAdapter(Context context, int resourceId, List<OwnOnlineDongtai> object) {
                super(context, resourceId, object);
                this.resourceId = resourceId;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                OwnOnlineDongtai dongtai = getItem(position);

                View layout = LayoutInflater.from(getContext()).inflate(resourceId, null);
                TextView contenttv = (TextView) layout.findViewById(R.id.userownDongtaiContent);
                TextView timetv = (TextView) layout.findViewById(R.id.userowndongtaiTime);
                TextView endtime = (TextView) layout.findViewById(R.id.userowndongtaiDeadlineDate);

                contenttv.setText(dongtai.getOnlineContent());
                 timetv.setText(dongtai.getStartTime());
                 endtime.setText(dongtai.getDeadline());

           /*
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


*/
                //从数据库中读取数据 把提取出来的时间 和 截止时间比较

           /*
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

*/
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

    private void initWidget(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_for_usercenter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userDongtaiList = (TabLayout) findViewById(R.id.userDongtaiList);
        userDongtaiList.addTab(userDongtaiList.newTab().setText("任务列表"));
        userNameDisplay = (TextView) findViewById(R.id.users_name_display);
        atNumber = (TextView) findViewById(R.id.attention_number);

        fansNumber = (TextView) findViewById(R.id.fans_number);
        rankDisplay = (TextView) findViewById(R.id.rankDisplay);


        lv = (ListView) findViewById(R.id.userDongtaiRealList);
        lv.setVerticalScrollBarEnabled(false);
        newOnit = (Button) findViewById(R.id.assignNewOnit);
        newOnit.setOnClickListener(this);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //cursor = dbReader.rawQuery("select * from " + NotesDB.TABLE_NAME,null);
                //cursor.moveToPosition(position);
                Intent i = new Intent(UserMainActivity.this, SelectActivity.class);
                // i.putExtra(NotesDB.ID,cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));
                //i.putExtra(NotesDB.CONTENT,cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT)));
                //i.putExtra(NotesDB.TIME,cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));

                //获取了数组中的第五个数字
                SingleDongtaiBean bean = selectEachDongtai(idbean.getResult().get(position));
                i.putExtra("text",bean.getText());
                startActivity(i);
            }
        });

    }
    private void changAvatar(){

        circleImageView = (CircleImageView) findViewById(R.id.user_avatar);
        Intent intent = getIntent();
        int bitmapSrc = intent.getIntExtra("key", R.drawable.java);
        circleImageView.setImageResource(bitmapSrc);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this,CreateNewDongtai.class);
        switch (v.getId()){
            case R.id.assignNewOnit:
                startActivity(i);
        }
    }

    //从数据库中读取数据并且进行判断

    /*
    public void selectDB(){
        Cursor cursor = dbReader.query(NotesDB.TABLE_NAME,null,null,null,null,null,null);
        myAdapter = new UserOwnDongtaiAdapter(this,cursor);
        lv.setAdapter(myAdapter);

    }
    */

    protected void onResume(){
        super.onResume();
        //selectDB();

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
            case R.id.logoutInUserMain:
                Intent logoutintent = new Intent(UserMainActivity.this,MainActivity.class);
                logoutintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logoutintent);
                break;
            case R.id.set_avatar:
                Intent i = new Intent(UserMainActivity.this,SelectAvatarActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private String statusResult(int level){

        String str1 = "初涉江湖";
        String str2 = "渐入佳境";
        String str3 = "炉火纯青";
        String str4 = "登峰造极";

        switch (level){
            case 0:
                rankDisplay.setText(str1);
                break;
            case 1:
                rankDisplay.setText(str2);
                break;
            case 2:
                rankDisplay.setText(str3);
                break;
            case 3:
                rankDisplay.setText(str4);
                break;
        }

        return str;
    }

    //position是任务的id
    private SingleDongtaiBean selectEachDongtai(final int position){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<SingleDongtaiBean> call3 = si.getSingleDongtai(App.storedUsername,
                        idbean.getResult().get(position),
                        App.storedUserToken);
                call3.enqueue(new Callback<SingleDongtaiBean>() {
                    @Override
                    public void onResponse(Call<SingleDongtaiBean> call, Response<SingleDongtaiBean> response) {
                        bean3 = response.body();
                    }
                    @Override
                    public void onFailure(Call<SingleDongtaiBean> call, Throwable t) {

                    }
                });
            }
        }).start();

        return bean3;
    }

    private void getEachDongtai() {
            for (int i = 0; i < idbean.getResult().size(); i++) {
                final int id = idbean.getResult().get(i);
                Call<SingleDongtaiBean> call2 = si.getSingleDongtai(App.storedUsername,
                        id,
                        App.storedUserToken);
                call2.enqueue(new Callback<SingleDongtaiBean>() {
                    @Override
                    public void onResponse(Call<SingleDongtaiBean> call, Response<SingleDongtaiBean> response) {
                        SingleDongtaiBean sbean = response.body();
                        OwnOnlineDongtai dongtai = new OwnOnlineDongtai(1, sbean.getCreated_at(), sbean.getComments_count(),
                                0, sbean.getText(), sbean.getDeadline(),id);
                        OList.add(dongtai);
                        if(OList.size()==idbean.getResult().size()){
                        handler.sendEmptyMessage(2);
                        Message msg = new Message();
                        msg.obj =  OList;
                        handler.sendMessage(msg);

                        }
                    }

                    @Override
                    public void onFailure(Call<SingleDongtaiBean> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }
    private void getUserProfile(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取用户的关注和粉丝数目；
                Call<UserProfileBean> call = si.getProfile(App.storedUsername,App.storedUserToken);
                call.enqueue(new Callback<UserProfileBean>() {
                    @Override
                    public void onResponse(Call<UserProfileBean> call, Response<UserProfileBean> response) {
                        bean = response.body();
                        handler.sendEmptyMessage(0);
                        Message msg = new Message();
                        msg.obj = bean;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(Call<UserProfileBean> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }).start();


    }
    private void getUserDongtaiId(){
        //获取用户的id
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<IdBean> call1 = si.getDongtaiId(App.storedUsername, App.storedUserToken);
                call1.enqueue(new Callback<IdBean>()
                {
                    @Override
                    public void onResponse(Call<IdBean> call, Response<IdBean> response) {
                        idbean = response.body();
                        if(idbean==null){
                            LinearLayout layout = (LinearLayout) findViewById(R.id.user_main);
                            Snackbar.make(layout,"任务栏空空如也！",Snackbar.LENGTH_INDEFINITE)
                                    .setAction("点击发布任务", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent  = new Intent(UserMainActivity.this,CreateNewDongtai.class);
                                            startActivity(intent);
                                        }
                                    }).show();
                        }else {
                            getEachDongtai();
                        }
                    }
                    @Override
                    public void onFailure(Call<IdBean> call, Throwable t) {
                    }
                });
            }
        }).start();
    }

    public void deleteListItem(int id){
       Call<voidClass> call = si.deleteDongtai(id,App.storedUserToken);
        call.enqueue(new Callback<voidClass>() {
            @Override
            public void onResponse(Call<voidClass> call, Response<voidClass> response) {
                Log.d("hoperight", "onResponse: ");
            }

            @Override
            public void onFailure(Call<voidClass> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    //为什么没有完全删除？！aa
    //如何从listview中删除一个item
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d("clicked", "onContextItemSelected: ");
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        Log.d("position", String.valueOf(position));
        deleteListItem(OList.get(position).getId());
        return super.onContextItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
