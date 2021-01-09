package com.ayata.purvamart.data.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelAddress implements Serializable {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("postal_code")
    @Expose
    private String postalCode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("street_name")
    @Expose
    private String streetName;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("latitide")
    @Expose
    private Object latitide;
    @SerializedName("longitude")
    @Expose
    private Object longitude;

    public ModelAddress(String contactNumber, String postalCode, String city, String name, String streetName, String country) {
        this.contactNumber = contactNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.name = name;
        this.streetName = streetName;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Object getLatitide() {
        return latitide;
    }

    public void setLatitide(Object latitide) {
        this.latitide = latitide;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

}