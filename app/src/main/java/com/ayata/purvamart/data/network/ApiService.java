package com.ayata.purvamart.data.network;


import com.ayata.purvamart.Model.ModelAddress;
import com.ayata.purvamart.data.network.response.CategoryListResponse;
import com.ayata.purvamart.data.network.response.HomeResponse;
import com.ayata.purvamart.data.network.response.ProductListResponse;
import com.ayata.purvamart.data.network.response.ProductListResponse2;
import com.ayata.purvamart.data.network.response.ProfileDetail;
import com.ayata.purvamart.data.network.response.RegisterDetail;
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

    @GET("home/")
    Call<HomeResponse> getAllHome();

    /**
     * authentication needed
     */

    @POST("profile-edit/")
    Call<JsonObject> updateProfile(@Header("Authorization") String header, @Body ProfileDetail profileDetail);

    @GET("usercart-list/")
    Call<JsonObject> getMyOrder(@Header("Authorization") String header);

    @GET("myorders-list/")
    Call<RegisterResponse> getMyOrderList(@Header("Authorization") String header);

    //    @POST("checkout/")
//    @FormUrlEncoded
//    Call<JsonObject> addToOrder(@Header("Authorization") String header, @Field("products_id") List<ModelItem> modelItemList);
    @POST("checkout/")
    @FormUrlEncoded
    Call<JsonObject> addToOrder(@Header("Authorization") String header, @Field("products_id") String modelItemList);

    @POST("addproductto-cart/")
    @FormUrlEncoded
    Call<JsonObject> addToCart(@Header("Authorization") String header, @Field("product_id") Integer productId);

    @POST("addproductcount/")
    @FormUrlEncoded
    Call<JsonObject> addProductCount(@Header("Authorization") String header, @Field("products_id") Integer productId);

    @POST("removeproductcount/")
    @FormUrlEncoded
    Call<JsonObject> minusProductCount(@Header("Authorization") String header, @Field("products_id") Integer productId);

    @POST("add-address/")
    Call<JsonObject> addAddress(@Header("Authorization") String header, @Body ModelAddress modelAddress);

    @GET("get-address/")
    Call<JsonObject> getAddress(@Header("Authorization") String header);
}