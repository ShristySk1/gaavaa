package com.ayata.purvamart.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserCartResponse extends BaseResponse<List<ProductDetail>> {
    @SerializedName("grand_total")
    @Expose
    private Integer grandTotal;
    public Integer getGrandTotal() {
        return grandTotal;
    }
    public void setGrandTotal(Integer grandTotal) {
        this.grandTotal = grandTotal;
    }

}