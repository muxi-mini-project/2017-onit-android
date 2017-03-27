package com.example.kolibreath.onit.Utils;

import com.example.kolibreath.onit.InterfaceAdapter.ServiceInterface;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kolibreath on 2017/3/26.
 */

public class SeverConnection {
    //封装了基本的网络请求模式 这些方法需要先初始化一个类的实例然后在调用！
    String server="http://121.42.12.214:5050/";
    Retrofit retrofit;
    ServiceInterface si;
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

    public String getServer(){
       return  server;
    }
    void initSever(HttpLoggingInterceptor interceptor,Retrofit retrofit){
        this.interceptor = interceptor;
        this.retrofit = retrofit;
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(getServer())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        si = retrofit.create(ServiceInterface.class);
    }
}
