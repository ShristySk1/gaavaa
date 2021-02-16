package com.ayata.purvamart.data.Model;

public class ModelOrderTrack {

    public static final String ORDER_TYPE_PLACED = "Order Placed";
    public static final String ORDER_TYPE_CONFIRMED = "Order Confirmed";
    public static final String ORDER_TYPE_PROCESS = "Order Processed";
    public static final String ORDER_TYPE_SHIP = "Ready to Ship";
    public static final String ORDER_TYPE_DELIVERY = "Out For Delivery";
    public static final String ORDER_TYPE_DELIVERED = "Delivered";
    public static final String ORDER_TYPE_NONE = "None";


    private String orderTrackTitle;
    private String orderTrackDescription;
    private String ordertype;
    private int color;
    private Boolean isActual;

    public ModelOrderTrack(String orderTrackTitle, String orderTrackDescription, String ordertype, int color, Boolean isActual) {
        this.orderTrackTitle = orderTrackTitle;
        this.orderTrackDescription = orderTrackDescription;
        this.ordertype = ordertype;
        this.color = color;
        this.isActual = isActual;
    }

    public Boolean getActual() {
        return isActual;
    }

    public void setActual(Boolean actual) {
        isActual = actual;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
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