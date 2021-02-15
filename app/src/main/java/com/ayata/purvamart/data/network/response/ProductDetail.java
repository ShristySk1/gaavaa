package com.ayata.purvamart.data.network.response;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductDetail implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("category")
    @Expose
    private String title = null;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("product_image")
    @Expose
    private List<String> productImage = null;
    @SerializedName("product_likes")
    @Expose
    private Integer productLikes = null;
    @SerializedName("quantity")
    @Expose
    private String quantity=null;
    @SerializedName("description")
    @Expose
    private String description=null;
    @SerializedName("from")
    @Expose
    private String from;

    @SerializedName("product_discount")
    @Expose
    private String productDiscount=null;
    @SerializedName("old_price")
    @Expose
    private Double oldPrice=null;
    @SerializedName("unit")
    @Expose
    private String unit=null;
    @SerializedName("product_price")
    @Expose
    private Double productPrice=null;
    @SerializedName("is_ordered")
    @Expose
    private Boolean isOrdered = null;
    @SerializedName("is_taken")
    @Expose
    private Boolean isTaken = null;
    @SerializedName("is_cancelled")
    @Expose
    private Boolean isCancelled = null;
    @SerializedName("is_completed")
    @Expose
    private Boolean isCompleted = null;
    @SerializedName("created_date")
    @Expose
    private String createdDate = null;

    public String getFrom() {
        return from;
    }

    @SerializedName("product_quantity")
    @Expose
    private String product_quantity = null;

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    @SerializedName("total_price")
    @Expose
    private String total_price = null;

    @SerializedName("payment_type")

    public String getCreatedDate() {
        return createdDate;
    }


    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Boolean getDiscounted() {
        Log.d("checkdiscount", "getDiscounted: " + productDiscount);
        if (productDiscount.equals("0.0%")) {
            return false;
        } else {
            Log.d("checkdiscount2", "getDiscounted: " + productDiscount);
            return true;
        }
    }

    public Boolean getOrdered() {
        return isOrdered;
    }

    public void setOrdered(Boolean ordered) {
        isOrdered = ordered;
    }

    public Boolean getTaken() {
        return isTaken;
    }

    public void setTaken(Boolean taken) {
        isTaken = taken;
    }

    public Boolean getCancelled() {
        return isCancelled;
    }

    public void setCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getProductImage() {
        return productImage;
    }

    public void setProductImage(List<String> productImage) {
        this.productImage = productImage;
    }

    public Integer getProductLikes() {
        return productLikes;
    }

    public void setProductLikes(Integer productLikes) {
        this.productLikes = productLikes;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        this.productDiscount = productDiscount;
    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public ProductDetail(String name, String productCategory, List<String> productImage, String quantity, String description, String productDiscount, Double oldPrice, Double productPrice, Integer productLikes) {
        this.name = name;
        this.title = productCategory;
        this.unit = quantity;
        this.description = description;
        this.productImage = productImage;
        this.productDiscount = productDiscount;
        this.oldPrice = oldPrice;
        this.productPrice = productPrice;
        this.productLikes = productLikes;

    }

    public Boolean getDiscount() {
        if (productDiscount.equals("0.0%")) {
            return false;
        } else {
            return true;
        }
    }
}
