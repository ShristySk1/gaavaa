package com.ayata.purvamart.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetail2 {
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("category_title")
    @Expose
    private String categoryTitle;
    @SerializedName("category_image")
    @Expose
    private String categoryImage;
    @SerializedName("product_details")
    @Expose
    private List<ProductDetail3> productDetails = null;
    @SerializedName("product_image")
    @Expose
    private List<String> productImage = null;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public List<ProductDetail3> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetail3> productDetails) {
        this.productDetails = productDetails;
    }

    public String getProductImage() {
        return "http://142.93.221.85"+productImage.get(0);
    }

    public void setProductImage(List<String> productImage) {
        this.productImage = productImage;
    }

}
