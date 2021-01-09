package com.ayata.purvamart.data.Model;

public class ModelPayment {
    String payment_name;
    int payment_image;

    public ModelPayment(String payment_name) {
        this.payment_name = payment_name;
    }

    public ModelPayment(String payment_name, int payment_image) {
        this.payment_name = payment_name;
        this.payment_image = payment_image;
    }

    public int getPayment_image() {
        return payment_image;
    }

    public void setPayment_image(int payment_image) {
        this.payment_image = payment_image;
    }

    public String getPayment_name() {
        return payment_name;
    }

    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }
}
