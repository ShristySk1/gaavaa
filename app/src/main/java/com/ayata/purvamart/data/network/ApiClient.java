package com.ayata.purvamart.data.network;

import android.content.Context;

import com.ayata.purvamart.data.network.interceptor.NetworkConnectionInterceptor;
import com.ayata.purvamart.data.network.interceptor.OkHttpHeaderInterceptor;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit instance creation for api calls
 */
public class ApiClient {
    public static final String BASE_URL = "http://142.93.221.85/api/v1/";
    private static Retrofit retrofit = null;
    private static WeakReference<Context> context;

    public ApiClient(WeakReference<Context> context) {
        this.context = context;
    }

    public static Retrofit getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new NetworkConnectionInterceptor(context))
                .addInterceptor(new OkHttpHeaderInterceptor(context))
                .readTimeout(300, TimeUnit.SECONDS)
                .connectTimeout(300, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService() {
        return ApiClient.getClient().create(ApiService.class);
    }
}
