package com.ayata.purvamart.ui.Fragment.shop.search;

public class ModelSearchBefore {

    enum MODELTYPE {
        TITLE, PRODUCT,CATEGORY;
    }

    public ModelSearchBefore(String title, String image, MODELTYPE type) {
        this.title = title;
        this.image = image;
        this.type = type;
    }

    private String title;
    private String image;
    MODELTYPE type;

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

    public MODELTYPE getType() {
        return type;
    }

    public void setType(MODELTYPE type) {
        this.type = type;
    }
}
