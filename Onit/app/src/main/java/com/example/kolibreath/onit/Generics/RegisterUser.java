package com.example.kolibreath.onit.Generics;

/**
 * Created by kolibreath on 2017/2/13.
 */

public class RegisterUser {

    //两边的变量名需要相同
    public String username;
    public String password;
    public String userUid;

    public RegisterUser(String userName,String userPassword){
        this.username= userName;
        this.password = userPassword;
    }
}
