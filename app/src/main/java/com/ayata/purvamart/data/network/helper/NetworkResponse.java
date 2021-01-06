package com.ayata.purvamart.data.network.helper;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;

public class NetworkResponse<ResponseType> implements Callback<ResponseType> {

    private WeakReference<NetworkResponseListener<ResponseType>> listener;

    public NetworkResponse(NetworkResponseListener<ResponseType> listener) {
        this.listener = new WeakReference<>(listener);
        listener.onLoading();
    }

    @Override
    public void onResponse(Call<ResponseType> call, retrofit2.Response<ResponseType> response) {

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
        if (listener != null && listener.get() != null) {
            listener.get().onError(t.getMessage());
        }
    }
}