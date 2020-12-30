package com.ayata.purvamart.data.network;


import com.ayata.purvamart.data.network.response.CategoryListResponse;
import com.ayata.purvamart.data.network.response.ProductListResponse;
import com.ayata.purvamart.data.network.response.ProductListResponse2;
import com.ayata.purvamart.data.network.response.ProfileDetail;
import com.ayata.purvamart.data.network.response.RegisterDetail;
import com.ayata.purvamart.data.network.response.RegisterResponse;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @POST("account/login/")
    @FormUrlEncoded
    Call<JsonObject> loginUser(@Field("mobile_number") String phone_no, @Field("password") String password);

    @POST("account/register/")
    Call<RegisterResponse> registerUser(@Body RegisterDetail details);

    @POST("account/verify/")
    @FormUrlEncoded
    Call<JsonObject> verifyOtp(@Header("Authorization") String header, @Field("otp") String otp);

    @GET("category-list/")
    Call<CategoryListResponse> getCategoryList();

    @GET("products-list/")
    Call<ProductListResponse> getProductsList();

    @POST("category-list/")
    @FormUrlEncoded
    Call<ProductListResponse2> getProductsListSpecific(@Field("category_title") String category_title);

    @POST("profile-edit/")
    Call<JsonObject> updateProfile(@Header("Authorization") String header, @Body ProfileDetail profileDetail);

    @GET("usercart-list/")
    Call<JsonObject> getMyOrder(@Header("Authorization") String header);

    @POST("addto-cart/")
    Call<JsonObject> addToCart(@Header("Authorization") String header,@Header("Content-Type") String content_type, @Body List<MyCart> myCarts);


}