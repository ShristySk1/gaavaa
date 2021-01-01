package com.ayata.purvamart.data.network.response;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductDetail implements Serializable {
//
//    @SerializedName("id")
//    @Expose
//    private Integer id;
//    @SerializedName("name")
//    @Expose
//    private String name;
//    @SerializedName("product_category")
//    @Expose
//    private String productCategory;
//    @SerializedName("product_image")
//    @Expose
//    private List<String> productImage = null;
//    private String image;
//    @SerializedName("product_likes")
//    @Expose
//    private Integer productLikes;
//    @SerializedName("quantity")
//    @Expose
//    private String quantity;
//    @SerializedName("description")
//    @Expose
//    private String description;
//    @SerializedName("product_discount")
//    @Expose
//    private String productDiscount;
//    @SerializedName("old_price")
//    @Expose
//    private Double oldPrice;
//    @SerializedName("product_price")
//    @Expose
//    private Double productPrice;
//    @SerializedName("unit")
//    @Expose
//    private String unit;
//
//    private Boolean isDiscounted;
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
//
//    public String getUnit() {
//        return unit;
//    }
//
//    public void setUnit(String unit) {
//        this.unit = unit;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getProductCategory() {
//        return productCategory;
//    }
//
//    public void setProductCategory(String productCategory) {
//        this.productCategory = productCategory;
//    }
//
//    public Boolean getDiscounted() {
//        return isDiscounted;
//    }
//
//    public void setDiscounted(Boolean discounted) {
//        isDiscounted = discounted;
//    }
//
//    public ProductDetail(String name, String productCategory, List<String> productImage, String quantity, String description, String productDiscount, Double oldPrice, Double productPrice, Integer productLikes) {
//        this.name = name;
//        this.productCategory = productCategory;
//        this.unit = quantity;
//        this.description = description;
//        this.productImage = productImage;
//        this.productDiscount = productDiscount;
//        this.oldPrice = oldPrice;
//        this.productPrice = productPrice;
//        this.productLikes = productLikes;
//        if (!productDiscount.equals("0.0%") | !productDiscount.equals("0.0")) {
//            setDiscounted(false);
//        } else {
//            Log.d("checkdiscount2", "getDiscounted: " + productDiscount);
//            setDiscounted(true);
//        }
//    }
//
//    public String getProductImage() {
//        if (productImage.size() > 0)
//            return productImage.get(0);
//        else
//            return "";
//    }
//
//    public void setProductImage(List<String> productImage) {
//        this.productImage = productImage;
//    }
//
//    public Integer getProductLikes() {
//        return productLikes;
//    }
//
//    public void setProductLikes(Integer productLikes) {
//        this.productLikes = productLikes;
//    }
//
//    public String getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(String quantity) {
//        this.quantity = quantity;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getProductDiscount() {
//        return productDiscount;
//    }
//
//    public void setProductDiscount(String productDiscount) {
//        this.productDiscount = productDiscount;
//    }
//
//    public Double getOldPrice() {
//        return oldPrice;
//    }
//
//    public void setOldPrice(Double oldPrice) {
//        this.oldPrice = oldPrice;
//    }
//
//    public Double getProductPrice() {
//        return productPrice;
//    }
//
//    public void setProductPrice(Double productPrice) {
//        this.productPrice = productPrice;
//    }

//    public Double getWithDiscount() {
//        return withDiscount;
//    }
//
//    public void setWithDiscount(Double withDiscount) {
//        this.withDiscount = withDiscount;
//    }

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
    private String quantity;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("from")
    @Expose
    private String from;

    @SerializedName("product_discount")
    @Expose
    private String productDiscount;
    @SerializedName("old_price")
    @Expose
    private Double oldPrice;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("product_price")
    @Expose
    private Double productPrice;

    public String getFrom() {
        return from;
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
}
