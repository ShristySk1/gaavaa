package com.ayata.purvamart.data.network.helper;

public interface NetworkResponseListener<ResponseType> {
    void onResponseReceived(ResponseType response);

    void onLoading();

    void onError(String message);
}
