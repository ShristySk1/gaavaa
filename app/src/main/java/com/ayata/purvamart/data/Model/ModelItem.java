package com.ayata.purvamart.data.Model;

import java.io.Serializable;

public class ModelItem implements Serializable {
    private Integer id;
    private String name, price, prev_price, discount_percent, quantity;
    private String image;
    private Boolean discount;
    private int count;
    private String unit;
    //cart
    private Double basePrice;
    //ordersummary
    private String paymentType;


    public Double getBasePrice() {
        return basePrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    //        public ModelItem(String name, String price, String prev_price, int image, String quantity, Boolean discount, String discount_percent) {
//        this.name = name;
//        this.price = price;
//        this.prev_price = prev_price;
//        this.image = image;
//        this.discount_percent = discount_percent;
//        this.quantity = quantity;
//        this.basePrice=Double.valueOf(price);
//        this.discount = discount;
//    }
    public ModelItem(Integer id, String name, String price, String prev_price, String image, String quantity, Boolean discount, String discount_percent, int count, String unit) {
        this.name = name;
        this.price = price;
        this.id = id;
        this.prev_price = prev_price;
        this.image = image;
        this.discount_percent = discount_percent;
        this.quantity = quantity;
        this.basePrice = Double.valueOf(price);
        this.discount = discount;
        this.count = count;
        this.unit = unit;
    }

    public ModelItem(Integer id, String name, String price, String prev_price, String image, String quantity, Boolean discount, String discount_percent, int count, String unit, String paymentType) {
        this.name = name;
        this.price = price;
        this.id = id;
        this.prev_price = prev_price;
        this.image = image;
        this.discount_percent = discount_percent;
        this.quantity = quantity;
        this.basePrice = Double.valueOf(price);
        this.discount = discount;
        this.count = count;
        this.unit = unit;
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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
        if (discount_percent.equals("0.0%")) {
            return false;
        } else {
            return true;
        }
    }

    public void setDiscount(Boolean discount) {
        this.discount = discount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
