package com.example.kolibreath.onit.InterfaceAdapter;

import com.example.kolibreath.onit.Beans.FriendsDongtaiBean;
import com.example.kolibreath.onit.Beans.LoginUserBean;
import com.example.kolibreath.onit.Beans.RegisterBean;
import com.example.kolibreath.onit.Beans.UserProfileBean;
import com.example.kolibreath.onit.Generics.LoginUser;
import com.example.kolibreath.onit.Generics.RegisterUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by kolibreath on 2017/2/11.
 */

public interface ServiceInterface {
    @GET("friends_timeline")
    Call<List<FriendsDongtaiBean>> getFriends();

    @POST("api/signup/")
    Call<RegisterBean> getRegisterInfo(@Body RegisterUser user);

    @GET("api/user/show_profile/")
    Call<UserProfileBean> getProfile(@Query("username")String username
    , @Header("token")String token);

    //注解后的内容是键名
    @POST("api/signin/")
    Call<LoginUserBean> getUserToken(@Body LoginUser user);


}
