package com.example.kolibreath.onit.InterfaceAdapter;

import com.example.kolibreath.onit.Beans.AttentionBean;
import com.example.kolibreath.onit.Beans.DongtaiSendBean;
import com.example.kolibreath.onit.Beans.FriendsBean;
import com.example.kolibreath.onit.Beans.IdBean;
import com.example.kolibreath.onit.Beans.LoginUserBean;
import com.example.kolibreath.onit.Beans.RegisterBean;
import com.example.kolibreath.onit.Beans.SingleDongtaiBean;
import com.example.kolibreath.onit.Beans.UserAttentionBean;
import com.example.kolibreath.onit.Beans.UserProfileBean;
import com.example.kolibreath.onit.Generics.LoginUser;
import com.example.kolibreath.onit.Generics.RegisterUser;
import com.example.kolibreath.onit.Generics.UserDongtaiContent;
import com.example.kolibreath.onit.Generics.voidClass;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by kolibreath on 2017/2/11.
 */

public interface ServiceInterface {

    //获取用户关注的用户任务的id
    //暂时设置一些关注“假”关注用户

    //在OnitMainActivity界面显示用户所关注的用户的列表
    @GET("/api/friendship/user_following/")
    Call<UserAttentionBean> getUserAttentionList(@Query("username") String username);
    //取消关注某一个用户 貌似目前只能在关注页面确定是不是取消关注这个用户
    @DELETE("/api/friendship/destroy_friendship/")
    Call<voidClass> cancelUserAttension(@Query("username") String username);

    //关注一个用户 用关注一个用户于在搜索界面点击关注之后
    @GET("/api/friendship/create_friendship/")
    Call<voidClass> getUserAttention(@Query("username")String username,
                                     @Query("current_user")String current_user,
                                     @Query("token") String token);

    //用户的好友关注接口
    @GET("/api/friendship/user_following/")
    Call<AttentionBean> getAttentionUser(@Query("username")String username,
                                         @Header("token")String token);

    //用户搜索好友接口
    @GET("/api/user/search_user/")
    Call<FriendsBean> getFriendsInfo(@Query("username")String username,
                                     @Header("token")String token);
    //用户任务删除接口
    @DELETE("/api/task/delete_task/")
    Call<voidClass> deleteDongtai(@Query("id") int id,
                                  @Header("token") String token);
    //可以获得一个动态id的list
    @GET("api/task/user_timeline/")
    Call<IdBean> getDongtaiId(@Query("username")String username,
                                   @Header("token") String token);
    //获取id所对应的单条任务
    @GET("api/task/get_task/")
    Call<SingleDongtaiBean> getSingleDongtai(@Query("username")String username,
                                             @Query("id")int id,
                                             @Header("token")String token);


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
