package com.ayata.purvamart.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductDetail implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("product_category")
    @Expose
    private Integer productCategory;
    @SerializedName("product_image")
    @Expose
    private List<String> productImage = null;
    private String image;
    @SerializedName("product_likes")
    @Expose
    private Integer productLikes;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("product_discount")
    @Expose
    private String productDiscount;
    @SerializedName("old_price")
    @Expose
    private Double oldPrice;
    @SerializedName("product_price")
    @Expose
    private Double productPrice;
    @SerializedName("unit")
    @Expose
    private String unit;

    private Boolean isDiscounted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Integer getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Integer productCategory) {
        this.productCategory = productCategory;
    }

    public Boolean getDiscounted() {
        if (!productDiscount.equals("0.00%")) return false;
        else return true;
    }

    public void setDiscounted(Boolean discounted) {
        isDiscounted = discounted;
    }

    public ProductDetail(String name, Integer productCategory, List<String> productImage, String quantity, String description, String productDiscount, Double oldPrice, Double productPrice) {
        this.name = name;
        this.productCategory = productCategory;
        this.unit = quantity;
        this.description = description;
        this.productImage = productImage;
        this.productDiscount = productDiscount;
        this.oldPrice = oldPrice;
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        if (productImage.get(0).contains("http://")) {
            return productImage.get(0);
        }
        return "http://" + productImage.get(0);
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
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

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

//    public Double getWithDiscount() {
//        return withDiscount;
//    }
//
//    public void setWithDiscount(Double withDiscount) {
//        this.withDiscount = withDiscount;
//    }

}
