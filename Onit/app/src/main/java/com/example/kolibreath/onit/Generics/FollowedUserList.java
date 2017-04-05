package com.example.kolibreath.onit.Generics;

import java.util.List;

/**
 * Created by kolibreath on 2017/4/5.
 */

public class FollowedUserList {
    private String username;
    private List<Integer> matchedId;

    public FollowedUserList(String username, List<Integer> matchedId){
        this.username = username;
        this.matchedId = matchedId;
    }

    public String getUsername(){
        return  username;
    }
    public List<Integer> getMatchedId(){
        return matchedId;
    }
}
