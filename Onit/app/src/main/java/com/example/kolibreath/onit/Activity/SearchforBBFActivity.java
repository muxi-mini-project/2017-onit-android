package com.example.kolibreath.onit.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kolibreath.onit.Beans.FriendsBean;
import com.example.kolibreath.onit.Generics.FoundUser;
import com.example.kolibreath.onit.Generics.voidClass;
import com.example.kolibreath.onit.InterfaceAdapter.ServiceInterface;
import com.example.kolibreath.onit.R;
import com.example.kolibreath.onit.Utils.App;
import com.example.kolibreath.onit.Utils.RankLevel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.kolibreath.onit.R.layout.activity_searchbbf;

/**
 * Created by kolibreath on 2017/2/2.
 */

public class SearchforBBFActivity extends AppCompatActivity implements View.OnClickListener{

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    Retrofit retrofit;
    ServiceInterface si;
    SearchView searchView ;
    String queryText;
    List<FoundUser> friendsList = new ArrayList<>();
    ListView listView;
    FriendsBean bean;
    FriendsAdapter adapter;
    RelativeLayout layout;
    private int count = 0;
    private Button testForAttention,cancelTestForAttention;

    //为什么没有接收到handler发送的消息

    private void initSearchView(){
        //设置searchView相关细节
        searchView = (SearchView) findViewById(R.id.searchView_for_friends);
        searchView.setIconifiedByDefault(true);
        searchView.onActionViewExpanded();
        searchView.requestFocus();
        searchView.setSubmitButtonEnabled(true);
        searchView.setFocusable(true);
        searchView.setIconified(true);
        searchView.requestFocusFromTouch();
        searchView.setFocusable(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryText = query;
                Log.d(queryText, "onQueryTextSubmit: ");
                if(friendsList.size()!=1){
                    //需要再点击关注之后就要清空list
                    SearchForFriends();
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                friendsList.clear();
                Context context = SearchforBBFActivity.this;
                adapter = new FriendsAdapter(context,R.layout.search_item,friendsList);
                listView.setAdapter(adapter);
                return true;
            }
        });
    }
    private void initWidget(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_for_friends_search);
        listView = (ListView) findViewById(R.id.recommendList);

        //设置关注或者是不关注
        /*
        only for TEST!! HERE
         */
        testForAttention = (Button) findViewById(R.id.testForAttention);
        testForAttention.setOnClickListener(this);

        cancelTestForAttention = (Button) findViewById(R.id.cancelTestForAttention);
        cancelTestForAttention.setOnClickListener(this);

        //绑定界面出了问题
        //layout = (RelativeLayout) findViewById(R.layout.activity_searchbbf);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // layout = (RelativeLayout) findViewById(activity_searchbbfXML);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.testForAttention:
                getUserAttention();
                break;
            case R.id.cancelTestForAttention:
                cancelUserAttention();
                break;
        }
    }

    private void getUserAttention(){
        Call<voidClass> call = si.getUserAttention("ybao","test",App.storedUserToken);
        call.enqueue(new Callback<voidClass>() {
            @Override
            public void onResponse(Call<voidClass> call, Response<voidClass> response) {
                Log.d("attentionSuccess", "onResponse: ");
            }
            @Override
            public void onFailure(Call<voidClass> call, Throwable t) {

            }
        });
    }

    private void cancelUserAttention(){
        //这里都是假设关注和取消关注的用户都是ybao
        Call<voidClass> call = si.cancelUserAttension("ybao");
        call.enqueue(new Callback<voidClass>() {
            @Override
            public void onResponse(Call<voidClass> call, Response<voidClass> response) {
                Log.d("cancelHasBeenSaved", "onResponse: ");
               // Snackbar.make().show();
            }

            @Override
            public void onFailure(Call<voidClass> call, Throwable t) {

            }
        });
    }

    private void SearchForFriends(){
        Call<FriendsBean> call = si.getFriendsInfo(queryText,App.storedUserToken);
        call.enqueue(new Callback<FriendsBean>() {
            @Override
            public void onResponse(Call<FriendsBean> call, Response<FriendsBean> response) {
                Log.d("research", "onResponse: ");
                bean= response.body();
                FoundUser foundUser = new FoundUser(bean.getUsername(),bean.getLevel());
                friendsList.add(foundUser);
                adapter  = new FriendsAdapter(SearchforBBFActivity.this,R.layout.search_item,friendsList);
                listView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<FriendsBean> call, Throwable t) {
             //   Snackbar.make()
            }
        });
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_searchbbf);
        initWidget();
        initSearchView();


        if(queryText!=null){

        }

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://121.42.12.214:5050/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        si = retrofit.create(ServiceInterface.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    class FriendsAdapter extends ArrayAdapter<FoundUser>{
        private int resourceId;
        public FriendsAdapter(Context context, int resource, List<FoundUser> Objects){
            super(context,resource,Objects);
            this.resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            FoundUser foundUser = getItem(position);
            RankLevel rankLevel = new RankLevel();
            View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            TextView getusername = (TextView) view.findViewById(R.id.search_user_id);
            TextView userRank = (TextView) view.findViewById(R.id.search_user_rank);
            final RelativeLayout layout1 = (RelativeLayout) view.findViewById(R.id.toAttention);
            final RelativeLayout layout2 = (RelativeLayout) view.findViewById(R.id.alreadyAttention);
            getusername.setText(foundUser.getUserName());
            userRank.setText(rankLevel.rank(foundUser.getLevel()));
            layout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count==0){
                        layout1.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);
                        count = 1;
                    }

                    if(count==1){
                        layout2.setVisibility(View.GONE);
                        layout1.setVisibility(View.VISIBLE);
                        count = 0;
                    }
                }
            });
            return view;
        }
    }

}
