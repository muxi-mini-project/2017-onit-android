package com.example.kolibreath.onit.Beans;

/**
 * Created by kolibreath on 2017/2/13.
 */

public class UserProfileBean {

    private int finished_task_num;
    private int followeds_count;
    private int followers_coumnt;
    private int level;
    private int role_id;
    private int uid;
    private int unfinished_task_num;
    private String username;

    public int getFinished_task_num() {
        return finished_task_num;
    }

    public void setFinished_task_num(int finished_task_num) {
        this.finished_task_num = finished_task_num;
    }

    public int getFolloweds_count() {
        return followeds_count;
    }

    public void setFolloweds_count(int followeds_count) {
        this.followeds_count = followeds_count;
    }

    public int getFollowers_coumnt() {
        return followers_coumnt;
    }

    public void setFollowers_coumnt(int followers_coumnt) {
        this.followers_coumnt = followers_coumnt;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUnfinished_task_num() {
        return unfinished_task_num;
    }

    public void setUnfinished_task_num(int unfinished_task_num) {
        this.unfinished_task_num = unfinished_task_num;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
