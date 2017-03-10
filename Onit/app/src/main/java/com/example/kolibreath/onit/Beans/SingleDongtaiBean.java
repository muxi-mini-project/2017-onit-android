package com.example.kolibreath.onit.Beans;

/**
 * Created by kolibreath on 2017/2/14.
 */

public class SingleDongtaiBean {

    /**
     * comments_count : 0
     * created_at : 2017/02/18
     * deadline : 2017/2/25
     * id : 12
     * status : n
     * text : this is a test ybao1
     * uid : 9
     */

    private int comments_count;
    private String created_at;
    private String deadline;
    private int id;
    private String status;
    private String text;
    private int uid;

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
