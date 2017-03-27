package com.example.kolibreath.onit.Generics;

/**
 * Created by kolibreath on 2017/3/27.
 *
 */

//found user is used for display the result after researching
public class FoundUser {
    private String userName;
    //this "level" is used to set ranks;
    private int level;
    public FoundUser(String userName,int level){
        this.userName  = userName;
        this.level = level;
    }
    public String getUserName(){
        return userName;
    }
    public int getLevel(){
        return level;
    }
}
