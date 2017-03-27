package com.example.kolibreath.onit.Utils;

/**
 * Created by kolibreath on 2017/3/27.
 */

public class RankLevel {

    public String rank(int level){
         String nameOfRank = "";
        switch (level){
            case 0:
                nameOfRank = "初涉江湖";
                break;
            case 1:
                nameOfRank = "渐入佳境";
                break;
            case 2:
                nameOfRank = "炉火纯青";
                break;
            case 3:
                nameOfRank = "登峰造极";
                break;
        }
        return nameOfRank;
    }
}
