package com.ayata.purvamart.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyCart {
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_quantity")
    @Expose
    private Integer productQuantity=null;

    public MyCart(Integer productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public MyCart() {
    }

    public MyCart(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

}