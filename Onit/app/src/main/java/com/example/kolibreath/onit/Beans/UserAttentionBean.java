package com.example.kolibreath.onit.Beans;

import java.util.List;

/**
 * Created by kolibreath on 2017/3/28.
 */

//存储用户关注的对象的列表
public class UserAttentionBean {

    private List<Integer> users_ids;

    public List<Integer> getUsers_ids() {
        return users_ids;
    }

    public void setUsers_ids(List<Integer> users_ids) {
        this.users_ids = users_ids;
    }
}
