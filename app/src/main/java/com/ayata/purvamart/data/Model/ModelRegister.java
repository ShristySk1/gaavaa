package com.ayata.purvamart.data.Model;

import com.ayata.purvamart.data.network.response.RDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelRegister {
    @SerializedName("email")
    @Expose
    private String email = null;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber = null;
    @SerializedName("password")
    @Expose
    private String password = null;
    @SerializedName("confirm_password")
    @Expose
    private String confirmPassword = null;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

