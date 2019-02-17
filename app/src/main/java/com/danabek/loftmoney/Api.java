package com.danabek.loftmoney;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("auth")
    Call<AuthResponse> auth(@Query("social_user_id")String userID);

    @GET("items")
    Call<List<ItemPosition>> getItems(@Query("type")String type, @Query("auth-token")String token);

//    auth?social_user_id=<user_id>
}
