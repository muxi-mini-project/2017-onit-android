package com.example.kolibreath.onit.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kolibreath.onit.Beans.IdBean;
import com.example.kolibreath.onit.Beans.SingleDongtaiBean;
import com.example.kolibreath.onit.Beans.UserProfileBean;
import com.example.kolibreath.onit.DataBase.NotesDB;
import com.example.kolibreath.onit.Generics.OwnOnlineDongtai;
import com.example.kolibreath.onit.Generics.voidClass;
import com.example.kolibreath.onit.InterfaceAdapter.ServiceInterface;
import com.example.kolibreath.onit.InterfaceAdapter.UserOwnDongtaiAdapter;
import com.example.kolibreath.onit.R;
import com.example.kolibreath.onit.Utils.App;

import java.io.File;
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

    private AlertDialog alertDialog;
    private final int  ALBUM_OK = 1, CAMERA_OK = 2,CUT_OK = 3;
    private File file;
    private Button button1,button2;

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
     //   transUsernameFromUid();
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

        file = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        file.delete();


        fansNumber = (TextView) findViewById(R.id.fans_number);
        rankDisplay = (TextView) findViewById(R.id.rankDisplay);


        lv = (ListView) findViewById(R.id.userDongtaiRealList);
        lv.setVerticalScrollBarEnabled(false);
        newOnit = (Button) findViewById(R.id.assignNewOnit);
        newOnit.setOnClickListener(this);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(UserMainActivity.this, SelectActivity.class);
                //直接把点击的任务的id传给下一个界面 在哪里请求一次
                //Olist是排序之后的list
                int dongTaiId = OList.get(position).getId();
                i.putExtra("dongTaiId",dongTaiId);
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
                    Log.d("alertCreated", "onOptionsItemSelected: ");
                    String selctions[] = {"从相册选择","相机拍照"};
                    Context context = UserMainActivity.this;
                   AlertDialog.Builder adb =  new AlertDialog.Builder(context).
                            setTitle("选择下面的操作").
                            setItems(selctions, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case 0:
                                            Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                                            albumIntent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                            startActivityForResult(albumIntent, ALBUM_OK);
                                            break;
                                        case 1:
                                            break;
                                    }
                                }
                            });
                adb.show();

               // Intent i = new Intent(UserMainActivity.this,SelectAvatarActivity.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                //startActivity(i);
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
                        handler.sendEmptyMessage(2);
                        Message msg = new Message();
                        msg.obj =  OList;
                        handler.sendMessage(msg);

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

    public void deleteListItem(final int id){
       Call<voidClass> call = si.deleteDongtai(id,App.storedUsername,App.storedUserToken);
        call.enqueue(new Callback<voidClass>() {
            @Override
            public void onResponse(Call<voidClass> call, Response<voidClass> response) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.user_main);
                String text = "这一条任务"+id+"已经被删除";
               Snackbar.make(layout,text,Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<voidClass> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    //为什么没有完全删除？！aa
    //如何从listview中删除一个item 感觉好像是后台的问题
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        deleteListItem(OList.get(position).getId());
        OList.remove(position);
        lv.invalidateViews();
        return super.onContextItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("requestCode = " + requestCode);
        switch (requestCode) {
            // 如果是直接从相册获取
            case ALBUM_OK:
                //从相册中获取到图片了，才执行裁剪动作
                if (data != null) {
                    clipPhoto(data.getData());
                }
                break;
            // 如果是调用相机拍照时
            case CAMERA_OK:
                // 当拍照到照片时进行裁减，否则不执行操作
                if (file.exists()) {
                    clipPhoto(Uri.fromFile(file));//开始裁减图片
                }
                break;
            // 取得裁剪后的图片，这里将其设置到imageview中
            case CUT_OK:
                if (data != null) {
                    setPicToView(data);
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void clipPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop = true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例，这里设置的是正方形（长宽比为1:1）
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CUT_OK);
    }

    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            circleImageView.setImageDrawable(drawable);
            file.delete();//设置成功后清除之前的照片文件
        }
    }

}
