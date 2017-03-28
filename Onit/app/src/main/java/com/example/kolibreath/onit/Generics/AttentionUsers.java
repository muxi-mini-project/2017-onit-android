package com.example.kolibreath.onit.Generics;

import java.util.List;

/**
 * Created by kolibreath on 2017/3/28.
 */

public class AttentionUsers {
    private List<Integer> UserList;
    public AttentionUsers(List<Integer> UserList){
        this.UserList = UserList;
    }
    public List getUserList(){
        return UserList;
    }
}
