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
import android.widget.ListView;
import android.widget.TextView;

import com.example.kolibreath.onit.App;
import com.example.kolibreath.onit.Beans.FriendsBean;
import com.example.kolibreath.onit.InterfaceAdapter.ServiceInterface;
import com.example.kolibreath.onit.R;

import java.util.ArrayList;
import java.util.List;

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

public class SearchforBBFActivity extends AppCompatActivity{

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    Retrofit retrofit;
    ServiceInterface si;
    SearchView searchView ;
    String queryText;
    List<FriendsBean> friendsList = new ArrayList<>();
    ListView listView;
    FriendsBean bean;
    FriendsAdapter adapter;

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
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }
    private void initWidget(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_for_friends_search);
        listView = (ListView) findViewById(R.id.recommendList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void SearchForFriends(){
        Call<FriendsBean> call = si.getFriendsInfo(queryText,App.storedUserToken);
        Log.d("queryText", queryText);
        Log.d("storedUserToken",App.storedUserToken);
        call.enqueue(new Callback<FriendsBean>() {
            @Override
            public void onResponse(Call<FriendsBean> call, Response<FriendsBean> response) {
                bean= response.body();
                friendsList.add(bean);
                String name = bean.getUsername();
                Log.d("queryusername" ,name);
            }
            @Override
            public void onFailure(Call<FriendsBean> call, Throwable t) {

            }
        });
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbbf);
        initWidget();
        initSearchView();

        if(queryText!=null){
            SearchForFriends();
        }

        adapter  = new FriendsAdapter(SearchforBBFActivity.this,R.layout.search_item,friendsList);

        listView.setAdapter(adapter);
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

    class FriendsAdapter extends ArrayAdapter<FriendsBean>{
        private int resourceId;
        public FriendsAdapter(Context context, int resource, List<FriendsBean> Objects){
            super(context,resource,Objects);
            this.resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FriendsBean bean = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            TextView getusername = (TextView) view.findViewById(R.id.search_user_id);
            getusername.setText(bean.getUsername());
            return view;
        }
    }

}
