package com.example.kolibreath.onit.Utils;

import android.app.Application;

/**
 * Created by kolibreath on 2017/2/11.
 */

public class App extends Application {

    public static String storedUsername;
    public static String storedUserToken;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String setUserText(){
        return storedUsername;
    }
    public void getUserToken(String response){
        storedUserToken = response;
    }
    public String setUserToken(){
        return  storedUserToken;
    }

}
