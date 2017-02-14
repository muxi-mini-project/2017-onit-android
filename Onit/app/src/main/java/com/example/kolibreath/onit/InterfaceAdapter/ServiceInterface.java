package com.example.kolibreath.onit.InterfaceAdapter;

import com.example.kolibreath.onit.Beans.DongtaiSendBean;
import com.example.kolibreath.onit.Beans.FriendsDongtaiBean;
import com.example.kolibreath.onit.Beans.LoginUserBean;
import com.example.kolibreath.onit.Beans.RegisterBean;
import com.example.kolibreath.onit.Beans.UserProfileBean;
import com.example.kolibreath.onit.Generics.LoginUser;
import com.example.kolibreath.onit.Generics.RegisterUser;
import com.example.kolibreath.onit.Generics.UserDongtaiContent;

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

    //注解后的内容是键名，token不用用户重新登录而发布消息只要用户登录一次就可以实现记住功能；
    //登录header也是post中的内容
    @GET("api/user/show_profile/")
    Call<UserProfileBean> getProfile(@Query("username")String username
    , @Header("token")String token);

    @POST("api/signin/")
    Call<LoginUserBean> getUserToken(@Body LoginUser user);

    @POST("api/task/update_task/")
    Call<DongtaiSendBean> sendUserDongtai(@Query("username")String username,@Body UserDongtaiContent content,
                                          @Header("token")String token);


}
