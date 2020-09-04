package com.ayata.purvamart.Model;

import java.io.Serializable;

public class ModelItem implements Serializable {

    private String name, price, prev_price, discount_percent, quantity;
    private int image;
    private Boolean discount;
    private Double totalPrice;

    public ModelItem(String name, String price, String prev_price, int image,String quantity, Boolean discount, String discount_percent) {
        this.name = name;
        this.price = price;
        this.prev_price = prev_price;
        this.image = image;
        this.discount_percent = discount_percent;
        this.quantity = quantity;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrev_price() {
        return prev_price;
    }

    public void setPrev_price(String prev_price) {
        this.prev_price = prev_price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDiscount_percent() {
        return discount_percent;
    }

    public void setDiscount_percent(String discount_percent) {
        this.discount_percent = discount_percent;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Boolean getDiscount() {
        return discount;
    }

    public void setDiscount(Boolean discount) {
        this.discount = discount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
