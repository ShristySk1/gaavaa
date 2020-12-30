package com.ayata.purvamart.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//public class OrderDetail {
//    @SerializedName("price")
//    @Expose
//    private Double price;
//    @SerializedName("product_id")
//    @Expose
//    private Integer productId;
//    @SerializedName("created_date")
//    @Expose
//    private String createdDate;
//    @SerializedName("product_quantity")
//    @Expose
//    private Integer productQuantity;
//    @SerializedName("is_ordered")
//    @Expose
//    private Boolean isOrdered;
//    @SerializedName("is_taken")
//    @Expose
//    private Boolean isTaken;
//    @SerializedName("total_price")
//    @Expose
//    private Double totalPrice;
//
//    public Double getPrice() {
//        return price;
//    }
//
//    public void setPrice(Double price) {
//        this.price = price;
//    }
//
//    public Integer getProductId() {
//        return productId;
//    }
//
//    public void setProductId(Integer productId) {
//        this.productId = productId;
//    }
//
//    public String getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(String createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public Integer getProductQuantity() {
//        return productQuantity;
//    }
//
//    public void setProductQuantity(Integer productQuantity) {
//        this.productQuantity = productQuantity;
//    }
//
//    public Boolean getIsOrdered() {
//        return isOrdered;
//    }
//
//    public void setIsOrdered(Boolean isOrdered) {
//        this.isOrdered = isOrdered;
//    }
//
//    public Boolean getIsTaken() {
//        return isTaken;
//    }
//
//    public void setIsTaken(Boolean isTaken) {
//        this.isTaken = isTaken;
//    }
//
//    public Double getTotalPrice() {
//        return totalPrice;
//    }
//    public void setTotalPrice(Double totalPrice) {
//        this.totalPrice = totalPrice;
//    }
//}
public class UserCartDetail {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_discount")
    @Expose
    private String productDiscount;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("image")
    @Expose
    private List<String> image = null;
    @SerializedName("product_quantity")
    @Expose
    private Integer productQuantity;
    @SerializedName("is_ordered")
    @Expose
    private Boolean isOrdered;
    @SerializedName("is_taken")
    @Expose
    private Boolean isTaken;
    @SerializedName("total_price")
    @Expose
    private Double totalPrice;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        this.productDiscount = productDiscount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Boolean getIsOrdered() {
        return isOrdered;
    }

    public void setIsOrdered(Boolean isOrdered) {
        this.isOrdered = isOrdered;
    }

    public Boolean getIsTaken() {
        return isTaken;
    }

    public void setIsTaken(Boolean isTaken) {
        this.isTaken = isTaken;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
