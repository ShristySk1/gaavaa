package com.ayata.purvamart.data.network.helper;

import com.google.gson.JsonObject;

public interface NetworkResponseListener<ResponseType> {
    void onResponseReceived(ResponseType response);
    void onLoading();

    void onError(String message);
}
