package com.ayata.purvamart.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

public class RegisterDetail {
    @JsonAdapter(value = RDetailJsonAdapter.class)
    @SerializedName("email")
    @Expose
    private RDetail email = null;

    @Expose
    private String username = null;

    @JsonAdapter(value = RDetailJsonAdapter.class)
    @SerializedName("mobile_number")
    @Expose
    private RDetail mobileNumber = null;
    @SerializedName("token")
    @Expose
    private String token = null;
    @SerializedName("otp_code")
    @Expose
    private String otpCode = null;
    @SerializedName("password")
    @Expose
    private String password = null;
    @SerializedName("confirm_password")
    @Expose
    private String confirmPassword = null;

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


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public RDetail getEmail() {
        return email;
    }

    public void setEmail(RDetail email) {
        this.email = email;
    }

    public RDetail getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(RDetail mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String toString() {
        return "RegisterDetail{" +
                "email='" + email + '\'' + ", mobileNumber='" + mobileNumber;
    }
}