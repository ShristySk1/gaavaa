package com.ayata.purvamart.Model;

public class ModelOrderTrack {
    //    enum OrderTrackType{
//        OUT_FOR_DELIVERY,SHIPPED,PROCESSED,CONFIRMED,PLACED,
//    }
//    private OrderTrackType orderTrackType;
    private String orderTrackType;
    private String orderTrackDescription;
    private Boolean isCompleted;
    private Boolean isBeingProcessed;
    private Boolean isStillLeft;

    public ModelOrderTrack(String orderTrackType, String orderTrackDescription, Boolean isCompleted, Boolean isBeingProcessed, Boolean isStillLeft) {
        this.orderTrackType = orderTrackType;
        this.orderTrackDescription = orderTrackDescription;
        this.isCompleted = isCompleted;
        this.isBeingProcessed = isBeingProcessed;
        this.isStillLeft = isStillLeft;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Boolean getBeingProcessed() {
        return isBeingProcessed;
    }

    public void setBeingProcessed(Boolean beingProcessed) {
        isBeingProcessed = beingProcessed;
    }

    public Boolean getStillLeft() {
        return isStillLeft;
    }

    public void setStillLeft(Boolean stillLeft) {
        isStillLeft = stillLeft;
    }

    public String getOrderTrackType() {
        return orderTrackType;
    }

    public void setOrderTrackType(String orderTrackType) {
        this.orderTrackType = orderTrackType;
    }

    public String getOrderTrackDescription() {
        return orderTrackDescription;
    }

    public void setOrderTrackDescription(String orderTrackDescription) {
        this.orderTrackDescription = orderTrackDescription;
    }
}
