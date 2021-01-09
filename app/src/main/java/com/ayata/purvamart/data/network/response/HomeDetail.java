package com.ayata.purvamart.data.network.response;

import com.ayata.purvamart.data.Model.ModelCategory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeDetail {
    @SerializedName("product_for_you")
    @Expose
    private List<ProductDetail> productForYou = null;
    @SerializedName("sliders")
    @Expose
    private List<Slider> sliders=null;
    @SerializedName("category")
    @Expose
    private List<ModelCategory> category=null;

    public List<ProductDetail> getProductForYou() {
        return productForYou;
    }

    public void setProductForYou(List<ProductDetail> productForYou) {
        this.productForYou = productForYou;
    }

    public List<Slider> getSliders() {
        return sliders;
    }

    public void setSliders(List<Slider> sliders) {
        this.sliders = sliders;
    }

    public List<ModelCategory> getCategory() {
        return category;
    }

    public void setCategory(List<ModelCategory> category) {
        this.category = category;
    }

}
