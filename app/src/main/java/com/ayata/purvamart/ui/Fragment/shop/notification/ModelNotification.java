package com.ayata.purvamart.ui.Fragment.shop.notification;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notification")
public class ModelNotification implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int image;
    private String title;
    private String desp;
    private String date;

    public ModelNotification(int image, String title, String desp, String date) {
        this.image = image;
        this.title = title;
        this.desp = desp;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

