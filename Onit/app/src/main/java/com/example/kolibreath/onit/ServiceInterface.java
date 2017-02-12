package com.example.kolibreath.onit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by kolibreath on 2017/2/11.
 */

public interface ServiceInterface {
    @GET("friends_timeline")
    Call<List<FriendsDongtaiBean>> getFriends();
}
