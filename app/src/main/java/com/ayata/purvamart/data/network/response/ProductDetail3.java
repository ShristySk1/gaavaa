package com.ayata.purvamart.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetail3 {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("product_weight")
    @Expose
    private Object productWeight;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("warning")
    @Expose
    private String warning;
    @SerializedName("visibility")
    @Expose
    private Boolean visibility;
    @SerializedName("reference_code")
    @Expose
    private Object referenceCode;
    @SerializedName("barcode")
    @Expose
    private Object barcode;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("owner")
    @Expose
    private Object owner;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("seo_title")
    @Expose
    private Object seoTitle;
    @SerializedName("seo_description")
    @Expose
    private String seoDescription;
    @SerializedName("old_price")
    @Expose
    private Double oldPrice;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("vat")
    @Expose
    private Double vat;
    @SerializedName("vat_included")
    @Expose
    private Boolean vatIncluded;
    @SerializedName("vat_amount")
    @Expose
    private Double vatAmount;
    @SerializedName("discount_percent")
    @Expose
    private Double discountPercent;
    @SerializedName("discount_amount")
    @Expose
    private Double discountAmount;
    @SerializedName("grand_total")
    @Expose
    private Double grandTotal;
    @SerializedName("expire_on")
    @Expose
    private Object expireOn;
    @SerializedName("is_new")
    @Expose
    private Boolean isNew;
    @SerializedName("is_on_sale")
    @Expose
    private Boolean isOnSale;
    @SerializedName("is_coming_soon")
    @Expose
    private Boolean isComingSoon;
    @SerializedName("order_count")
    @Expose
    private Integer orderCount;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("priority")
    @Expose
    private Integer priority;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(Object productWeight) {
        this.productWeight = productWeight;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public Object getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(Object referenceCode) {
        this.referenceCode = referenceCode;
    }

    public Object getBarcode() {
        return barcode;
    }

    public void setBarcode(Object barcode) {
        this.barcode = barcode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Object getOwner() {
        return owner;
    }

    public void setOwner(Object owner) {
        this.owner = owner;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Object getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(Object seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Boolean getVatIncluded() {
        return vatIncluded;
    }

    public void setVatIncluded(Boolean vatIncluded) {
        this.vatIncluded = vatIncluded;
    }

    public Double getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(Double vatAmount) {
        this.vatAmount = vatAmount;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Object getExpireOn() {
        return expireOn;
    }

    public void setExpireOn(Object expireOn) {
        this.expireOn = expireOn;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Boolean getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Boolean isOnSale) {
        this.isOnSale = isOnSale;
    }

    public Boolean getIsComingSoon() {
        return isComingSoon;
    }

    public void setIsComingSoon(Boolean isComingSoon) {
        this.isComingSoon = isComingSoon;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

}
