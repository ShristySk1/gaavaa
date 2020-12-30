package com.ayata.purvamart.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserCartResponse {
    //    @SerializedName("code")
//    @Expose
//    private Integer code;
//    @SerializedName("status")
//    @Expose
//    private String status;
//    @SerializedName("message")
//    @Expose
//    private String message;
//    @SerializedName("details")
//    @Expose
//    private List<OrderDetail> details = null;
//
//    public Integer getCode() {
//        return code;
//    }
//
//    public void setCode(Integer code) {
//        this.code = code;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public List<OrderDetail> getDetails() {
//        return details;
//    }
//
//    public void setDetails(List<OrderDetail> details) {
//        this.details = details;
//    }
//
//}
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private List<UserCartDetail> details = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserCartDetail> getDetails() {
        return details;
    }

    public void setDetails(List<UserCartDetail> details) {
        this.details = details;
    }
}