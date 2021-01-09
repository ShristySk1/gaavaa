package com.ayata.purvamart.data.Model;

public class ModelAd {

    private String info;
    private String title;
    private int image;

    public ModelAd(String info, String title, int image) {
        this.info = info;
        this.title = title;
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
