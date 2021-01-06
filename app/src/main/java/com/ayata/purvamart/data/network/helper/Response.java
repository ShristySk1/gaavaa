package com.ayata.purvamart.data.network.helper;

import androidx.annotation.Nullable;
import okhttp3.ResponseBody;

public final class Response<T> {
    private final @Nullable T body=null;
    private final @Nullable ResponseBody errorBody = null;

    public @Nullable
    T body() {
        return body;
    }

    public @Nullable
    ResponseBody errorBody() {
        return errorBody;
    }

}