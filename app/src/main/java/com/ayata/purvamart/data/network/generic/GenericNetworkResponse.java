package com.ayata.purvamart.data.network.generic;

import android.util.Log;

import com.ayata.purvamart.data.network.exception.NoConnectivityException;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;

public class GenericNetworkResponse<ResponseType> implements Callback<ResponseType> {

    private WeakReference<NetworkResponseListener<ResponseType>> listener;

    public GenericNetworkResponse(NetworkResponseListener<ResponseType> listener) {
        this.listener = new WeakReference<>(listener);
        listener.onLoading();
    }

    @Override
    public void onResponse(Call<ResponseType> call, retrofit2.Response<ResponseType> response) {
        Log.d("generalresponse", "onResponse:received ");
        if (listener != null && listener.get() != null) {
            if (response.isSuccessful() && response != null) {
                listener.get().onResponseReceived(response.body());
            } else {
                try {
//                    listener.get().onError(new JSONObject(response.message()).getString("message"));
                    listener.get().onError(response.message());
                } catch (Exception e) {
                    listener.get().onError(e.getMessage());
                }
            }
        }
    }

    @Override
    public void onFailure(@NonNull Call<ResponseType> call, @NonNull Throwable t) {
        if (t instanceof NoConnectivityException) {
            listener.get().onError("No Internet Connection");
        } else {
            if (listener != null && listener.get() != null) {
                listener.get().onError(t.getMessage());
            }
        }
    }
}