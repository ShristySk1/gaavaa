package com.ayata.purvamart.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelCategory implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer category_id;
    @SerializedName("title")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("is_selected")
    @Expose
    private Boolean isSelected=null;

    public ModelCategory(Integer category_id, String name, String image, Boolean isSelected) {
        this.category_id = category_id;
        this.name = name;
        this.image = image;
        this.isSelected = isSelected;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return  image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
