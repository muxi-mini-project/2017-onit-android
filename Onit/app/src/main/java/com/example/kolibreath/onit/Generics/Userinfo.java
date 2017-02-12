package com.example.kolibreath.onit.Generics;

/**
 * Created by kolibreath on 2017/2/4.
 */

public class Userinfo {
    public String username;
    public int userAvatar;
    public String dongtaitime;
    public String content;
    public String favorNumber;
    public String commentsNumber;
    public String dongtaiDeadline;
    public int userOnitStatus;

    public Userinfo(int userAvatar,String dongtaitime,
                    String content,String commentsNumber,
                    String favorNumber,String dongtaiDeadline,
                    int userOnitStatus,String username){
        this.username = username;
        this.userAvatar = userAvatar;
        this.dongtaitime = dongtaitime;
        this.content = content;
        this.commentsNumber = commentsNumber;
        this.favorNumber = favorNumber;
        this.dongtaiDeadline = dongtaiDeadline;
        this.userOnitStatus = userOnitStatus;
    }
    public String getUsername(){
        return  username;
    }
    public int getUserAvatar(){
        return userAvatar;
    }
    public String getDongtaitime(){
        return dongtaitime;
    }
    public String getContent(){
        return content;
    }
    public String getCommentsNumber(){
        return commentsNumber;
    }
    public String getDongtaiDeadline(){
        return dongtaiDeadline;
    }
    public String getFavorNumber(){
        return favorNumber;
    }
    public int getUserOnitStatus(){return  userOnitStatus;}

}
