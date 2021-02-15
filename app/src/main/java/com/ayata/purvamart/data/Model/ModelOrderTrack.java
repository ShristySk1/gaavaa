package com.ayata.purvamart.data.Model;

public class ModelOrderTrack {

    public static final int ORDER_TYPE_PLACED = 0;
    public static final int ORDER_TYPE_CONFIRMED = 1;
    public static final int ORDER_TYPE_PROCESS = 2;
    public static final int ORDER_TYPE_SHIP = 3;
    public static final int ORDER_TYPE_DELIVERY = 4;
    public static final int ORDER_TYPE_DELIVERED = 5;
    public static final int ORDER_TYPE_NONE = 6;


    private String orderTrackTitle;
    private String orderTrackDescription;
    private int ordertype;

    public ModelOrderTrack(String orderTrackTitle, String orderTrackDescription, int ordertype) {
        this.orderTrackTitle = orderTrackTitle;
        this.orderTrackDescription = orderTrackDescription;
        this.ordertype = ordertype;
    }

    public int getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(int ordertype) {
        this.ordertype = ordertype;
    }

    public String getOrderTrackTitle() {
        return orderTrackTitle;
    }

    public void setOrderTrackTitle(String orderTrackTitle) {
        this.orderTrackTitle = orderTrackTitle;
    }

    public String getOrderTrackDescription() {
        return orderTrackDescription;
    }

    public void setOrderTrackDescription(String orderTrackDescription) {
        this.orderTrackDescription = orderTrackDescription;
    }

}