package com.ayata.purvamart.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecentOrderDetails {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("items")
    @Expose
    private List<ProductDetail> items = null;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("estimated_date")
    @Expose
    private String estimatedDate;
    @SerializedName("conditional_status")
    @Expose
    private String conditional_status;
    public String getCreatedDate() {
        return createdDate;
    }

    public String getConditional_status() {
        return conditional_status;
    }

    public void setConditional_status(String conditional_status) {
        this.conditional_status = conditional_status;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getEstimatedDate() {
        return estimatedDate;
    }

    public void setEstimatedDate(String estimatedDate) {
        this.estimatedDate = estimatedDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<ProductDetail> getItems() {
        return items;
    }

    public void setItems(List<ProductDetail> items) {
        this.items = items;
    }

}