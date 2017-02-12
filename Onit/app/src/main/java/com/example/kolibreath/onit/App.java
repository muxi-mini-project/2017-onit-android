package com.example.kolibreath.onit;

import android.app.Application;

/**
 * Created by kolibreath on 2017/2/11.
 */

public class App extends Application {
    public String storedUsername;

    public void getUserText(String edittext){
        storedUsername = edittext;
    }
    public String setUserText(){
        return storedUsername;
    }
}
