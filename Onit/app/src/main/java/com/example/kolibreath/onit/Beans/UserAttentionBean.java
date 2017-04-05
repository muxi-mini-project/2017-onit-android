package com.example.kolibreath.onit.Beans;

import java.util.List;

/**
 * Created by kolibreath on 2017/3/28.
 */

//存储用户关注的对象的列表
public class UserAttentionBean {

    private List<Integer> user_ids;

    public List<Integer> getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(List<Integer> user_ids) {
        this.user_ids = user_ids;
    }
}
