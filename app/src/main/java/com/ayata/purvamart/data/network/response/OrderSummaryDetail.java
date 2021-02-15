package com.ayata.purvamart.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderSummaryDetail implements Serializable {

    @SerializedName("product")
    @Expose
    private List<ProductDetail> product = null;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;
    @SerializedName("address")
    @Expose
    private OrderSummaryAddress address;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;

    public List<ProductDetail> getProduct() {
        return product;
    }

    public void setProduct(List<ProductDetail> product) {
        this.product = product;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public OrderSummaryAddress getAddress() {
        return address;
    }

    public void setAddress(OrderSummaryAddress address) {
        this.address = address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;

    }
}
