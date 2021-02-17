package com.ayata.purvamart.data.Model;

public class Stories {
    String imageUrl;
    String title;

    public Stories(String imageUrl,String title) {
        this.title=title;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
