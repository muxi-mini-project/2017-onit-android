package com.example.kolibreath.onit;

import android.app.Application;

/**
 * Created by kolibreath on 2017/2/11.
 */

public class App extends Application {
    public String storedUsername;

    public String storedUserToken;

    public void getUserText(String edittext){
        storedUsername = edittext;
    }
    public String setUserText(){
        return storedUsername;
    }
    public void getUserToken(String response){storedUserToken = response;}
    public String setUserToken(){return  storedUserToken;}
}
