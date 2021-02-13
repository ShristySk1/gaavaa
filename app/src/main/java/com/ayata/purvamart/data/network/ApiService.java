package com.ayata.purvamart.data.network;


import com.ayata.purvamart.data.Model.ModelAddress;
import com.ayata.purvamart.data.Model.ModelCategory;
import com.ayata.purvamart.data.Model.ModelRegister;
import com.ayata.purvamart.data.network.response.BaseResponse;
import com.ayata.purvamart.data.network.response.HomeDetail;
import com.ayata.purvamart.data.network.response.LoginDetail;
import com.ayata.purvamart.data.network.response.OrderSummaryDetail;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.data.network.response.ProfileDetail;
import com.ayata.purvamart.data.network.response.RegisterDetail;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("account/login/")
    @FormUrlEncoded
    Call<BaseResponse<LoginDetail>> loginUser(@Field("email") String phone_or_email, @Field("password") String password);

    @POST("account/register/")
    Call<BaseResponse<RegisterDetail>> registerUser(@Body ModelRegister details);

    @POST("account/verify/")
    @FormUrlEncoded
    Call<BaseResponse<String>> verifyOtp(@Field("otp") String otp);

    @GET("category-list/")
    Call<BaseResponse<List<ModelCategory>>> getCategoryList();

    @GET("products-list/")
    Call<BaseResponse<List<ProductDetail>>> getProductsList();

    @GET("home/")
    Call<BaseResponse<List<HomeDetail>>> getAllHome();

    /**
     * authentication needed
     */

    @POST("profile-edit/")
    Call<JsonObject> updateProfile(@Body ProfileDetail profileDetail);

    @GET("usercart-list/")
    Call<JsonObject> getMyOrder();

//    @GET("myorders-list/")
//    Call<RegisterResponse> getMyOrderList();

    @POST("checkout/")
    @FormUrlEncoded
    Call<JsonObject> addToOrder(@Field("products_id") String modelItemList);

    @GET("order-summary")
    Call<BaseResponse<List<OrderSummaryDetail>>> getOrderSummary(@Query("order_id") String orderId,
                                                                 @Query("address_id") String addressId,
                                                                 @Query("gateway") String gateway);

    @POST("addproductto-cart/")
    @FormUrlEncoded
    Call<JsonObject> addToCart(@Field("product_id") Integer productId);

    @POST("addproductcount/")
    @FormUrlEncoded
    Call<JsonObject> addProductCount(@Field("products_id") Integer productId);

    @POST("removeproductcount/")
    @FormUrlEncoded
    Call<JsonObject> minusProductCount(@Field("products_id") Integer productId);

    @POST("add-address/")
    Call<JsonObject> addAddress(@Body ModelAddress modelAddress);

    @POST("update-address/")
    Call<JsonObject> updateAddress(@Body ModelAddress modelAddress);

    @GET("get-address/")
    Call<JsonObject> getAddress();

    @GET("recent-orders/")
    Call<JsonObject> getRecentOrder();

    @POST("cancelorder/")
    @FormUrlEncoded
    Call<JsonObject> cancelOrder(@Field("order_id") String orderId);

    @POST("confirm-checkout/")
    @FormUrlEncoded
    Call<JsonObject> confirmOrder(
            @Field("order_id") String orderId,
            @Field("gateway") String gateway,
            @Field("address_id") String addressId);
}