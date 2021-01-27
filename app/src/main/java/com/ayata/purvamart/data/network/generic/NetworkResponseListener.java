package com.ayata.purvamart.data.network.generic;

public interface NetworkResponseListener<ResponseType> {
    void onResponseReceived(ResponseType response);
    void onLoading();

    void onError(String message);
}
