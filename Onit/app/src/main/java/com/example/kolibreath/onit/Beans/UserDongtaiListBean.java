package com.example.kolibreath.onit.Beans;

import java.util.List;

/**
 * Created by kolibreath on 2017/3/28.
 */

//这个bean用于储存用户及其关注的人的任务id
public class UserDongtaiListBean {

    private List<Integer> results;

    public List<Integer> getResults() {
        return results;
    }

    public void setResults(List<Integer> results) {
        this.results = results;
    }
}
