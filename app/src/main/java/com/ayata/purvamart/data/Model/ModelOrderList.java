package com.ayata.purvamart.data.Model;

import com.ayata.purvamart.data.network.response.ProductDetail;

import java.io.Serializable;
import java.util.List;

public class ModelOrderList implements Serializable {

    private String image;
    private String order_id;
    private String date;
    private String time;
    private String delivery_date;
    private List<ProductDetail> productDetails;

    public List<ProductDetail> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetail> productDetails) {
        this.productDetails = productDetails;
    }

    public ModelOrderList(String image, String order_id, String date, String time, String delivery_date,List<ProductDetail> productDetails) {
        this.image = image;
        this.order_id = order_id;
        this.date = date;
        this.time = time;
        this.delivery_date = delivery_date;
        this.productDetails=productDetails;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }
}
