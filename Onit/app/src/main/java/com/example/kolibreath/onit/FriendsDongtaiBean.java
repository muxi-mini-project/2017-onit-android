package com.example.kolibreath.onit;

/**
 * Created by kolibreath on 2017/2/10.
 */

public class FriendsDongtaiBean {

    /**
     * id : 1
     * text : 这是一条任务
     * comments_count : 1
     * attitudes_count : 1
     * starttime : 2017/2/1
     * deadline : 2017/2/2
     * user_id : 1
     * user_idstr : 第一个用户
     */

    private int id;
    private String text;
    private int comments_count;
    private int attitudes_count;
    private String starttime;
    private String deadline;
    private int user_id;
    private String user_idstr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getAttitudes_count() {
        return attitudes_count;
    }

    public void setAttitudes_count(int attitudes_count) {
        this.attitudes_count = attitudes_count;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_idstr() {
        return user_idstr;
    }

    public void setUser_idstr(String user_idstr) {
        this.user_idstr = user_idstr;
    }
}
