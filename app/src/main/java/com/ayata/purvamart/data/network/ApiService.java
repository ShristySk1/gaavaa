package com.ayata.purvamart.data.network;


import com.ayata.purvamart.data.network.response.CategoryListResponse;
import com.ayata.purvamart.data.network.response.Details;
import com.ayata.purvamart.data.network.response.RegisterResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("account/login/")
    @FormUrlEncoded
    Call<JsonObject> loginUser(@Field("phone_no") String phone_no, @Field("password") String password);

    @POST("account/register/")
    Call<RegisterResponse> registerUser(@Body Details details);

    @POST("account/verify/")
    @FormUrlEncoded
    Call<JsonObject> verifyOtp(@Header("Authorization") String header, @Field("otp") String otp);

    @GET("category-list/")
    Call<CategoryListResponse> getCategoryList();
}