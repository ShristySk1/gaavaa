package com.ayata.purvamart.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfirmCheckoutResponse {
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("merchant_id")
    @Expose
    private String merchantId;
    @SerializedName("merchant_secret")
    @Expose
    private String merchantSecret;
    @SerializedName("scd")
    @Expose
    private String scd;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantSecret() {
        return merchantSecret;
    }

    public void setMerchantSecret(String merchantSecret) {
        this.merchantSecret = merchantSecret;
    }

    public String getScd() {
        return scd;
    }

    public void setScd(String scd) {
        this.scd = scd;
    }

}
