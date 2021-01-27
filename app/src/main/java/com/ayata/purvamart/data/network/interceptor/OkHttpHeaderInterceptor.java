package com.ayata.purvamart.data.network.interceptor;

import android.content.Context;
import android.util.Log;

import com.ayata.purvamart.data.preference.PreferenceHandler;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * intercepts header for auth token
 */
public class OkHttpHeaderInterceptor implements Interceptor {
    private WeakReference<Context> mContext;

    public OkHttpHeaderInterceptor(WeakReference<Context> mContext) {
        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String login = "/login/";
        String register = "/register/";
        String categoryList = "/category-list/";
        String productList = "/products-list/";
        String home = "/home/";
        if ((originalRequest.url().toString().contains(home)) |
                (originalRequest.url().toString().contains(login)) |
                register.contains(originalRequest.url().toString()) |
                categoryList.contains(originalRequest.url().toString()) |
                productList.contains(originalRequest.url().toString())) {
            Log.d("orifginal url", "intercept: " + originalRequest.url());
            return chain.proceed(originalRequest);
        }
        String token = PreferenceHandler.getToken(mContext.get());
        Request newRequest = originalRequest.newBuilder()
                .header("Authorization", token)
                .build();
        Log.d("orifginal url3", "intercept: " + newRequest);

        return chain.proceed(newRequest);
    }
}