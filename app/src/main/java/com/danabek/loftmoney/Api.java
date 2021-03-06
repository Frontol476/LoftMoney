package com.danabek.loftmoney;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("auth")
    Call<AuthResponse> auth(@Query("social_user_id") String userID);

    @GET("items")
    Call<List<ItemPosition>> getItems(@Query("type") String type, @Query("auth-token") String token);

    @POST("items/add")
    Call<Object> addItem(@Body AddItemRequest request, @Query("auth-token") String token);

    @POST("items/remove")
    Call<Object> removeItem(@Query("id")Long id,@Query("auth-token") String token );

}
