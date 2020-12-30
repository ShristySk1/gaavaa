package com.ayata.purvamart.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileDetail {
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("address_line1")
    @Expose
    private String addressLine1;
    @SerializedName("contact_no1")
    @Expose
    private String contactNo1;
    @SerializedName("billing_addr")
    @Expose
    private Boolean billingAddr;
    @SerializedName("shipping_addr")
    @Expose
    private Boolean shippingAddr;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getContactNo1() {
        return contactNo1;
    }

    public void setContactNo1(String contactNo1) {
        this.contactNo1 = contactNo1;
    }

    public Boolean getBillingAddr() {
        return billingAddr;
    }

    public void setBillingAddr(Boolean billingAddr) {
        this.billingAddr = billingAddr;
    }

    public Boolean getShippingAddr() {
        return shippingAddr;
    }

    public void setShippingAddr(Boolean shippingAddr) {
        this.shippingAddr = shippingAddr;
    }

}
