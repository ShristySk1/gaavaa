package com.ayata.purvamart.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisterDetailError {
    @SerializedName("email")
    @Expose
    private List<String> email = null;
    @SerializedName("username")
    @Expose
    private List<String> username = null;
    @SerializedName("mobile_number")
    @Expose
    private List<String> mobileNumber = null;

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getUsername() {
        return username;
    }

    public void setUsername(List<String> username) {
        this.username = username;
    }

    public List<String> getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(List<String> mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String toString() {
        return "DetailsError{" +
                "email=" + email +
                ", username=" + username +
                ", mobileNumber=" + mobileNumber +
                '}';
    }
}
