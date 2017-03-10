package com.example.kolibreath.onit.Generics;

import java.util.Comparator;

/**
 * Created by kolibreath on 2017/2/18.
 */

public class OwnOnlineDongtai implements Comparator {
    private int dongtaiStatus;
    private String startTime;
    private int commentsNumber;
    private int favorNumber;
    private String onlineContent;
    private String deadline;
    private int id;

    public OwnOnlineDongtai(int dongtaiStatus,String  startTime,int commentsNumber,int favorNumber,
                            String content,String deadline,int id){
        this.dongtaiStatus = dongtaiStatus;
        this.startTime = startTime;
        this.commentsNumber = commentsNumber;
        this.favorNumber = favorNumber;
        onlineContent = content;
        this.deadline = deadline;
        this.id = id;
    }

    public OwnOnlineDongtai(){}

    public String getDeadline(){
        return deadline;
    }
    public int getDongtaiStatus(){
        return dongtaiStatus;
    }
    public String getStartTime(){
        return startTime;
    }
    public int getCommentsNumber(){
        return commentsNumber;
    }
    public int getFavorNumber(){
        return favorNumber;
    }
    public String getOnlineContent(){
        return onlineContent;
    }
    public int getId(){
        return id;
    }

    @Override
    public int compare(Object o1, Object o2) {
        OwnOnlineDongtai own1 = (OwnOnlineDongtai)o1;
        OwnOnlineDongtai own2 = (OwnOnlineDongtai)o2;
        int id1 = own1.getId();
        int id2 = own2.getId();
        int flag=0;
        if(id1>id2){
            flag = -1;
        }
        if(id1==id2){
            flag =0;
        }
        if(id1<id2){
            flag = 1;
        }
        return flag;
    }
}
