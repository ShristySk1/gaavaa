package com.ayata.purvamart.Model;

import java.io.Serializable;

public class ModelCategory implements Serializable {

    private String category_id;
    private String name;
    private int image;
    private Boolean isSelected;

    public ModelCategory(String category_id, String name, int image, Boolean isSelected) {
        this.category_id = category_id;
        this.name = name;
        this.image = image;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
