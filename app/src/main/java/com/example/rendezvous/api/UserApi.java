package com.example.rendezvous.api;

import com.example.rendezvous.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {
    @GET("user/get")
    Call<List<User>> getAllUser();

    @POST("user/save")
    Call<User> saveUser(@Body User user);
    @POST("user/login")
    Call<User> authenticateUser(@Body User user);
}
