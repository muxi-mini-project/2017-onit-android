package com.example.kolibreath.onit.Activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kolibreath.onit.Beans.SingleDongtaiBean;
import com.example.kolibreath.onit.DataBase.NotesDB;
import com.example.kolibreath.onit.InterfaceAdapter.ServiceInterface;
import com.example.kolibreath.onit.R;
import com.example.kolibreath.onit.Utils.App;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kolibreath on 2017/2/8.
 */

public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    private Button delete, back;
    private TextView s_textview;
    private NotesDB notesDB;
    private SQLiteDatabase dbWriter;
    private TextView displaySpecific;
    private int id;

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    Retrofit retrofit;
    ServiceInterface si;

    private void initWidget(){
        id = (int) getIntent().getExtras().get("dongTaiId");
        delete = (Button)findViewById(R.id.delete);
        back = (Button)findViewById(R.id.back);
        //   s_textview = (TextView) findViewById(R.id.s_textView);
        notesDB =  new NotesDB(this);
        dbWriter = notesDB.getWritableDatabase();
        delete.setOnClickListener(this);
        back.setOnClickListener(this);
        // s_textview.setText(getIntent().getStringExtra(NotesDB.CONTENT));
        displaySpecific = (TextView) findViewById(R.id.s_textView);
    }

    private void getSingleDongtai(){
        Call<SingleDongtaiBean> call = si.getSingleDongtai(App.storedUsername,id,App.storedUserToken);
        call.enqueue(new Callback<SingleDongtaiBean>() {
            @Override
            public void onResponse(Call<SingleDongtaiBean> call, Response<SingleDongtaiBean> response) {
                SingleDongtaiBean bean = response.body();
                String text = bean.getText();
                displaySpecific.setText(text);
            }

            @Override
            public void onFailure(Call<SingleDongtaiBean> call, Throwable t) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onitdongtai_specific);

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://121.42.12.214:5050/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        si = retrofit.create(ServiceInterface.class);

        initWidget();
        getSingleDongtai();

    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.delete:
                deleteDataBase();
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }

    }
    public void deleteDataBase(){
        dbWriter.delete(NotesDB.TABLE_NAME,"_id="+ getIntent().getIntExtra(NotesDB.ID,0),null);
    }

}
