package com.example.kolibreath.onit.Generics;

/**
 * Created by kolibreath on 2017/2/12.
 */

public class OtherUserInfo {
        private String dongtaiContent;
        private String dongtaiStartTime;
        private String dongtaiDeadline;
        private int favorNumber;
        private int commentsNumber;

        public OtherUserInfo(String dongtaiContent,String dongtaiStartTime,
                                    String dongtaiDeadline,int favorNumber,
                                    int commentsNumber){
            this.dongtaiContent = dongtaiContent;
            this.dongtaiStartTime = dongtaiStartTime;
            this.dongtaiDeadline = dongtaiDeadline;
            this.favorNumber = favorNumber;
            this.commentsNumber = commentsNumber;
        }
        public String getDongtaiContent(){
            return dongtaiContent;
        }
        public String getDongtaiStartTime(){
            return dongtaiStartTime;
        }
        public String getDongtaiDeadline(){
            return dongtaiDeadline;
        }
        public int getFavorNumber(){
            return  favorNumber;
        }
        public int getCommentsNumber(){
            return commentsNumber;
        }
    }
