package com.example.kolibreath.onit.Generics;

/**
 * Created by kolibreath on 2017/2/12.
 */

public class CommentClass {
    private String commentUserName;
    private String commentContents;
    private String commentTime;
    private int commentuserAvatar;

    public CommentClass(String commentUserName,String commentContents,
                        String commentTime,int commentuserAvatar){
        this.commentUserName= commentUserName;
        this.commentContents = commentContents;
        this.commentTime =commentTime;
        this.commentuserAvatar = commentuserAvatar;
    }
    public String getCommentUserName(){
        return commentUserName;
    }
    public String getCommentContents(){
        return commentContents;
    }
    public String getCommentTime(){
        return commentTime;
    }
    public int getCommentuserAvatar(){
        return commentuserAvatar;
    }
}
